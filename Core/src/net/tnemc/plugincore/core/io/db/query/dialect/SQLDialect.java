package net.tnemc.plugincore.core.io.db.query.dialect;

/**
 * Represents a SQL Dialect that will be used within a query. This class is utilized by the
 * {@link net.tnemc.plugincore.core.io.db.query.QueryBuilder} class for query building with
 * maximum readability without requiring constant static strings to be used. This also enables
 * us to add new dialects in the future without
 */

/**
 *  BalanceTable.insert
 */
public interface SQLDialect {

  String select(String table);

  String select(String table, String... columns);

  String select(String table, String where);

  String select(String table, String where, String... columns);

  String insert(String table, Object[] values, String... columns);
}