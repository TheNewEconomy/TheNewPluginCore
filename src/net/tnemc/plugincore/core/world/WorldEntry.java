package net.tnemc.plugincore.core.world;

import java.util.HashMap;
import java.util.Map;

public class WorldEntry {

  private Map<String, String> worldTypes = new HashMap<>();

  private String name;

  public WorldEntry(String name, String connection) {
    this.name = name;
    addConnection("connection", connection);
  }

  /**
   * Used to add a connection to this world for the specified connection type.
   * @param connectionType The name of the connection type.
   * @param connection The name of the world to resolve to for this connection.
   */
  public void addConnection(String connectionType, String connection) {
    worldTypes.put(connectionType, connection);
  }

  /**
   * Used to resolve a world name to the specified connection type.
   * @param name The name of the world to resolve.
   * @param connectionType The name of the connection type to resolve to.
   * @return The resolves world name.
   */
  public String resolveWorld(String name, String connectionType) {
    return worldTypes.getOrDefault(connectionType, worldTypes.get("connection"));
  }
}