package net.tnemc.plugincore.api.logging;

/*
 * The New Plugin Core
 * Copyright (C) 2022 - 2026 Daniel "creatorfromhell" Vidmar
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

/**
 * Provides a compatibility layer for logging purposes.
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public interface Logger {

  /**
   * Sends an informative message, which doesn't contain an error or debug message.
   *
   * @param message The message to send.
   */
  default void inform(final String message) {

    inform(message, DebugLevel.OFF);
  }

  /**
   * Sends an informative message, which doesn't contain an error or debug message.
   *
   * @param message The message to send.
   * @param level   The {@link DebugLevel} to log this message at.
   */
  void inform(String message, DebugLevel level);

  /**
   * Sends a message related to debug purposes.
   *
   * @param message The message to send.
   */
  default void debug(final String message) {

    debug(message, DebugLevel.STANDARD);
  }

  /**
   * Sends a message related to debug purposes.
   *
   * @param message The message to send.
   * @param level   The {@link DebugLevel} to log this message at.
   */
  void debug(String message, DebugLevel level);

  /**
   * Sends a warning message.
   *
   * @param message The message to send.
   */
  default void warning(final String message) {

    warning(message, DebugLevel.STANDARD);
  }

  /**
   * Sends a warning message.
   *
   * @param message The message to send.
   * @param level   The {@link DebugLevel} to log this message at.
   */
  void warning(String message, DebugLevel level);

  /**
   * Sends an error-related message.
   *
   * @param message The message to send.
   */
  default void error(final String message) {

    error(message, DebugLevel.STANDARD);
  }

  /**
   * Sends an error-related message.
   *
   * @param message The message to send.
   * @param level   The {@link DebugLevel} to log this message at.
   */
  void error(String message, DebugLevel level);

  /**
   * Sends an error-related message.
   *
   * @param message   The message to send.
   * @param throwable The error's {@link Throwable}.
   * @param level     The {@link DebugLevel} to log this message at.
   */
  void error(String message, Throwable throwable, DebugLevel level);


  /**
   * Sends an error that is SQL-related.
   *
   * @param message   The message to send.
   * @param throwable The error's {@link Throwable}.
   * @param query     The query string.
   * @param variables An array of variables for the prepared statement.
   * @param level     The {@link DebugLevel} to log this message at.
   */
  default void sqlError(final String message, final Throwable throwable, final String query, final Object[] variables, final DebugLevel level) {

    error("======= Query Error =======", level);
    error(message, throwable, level);

    error("======= Query Statement =======", level);
    error(query, level);
    error("======= Query Variables Statement =======", level);

    for(int i = 0; i < variables.length; i++) {
      error("Variable - " + variables[i], level);
    }
    error("======= End Query Statement =======", level);
  }
}