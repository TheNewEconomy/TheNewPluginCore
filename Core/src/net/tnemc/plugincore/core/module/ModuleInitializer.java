package net.tnemc.plugincore.core.module;


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

/**
 * A class which is used to interface with the main {@link ModuleLoader} methods. These
 * are intended to be used by the implementation of TNPC to add extra loading/unloading calls.
 */
public interface ModuleInitializer {

  /**
   * This is called during the initialization phase. The module is not fully loaded, but
   * the information for the module is loaded.
   *
   * @param wrapper The instance of the {@link ModuleWrapper} object associated with this module.
   */
  void initialize(final ModuleWrapper wrapper);

  /**
   * This is called when the module is loaded.
   *
   * @param wrapper The instance of the {@link ModuleWrapper} object associated with this module.
   */
  void load(final ModuleWrapper wrapper);

  /**
   * This is called when the module is unloaded.
   *
   * @param wrapper The instance of the {@link ModuleWrapper} object associated with this module.
   */
  void unload(final ModuleWrapper wrapper);
}