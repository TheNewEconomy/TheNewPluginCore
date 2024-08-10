package net.tnemc.plugincore.core.module;

import com.vdurmont.semver4j.Semver;
import net.tnemc.plugincore.PluginCore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/*
 * The New Plugin Core
 * Copyright (C) 2022 - 2024 Daniel "creatorfromhell" Vidmar
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class ModuleLoader {

  private final Map<String, ModuleWrapper> modules = new HashMap<>();

  public boolean hasModule(String moduleName) {
    return modules.containsKey(moduleName);
  }

  public boolean hasModuleWithoutCase(String moduleName) {
    for (String key : modules.keySet()) {
      if(key.equalsIgnoreCase(moduleName)) return true;
    }
    return false;
  }

  public ModuleWrapper getModule(String name) {
    return modules.get(name);
  }

  public Map<String, ModuleWrapper> getModules() {
    return modules;
  }

  public void load() {
    final File directory = new File(PluginCore.directory(), "modules");

    if(directory.exists()) {

      final File[] jars = directory.listFiles((dir, name) -> name.endsWith(".jar"));

      if(jars != null) {
        for(File jar : jars) {

          try {
            final ModuleWrapper wrapper = loadModuleWrapper(jar.getAbsolutePath());

            if(wrapper.getModule() == null) {
              PluginCore.log().inform("Skipping file due to invalid module: " + jar.getName());
              continue;
            }

            if(jar.getName().contains("old-")) continue;

            if(!wrapper.getModule().getClass().isAnnotationPresent(ModuleInfo.class)) {
              PluginCore.log().inform("Invalid module format! ModuleOld File: " + jar.getName());
              continue;
            }

            wrapper.setInfo(wrapper.getModule().getClass().getAnnotation(ModuleInfo.class));
            PluginCore.log().inform("Found module: " + wrapper.name() + " version: " + wrapper.version());
            modules.put(wrapper.name(), wrapper);

            if (!wrapper.getInfo().updateURL().trim().equalsIgnoreCase("")) {
              PluginCore.log().inform("Checking for updates for module " + wrapper.info.name());
              ModuleUpdateChecker checker = new ModuleUpdateChecker(wrapper.info.name(), wrapper.info.updateURL(), wrapper.version());
              checker.check();
            }
          } catch(Exception ignore) {
            PluginCore.log().inform("Unable to load module file: " + jar.getName() + ". Are you sure it's up to date?");
          }
        }
      }
    } else {
      if(directory.mkdir()) {
        PluginCore.log().inform("Created module directory: " + directory.getAbsolutePath());
      }
    }
  }

  public boolean load(String moduleName) {
    final String path = findPath(moduleName);
    if(path != null) {
      try {
        final ModuleWrapper wrapper = loadModuleWrapper(path);
        if(!wrapper.getModule().getClass().isAnnotationPresent(ModuleInfo.class)) {
          PluginCore.log().inform("Invalid module format! ModuleOld File: " + moduleName);
          return false;
        }

        wrapper.setInfo(wrapper.getModule().getClass().getAnnotation(ModuleInfo.class));

        if(!wrapper.getInfo().pluginVersion().equalsIgnoreCase("0.0.0.0") &&
           new Semver(wrapper.info.pluginVersion()).isGreaterThan(PluginCore.engine().version())) {

          PluginCore.log().error("Unable to load module: " + wrapper.name() + " Requires a higher plugin version. Required version: " + PluginCore.engine().version());
          return false;
        }

        PluginCore.log().inform("Found module: " + wrapper.name() + " version: " + wrapper.version());
        modules.put(wrapper.name(), wrapper);

        /*if(!wrapper.getInfo().updateURL().trim().equalsIgnoreCase("")) {
          PluginCore.log().inform("Checking for updates for module " + moduleName);
          ModuleUpdateChecker checker = new ModuleUpdateChecker(moduleName, wrapper.info.updateURL(), wrapper.version());
          checker.check();
        }*/
        return true;
      } catch(Exception ignore) {
        PluginCore.log().inform("Unable to load module: " + moduleName + ". Are you sure it exists?");
      }
    }
    return false;
  }

  public void unload(String moduleName) {
    if(hasModule(moduleName)) {
      ModuleWrapper wrapper = getModule(moduleName);
      //TODO: Command and configuration unloading.
      wrapper.getModule().disable(PluginCore.instance());

      try {
        Field f = ClassLoader.class.getDeclaredField("classes");
        f.setAccessible(true);

        Vector<Class> classes =  (Vector<Class>) f.get(PluginCore.loader().getModule(moduleName).getLoader());
        for(Class clazz : classes) {
          PluginCore.log().debug("Loaded: " + clazz.getName());
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      wrapper.unload();
      wrapper.setModule(null);
      wrapper.setInfo(null);
      wrapper.setLoader(null);
      wrapper = null;
      System.gc();

      modules.remove(moduleName);
    }
  }

  protected String findPath(String moduleName) {
    final File directory = new File(PluginCore.directory(), "modules");
    final File[] jars = directory.listFiles((dir, name) -> name.endsWith(".jar"));

    if(jars != null) {
      for(File jar : jars) {
        if(jar.getAbsolutePath().toLowerCase().contains(moduleName.toLowerCase() + ".jar")) {
          return jar.getAbsolutePath();
        }
      }
    }
    return null;
  }

  private ModuleWrapper loadModuleWrapper(String modulePath) {
    ModuleWrapper wrapper;

    Module module = null;

    final File file = new File(modulePath);
    Class<? extends Module> moduleClass;

    URLClassLoader classLoader = null;
    try {
      classLoader = new URLClassLoader(new URL[]{ file.toURI().toURL() }, PluginCore.instance().getClass().getClassLoader());
      moduleClass = classLoader.loadClass(getModuleMain(new File(modulePath))).asSubclass(Module.class);
      module = moduleClass.newInstance();
    } catch (Exception ignore) {
      PluginCore.log().inform("Unable to locate module main class for file " + file.getName());
    }
    wrapper = new ModuleWrapper(module);
    wrapper.setLoader(classLoader);
    return wrapper;
  }

  public boolean downloadModule(String module) {
    if(modules.containsKey(module)) {
      try {
        final String fileURL = modules.get(module).info.updateURL();
        final URL url = new URL(fileURL);
        final HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        final int responseCode = httpConn.getResponseCode();
        if(responseCode == HttpURLConnection.HTTP_OK) {
          final String fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1, fileURL.length());

          final InputStream in = httpConn.getInputStream();
          final File file = new File(PluginCore.directory() + File.separator + "modules", fileName);

          if(file.exists()) {
            if(!file.renameTo(new File(PluginCore.directory() + File.separator + "modules", "outdated-" + fileName))) {
              return false;
            }
          }

          final FileOutputStream out = new FileOutputStream(file);

          int bytesRead = -1;
          final byte[] buffer = new byte[4096];
          while ((bytesRead = in.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);
          }

          out.close();
          in.close();
        }
      } catch(Exception ignore) {
        return false;
      }
      return true;
    }
    return false;
  }

  private String getModuleMain(File jarFile) {
    String main = "";
    JarFile jar = null;
    InputStream in = null;
    BufferedReader reader = null;

    try {
      jar = new JarFile(jarFile);
      final JarEntry infoFile = jar.getJarEntry("module.tne");

      if(infoFile == null) {
        PluginCore.log().inform("TNE encountered an error while loading a module! No module.tne file!");
        return "";
      }

      in = jar.getInputStream(infoFile);
      reader = new BufferedReader(new InputStreamReader(in));

      main = reader.readLine().split("=")[1].trim();
    } catch(IOException e) {
      PluginCore.log().debug(e.toString());
    } finally {
      if(jar != null) {
        try {
          jar.close();
        } catch(IOException e) {
          PluginCore.log().debug(e.toString());
        }
      }

      if(in != null) {
        try {
          in.close();
        } catch(IOException e) {
          PluginCore.log().debug(e.toString());
        }
      }

      if(reader != null) {
        try {
          reader.close();
        } catch(IOException e) {
          PluginCore.log().debug(e.toString());
        }
      }
    }
    return main;
  }
}