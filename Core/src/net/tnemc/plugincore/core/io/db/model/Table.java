package net.tnemc.plugincore.core.io.db.model;

import net.tnemc.plugincore.core.io.db.query.QueryBuilder;
import net.tnemc.plugincore.core.io.db.query.dialect.SQLDialect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Optional;

public interface Table {

  /**
   * @return A {@link LinkedHashMap} containing the columns that this table contains.
   */
  LinkedHashMap<String, Column> columns();

  /**
   * @return The name of the table, this includes and prefixes that may precede it.
   */
  String name();

  /**
   * @return The connection object that represents our connection to the database.
   */
  Connection connection();

  /**
   * @return The {@link SQLDialect} associated with this table.
   */
  SQLDialect dialect();

  /**
   * Attempts to drop this table from the MySQL database.
   * @return True if the table was successfully dropped, otherwise false.
   */
  boolean drop();

  /**
   * Used to run a query on this table.
   * @param builder The {@link QueryBuilder builder} to use for the query.
   * @return An optional containing a {@link ResultSet} for the query if applicable.
   */
  Optional<ResultSet> query(QueryBuilder builder);

  /**
   * Used to do a union query on this table with another table.
   * @param table The table to perform the union with.
   * @param columns The columns to union on.
   * @return An optional containing a {@link ResultSet} for the query if applicable.
   */
  Optional<ResultSet> union(Table table, String... columns);
}