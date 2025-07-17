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

public class MySQL extends StandardSQL {

  public MySQL(final String prefix, final Dialect dialect) {

    super(prefix, dialect);
  }

  /**
   * The name of this engine.
   *
   * @return The engine name.
   */
  @Override
  public String name() {

    return "mysql";
  }

  @Override
  public String[] driver() {

    return new String[]{
            "org.mariadb.jdbc.Driver",
            "com.mysql.cj.jdbc.Driver",
            "com.mysql.jdbc.Driver"
    };
  }

  @Override
  public String[] dataSource() {

    return new String[]{
            "org.mariadb.jdbc.MariaDbDataSource",
            "com.mysql.jdbc.jdbc2.optional.MysqlDataSource",
            "com.mysql.cj.jdbc.MysqlDataSource"
    };
  }

  @Override
  public String url(final String file, final String host, final int port, final String database) {

    final StringBuilder builder = new StringBuilder("jdbc:mysql://");
    builder.append(host);
    builder.append(":");
    builder.append(port);
    builder.append("/");
    builder.append(database);
    builder.append("?allowPublicKeyRetrieval=");
    builder.append(StorageManager.instance().settings().publicKey());
    builder.append("&useSSL=");
    builder.append(StorageManager.instance().settings().ssl());
    return builder.toString();
  }

  /**
   * Used to get addition hikari properties for this {@link SQLEngine}.
   *
   * @return A map containing the additional properties.
   */
  @Override
  public Map<String, Object> properties() {

    final Map<String, Object> properties = new HashMap<>();

    properties.put("autoReconnect", true);
    properties.put("cachePrepStmts", true);
    properties.put("prepStmtCacheSize", 250);
    properties.put("prepStmtCacheSqlLimit", 2048);
    properties.put("rewriteBatchedStatements", true);
    properties.put("useServerPrepStmts", true);
    properties.put("cacheCallableStmts", true);
    properties.put("cacheResultSetMetadata", true);
    properties.put("cacheServerConfiguration", true);
    properties.put("useLocalSessionState", true);
    properties.put("elideSetAutoCommits", true);
    properties.put("alwaysSendSetIsolation", false);
    return properties;
  }
}