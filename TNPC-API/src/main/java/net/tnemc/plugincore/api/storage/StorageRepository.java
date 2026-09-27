package net.tnemc.plugincore.api.storage;

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
 * StorageRepository
 *
 * @author creatorfromhell
 * @since 2.0.0.0
 */
public interface StorageRepository<I, T> {

  /**
   * Retrieves the {@code Class} object representing the type of objects managed by this repository.
   *
   * @return the {@code Class} object corresponding to the type {@code T} managed by this repository
   */
  Class<T> type();

  /**
   * Loads an object from the storage using the specified unique identifier.
   *
   * @param identifier the unique identifier of the object to be loaded
   * @return an {@code Optional} containing the loaded object if found, or an empty {@code Optional} if not found
   */
  Optional<T> load(I identifier);

  /**
   * Retrieves all objects stored in the repository.
   *
   * @return a collection of all objects available in the storage
   */
  Collection<T> loadAll();

  /**
   * Stores the provided object in the storage.
   *
   * @param object the object to be stored
   */
  void store(T object);

  /**
   * Deletes an object associated with the specified identifier from the storage.
   *
   * @param identifier the unique identifier of the object to be deleted
   */
  void delete(I identifier);
}