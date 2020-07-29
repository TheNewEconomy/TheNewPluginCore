package net.tnemc.plugincore.core.world;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CoreWorldConnectionProvider implements WorldConnectionProvider {

  private Map<String, WorldEntry> connections = new HashMap<>();

  private String name;

  public CoreWorldConnectionProvider(String name) {
    this.name = name;
  }
  /**
   * @return The unique name tied to this WorldConnectionProvider.
   */
  @Override
  public String name() {
    return name;
  }

  /**
   * Used to add a connection between the specified world and another. This will allow the specified
   * world name to resolve to the specified connection world name.
   *
   * @param worldName The name of the world to link to another.
   * @param connection The name of the world to resolve to for this connection.
   */
  @Override
  public void addConnection(String worldName, String connection) {

  }

  /**
   * Used to add a connection between the specified world and another. This will allow the specified
   * world name to resolve to the specified connection world name for the specified connection type.
   *
   * @param worldName The name of the world to link to another.
   * @param connectionType The name of the connection type.
   * @param connection The name of the world to resolve to for this connection.
   */
  @Override
  public void addConnection(String worldName, String connectionType, String connection) {

  }

  /**
   * Used to remove the connection added for the specified world name.
   *
   * @param worldName The world name to remove the connection from.
   */
  @Override
  public void removeConnection(String worldName) {

  }

  /**
   * Used to remove all connections for the specified world link.
   *
   * @param connection The name of the world other worlds are linked to, which will no longer be
   * linked to any worlds.
   */
  @Override
  public void removeConnections(String connection) {

  }

  /**
   * Used to resolve the world name to its connection or itself if no connection exists.
   *
   * @param world The world to get the true name, with connection if possible.
   *
   * @return The world name if no connection, otherwise the name of the connection.
   */
  @Override
  public String resolveWorld(String world) {
    return null;
  }

  /**
   * Used to resolve the world name to its connection for a given connection type or itself if no
   * connection exists.
   *
   * @param world The world to get the true name, with connection if possible.
   * @param connectionType The name of the connection type.
   *
   * @return The world name if no connection, otherwise the name of the connection.
   */
  @Override
  public String resolveWorld(String world, String connectionType) {
    return null;
  }

  /**
   * Used to resolve the world name to its connection or itself if no connection exists based on a
   * player.
   *
   * @param player The player to use for getting the world connection.
   *
   * @return The world name if no connection, otherwise the name of the connection.
   */
  @Override
  public String resolveWorld(UUID player) {
    return null;
  }

  /**
   * Used to resolve the world name to its connection for a given connection type or itself if no
   * connection exists based on a player.
   *
   * @param player The player to use for getting the world connection.
   * @param connectionType The name of the connection type.
   *
   * @return The world name if no connection, otherwise the name of the connection.
   */
  @Override
  public String resolveWorld(UUID player, String connectionType) {
    return null;
  }
}