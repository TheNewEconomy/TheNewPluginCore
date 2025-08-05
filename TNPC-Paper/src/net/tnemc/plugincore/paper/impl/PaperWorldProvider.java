package net.tnemc.plugincore.paper.impl;
/*
 * The New Plugin Core
 * Copyright (C) 2022 - 2025 Daniel "creatorfromhell" Vidmar
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

import net.tnemc.plugincore.core.compatibility.ChunkProvider;
import net.tnemc.plugincore.core.compatibility.WorldProvider;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

/**
 * PaperWorldProvider
 *
 * @author creatorfromhell
 * @since 1.1.0.1
 */
public class PaperWorldProvider implements WorldProvider {

  private final World world;

  public PaperWorldProvider(final World world) {
    this.world = world;
  }

  /**
   * Returns the name associated with this object.
   *
   * @return a string representing the name.
   */
  @Override
  public @NotNull String name() {

    return world.getName();
  }

  /**
   * Returns the dimension of the world provider.
   *
   * @return a string representing the dimension of the world provider.
   */
  @Override
  public @NotNull String dimension() {

    return world.getEnvironment().name();
  }

  /**
   * Retrieves the phase of the moon.
   *
   * @return a string representing the phase of the moon.
   */
  @Override
  public @NotNull String moonPhase() {

    return world.getMoonPhase().name();
  }

  /**
   * Retrieves the current weather conditions.
   *
   * @return a string representing the current weather conditions.
   */
  @Override
  public @NotNull String weather() {

    if(world.isThundering()) {
      return "THUNDER";
    }

    if(world.hasStorm()) {
      return "DOWNFALL";
    }

    return "CLEAR";
  }

  /**
   * Retrieves the current world time.
   *
   * @return the current world time as a long value.
   */
  @Override
  public long worldTime() {

    return world.getTime();
  }

  /**
   * Returns the current game time.
   *
   * @return the current game time as a long value.
   */
  @Override
  public long gameTime() {

    return world.getGameTime();
  }

  /**
   * Retrieves a ChunkProvider object based on the provided coordinates.
   *
   * @param x The x-coordinate for the ChunkProvider.
   * @param z The z-coordinate for the ChunkProvider.
   *
   * @return A ChunkProvider object that handles chunk-related operations for the specified
   * coordinates.
   */
  @Override
  public ChunkProvider chunkProvider(final int x, final int z) {

    return new PaperChunkProvider(world.getChunkAt(x, z));
  }
}