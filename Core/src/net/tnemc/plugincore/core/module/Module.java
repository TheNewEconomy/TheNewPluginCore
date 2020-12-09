package net.tnemc.plugincore.core.module;

import net.tnemc.plugincore.core.module.annotations.ModuleDepend;
import net.tnemc.plugincore.core.module.annotations.ModuleData;


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