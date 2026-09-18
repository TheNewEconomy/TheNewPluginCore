package net.tnemc.plugincore.api.registry;

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

import java.util.Collection;
import java.util.Optional;

/**
 * Registry
 *
 * @author creatorfromhell
 * @since 2.0.0.0
 */
public interface Registry<K, V> {

  /**
   * Retrieves the value associated with the specified key, if it exists.
   *
   * @param key The key whose associated value is to be returned. Must not be null.
   * @return An {@link Optional} containing the value associated with the specified key,
   *         or an empty {@link Optional} if no such value exists.
   */
  Optional<V> get(K key);

  /**
   * Retrieves a collection containing all values currently stored in the registry.
   *
   * @return A {@link Collection} of all values managed by the registry. The returned collection
   *         is a view of the current state of the registry and may be empty if no values are stored.
   */
  Collection<V> values();

  /**
   * Checks whether the registry contains a value associated with the specified key.
   *
   * @param key The key to check for existence within the registry. Must not be null.
   * @return {@code true} if the registry contains a value for the specified key, otherwise {@code false}.
   */
  boolean contains(K key);
}