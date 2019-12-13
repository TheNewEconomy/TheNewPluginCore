package net.tnemc.plugincore.core.world;

import org.bukkit.entity.Player;

public interface WorldConnectionProvider {


  /**
   * @return The unique name tied to this WorldConnectionProvider.
   */
  String name();

  /**
   * Used to add a connection between the specified world and another. This will return the connection
   * parameter value when worldName is provided to the getWorld methods.
   * @param worldName The name of the world to link to another.
   * @param connection The world that the specified world name should be linked to.
   */
  void addConnection(String worldName, String connection);

  /**
   * Used to remove the connection added for the specified world name.
   * @param worldName The world name to remove the connection from.
   */
  void removeConnection(String worldName);

  /**
   * Used to remove all connections for the specified world link.
   * @param connection The name of the world other worlds are linked to, which will no longer be linked
   * to any worlds.
   */
  void removeConnections(String connection);

  /**
   * Used to get the world name, connection if exists, of the specified world
   * @param world The world to get the true name, with connection if possible.
   * @return The world name if no connection, otherwise the name of the connection.
   */
  String getWorld(String world);

  String getWorld(Player player);
}