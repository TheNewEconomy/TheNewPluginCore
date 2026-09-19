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

    int x();

    int z();

    boolean isSlime();

    boolean containsBiome(String biome);

    default boolean containsAllBiomes(final Collection<String> biomes) {
        Objects.requireNonNull(biomes, "biomes");
        return biomes.stream().allMatch(this::containsBiome);
    }

    default boolean containsAnyBiome(final Collection<String> biomes) {
        Objects.requireNonNull(biomes, "biomes");
        return biomes.stream().anyMatch(this::containsBiome);
    }

    boolean containsStructure(String structure);

    default boolean containsAllStructures(final Collection<String> structures) {
        Objects.requireNonNull(structures, "structures");
        return structures.stream().allMatch(this::containsStructure);
    }

    default boolean containsAnyStructure(final Collection<String> structures) {
        Objects.requireNonNull(structures, "structures");
        return structures.stream().anyMatch(this::containsStructure);
    }
}
