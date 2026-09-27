package net.tnemc.plugincore.api.server.world;

/*
 * The New Plugin Core
 * Copyright (C) 2022 - 2026 Daniel "creatorfromhell" Vidmar
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

import java.util.Collection;
import java.util.Objects;

/**
 * ChunkProvider
 *
 * @author creatorfromhell
 * @since 1.1.0.1
 */
public interface ChunkProvider {

    /**
     * Retrieves the x-coordinate of the chunk represented by the ChunkProvider.
     *
     * @return The x-coordinate of the chunk as an integer.
     */
    int x();

    /**
     * Retrieves the z-coordinate of the chunk represented by the ChunkProvider.
     *
     * @return The z-coordinate of the chunk as an integer.
     */
    int z();

    /**
     * Checks if the current chunk is classified as a slime chunk.
     *
     * @return {@code true} if the chunk is a slime chunk, otherwise {@code false}.
     */
    boolean isSlime();

    /**
     * Checks if the specified biome is present.
     *
     * @param biome The name of the biome to check. Cannot be null.
     * @return {@code true} if the specified biome is present, otherwise {@code false}.
     * @throws NullPointerException if {@code biome} is null.
     */
    boolean containsBiome(String biome);

    /**
     * Checks if all of the specified biomes are present.
     *
     * @param biomes A collection of biome names to check. Cannot be null.
     * @return {@code true} if all of the specified biomes are present, otherwise {@code false}.
     * @throws NullPointerException if {@code biomes} is null.
     */
    default boolean containsAllBiomes(final Collection<String> biomes) {
        Objects.requireNonNull(biomes, "biomes");
        return biomes.stream().allMatch(this::containsBiome);
    }

    /**
     * Checks if any of the specified biomes are present.
     *
     * @param biomes A collection of biome names to check. Cannot be null.
     * @return {@code true} if at least one of the specified biomes is present, otherwise {@code false}.
     * @throws NullPointerException if {@code biomes} is null.
     */
    default boolean containsAnyBiome(final Collection<String> biomes) {
        Objects.requireNonNull(biomes, "biomes");
        return biomes.stream().anyMatch(this::containsBiome);
    }

    /**
     * Checks if a specific structure is present.
     *
     * @param structure The name of the structure to check. Cannot be null.
     * @return {@code true} if the specified structure is present, otherwise {@code false}.
     * @throws NullPointerException if {@code structure} is null.
     */
    boolean containsStructure(String structure);

    /**
     * Checks if all of the specified structures are present.
     *
     * @param structures A collection of structure names to check. Cannot be null.
     * @return {@code true} if all of the specified structures are present, otherwise {@code false}.
     * @throws NullPointerException if {@code structures} is null.
     */
    default boolean containsAllStructures(final Collection<String> structures) {
        Objects.requireNonNull(structures, "structures");
        return structures.stream().allMatch(this::containsStructure);
    }

    /**
     * Checks if any of the specified structures are present.
     *
     * @param structures A collection of structure names to check. Cannot be null.
     * @return {@code true} if at least one of the specified structures is present, otherwise {@code false}.
     * @throws NullPointerException if {@code structures} is null.
     */
    default boolean containsAnyStructure(final Collection<String> structures) {
        Objects.requireNonNull(structures, "structures");
        return structures.stream().anyMatch(this::containsStructure);
    }
}
