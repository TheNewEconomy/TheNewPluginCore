package net.tnemc.plugincore.sponge.impl;
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
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.world.server.ServerWorld;

/**
 * SpongeWorldProvider
 *
 * @author creatorfromhell
 * @since 1.1.0.1
 */
public class SpongeWorldProvider implements WorldProvider {

  protected final ServerWorld world;

  public SpongeWorldProvider(final ServerWorld world) {
    this.world = world;
  }

  /**
   * Returns the name associated with this object.
   *
   * @return a string representing the name.
   */
  @Override
  public @NotNull String name() {

    return world.key().value();
  }

  /**
   * Returns the dimension of the world provider.
   *
   * @return a string representing the dimension of the world provider.
   */
  @Override
  public @NotNull String dimension() {

    return world.properties().name();
  }

  /**
   * Retrieves the phase of the moon.
   *
   * @return a string representing the phase of the moon.
   */
  @Override
  public @NotNull String moonPhase() {

    return "";
  }

  /**
   * Retrieves the current weather conditions.
   *
   * @return a string representing the current weather conditions.
   */
  @Override
  public @NotNull String weather() {

    return "";
  }

  /**
   * Retrieves the current world time.
   *
   * @return the current world time as a long value.
   */
  @Override
  public long worldTime() {

    return 0;
  }

  /**
   * Returns the current game time.
   *
   * @return the current game time as a long value.
   */
  @Override
  public long gameTime() {

    return 0;
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

    return null;
  }
}