package net.tnemc.plugincore.core.channel;
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


import net.kyori.adventure.key.Key;
import net.tnemc.plugincore.PluginCore;
import net.tnemc.plugincore.api.channel.ChannelData;
import net.tnemc.plugincore.api.channel.ChannelMessageHandler;
import net.tnemc.plugincore.api.proxy.ProxyProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * ChannelMessageManager
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public final class ChannelMessageManager {

  private final Map<Key, ChannelMessageHandler> handlers = new HashMap<>();

  private final ProxyProvider proxy;

  public ChannelMessageManager(final ProxyProvider proxy) {

    this.proxy = proxy;
  }

  public void register(final Key channel, final ChannelMessageHandler handler) {

    handlers.put(channel, handler);

    proxy.registerChannel(channel.asString());
  }

  public void handle(final Key channel, final UUID source, final byte[] data) throws IOException {

    final ChannelMessageHandler handler = handlers.get(channel);

    if(handler != null) {
      try(final ChannelData channelData = new StandardChannelData(data)) {
        handler.handle(source, channelData);
      }
    }
  }
}