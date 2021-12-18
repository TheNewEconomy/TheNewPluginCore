package net.tnemc.plugincore.core.module;

import net.tnemc.plugincore.core.module.annotations.ModuleData;
import net.tnemc.plugincore.core.module.annotations.ModuleDepend;
import net.tnemc.plugincore.core.providers.log.DebugLevel;
import net.tnemc.plugincore.core.utils.IOUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Vector;


/**
 * Created by creatorfromhell.
 *
 * The New Plugin Core Minecraft Server Plugin
 *
 * All rights reserved.
 *
 * Some Details about what is acceptable use of this software:
 *
 * This project accepts user contributions.
 *
 * Direct redistribution of this software is not allowed without written permission. However,
 * compiling this project into your software to utilize it as a library is acceptable as long
 * as it's not used for commercial purposes.
 *
 * Commercial usage is allowed if a commercial usage license is bought and verification of the
 * purchase is able to be provided by both parties.
 *
 * By contributing to this software you agree that your rights to your contribution are handed
 * over to the owner of the project.
 */
public class ModuleLoader {

  private Map<String, ModuleWrapper> initialized = new HashMap<>();

  final File directory;
  final ModuleInitializer initializer;

  public ModuleLoader(File directory, ModuleInitializer initializer) {
    this.directory = directory;
    this.initializer = initializer;
  }

  public boolean initialize(final String module) {
    final Optional<String> path = IOUtils.findFileInsensitive(module, directory);

    if(path.isPresent()) {

      final Optional<ModuleWrapper> wrapper = initializeWrapper(path.get());

      if(wrapper.isPresent()) {

        if(!wrapper.get().getModule().getClass().isAnnotationPresent(ModuleData.class)) {
          System.out.println("Unable to location ModuleData annotation in module: " + module);
          return false;
        }

        wrapper.get().setData(wrapper.get().getModule().getClass().getAnnotation(ModuleData.class));

        if(wrapper.get().getModule().getClass().isAnnotationPresent(ModuleDepend.class)) {

          for(ModuleDepend depend : wrapper.get().getModule().getClass().getAnnotationsByType(ModuleDepend.class)) {
            for(String name : depend.depends()) {
              wrapper.get().addDepend(name, depend.required());
            }
          }
        }

        System.out.println("Initialized Module: " + wrapper.get().name()
                               + " Version: " + wrapper.get().version());

        initializer.initialize(wrapper.get());
        initialized.put(wrapper.get().name(), wrapper.get());
        return true;
      }
    }
    return false;
  }

  private Optional<ModuleWrapper> initializeWrapper(final String path) {
    ModuleWrapper wrapper = null;

    Module module;

    final File moduleFile = new File(path);

    Class<? extends Module> moduleClass;

    ModuleClassLoader loader;

    try {
      loader = new ModuleClassLoader(moduleFile.toURI().toURL(), getClass().getClassLoader());

      final String[] main = new String[1];

      IOUtils.loadFileFromJar(new File(path), "module.info", (reader)->{
        try {

          if(reader.isPresent())
            main[0] = reader.get().readLine().split("=")[1].trim();

        } catch(IOException ignore) {
          System.out.println("Failed to read module.info file for module at: " + path);
        }
      });

      moduleClass = loader.loadClass(main[0]).asSubclass(Module.class);

      module = moduleClass.newInstance();

      wrapper = new ModuleWrapper(module);
      wrapper.setLoader(loader);
    } catch(MalformedURLException ignore) {

      System.out.println("Invalid path designated during module loading: " + path);
    } catch(ClassNotFoundException ignore) {
      System.out.println("Specified main class could not be located in module at: " + path);
    } catch(IllegalAccessException ignore) {
      System.out.println("Error while initializing module at: " + path);
    } catch(InstantiationException ignore) {
      System.out.println("Error while initializing module at: " + path);
    }

    return Optional.ofNullable(wrapper);
  }

  public boolean isLoaded(final String module) {
    return initialized.containsKey(module) || ModuleManager.instance().getModules().containsKey(module);
  }

  public boolean unload(final String module) {
    if(isLoaded(module)) {
      initialized.remove(module);
    }
    final ModuleWrapper wrapper = ModuleManager.instance().getModule(module);

    if(wrapper != null) {
      initializer.unload(wrapper);

      wrapper.getModule().unload();

      ModuleManager.instance().removeModule(module);

      try {
        Field f = ClassLoader.class.getDeclaredField("classes");
        f.setAccessible(true);

        Vector<Class> classes =  (Vector<Class>) f.get(wrapper.getLoader());
        for(Class clazz : classes) {
          ModuleManager.instance().getCore().log().inform("Loaded: " + clazz.getName(), DebugLevel.DETAILED);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }

      wrapper.unload();

      wrapper.setModule(null);
      wrapper.setData(null);
      wrapper.setLoader(null);
      wrapper.setDepends(null);


      System.gc();
    }
    return true;
  }
}