package net.tnemc.plugincore.core.io.storage;
/*
 * The New Kings
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

import org.jetbrains.annotations.NotNull;

/**
 * StorageProvider
 *
 * @author creatorfromhell
 * @since 0.0.1.0
 */
public interface StorageProvider {

  /**
   * Initialize the {@link StorageProvider}. This is where the {@link StorageConnector connector}
   * and the {@link StorageEngine engine} should be initialized.
   * @param engine
   */
  void initialize(final String engine);

  /**
   * Used to get the storage connector.
   * @return The {@link StorageConnector connector} that should be used.
   */
  StorageConnector<?> connector();

  /**
   * Used to retrieve the storage engine.
   * @return The {@link StorageEngine engine} that should be used.
   */
  StorageEngine engine();

  /**
   * Used to store all data for an identifier in TNE. This method is not switched over to a secondary
   * thread automatically. Please make sure to use wisely.
   */
  void storeAll(@NotNull final String identifier);

  /**
   * Used to store all data in TNE.
   */
  void storeAll();
}