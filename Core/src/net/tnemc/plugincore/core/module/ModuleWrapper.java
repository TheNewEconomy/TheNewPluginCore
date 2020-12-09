package net.tnemc.plugincore.core.module;

import net.tnemc.plugincore.core.module.annotations.ModuleData;

import java.io.IOException;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;


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
public class ModuleWrapper {

  ModuleData data;
  Map<String, Boolean> depends = new HashMap<>();
  Module module;
  URLClassLoader loader;

  public ModuleWrapper(Module module) {
    this.module = module;
  }

  public void unload() {
    try {
      loader.close();
      loader = null;
      System.gc();
    } catch (IOException ignore) {
      System.out.println("Module " + data.name() + " unloaded incorrectly.");
    }
    data = null;
  }

  public String name() {
    if(data == null) return "unknown";
    return data.name();
  }

  public String author() {
    if(data == null) return "unknown";
    return data.author();
  }

  public String version() {
    if(data == null) return "unknown";
    return data.version();
  }

  public ModuleData getData() {
    return data;
  }

  public void setData(ModuleData data) {
    this.data = data;
  }

  public void addDepend(final String name, final boolean required) {
    depends.put(name, required);
  }

  public Map<String, Boolean> getDepends() {
    return depends;
  }

  public void setDepends(Map<String, Boolean> depends) {
    this.depends = depends;
  }

  public Module getModule() {
    return module;
  }

  public void setModule(Module module) {
    this.module = module;
  }

  public URLClassLoader getLoader() {
    return loader;
  }

  public void setLoader(URLClassLoader loader) {
    this.loader = loader;
  }
}