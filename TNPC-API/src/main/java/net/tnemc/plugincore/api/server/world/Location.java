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

import java.util.Objects;

/**
 * Location
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public record Location(String world, double x, double y, double z, float yaw, float pitch) {
    public Location {
        Objects.requireNonNull(world, "world");
    }

    public Location(final String world, final double x, final double y, final double z) {
        this(world, x, y, z, 0.0F, 0.0F);
    }

    public int chunkX() {
        return Math.floorDiv((int) Math.floor(x), 16);
    }

    public int chunkY() {
        return Math.floorDiv((int) Math.floor(y), 16);
    }

    public int chunkZ() {
        return Math.floorDiv((int) Math.floor(z), 16);
    }

    public double distance(final Location other) {
        Objects.requireNonNull(other, "other");
        if (!world.equals(other.world)) {
            throw new IllegalArgumentException("Cannot calculate distance between different worlds.");
        }
        final double dx = x - other.x;
        final double dy = y - other.y;
        final double dz = z - other.z;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
}
