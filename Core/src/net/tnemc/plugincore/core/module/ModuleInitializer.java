package net.tnemc.plugincore.core.module;

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