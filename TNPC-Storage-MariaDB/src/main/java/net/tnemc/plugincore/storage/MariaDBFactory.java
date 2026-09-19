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

import cc.carm.lib.easysql.api.SQLManager;
import net.tnemc.plugincore.api.storage.StorageFactory;
import net.tnemc.plugincore.api.storage.StorageProvider;
import net.tnemc.plugincore.core.storage.SQLStorageConfiguration;
import net.tnemc.plugincore.core.storage.SQLStorageProvider;

/**
 * MySQLFactory
 *
 * @author creatorfromhell
 * @since 2.0.0.0
 */
public class MariaDBFactory implements StorageFactory<SQLStorageConfiguration> {

  @Override
  public String identifier() {

    return "mariadb";
  }

  @Override
  public StorageProvider create(final SQLStorageConfiguration configuration) {

    final String url = "jdbc:mariadb://" + configuration.host() + ":" + configuration.port() + "/" + configuration.database();

    //TODO: Create SQLManager
    final SQLManager manager = null;

    return new SQLStorageProvider("mariadb", manager);
  }
}