package net.tnemc.plugincore.core.channel;
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

import net.tnemc.plugincore.PluginCore;
import net.tnemc.plugincore.core.compatibility.log.DebugLevel;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

/**
 * ChannelMessageHandler represents a handler for a plugin channel message.
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public abstract class ChannelMessageHandler {

  protected final String tag;
  protected UUID server;

  public ChannelMessageHandler(String tag) {
    this.tag = tag;
  }

  public void handle(byte[] bytes) {

    try(ChannelBytesWrapper wrapper = new ChannelBytesWrapper(bytes)) {

      Optional<UUID> serverID = Optional.empty();
      try {
        serverID = wrapper.readUUID();
      } catch (IOException e) {
        e.printStackTrace();
      }

      if(serverID.isPresent()) {
        server = serverID.get();

        PluginCore.log().debug("Message Received:", DebugLevel.DEVELOPER);
        PluginCore.log().debug("Received:" + server, DebugLevel.DEVELOPER);
        if(!PluginCore.instance().getServerID().equals(server)) {
          handle(wrapper);
        }
      }
    }
  }

  public abstract void handle(ChannelBytesWrapper wrapper);
}