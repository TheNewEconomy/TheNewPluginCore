package net.tnemc.plugincore.core.module;

import net.tnemc.plugincore.PluginCore;

import java.util.HashMap;
import java.util.Map;

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