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

import java.util.Objects;

/**
 * Key
 *
 * @author creatorfromhell
 * @since 2.0.0.0
 */
public record Key(String namespace, String value) {

  public Key {

    Objects.requireNonNull(namespace, "namespace");
    Objects.requireNonNull(value, "value");

    if(namespace.isBlank()) {
      throw new IllegalArgumentException("Namespace cannot be blank.");
    }

    if(value.isBlank()) {
      throw new IllegalArgumentException("Value cannot be blank.");
    }
  }

  /**
   * Creates a new {@link Key} instance using the provided namespace and value.
   *
   * @param namespace The namespace part of the key. Must not be null or blank.
   * @param value The value part of the key. Must not be null or blank.
   * @return A new {@link Key} instance with the specified namespace and value.
   * @throws NullPointerException If the namespace or value is null.
   * @throws IllegalArgumentException If the namespace or value is blank.
   */
  public static Key of(final String namespace, final String value) {

    return new Key(namespace, value);
  }

  @Override
  public String toString() {
    return namespace + ":" + value;
  }
}