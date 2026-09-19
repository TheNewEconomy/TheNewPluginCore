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

import java.util.Collection;
import java.util.Optional;

/**
 * ServiceRegistry
 *
 * @author creatorfromhell
 * @since 2.0.0.0
 */
public interface ServiceRegistry {

  /**
   * Registers a service with the service registry. Each service is identified by its type
   * and an associated unique name, ensuring that multiple services of the same type can
   * coexist under different names.
   *
   * @param <T>     the type of the service to be registered
   * @param type    the {@code Class} object representing the type of the service
   * @param name    the unique name associated with the service
   * @param service the instance of the service to register
   */
  <T> void register(Class<T> type, String name, T service);

  /**
   * Retrieves an {@link Optional} containing the service instance based on its type and name
   * if a matching service is registered in the service registry. If no service is found,
   * an empty {@link Optional} is returned.
   *
   * @param <T>  the type of the service to retrieve
   * @param type the {@code Class} object representing the type of the service
   * @param name the unique name associated with the service
   * @return an {@link Optional} containing the service instance if found, or an empty {@link Optional} if not found
   */
  <T> Optional<T> service(Class<T> type, String name);

  /**
   * Retrieves all registered services within the service registry that match the specified type.
   *
   * @param <T>  the type of services to retrieve
   * @param type the {@code Class} object representing the type of services to retrieve
   * @return a collection of all services currently registered that match the specified type
   */
  <T> Collection<T> services(Class<T> type);

  /**
   * Determines whether a service identified by its type and name is already registered
   * in the service registry.
   *
   * @param type the {@code Class} object representing the type of the service
   * @param name the unique name of the service
   * @return {@code true} if the service is registered, {@code false} otherwise
   */
  boolean registered(Class<?> type, String name);

  /**
   * Unregisters a service from the registry, identified by its type and name.
   * Once unregistered, the service will no longer be available for retrieval
   * or use through the registry.
   *
   * @param <T>  the type of the service to be unregistered
   * @param type the {@code Class} object representing the type of the service to be unregistered
   * @param name the unique name of the service to be unregistered
   */
  <T> void unregister(Class<T> type, String name);
}