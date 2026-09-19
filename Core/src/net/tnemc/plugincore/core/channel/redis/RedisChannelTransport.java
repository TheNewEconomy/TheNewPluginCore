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
import net.tnemc.plugincore.api.logging.DebugLevel;
import net.tnemc.plugincore.api.logging.Logger;
import net.tnemc.plugincore.core.channel.ChannelMessageManager;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.nio.charset.StandardCharsets;

/**
 * TNEJedisManager
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public final class RedisChannelTransport implements AutoCloseable {

  private final JedisPool pool;
  private final Logger logger;

  private final RedisChannelSubscriber subscriber;
  private Thread subscriberThread;

  public RedisChannelTransport(final JedisPool pool, final ChannelMessageManager channelManager, final Logger logger) {

    this.pool = pool;
    this.logger = logger;
    this.subscriber = new RedisChannelSubscriber(channelManager);
  }

  public boolean connected() {

    try(final Jedis jedis = pool.getResource()) {

      return "PONG".equalsIgnoreCase(jedis.ping());

    } catch(final Exception e) {

      logger.error("Redis connection test failed.", e, DebugLevel.STANDARD);

      return false;
    }
  }

  public void start(final Key channel) {

    if(subscriberThread != null
       && subscriberThread.isAlive()) {

      return;
    }

    if(!connected()) {
      return;
    }

    subscriberThread = Thread.ofPlatform().name("TNPC Redis Subscriber")
            .start(()->{

              try(final Jedis jedis = pool.getResource()) {

                jedis.subscribe(subscriber, channel.asString().getBytes(StandardCharsets.UTF_8));

              } catch(final Exception e) {

                if(!Thread.currentThread().isInterrupted()) {

                  logger.error("Redis subscriber stopped unexpectedly.", e, DebugLevel.STANDARD);
                }
              }
            });
  }

  public void publish(final Key channel, final byte[] data) {

    try(final Jedis jedis = pool.getResource()) {

      jedis.publish(channel.asString().getBytes(StandardCharsets.UTF_8), data);
    }
  }

  @Override
  public void close() {

    if(subscriber != null && subscriber.isSubscribed()) {
      subscriber.unsubscribe();
    }

    if(subscriberThread != null) {

      try {

        subscriberThread.join(5000);

      } catch(final InterruptedException e) {

        Thread.currentThread().interrupt();

        logger.error("Interrupted while waiting for Redis subscriber thread to stop.", e, DebugLevel.STANDARD);
      }

      if(subscriberThread.isAlive()) {
        subscriberThread.interrupt();
      }
    }

    if(!pool.isClosed()) {
      pool.close();
    }
  }
}