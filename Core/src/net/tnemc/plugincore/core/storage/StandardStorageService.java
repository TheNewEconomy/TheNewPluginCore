package net.tnemc.plugincore.core.storage;

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

import net.tnemc.plugincore.api.service.StorageService;
import net.tnemc.plugincore.api.storage.StorageProvider;
import net.tnemc.plugincore.api.storage.StorageRepository;
import net.tnemc.plugincore.api.storage.schema.Schema;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * StandardStorageService
 *
 * @author creatorfromhell
 * @since 2.0.0.0
 */
public final class StandardStorageService implements StorageService, AutoCloseable {

  private final Map<String, StorageProvider> providers = new LinkedHashMap<>();
  private final Map<Class<?>, StorageRepository<?, ?>> repositories = new LinkedHashMap<>();
  private final Map<String, Schema> schemas = new LinkedHashMap<>();

  @Override
  public void registerProvider(final StorageProvider provider) {

    final String identifier = normalize(provider.identifier());

    if(providers.putIfAbsent(identifier, provider) != null) {

      throw new IllegalStateException("Storage provider already registered: " + provider.identifier());
    }
  }

  @Override
  public void unregisterProvider(final String identifier) {

    final StorageProvider provider = providers.remove(normalize(identifier));

    if(provider != null) {

      provider.close();
    }
  }

  @Override
  public Optional<StorageProvider> provider(final String identifier) {

    return Optional.ofNullable(providers.get(normalize(identifier)));
  }

  @Override
  public Collection<StorageProvider> providers() {

    return List.copyOf(providers.values());
  }

  @Override
  public <I, T> void registerRepository(final StorageRepository<I, T> repository) {

    if(repositories.putIfAbsent(repository.type(), repository) != null) {

      throw new IllegalStateException("Storage repository already registered for: " + repository.type().getName());
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public <I, T> Optional<StorageRepository<I, T>> repository(final Class<T> type) {

    return Optional.ofNullable((StorageRepository<I, T>)repositories.get(type));
  }

  @Override
  public Collection<StorageRepository<?, ?>> repositories() {

    return List.copyOf(repositories.values());
  }

  @Override
  public void registerSchema(final Schema schema) {

    final String identifier = normalize(schema.name());

    if(schemas.putIfAbsent(identifier, schema) != null) {

      throw new IllegalStateException("Schema already registered: " + schema.name());
    }
  }

  @Override
  public Collection<Schema> schemas() {

    return List.copyOf(schemas.values());
  }

  @Override
  public void close() {

    for(final StorageProvider provider : providers.values()) {

      try {

        provider.close();
      } catch(final Exception ignored) {
      }
    }

    providers.clear();
    repositories.clear();
    schemas.clear();
  }

  private String normalize(final String identifier) {

    return identifier.toLowerCase(Locale.ROOT);
  }
}