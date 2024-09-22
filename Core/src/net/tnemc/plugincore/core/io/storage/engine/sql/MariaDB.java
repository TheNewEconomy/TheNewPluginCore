package net.tnemc.plugincore.core.io.storage.engine.sql;

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


import net.tnemc.plugincore.core.io.storage.Dialect;
import net.tnemc.plugincore.core.io.storage.SQLEngine;
import net.tnemc.plugincore.core.io.storage.StorageManager;
import net.tnemc.plugincore.core.io.storage.engine.StandardSQL;

import java.util.HashMap;
import java.util.Map;

public class MariaDB extends StandardSQL {

  public MariaDB(final String prefix, Dialect dialect) {
    super(prefix, dialect);
  }

  /**
   * The name of this engine.
   *
   * @return The engine name.
   */
  @Override
  public String name() {
    return "maria";
  }

  @Override
  public String[] driver() {
    return new String[] {
        "org.mariadb.jdbc.Driver"
    };
  }

  @Override
  public String[] dataSource() {
    return new String[] {
        "org.mariadb.jdbc.MariaDbDataSource"
    };
  }

  @Override
  public String url(String file, String host, int port, String database) {

    final String ssl = (StorageManager.instance().settings().ssl())? "trust" : "disable";
    final StringBuilder builder = new StringBuilder("jdbc:mariadb://");
    builder.append(host);
    builder.append(":");
    builder.append(port);
    builder.append("/");
    builder.append(database);
    builder.append("?allowPublicKeyRetrieval=");
    builder.append(StorageManager.instance().settings().publicKey());
    builder.append("&sslMode=");
    builder.append(ssl);
    return builder.toString();
  }

  /**
   * Used to get addition hikari properties for this {@link SQLEngine}.
   * @return A map containing the additional properties.
   */
  @Override
  public Map<String, Object> properties() {
    return new HashMap<>();
  }
}