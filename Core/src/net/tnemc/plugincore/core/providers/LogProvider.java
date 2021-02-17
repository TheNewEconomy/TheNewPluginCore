package net.tnemc.plugincore.core.providers;


import net.tnemc.plugincore.core.providers.log.DebugLevel;

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
public interface LogProvider {

  /**
   * Sends an informative message, which doesn't contain an error or debug message.
   * @param message The message to send.
   * @param level The {@link DebugLevel} to log this message at.
   */
  void inform(String message, DebugLevel level);

  /**
   * Sends a message related to debug purposes.
   * @param message The message to send.
   * @param level The {@link DebugLevel} to log this message at.
   */
  void debug(String message, DebugLevel level);

  /**
   * Sends an error-related message.
   * @param message The message to send.
   * @param level The {@link DebugLevel} to log this message at.
   */
  void error(String message, DebugLevel level);

  /**
   * Sends an error-related message.
   * @param message The message to send.
   * @param level The {@link DebugLevel} to log this message at.
   */
  void error(String message, Exception exception, DebugLevel level);
}