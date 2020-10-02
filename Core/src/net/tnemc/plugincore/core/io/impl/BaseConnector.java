package net.tnemc.plugincore.core.io.impl;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.tnemc.plugincore.core.io.DatabaseConnector;
import net.tnemc.plugincore.core.providers.DatabaseProvider;
import org.javalite.activejdbc.DB;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

public class BaseConnector implements DatabaseConnector {

  private HikariDataSource datasource;
  private DB database;

  /**
   * @return The DataSource based on the configuration of the {@link DatabaseProvider}.
   */
  @Override
  public DataSource dataSource(Map<String, Object> configurations) {
    if(database == null) {
      database = new DB((String)configurations.getOrDefault("connection_id", "TNPC"));
    }


    if(datasource == null) {

      HikariConfig config = new HikariConfig();

      for(Map.Entry<String, Object> entry : configurations.entrySet()) {
        config.addDataSourceProperty(entry.getKey(), entry.getValue());
      }

      datasource = new HikariDataSource(config);
    }

    return datasource;
  }

  /**
   * Used to get the SQL Connection. This will create a connect if one doesn't exist already.
   *
   * @return The {@link Connection} object.
   */
  @Override
  public Connection connection() {
    if(!database.hasConnection()) database.open(datasource);
    return database.getConnection();
  }

  /**
   * Used to open the connection.
   */
  @Override
  public void open() {
    if(!database.hasConnection()) database.open(datasource);
  }

  /**
   * Used to close the connection.
   */
  @Override
  public void close() {
    if(database.hasConnection()) database.close();
  }

  /**
   * Used to execute a query on this connector.
   * @param query The query string.
   * @param variables An Object array of the variables for the query string.
   * @return An {@link Optional} containing the {@link ResultSet} if successful, otherwise an empty
   * Optional.
   */
  @Override
  public Optional<ResultSet> query(final String query, final Object[] variables) {
    try(PreparedStatement statement = connection().prepareStatement(query)) {
      for(int i = 0; i < variables.length; i++) {
        statement.setObject((i + 1), variables[i]);
      }
      return Optional.ofNullable(statement.executeQuery());
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }


  /**
   * Used to execute an update on this connector.
   * @param query The query string.
   * @param variables An Object array of the variables for the query string.
   */
  @Override
  public void update(final String query, final Object[] variables) {
    open();
    try(PreparedStatement statement = connection().prepareStatement(query)) {

      for(int i = 0; i < variables.length; i++) {
        statement.setObject((i + 1), variables[i]);
      }
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    close();
  }
}