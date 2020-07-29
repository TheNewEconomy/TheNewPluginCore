package net.tnemc.plugincore.core.module;

import net.tnemc.plugincore.core.module.annotations.ModuleDepend;
import net.tnemc.plugincore.core.module.annotations.ModuleData;

public interface Module {

  /**
   * Called when the Module class is initialized to begin reading the {@link ModuleData} and
   * {@link ModuleDepend} annotations.
   */
  void initialize();

  /**
   * Called when the loading phase begins.
   */
  void load();

  /**
   * Called when this module's files are loaded into memory.
   */
  void initFiles();

  /**
   * Called when this module's configurations are loaded into memory.
   */
  void initConfigurations();

  /**
   * Enables the IO for this module.
   */
  void enableIO();

  /**
   * Called when this module's commands are loaded into memory.
   */
  void initCommands();

  /**
   * Called when this module's menus are loaded into memory.
   */
  void initMenus();

  /**
   * Called after the loading phase is complete.
   */
  void postLoad();

  /**
   * Adds this module's consumable listeners to the module manager.
   */
  void initConsumables();

  /**
   * Called when the unloading phase begins.
   */
  void unload();

  /**
   * Called when this module's menus are removed from memory.
   */
  void removeMenus();

  /**
   * Called when this module's commands are removed from memory.
   */
  void removeCommands();

  /**
   * Called before this module's IO is disabled.
   */
  void backupIO();

  /**
   * Disables the IO for this module.
   */
  void disableIO();

  /**
   * Called when this module's configurations are removed from memory.
   */
  void removeConfigurations();

  /**
   * Called when this module's files are removed from memory.
   */
  void removeFiles();
}