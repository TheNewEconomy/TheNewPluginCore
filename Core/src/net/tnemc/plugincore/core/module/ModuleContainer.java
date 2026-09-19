package net.tnemc.plugincore.core.module;

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

import net.tnemc.plugincore.api.module.ModuleInfo;
import net.tnemc.plugincore.api.module.Module;

import java.io.IOException;
import java.nio.file.Path;

/**
 * ModuleContainer
 *
 * @author creatorfromhell
 * @since 2.0.0.0
 */
class ModuleContainer implements AutoCloseable {

  private final ModuleInfo info;
  private final Module module;
  private final ModuleClassLoader classLoader;
  private final Path file;

  ModuleContainer(final ModuleInfo info, final Module module, final ModuleClassLoader classLoader, final Path file) {

    this.info = info;
    this.module = module;
    this.classLoader = classLoader;
    this.file = file;
  }

  ModuleInfo info() {

    return info;
  }

  Module module() {

    return module;
  }

  Path file() {

    return file;
  }

  @Override
  public void close() throws IOException {

    classLoader.close();
  }
}