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
import net.tnemc.plugincore.api.channel.ChannelDataInput;
import net.tnemc.plugincore.api.channel.ChannelMessageHandler;
import net.tnemc.plugincore.api.logging.DebugLevel;
import net.tnemc.plugincore.api.logging.Logger;
import net.tnemc.plugincore.api.proxy.ProxyProvider;
import net.tnemc.plugincore.core.exception.ChannelVerificationException;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * ChannelMessageManager
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public final class ChannelMessageManager {

  private final Map<Key, ChannelMessageHandler> handlers = new HashMap<>();

  private final ChannelMessageVerifier verifier;

  private final ProxyProvider proxy;
  private final Logger logger;

  //TODO: How to handle the secret key?
  //final SecretKey secretKey = ChannelSecurity.decodeKey(configuration.channelSecret());

  public ChannelMessageManager(final ProxyProvider proxy, final SecretKey secretKey, final Logger logger) {

    this.proxy = proxy;
    this.logger = logger;
    this.verifier = new ChannelMessageVerifier(secretKey);
  }

  public void register(final Key channel, final ChannelMessageHandler handler) {

    handlers.put(channel, handler);

    proxy.registerChannel(channel.asString());
  }

  public void handle(final Key channel, final byte[] data) {

    final ChannelMessageHandler handler = handlers.get(channel);

    if(handler == null) {
      return;
    }

    try {

      final SecureChannelMessage message = verifier.verify(channel, data);

      try(final ChannelDataInput channelData = new StandardChannelData(message.payload())) {

        handler.handle(message.context(), channelData);
      }

    } catch(final ChannelVerificationException e) {

      logger.debug("Rejected channel message for " + channel.asString() + ": " + e.getMessage(), DebugLevel.DEVELOPER);

    } catch(final IOException e) {

      logger.error("Failed to handle channel message: " + channel.asString(), e, DebugLevel.OFF);
    }
  }
}