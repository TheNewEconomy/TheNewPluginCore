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

import net.tnemc.plugincore.core.channel.timed.TimedKey;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * TimedCache
 *
 * @author creatorfromhell
 * @since 2.0.0.0
 */
final class TimedCache {

  private final ConcurrentMap<TimedKey, Instant> keysCache = new ConcurrentHashMap<>();

  private final Duration retention;

  TimedCache(final Duration retention) {

    this.retention = retention;
  }

  boolean claim(final UUID source, final long nonce, final Instant timestamp) {

    cleanup();

    final TimedKey key = new TimedKey(source, nonce);

    return keysCache.putIfAbsent(key, timestamp) == null;
  }

  private void cleanup() {

    final Instant cutoff = Instant.now().minus(retention);

    keysCache.entrySet().removeIf(entry -> entry.getValue().isBefore(cutoff));
  }
}