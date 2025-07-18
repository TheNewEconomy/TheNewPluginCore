package net.tnemc.plugincore.sponge.impl;

/*
 * The New Plugin Core
 * Copyright (C) 2022 - 2024 Daniel "creatorfromhell" Vidmar
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import net.tnemc.plugincore.PluginCore;
import net.tnemc.plugincore.core.compatibility.LogProvider;
import net.tnemc.plugincore.core.compatibility.log.DebugLevel;
import org.apache.logging.log4j.Logger;

/**
 * SpongeLogProvider
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public record SpongeLogProvider(Logger logger) implements LogProvider {

  /**
   * Sends an informative message, which doesn't contain an error or debug message.
   *
   * @param message The message to send.
   * @param level   The {@link DebugLevel} to log this message at.
   */
  @Override
  public void inform(final String message, final DebugLevel level) {

    if(level.compare(PluginCore.instance().getLevel())) {
      logger.info(message);
    }
  }

  /**
   * Sends a message related to debug purposes.
   *
   * @param message The message to send.
   * @param level   The {@link DebugLevel} to log this message at.
   */
  @Override
  public void debug(final String message, final DebugLevel level) {

    if(level.compare(PluginCore.instance().getLevel())) {
      logger.debug(message);
    }
  }

  /**
   * Sends a warning message.
   *
   * @param message The message to send.
   * @param level   The {@link DebugLevel} to log this message at.
   */
  @Override
  public void warning(final String message, final DebugLevel level) {

    if(level.compare(PluginCore.instance().getLevel())) {
      logger.warn(message);
    }
  }

  /**
   * Sends an error-related message.
   *
   * @param message The message to send.
   * @param level   The {@link DebugLevel} to log this message at.
   */
  @Override
  public void error(final String message, final DebugLevel level) {

    if(level.compare(PluginCore.instance().getLevel())) {
      logger.error(message);
    }
  }

  /**
   * Sends an error-related message.
   *
   * @param message   The message to send.
   * @param exception The error's {@link Exception}.
   * @param level     The {@link DebugLevel} to log this message at.
   */
  @Override
  public void error(final String message, final Exception exception, final DebugLevel level) {

    if(level.compare(PluginCore.instance().getLevel())) {
      logger.error("====== Exception Occurred ======");
      exception.printStackTrace();
      logger.error("====== Please report this to someone ======");
    }
  }
}