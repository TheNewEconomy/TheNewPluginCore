package net.tnemc.plugincore.api.channel;

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

import java.io.IOException;
import java.util.UUID;

/**
 * ChannelMessageHandler
 *
 * @author creatorfromhell
 * @since 2.0.0.0
 */
@FunctionalInterface
public interface ChannelMessageHandler {

  /**
   * Handles an incoming channel message.
   *
   * @param source The server that sent the message.
   * @param data   The message payload.
   */
  void handle(UUID source, ChannelData data) throws IOException;;
}