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

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.tnemc.plugincore.PluginCore;
import net.tnemc.plugincore.core.compatibility.log.DebugLevel;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.nio.charset.StandardCharsets;

/**
 * TNEJedisManager
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public class TNEJedisManager {

  private static TNEJedisManager instance;
  protected final JedisPool pool;
  protected final TNESubscriber subscriber = new TNESubscriber();
  protected final byte[] channel = "tne:balance".getBytes(StandardCharsets.UTF_8);
  final Thread redisThread;

  public TNEJedisManager(final JedisPool pool) {

    this.pool = pool;

    if(connectionTest()) {

      redisThread = new Thread(()->{
        try(Jedis jedis = pool.getResource()) {
          jedis.subscribe(subscriber, channel);
        }
      }, "TNE Redis Thread");

      redisThread.start();

      PluginCore.log().inform("Redis Subscriber Thread Started on host", DebugLevel.OFF);
    } else {
      redisThread = null;
    }

    instance = this;
  }

  public static TNEJedisManager instance() {

    return instance;
  }

  public static void send(final byte[] data) {

    final ByteArrayDataOutput out = ByteStreams.newDataOutput();
    out.writeUTF(PluginCore.instance().getServerID().toString());

    out.write(data);

    instance.publish(out.toByteArray());
  }

  public boolean connectionTest() {

    try(Jedis jedis = pool.getResource()) {
      jedis.ping();
      return true;
    } catch(Exception e) {
      PluginCore.log().error("Redis Connection Test Failed!", e, DebugLevel.STANDARD);
      return false;
    }
  }

  public void publish(final byte[] data) {

    try(Jedis jedis = pool.getResource()) {
      jedis.publish(channel, data);
    }
  }

  public void publish(final String channel, final byte[] data) {

    try(Jedis jedis = pool.getResource()) {
      jedis.publish(channel.getBytes(StandardCharsets.UTF_8), data);
    }
  }
}