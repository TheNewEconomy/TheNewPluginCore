package net.tnemc.plugincore.core.world;

import java.util.HashMap;
import java.util.Map;


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