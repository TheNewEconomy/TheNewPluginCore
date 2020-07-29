package net.tnemc.plugincore.core.module.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ModuleData {

  /**
   * @return The module's name.
   */
  String name();

  /**
   * @return The module's author.
   */
  String author();

  /**
   * @return The module's version.
   */
  String version();

  /**
   * @return The module's URL for update checks.
   */
  String updateURL() default "";
}