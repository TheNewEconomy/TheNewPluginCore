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

import java.util.List;

/**
 * ChunkProvider
 *
 * @author creatorfromhell
 * @since 1.1.0.1
 */
public interface ChunkProvider {

  /**
   * This method is used to get the chunk's x coordinate.
   *
   * @return The chunk's x coordinate.
   */
  int x();

  /**
   * This method is used to get the chunk's y coordinate.
   *
   * @return The chunk's y coordinate.
   */
  default int y() {
    return 0;
  }

  /**
   * This method is responsible for returning a value of type int. The specific logic and calculations
   * performed to determine this value are not documented.
   *
   * @return The integer value calculated by the method.
   */
  int z();

  /**
   * Checks if the ChunkProvider represents a slime chunk.
   *
   * @return True if the ChunkProvider is a slime chunk, false otherwise.
   */
  boolean isSlime();

  /**
   * Checks if the specified biome is contained within the chunk provider.
   *
   * @param biome The biome to check for.
   * @return True if the specified biome is contained, false otherwise.
   */
  boolean containsBiome(final @NotNull String biome);

  /**
   * Checks if the provided list of biomes contains any of the specified biomes.
   *
   * @param biomes The list of biomes to check for.
   * @return True if any of the specified biomes are contained in the list, false otherwise.
   */
  default boolean containsBiomes(final @NotNull List<String> biomes) {

    for(final String biome : biomes) {
      if(!containsBiome(biome)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Checks if the provided structure is contained.
   *
   * @param structure The structure to check if contained.
   * @return True if the structure is contained, false otherwise.
   */
  boolean containsStructure(final @NotNull String structure);

  /**
   * Checks if the provided list of structures contains any structures.
   *
   * @param structures The list of structures to check for.
   * @return True if any of the structures are contained in the list, false otherwise.
   */
  default boolean containsStructures(final @NotNull List<String> structures) {

    for(final String structure : structures) {
      if(!containsStructure(structure)) {
        return false;
      }
    }
    return true;
  }
}