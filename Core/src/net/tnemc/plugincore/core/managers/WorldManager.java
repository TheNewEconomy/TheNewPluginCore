package net.tnemc.plugincore.core.managers;

import net.tnemc.plugincore.core.world.WorldConnectionProvider;

import java.util.HashMap;
import java.util.Map;

/**
 * The WorldManager class is used to store connections between various worlds. It allows you to directly
 * store what world name should be returned for each world name request, i.e. if you have a feature
 * that should link the worlds world_nether, world_example, secondary_world to world when requesting
 * the player's world name. It also allows you to register a world provider linked to a service name
 * which would let you to have multiple world connections based on service name, i.e. world_secondary
 * would be linked to "world" in the economy service, but have no link under the "statistics" service.
 */
public class WorldManager {

  private Map<String, WorldConnectionProvider> providers = new HashMap<>();

  private String defaultWorld = "world";

  public WorldManager(String defaultWorld) {
    this.defaultWorld = defaultWorld;
  }
}