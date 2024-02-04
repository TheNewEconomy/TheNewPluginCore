package net.tnemc.plugincore.core.io.storage.engine.flat;
/*
 * The New Plugin Core
 * Copyright (C) 2022 - 2024 Daniel "creatorfromhell" Vidmar
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
 * YAML
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public class YAML implements StorageEngine {

  protected final Map<Class<?>, Datable<?>> datables = new HashMap<>();

  public YAML() {
  }

  /**
   * The name of this engine.
   *
   * @return The engine name.
   */
  @Override
  public String name() {
    return "yaml";
  }

  /**
   * Called after the connection is initialized, so we can do any actions that need done immediately
   * after connecting.
   *
   * @param connector The {@link StorageConnector connector} used for initialization.
   */
  @Override
  public void initialize(StorageConnector<?> connector) {

  }

  /**
   * Used to reset all data for this engine.
   *
   * @param connector The storage connector to use for this transaction.
   */
  @Override
  public void reset(StorageConnector<?> connector) {
  }

  /**
   * Used to back up all data in the database for this engine.
   *
   * @param connector The storage connector to use for this transaction.
   */
  @Override
  public void backup(StorageConnector<?> connector) {

  }

  /**
   * Used to get the {@link Datable} classes for this engine.
   *
   * @return A map with the datables.
   */
  @Override
  public Map<Class<?>, Datable<?>> datables() {
    return datables;
  }
}