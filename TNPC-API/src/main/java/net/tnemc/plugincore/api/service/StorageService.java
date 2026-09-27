package net.tnemc.plugincore.api.service;

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

import net.tnemc.plugincore.api.storage.StorageProvider;
import net.tnemc.plugincore.api.storage.StorageRepository;
import net.tnemc.plugincore.api.storage.schema.Schema;

import java.util.Collection;
import java.util.Optional;

/**
 * StorageService
 *
 * @author creatorfromhell
 * @since 2.0.0.0
 */
public interface StorageService {

  /**
   * Registers a storage provider with the storage service. The registered provider
   * will be utilized for handling storage operations as per its implementation.
   *
   * @param provider the {@link StorageProvider} instance to register
   */
  void registerProvider(StorageProvider provider);

  /**
   * Unregisters a storage provider from the storage service using its unique identifier.
   *
   * @param identifier the unique identifier of the storage provider to be unregistered
   */
  void unregisterProvider(String identifier);

  /**
   * Retrieves an {@link Optional} containing the registered {@link StorageProvider} associated
   * with the specified identifier. If no provider is registered with the given identifier,
   * an empty {@link Optional} is returned.
   *
   * @param identifier the unique identifier of the storage provider to retrieve
   * @return an {@link Optional} containing the {@link StorageProvider} if found, or an empty {@link Optional} if not found
   */
  Optional<StorageProvider> provider(String identifier);

  /**
   * Retrieves all registered storage providers within the storage service.
   *
   * @return a collection of {@link StorageProvider} objects representing the storage providers currently registered
   */
  Collection<StorageProvider> providers();

  /**
   * Registers a {@link StorageRepository} with the storage service. The registered repository
   * will be responsible for managing objects of the type it supports.
   *
   * @param <I>         the type of the identifier for objects managed by the repository
   * @param <T>         the type of objects managed by the repository
   * @param repository  the {@link StorageRepository} instance to register
   */
  <I, T> void registerRepository(StorageRepository<I, T> repository);

  /**
   * Retrieves an {@link Optional} containing the {@link StorageRepository} registered for the specified type.
   * If no repository is associated with the given type, an empty {@link Optional} is returned.
   *
   * @param <I>  the type of the identifier for objects managed by the repository
   * @param <T>  the type of objects managed by the repository
   * @param type the {@code Class} object representing the type of objects the repository manages
   * @return an {@link Optional} containing the {@link StorageRepository} for the specified type, or an empty {@link Optional} if none is found
   */
  <I, T> Optional<StorageRepository<I, T>> repository(Class<T> type);

  /**
   * Retrieves all storage repositories registered within the storage service.
   *
   * @return a collection of {@link StorageRepository} objects representing the registered repositories
   */
  Collection<StorageRepository<?, ?>> repositories();

  /**
   * Registers a schema instance with the storage service. This schema can represent
   * a specific structure or organization of data within the storage system,
   * and may also define its associated migrations.
   *
   * @param schema the {@link Schema} instance to register
   */
  void registerSchema(Schema schema);

  /**
   * Retrieves all schemas registered within the storage service.
   *
   * @return a collection of {@link Schema} objects representing the registered schemas
   */
  Collection<Schema> schemas();
}