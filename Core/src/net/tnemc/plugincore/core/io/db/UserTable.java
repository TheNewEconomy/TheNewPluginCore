package net.tnemc.plugincore.core.io.db;

import net.tnemc.plugincore.core.io.db.model.Table;
import net.tnemc.plugincore.core.io.db.query.QueryBuilder;
import net.tnemc.plugincore.core.io.db.query.dialect.SQLDialect;

import java.sql.ResultSet;
import java.util.Optional;

public class UserTable implements Table {

  public UserTable(SQLDialect dialect) {

  }
  /**
   * @return The name of the table, this includes and prefixes that may precede it.
   */
  @Override
  public String name() {
    return null;
  }

  /**
   * @return The {@link SQLDialect} associated with this table.
   */
  @Override
  public SQLDialect dialect() {
    return null;
  }

  /**
   * Used to run a query on this table.
   *
   * @param builder The {@link QueryBuilder builder} to use for the query.
   *
   * @return An optional containing a {@link ResultSet} for the query if applicable.
   */
  @Override
  public Optional<ResultSet> query(QueryBuilder builder) {
    return Optional.empty();
  }

  /**
   * Used to do a union query on this table with another table.
   *
   * @param table The table to perform the union with.
   * @param columns The columns to union on.
   *
   * @return An optional containing a {@link ResultSet} for the query if applicable.
   */
  @Override
  public Optional<ResultSet> union(Table table, String... columns) {
    return Optional.empty();
  }
}
