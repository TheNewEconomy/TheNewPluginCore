package net.tnemc.plugincore.api.command;

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

import net.tnemc.plugincore.api.server.player.PlayerProvider;
import revxrsal.commands.command.CommandActor;

import java.util.Optional;
import java.util.UUID;

/**
 * Provides a compatibility layer for logging purposes.
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public interface CommandSource<T extends CommandActor> {

    /**
     * The UUID of this command source.
     *
     * @return The UUID of this command source.
     */
    default Optional<UUID> identifier() {

        if(!isPlayer()) {
            return Optional.empty();
        }
        return Optional.of(actor().uniqueId());
    }

    /**
     * The name of this command source.
     *
     * @return The name of this command source.
     */
    String name();

    /**
     * Determines if this {@link CommandSource} is an instance of a player.
     *
     * @return True if this represents a player, otherwise false if it's a non-player such as the
     * console.
     */
    boolean isPlayer();

    /**
     * Determines if this {@link CommandSource} is the console.
     *
     * @return True if this represents the console, otherwise false if it's a player.
     */
    default boolean isConsole() {
        return !isPlayer();
    }

    /**
     * Used to get the related {@link PlayerProvider} for this command source.
     *
     * @return An optional containing the related {@link PlayerProvider} if this command source is a
     * player, otherwise an empty {@link Optional}.
     */
    Optional<PlayerProvider> player();

    /**
     * Checks whether the command source has the specified permission.
     *
     * @param permission The permission string to check.
     * @return True if the command source has the specified permission, otherwise false.
     */
    boolean hasPermission(String permission);

    /**
     * Sends a message to this command source.
     *
     * @param message The message to be sent to the command source.
     */
    void message(String message);

    /**
     * Retrieves the actor associated with this command source.
     *
     * @return The associated actor of type T.
     */
    T actor();
}
