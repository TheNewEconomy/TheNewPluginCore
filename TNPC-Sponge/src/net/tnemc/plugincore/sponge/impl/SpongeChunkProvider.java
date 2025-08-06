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
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.world.chunk.WorldChunk;

/**
 * SpongeChunkProvider
 *
 * @author creatorfromhell
 * @since 1.1.0.1
 */
public class SpongeChunkProvider implements ChunkProvider {

  protected final WorldChunk chunk;

  public SpongeChunkProvider(final WorldChunk chunk) {
    this.chunk = chunk;
  }

  /**
   * This method is used to get the chunk's x coordinate.
   *
   * @return The chunk's x coordinate.
   */
  @Override
  public int x() {

    return chunk.chunkPosition().x();
  }

  /**
   * This method is used to get the chunk's y coordinate.
   *
   * @return The chunk's y coordinate.
   */
  @Override
  public int y() {

    return chunk.chunkPosition().y();
  }

  /**
   * This method is used to get the chunk's z coordinate.
   *
   * @return The chunk's z coordinate.
   */
  @Override
  public int z() {

    return chunk.chunkPosition().z();
  }

  /**
   * Checks if the ChunkProvider represents a slime chunk.
   *
   * @return True if the ChunkProvider is a slime chunk, false otherwise.
   */
  @Override
  public boolean isSlime() {

    return false;
  }

  /**
   * Checks if the specified biome is contained within the chunk provider.
   *
   * @param biome The biome to check for.
   *
   * @return True if the specified biome is contained, false otherwise.
   */
  @Override
  public boolean containsBiome(final @NotNull String biome) {

    return false;
  }

  /**
   * Checks if the provided structure is contained.
   *
   * @param structure The structure to check if contained.
   *
   * @return True if the structure is contained, false otherwise.
   */
  @Override
  public boolean containsStructure(final @NotNull String structure) {

    return false;
  }
}
