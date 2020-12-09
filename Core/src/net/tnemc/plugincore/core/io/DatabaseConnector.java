package net.tnemc.plugincore.core.io;

import net.tnemc.plugincore.core.providers.DatabaseProvider;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Optional;

/**
 * Created by creatorfromhell.
 *
 * The New Plugin Core Minecraft Server Plugin
 *
 * All rights reserved.
 *
 * Some Details about what is acceptable use of this software:
 *
 * This project accepts user contributions.
 *
 * Direct redistribution of this software is not allowed without written permission. However,
 * compiling this project into your software to utilize it as a library is acceptable as long
 * as it's not used for commercial purposes.
 *
 * Commercial usage is allowed if a commercial usage license is bought and verification of the
 * purchase is able to be provided by both parties.
 *
 * By contributing to this software you agree that your rights to your contribution are handed
 * over to the owner of the project.
 */
public interface DatabaseConnector {

  /**
   * @param configurations A map containing the configurations for the database.
   * @return The DataSource based on the configuration of the
   * {@link DatabaseProvider}.
   */
  DataSource dataSource(Map<String, Object> configurations);

  /**
   * Used to get the SQL Connection. This will create a connect if one doesn't exist already.
   * @return The {@link Connection} object.
   */
  Connection connection();

  /**
   * Used to open the connection.
   */
  void open();

  /**
   * Used to close the connection.
   */
  void close();

  /**
   * Used to execute a query on this connector.
   * @param query The query string.
   * @param variables An Object array of the variables for the query string.
   * @return An {@link Optional} containing the {@link ResultSet} if successful, otherwise an empty
   * Optional.
   */
  Optional<ResultSet> query(final String query, final Object[] variables);


  /**
   * Used to execute an update on this connector.
   * @param query The query string.
   * @param variables An Object array of the variables for the query string.
   */
  void update(final String query, final Object[] variables);
}