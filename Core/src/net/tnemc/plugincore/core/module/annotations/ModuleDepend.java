package net.tnemc.plugincore.core.module.annotations;

public @interface ModuleDepend {

  /**
   * The name of the dependency/dependencies.
   */
  String[] depends();

  /**
   * Whether this dependency entry is required or not. If not required
   * then the module can still load without it.
   */
  boolean required() default true;
}