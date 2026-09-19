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

/**
 * StorageProvider
 *
 * @author creatorfromhell
 * @since 2.0.0.0
 */
public interface StorageProvider extends AutoCloseable {

  /**
   * Retrieves the type identifier for this storage provider.
   *
   * @return the type identifier as a string
   */
  String type();

  /**
   * Checks whether the storage provider is currently available for use.
   *
   * @return true if the storage provider is available, false otherwise
   */
  boolean available();

  @Override
  void close();
}