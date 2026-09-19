package net.tnemc.plugincore.api;

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

import net.tnemc.plugincore.api.logging.Logger;
import net.tnemc.plugincore.api.scheduler.SchedulerProvider;
import net.tnemc.plugincore.api.server.ServerConnector;

import java.nio.file.Path;

/**
 * PluginContext
 *
 * @author creatorfromhell
 * @since 2.0.0.0
 */
public interface PluginContext {

    PluginMetadata metadata();

    Logger logger();

    ServerConnector server();

    SchedulerProvider<?> scheduler();

    Path dataDirectory();

    Platform platform();

    String version();
}
