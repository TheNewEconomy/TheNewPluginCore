package net.tnemc.plugincore.core.io.redis;
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
import redis.clients.jedis.BinaryJedisPubSub;

import java.nio.charset.StandardCharsets;

/**
 * TNESubscriber
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public class TNESubscriber extends BinaryJedisPubSub {

  @Override
  public void onMessage(byte[] channel, byte[] message) {

    super.onMessage(channel, message);

    final String channelStr = new String(channel, StandardCharsets.UTF_8);

    PluginCore.log().debug("Redis Message Received: " + channelStr, DebugLevel.STANDARD);
    PluginCore.instance().getChannelMessageManager().handle(channelStr, message);
  }
}