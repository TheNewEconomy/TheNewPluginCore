package net.tnemc.plugincore;

import net.tnemc.plugincore.core.providers.DatabaseProvider;
import net.tnemc.plugincore.core.module.ModuleManager;
import net.tnemc.plugincore.core.providers.ColorProvider;
import net.tnemc.plugincore.core.providers.LogProvider;
import net.tnemc.plugincore.core.providers.MenuProvider;
import net.tnemc.plugincore.core.providers.UUIDProvider;

import java.util.Optional;
import java.util.regex.Pattern;

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
public interface PluginCore {

  Pattern USERNAME_PATTERN = Pattern.compile("^\\w*$");
  Pattern UUID_MATCHER_PATTERN = Pattern.compile("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})");

  /**
   * @return The {@link UUIDProvider}
   */
  UUIDProvider uuid();

  /**
   * @return The {@link DatabaseProvider}.
   */
  DatabaseProvider database();

  /**
   * @return The {@link MenuProvider}
   */
  MenuProvider menu();

  /**
   * @return The {@link LogProvider}
   */
  LogProvider log();

  /**
   * @return The {@link ColorProvider}
   */
  ColorProvider color();

  /**
   * @return The {@link ModuleManager}
   */
  default Optional<ModuleManager> moduleManager() {
    return Optional.empty();
  }

  /**
   * Checks if this plugin has modules enabled.
   * @return True if modules are enabled.
   */
  default boolean modulesEnabled() {
    return moduleManager().isPresent();
  }
}