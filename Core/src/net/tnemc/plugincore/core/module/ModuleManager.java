package net.tnemc.plugincore.core.module;

import net.tnemc.plugincore.PluginCore;

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
public class ModuleManager {

  private Map<String, ModuleWrapper> modules = new HashMap<>();

  private static ModuleManager instance;
  private PluginCore core;

  public ModuleManager(PluginCore core) {
    instance = this;

    this.core = core;
  }

  public static ModuleManager instance() {
    return instance;
  }

  public Map<String, ModuleWrapper> getModules() {
    return modules;
  }

  public void setModules(Map<String, ModuleWrapper> modules) {
    this.modules = modules;
  }

  public void addModule(final String module, final ModuleWrapper wrapper) {
    modules.put(module, wrapper);
  }

  public ModuleWrapper getModule(final String module) {
    return modules.get(module);
  }

  public void removeModule(final String module) {
    modules.remove(module);
  }

  public PluginCore getCore() {
    return core;
  }

  public void setCore(PluginCore core) {
    this.core = core;
  }
}