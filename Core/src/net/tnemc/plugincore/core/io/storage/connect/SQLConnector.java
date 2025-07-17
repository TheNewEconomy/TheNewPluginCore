package net.tnemc.plugincore.core.io.storage.connect;
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

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.tnemc.plugincore.PluginCore;
import net.tnemc.plugincore.core.compatibility.log.DebugLevel;
import net.tnemc.plugincore.core.io.storage.Dialect;
import net.tnemc.plugincore.core.io.storage.SQLEngine;
import net.tnemc.plugincore.core.io.storage.StorageConnector;
import net.tnemc.plugincore.core.io.storage.StorageEngine;
import net.tnemc.plugincore.core.io.storage.StorageManager;
import org.intellij.lang.annotations.Language;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * SQLConnector
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public class SQLConnector implements StorageConnector<Connection> {

  protected HikariDataSource dataSource;

  protected String sourceClass;
  protected String driverClass;

  /**
   * Used to initialize a connection to the specified {@link StorageEngine}
   */
  @Override
  public void initialize() {

    findDriverSource();

    final HikariConfig config = new HikariConfig();

    if(sourceClass != null) {
      config.setDataSourceClassName(sourceClass);
    }

    //String file, String host, int port, String database
    config.addDataSourceProperty("url",
                                 ((SQLEngine)StorageManager.instance().getEngine()).url(
                                         new File(PluginCore.directory(), StorageManager.instance().settings().fileName()).getAbsolutePath(),
                                         StorageManager.instance().settings().host(),
                                         StorageManager.instance().settings().port(),
                                         StorageManager.instance().settings().database()
                                                                                       ));

    config.addDataSourceProperty("user", StorageManager.instance().settings().user());
    config.addDataSourceProperty("password", StorageManager.instance().settings().password());

    config.setPoolName(StorageManager.instance().settings().poolName());
    config.setConnectionTestQuery("SELECT 1");
    config.setMaximumPoolSize(StorageManager.instance().settings().maxPool());
    config.setMaxLifetime(StorageManager.instance().settings().maxLife());
    config.setConnectionTimeout(StorageManager.instance().settings().timeout());

    for(final Map.Entry<String, Object> entry : ((SQLEngine)StorageManager.instance().getEngine()).properties().entrySet()) {
      config.addDataSourceProperty(entry.getKey(), entry.getValue());
    }

    this.dataSource = new HikariDataSource(config);
  }

  /**
   * Used to get the connection from the
   *
   * @return The connection.
   */
  @Override
  public Connection connection() throws SQLException {

    if(dataSource == null) initialize();
    return dataSource.getConnection();
  }

  public boolean checkVersion() {

    boolean result = true;

    if(dialect().requirement().equalsIgnoreCase("none")) return true;

    try(final Connection connection = connection()) {
      final DatabaseMetaData meta = connection.getMetaData();
      final String version = dialect().parseVersion(meta.getDatabaseProductVersion());

      PluginCore.log().debug("SQL Requirement: " + dialect().requirement(), DebugLevel.OFF);
      PluginCore.log().debug("SQL Version: " + version, DebugLevel.OFF);
      result = dialect().checkRequirement(version);

    } catch(final Exception ignore) {
      PluginCore.log().error("Issue attempting to access SQL Version.");
    }
    return result;
  }

  /**
   * Used to execute a prepared query.
   *
   * @param query     The query string.
   * @param variables An array of variables for the prepared statement.
   *
   * @return The {@link ResultSet}.
   */
  public ResultSet executeQuery(@Language("SQL") final String query, final Object[] variables) {

    try(final Connection connection = connection();
        final PreparedStatement statement = connection.prepareStatement(query)) {

      for(int i = 0; i < variables.length; i++) {
        statement.setObject((i + 1), variables[i]);
      }
      return statement.executeQuery();

    } catch(final SQLException e) {
      PluginCore.log().sqlError("", e, query, variables, DebugLevel.OFF);
    }
    return null;
  }

  /**
   * Used to execute a prepared update.
   *
   * @param query     The query string.
   * @param variables An array of variables for the prepared statement.
   *
   * @return True to indicate that the statement has executed successfully, otherwise false.
   */
  public int executeUpdate(@Language("SQL") final String query, final Object[] variables) {

    try(final Connection connection = connection();
        final PreparedStatement statement = connection.prepareStatement(query)) {

      for(int i = 0; i < variables.length; i++) {
        statement.setObject((i + 1), variables[i]);
      }
      return statement.executeUpdate();
    } catch(final SQLException e) {
      PluginCore.log().sqlError("", e, query, variables, DebugLevel.OFF);
    }
    return 0;
  }

  public Dialect dialect() {

    return ((SQLEngine)StorageManager.instance().getEngine()).dialect();
  }

  protected void findDriverSource(final SQLEngine engine) {

    for(final String source : engine.dataSource()) {

      if(sourceClass != null) {
        break;
      }

      try {

        Class.forName(source);

        this.sourceClass = source;
      } catch(final Exception ignore) { }
    }

    for(final String driver : engine.driver()) {

      if(driverClass != null) {
        break;
      }

      try {

        Class.forName(driver);

        this.driverClass = driver;
      } catch(final Exception ignore) { }
    }
  }

  protected void findDriverSource() {

    findDriverSource((SQLEngine)StorageManager.instance().getEngine());
  }
}