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

/**
 * WorldCompatibility
 *
 * @author creatorfromhell
 * @since 1.1.0.1
 */
public interface WorldProvider {

    /**
     * Retrieves the name of the world.
     *
     * @return A string representing the name of the world.
     */
    String name();

    /**
     * Retrieves the identifier for the dimension associated with this world.
     *
     * @return A string representing the dimension identifier, such as "overworld",
     *         "nether", or "end".
     */
    String dimension();

    /**
     * Retrieves the spawn location of the world. The location typically represents
     * the default point where players or entities initially appear when entering the world.
     *
     * @return A {@code Location} object representing the spawn point of the world,
     *         including coordinates and orientation information.
     */
    Location spawn();

    /**
     * Retrieves the current phase of the moon in the world.
     *
     * @return A string representing the current moon phase, such as "full moon",
     *         "new moon", or other possible phases.
     */
    String moonPhase();

    /**
     * Retrieves the current weather condition in the world.
     *
     * @return A string representing the current weather, such as "clear", "rain", or "thunder".
     */
    String weather();

    /**
     * Retrieves the current in-game time in ticks for this world.
     * The world time typically represents the day/night cycle in the game.
     *
     * @return The current world time in ticks.
     */
    long worldTime();

    /**
     * Retrieves the elapsed in-game time in ticks within the world.
     *
     * @return The total game time in ticks.
     */
    long gameTime();

    /**
     * Provides access to a specific chunk at the given coordinates within the world.
     *
     * @param x The x-coordinate of the chunk.
     * @param z The z-coordinate of the chunk.
     * @return A {@code ChunkProvider} instance representing the chunk at the specified coordinates.
     */
    ChunkProvider chunkProvider(int x, int z);
}
