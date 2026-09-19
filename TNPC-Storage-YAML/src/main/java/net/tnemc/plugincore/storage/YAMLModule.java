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

import net.tnemc.plugincore.api.module.Module;
import net.tnemc.plugincore.api.module.ModuleContext;
import net.tnemc.plugincore.api.module.ModuleInfo;
import net.tnemc.plugincore.api.storage.StorageFactory;

/**
 * MySQLModule
 *
 * @author creatorfromhell
 * @since 2.0.0.0
 */
@ModuleInfo(
        name = "TNPC-Storage-YAML",
        author = "creatorfromhell",
        version = "2.0.0.0",
        minimumVersion = "2.0.0.0"
)
public class YAMLModule implements Module {

  private ModuleContext context;

  @Override
  public void initialize(final ModuleContext context) {

    this.context = context;
  }

  @Override
  public void enable() {

    context.services().register(StorageFactory.class, "yaml", new YAMLFactory());
  }

  @Override
  public void disable() {

    context.services().unregister(StorageFactory.class, "yaml");
  }
}