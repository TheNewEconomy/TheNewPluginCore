package net.tnemc.plugincore.core.compatibility;
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

import org.jetbrains.annotations.NotNull;

/**
 * WorldCompatibility
 *
 * @author creatorfromhell
 * @since 1.1.0.1
 */
public interface WorldProvider {

  /**
   * Returns the name associated with this object.
   *
   * @return a string representing the name.
   */
  @NotNull String name();

  /**
   * Returns the dimension of the world provider.
   *
   * @return a string representing the dimension of the world provider.
   */
  @NotNull String dimension();

  /**
   * Retrieves the spawn location.
   *
   * @return a Location object representing the spawn location.
   */
  @NotNull Location spawn();

  /**
   * Retrieves the phase of the moon.
   *
   * @return a string representing the phase of the moon.
   */
  @NotNull String moonPhase();

  /**
   * Retrieves the current weather conditions.
   *
   * @return a string representing the current weather conditions.
   */
  @NotNull String weather();

  /**
   * Retrieves the current world time.
   *
   * @return the current world time as a long value.
   */
  long worldTime();

  /**
   * Returns the current game time.
   *
   * @return the current game time as a long value.
   */
  long gameTime();
  /**
   * Retrieves a ChunkProvider object based on the provided coordinates.
   *
   * @param x The x-coordinate for the ChunkProvider.
   * @param y The y-coordinate for the ChunkProvider.
   * @param z The z-coordinate for the ChunkProvider.
   * @return A ChunkProvider object that handles chunk-related operations for the specified coordinates.
   */
  ChunkProvider chunkProvider(final int x, final int y, final int z);
}