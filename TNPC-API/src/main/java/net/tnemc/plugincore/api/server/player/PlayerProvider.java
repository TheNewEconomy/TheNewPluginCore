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

    UUID identifier();

    String getName();

    Optional<Location> getLocation();

    String world();

    String biome();

    int getExp();

    void setExp(float exp);

    int getExpLevel();

    void setExpLevel(int level);

    InventoryProvider<?> inventory();

    List<String> getEffectivePermissions();

    boolean hasPermission(String permission);

    void message(String message);
}
