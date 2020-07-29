package net.tnemc.plugincore.core.providers;

public interface LogProvider {

  /**
   * Sends a debug message.
   * @param message The message to send.
   */
  void DEBUG(String message);

  /**
   * Sends a generic informative message.
   * @param message The message to send.
   */
  void INFO(String message);
}