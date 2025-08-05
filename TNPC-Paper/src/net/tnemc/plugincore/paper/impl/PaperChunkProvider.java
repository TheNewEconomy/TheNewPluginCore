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

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import net.tnemc.item.providers.VersionUtil;
import net.tnemc.plugincore.core.compatibility.ChunkProvider;
import org.bukkit.Chunk;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.StructureType;
import org.bukkit.block.Biome;
import org.bukkit.generator.structure.Structure;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

/**
 * PaperChunkProvider
 *
 * @author creatorfromhell
 * @since 1.1.0.1
 */
public class PaperChunkProvider implements ChunkProvider {

  private final Chunk chunk;

  public PaperChunkProvider(final Chunk chunk) {

    this.chunk = chunk;
  }

  /**
   * This method is responsible for calculating and returning an integer value.
   *
   * @return The calculated integer value.
   */
  @Override
  public int x() {

    return chunk.getX();
  }

  /**
   * This method is responsible for returning a value of type int. The specific logic and
   * calculations performed to determine this value are not documented.
   *
   * @return The integer value calculated by the method.
   */
  @Override
  public int z() {

    return chunk.getZ();
  }

  /**
   * Checks if the ChunkProvider represents a slime chunk.
   *
   * @return True if the ChunkProvider is a slime chunk, false otherwise.
   */
  @Override
  public boolean isSlime() {

    return chunk.isSlimeChunk();
  }

  /**
   * Checks if the specified biome is contained within the chunk provider.
   *
   * @param biome The biome to check for.
   *
   * @return True if the specified biome is contained, false otherwise.
   */
  @SuppressWarnings({ "removal", "UnstableApiUsage" })
  @Override
  public boolean containsBiome(final @NotNull String biome) {

    try {

      final Registry<@NotNull Biome> registry = RegistryAccess.registryAccess().getRegistry(RegistryKey.BIOME);

      final NamespacedKey biomeKey = NamespacedKey.fromString(biome.toLowerCase(Locale.ROOT));
      if(biomeKey == null) {
        return false;
      }

      final Biome biomeObj = registry.get(biomeKey);
      if(biomeObj == null) {
        return false;
      }

      return chunk.contains(biomeObj);

    } catch(final NoSuchMethodError ignore) {

      return chunk.contains(Biome.valueOf(biome));
    }
  }

  /**
   * Checks if the provided structure is contained. Only on 1.21.1.
   *
   * @param structure The structure to check if contained.
   *
   * @return True if the structure is contained, false otherwise.
   */
  @Override
  public boolean containsStructure(final @NotNull String structure) {

    final Registry<@NotNull Structure> registry = RegistryAccess.registryAccess().getRegistry(RegistryKey.STRUCTURE);

    final NamespacedKey structureKey = NamespacedKey.fromString(structure.toLowerCase(Locale.ROOT));
    if(structureKey == null) {
      return false;
    }

    final Structure structureObj = registry.get(structureKey);
    if(structureObj == null) {
      return false;
    }

    return !chunk.getStructures(structureObj).isEmpty();
  }
}