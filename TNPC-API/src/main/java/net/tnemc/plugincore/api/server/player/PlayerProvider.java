package net.tnemc.plugincore.api.server.player;

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

import net.tnemc.plugincore.api.server.inventory.InventoryProvider;
import net.tnemc.plugincore.api.server.world.Location;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * A class that acts as a bridge between various player objects on different server software
 * providers.
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public interface PlayerProvider {

    /**
     * Retrieves the unique identifier associated with the player.
     *
     * @return A {@code UUID} representing the player's unique identifier.
     */
    UUID identifier();

    /**
     * Retrieves the name associated with the player.
     *
     * @return The name of the player as a string.
     */
    String getName();

    /**
     * Retrieves the current location of the player.
     *
     * @return An {@code Optional<Location>} representing the player's current position. If the
     *         location is unavailable or the player is not in a valid state, an empty
     *         {@code Optional} is returned.
     */
    Optional<Location> getLocation();

    /**
     * Retrieves the name of the world associated with this player.
     *
     * @return The name of the world as a string.
     */
    String world();

    /**
     * Retrieves the biome in which the player is currently located.
     *
     * @return The name of the biome as a string.
     */
    String biome();

    /**
     * Retrieves the fractional progress of the player's experience towards the next level.
     *
     * @return The progress towards the next level as an integer. The value is scaled where
     *         0 represents no progress and the maximum value defined for the system
     *         represents full progress to the next level.
     */
    int getExp();

    /**
     * Sets the fractional progress of the player's experience towards the next level.
     *
     * @param exp The progress towards the next level as a float, where 0.0 represents no progress
     *            and 1.0 represents full progress to the next level.
     */
    void setExp(float exp);

    /**
     * Retrieves the experience level of the player.
     *
     * @return The current experience level of the player as an integer.
     */
    int getExpLevel();

    /**
     * Sets the experience level of the player.
     *
     * @param level The new experience level to set. Must be a non-negative integer.
     */
    void setExpLevel(int level);

    /**
     * Retrieves the inventory provider associated with the player.
     *
     * @return The inventory provider, which acts as a platform-agnostic interface
     *         to the underlying inventory system.
     */
    InventoryProvider<?> inventory();

    /**
     * Retrieves a list of permissions that the player possesses, taking into account
     * all inherited permissions, explicit permissions, and contextual modifiers.
     *
     * @return A list of strings representing the effective permissions of the player.
     */
    List<String> getEffectivePermissions();

    /**
     * Checks if the player has the given permission.
     *
     * @param permission The permission to check. Cannot be null.
     * @return {@code true} if the player has the specified permission, otherwise {@code false}.
     * @throws NullPointerException if {@code permission} is null.
     */
    boolean hasPermission(String permission);

    /**
     * Sends a message to the player associated with this provider.
     *
     * @param message The message to be sent. Cannot be null.
     * @throws NullPointerException if {@code message} is null.
     */
    void message(String message);
}
