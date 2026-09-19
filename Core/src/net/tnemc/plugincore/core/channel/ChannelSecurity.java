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

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * ChannelSecurity
 *
 * @author creatorfromhell
 * @since 2.0.0.0
 */
final class ChannelSecurity {

  private static final int KEY_SIZE = 32;

  static SecretKey decodeKey(final String encoded) {

    final byte[] bytes;

    try {

      bytes = Base64.getDecoder().decode(encoded);
    } catch(final IllegalArgumentException e) {

      throw new IllegalArgumentException("Invalid Base64 channel secret.", e);
    }

    if(bytes.length != KEY_SIZE) {

      throw new IllegalArgumentException("Channel secret must be 256 bits.");
    }

    return new SecretKeySpec(bytes, "HmacSHA256");
  }

  static String generateKey() {

    final byte[] bytes = new byte[KEY_SIZE];

    new SecureRandom().nextBytes(bytes);

    return Base64.getEncoder().encodeToString(bytes);
  }

  private ChannelSecurity() {
  }
}