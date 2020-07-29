package net.tnemc.plugincore;

import net.tnemc.plugincore.core.module.ModuleManager;
import net.tnemc.plugincore.core.providers.ColorProvider;
import net.tnemc.plugincore.core.providers.LogProvider;
import net.tnemc.plugincore.core.providers.UUIDProvider;

import java.util.Optional;
import java.util.regex.Pattern;

public interface PluginCore {

  Pattern USERNAME_PATTERN = Pattern.compile("^\\w*$");
  Pattern UUID_MATCHER_PATTERN = Pattern.compile("");

  /**
   * @return The {@link UUIDProvider}
   */
  UUIDProvider uuid();

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
}