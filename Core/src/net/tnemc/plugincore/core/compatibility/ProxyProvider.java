package net.tnemc.plugincore.core.compatibility;

/*
 * The New Plugin Core
 * Copyright (C) 2022 - 2024 Daniel "creatorfromhell" Vidmar
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
 * ProxyProvider represents a Proxy method instances such as Bungee/Velocity in an implementation.
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public interface ProxyProvider {

  /**
   * Used to register a plugin message channel.
   *
   * @param channel The channel to register.
   */
  void registerChannel(final String channel);

  /**
   * Used to send a message through a specific plugin message channel.
   *
   * @param channel The channel to send the message through.
   * @param bytes   The byte data to send.
   */
  void send(final String channel, final byte[] bytes);
}