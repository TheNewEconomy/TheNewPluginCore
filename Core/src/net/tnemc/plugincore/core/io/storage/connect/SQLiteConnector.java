package net.tnemc.plugincore.core.io.storage.connect;
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

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.tnemc.plugincore.core.io.storage.StorageConnector;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * SQLiteConnector
 *
 * @author creatorfromhell
 * @since 1.0.0.2
 */
public class SQLiteConnector implements StorageConnector<Connection> {

  private final String dbPath;
  private HikariDataSource dataSource;

  public SQLiteConnector(final String dbPath) {

    this.dbPath = dbPath;
  }

  @Override
  public void initialize() {

    final HikariConfig config = new HikariConfig();
    config.setJdbcUrl("jdbc:sqlite:" + dbPath);
    config.setMaximumPoolSize(10);

    dataSource = new HikariDataSource(config);
  }

  @Override
  public Connection connection() throws SQLException {

    if(dataSource == null) initialize();

    return dataSource.getConnection();
  }
}