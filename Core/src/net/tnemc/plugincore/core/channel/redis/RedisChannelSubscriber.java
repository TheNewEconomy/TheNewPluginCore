package net.tnemc.plugincore.core.channel.redis;
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
import net.tnemc.plugincore.core.channel.ChannelMessageManager;
import redis.clients.jedis.BinaryJedisPubSub;

import java.nio.charset.StandardCharsets;

/**
 * TNESubscriber
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public class RedisChannelSubscriber extends BinaryJedisPubSub {

  private final ChannelMessageManager manager;

  RedisChannelSubscriber(final ChannelMessageManager manager) {

    this.manager = manager;
  }

  @Override
  public void onMessage(final byte[] channel, final byte[] message) {

    final Key key = Key.key(new String(channel, StandardCharsets.UTF_8));

    manager.handle(key, message);
  }
}