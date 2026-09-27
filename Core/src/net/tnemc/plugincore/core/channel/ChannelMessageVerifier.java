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
import net.tnemc.plugincore.api.channel.ChannelMessageContext;
import net.tnemc.plugincore.core.exception.ChannelVerificationException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

/**
 * ChannelMessageVerifier
 *
 * @author creatorfromhell
 * @since 2.0.0.0
 */
final class ChannelMessageVerifier {

  private static final int PROTOCOL_VERSION = 1;
  private static final int SIGNATURE_LENGTH = 32;

  private static final int MAX_MESSAGE_SIZE = 32 * 1024;
  private static final int MAX_PAYLOAD_SIZE = 24 * 1024;

  private static final Duration MAX_MESSAGE_AGE = Duration.ofSeconds(60);

  private final SecretKey secretKey;
  private final TimedCache nonceCache;

  public ChannelMessageVerifier(final SecretKey secretKey) {

    this.secretKey = secretKey;
    this.nonceCache = new TimedCache(Duration.ofMinutes(2));
  }

  public SecureChannelMessage verify(final Key channel, final byte[] data) throws ChannelVerificationException {

    if(data == null || data.length == 0) {

      throw new ChannelVerificationException("Message contains no data.");
    }

    if(data.length > MAX_MESSAGE_SIZE) {

      throw new ChannelVerificationException("Message exceeds maximum size.");
    }

    try(final DataInputStream input = new DataInputStream(new ByteArrayInputStream(data))) {

      final int version = input.readInt();
      if(version != PROTOCOL_VERSION) {

        throw new ChannelVerificationException("Unsupported protocol version: " + version);
      }

      final UUID source = new UUID(input.readLong(), input.readLong());

      final long timestampMillis = input.readLong();
      final Instant timestamp = Instant.ofEpochMilli(timestampMillis);

      validateTimestamp(timestamp);

      final long nonce = input.readLong();

      final int payloadLength = input.readInt();
      if(payloadLength < 0 || payloadLength > MAX_PAYLOAD_SIZE) {

        throw new ChannelVerificationException("Invalid payload length.");
      }

      if(input.available() != payloadLength + SIGNATURE_LENGTH) {

        throw new ChannelVerificationException("Invalid message length.");
      }

      final byte[] payload = input.readNBytes(payloadLength);
      if(payload.length != payloadLength) {
        throw new ChannelVerificationException("Incomplete payload.");
      }

      final byte[] signature = input.readNBytes(SIGNATURE_LENGTH);
      if(signature.length != SIGNATURE_LENGTH) {

        throw new ChannelVerificationException("Invalid signature length.");
      }

      verifySignature(channel, version, source, timestampMillis, nonce, payload, signature);

      if(!nonceCache.claim(source, nonce, timestamp)) {

        throw new ChannelVerificationException("Replay detected.");
      }

      final ChannelMessageContext context = new ChannelMessageContext(channel, source, timestamp);

      return new SecureChannelMessage(context, payload);

    } catch(final IOException e) {

      throw new ChannelVerificationException("Malformed channel message.");
    }
  }

  private void validateTimestamp(final Instant timestamp) throws ChannelVerificationException {

    final Instant now = Instant.now();
    if(timestamp.isBefore(now.minus(MAX_MESSAGE_AGE)) || timestamp.isAfter(now.plus(MAX_MESSAGE_AGE))) {

      throw new ChannelVerificationException("Message timestamp is outside the allowed window.");
    }
  }

  private void verifySignature(final Key channel, final int version, final UUID source,
                               final long timestamp, final long nonce, final byte[] payload,
                               final byte[] receivedSignature) throws ChannelVerificationException {

    try {

      final Mac mac = Mac.getInstance("HmacSHA256");
      mac.init(secretKey);

      mac.update(channel.asString().getBytes(StandardCharsets.UTF_8));
      mac.update(intBytes(version));
      mac.update(longBytes(source.getMostSignificantBits()));
      mac.update(longBytes(source.getLeastSignificantBits()));
      mac.update(longBytes(timestamp));
      mac.update(longBytes(nonce));
      mac.update(intBytes(payload.length));
      mac.update(payload);

      final byte[] expectedSignature = mac.doFinal();
      if(!MessageDigest.isEqual(expectedSignature, receivedSignature)) {

        throw new ChannelVerificationException("Invalid message signature.");
      }

    } catch(final GeneralSecurityException e) {

      throw new ChannelVerificationException("Unable to verify message signature.");
    }
  }

  private byte[] intBytes(final int value) {

    return ByteBuffer.allocate(Integer.BYTES).putInt(value).array();
  }

  private byte[] longBytes(final long value) {

    return ByteBuffer.allocate(Long.BYTES).putLong(value).array();
  }
}