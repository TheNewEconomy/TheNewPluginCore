package net.tnemc.plugincore.storage;

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

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * YAMLStorageProvider
 *
 * @author creatorfromhell
 * @since 2.0.0.0
 */
public class YAMLStorageProvider implements StorageProvider {

  private final Path directory;

  public YAMLStorageProvider(final Path directory) {

    this.directory = directory;
  }

  @Override
  public String type() {

    return "yaml";
  }

  @Override
  public boolean available() {

    return Files.isDirectory(directory) && Files.isReadable(directory) && Files.isWritable(directory);
  }

  public Path directory() {

    return directory;
  }

  @Override
  public void close() {
  }
}