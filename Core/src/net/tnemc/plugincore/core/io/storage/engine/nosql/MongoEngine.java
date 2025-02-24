package net.tnemc.plugincore.core.io.storage.engine.nosql;
/*
 * The New Plugin Core
 * Copyright (C) 2022 - 2025 Daniel "creatorfromhell" Vidmar
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

import net.tnemc.plugincore.core.io.storage.Datable;
import net.tnemc.plugincore.core.io.storage.StorageConnector;
import net.tnemc.plugincore.core.io.storage.StorageEngine;

import java.util.HashMap;
import java.util.Map;

/**
 * MongoEngine
 *
 * @author creatorfromhell
 * @since 1.0.0.2
 */
public class MongoEngine implements StorageEngine {

  private final String name = "MongoDB";
  private final Map<Class<?>, Datable<?>> datables = new HashMap<>();

  @Override
  public String name() {
    return name;
  }

  @Override
  public void initialize(final StorageConnector<?> connector) {
    // Additional setup if needed
  }

  @Override
  public void reset(final StorageConnector<?> connector) {

  }

  @Override
  public void backup(final StorageConnector<?> connector) {
    // Implement backup logic
  }

  @Override
  public Map<Class<?>, Datable<?>> datables() {
    return datables;
  }
}