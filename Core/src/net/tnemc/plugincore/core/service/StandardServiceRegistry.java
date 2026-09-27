package net.tnemc.plugincore.core.service;

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

import net.tnemc.plugincore.api.service.ServiceRegistry;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * StandardServiceRegistry
 *
 * @author creatorfromhell
 * @since 2.0.0.0
 */
public final class StandardServiceRegistry implements ServiceRegistry {

  private final Map<Class<?>, Map<String, Object>> services = new ConcurrentHashMap<>();

  @Override
  public <T> void register(final Class<T> type, final String name, final T service) {

    final Map<String, Object> registered = services.computeIfAbsent(type, key->new ConcurrentHashMap<>());
    if(registered.putIfAbsent(normalize(name), service) != null) {

      throw new IllegalStateException("Service already registered: " + type.getName() + " (" + name + ")");
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> Optional<T> service(final Class<T> type, final String name) {

    final Map<String, Object> registered = services.get(type);
    if(registered == null) {

      return Optional.empty();
    }

    return Optional.ofNullable((T)registered.get(normalize(name)));
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> Collection<T> services(final Class<T> type) {

    final Map<String, Object> registered = services.get(type);
    if(registered == null) {

      return List.of();
    }

    return registered.values().stream().map(service->(T)service).toList();
  }

  @Override
  public boolean registered(final Class<?> type, final String name) {

    final Map<String, Object> registered = services.get(type);

    return registered != null && registered.containsKey(normalize(name));
  }

  @Override
  public <T> void unregister(final Class<T> type, final String name) {

    final Map<String, Object> registered = services.get(type);
    if(registered == null) {

      return;
    }

    registered.remove(normalize(name));

    if(registered.isEmpty()) {

      services.remove(type, registered);
    }
  }

  private String normalize(final String name) {

    return name.toLowerCase(Locale.ROOT);
  }
}