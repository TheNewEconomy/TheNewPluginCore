package net.tnemc.plugincore.api.id;

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

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * UUIDProvider
 *
 * @author creatorfromhell
 * @since 2.0.0.0
 */
public interface UUIDProvider {

  /**
   * Used to retrieve a UUID pair by username.
   *
   * @param name The username.
   *
   * @return The associated pair, if available.
   */
  Optional<UUIDPair> retrieve(String name);

  /**
   * Used to retrieve a username by UUID.
   *
   * @param identifier The UUID.
   *
   * @return The associated username, if available.
   */
  Optional<String> retrieveName(UUID identifier);

  /**
   * Stores a UUID and username pair.
   *
   * @param pair The pair to store.
   */
  void store(UUIDPair pair);

  /**
   * Generates an offline-mode UUID for the specified username.
   *
   * @param name The username.
   *
   * @return The generated UUID.
   */
  default UUID generateOffline(final String name) {

    return UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes(StandardCharsets.UTF_8));
  }

  /**
   * Retrieves all known UUID pairs.
   *
   * @return The known UUID pairs.
   */
  Collection<UUIDPair> pairs();
}