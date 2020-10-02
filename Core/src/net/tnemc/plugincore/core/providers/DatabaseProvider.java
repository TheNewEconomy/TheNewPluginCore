package net.tnemc.plugincore.core.providers;

import net.tnemc.plugincore.core.io.DatabaseConnector;

import java.util.Map;

public interface DatabaseProvider {

  /**
   * @return The unique identifiable name for this {@link DatabaseProvider provider}.
   */
  String type();

  /**
   * Used to get the {@link DatabaseConnector} associated with this {@link DatabaseProvider} for
   * performing database operations with.
   * @param configurations A map containing the configurations for the database.
   * @return The {@link DatabaseConnector connector} object.
   */
  DatabaseConnector connector(Map<String, Object> configurations);
}