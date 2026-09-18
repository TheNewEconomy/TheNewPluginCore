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

    String name();

    String dimension();

    Location spawn();

    String moonPhase();

    String weather();

    long worldTime();

    long gameTime();

    ChunkProvider chunkProvider(int x, int z);
}
