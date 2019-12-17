package net.tnemc.plugincore.core.common.data.sql;

import net.tnemc.plugincore.core.common.data.SQLDatabase;
import net.tnemc.plugincore.core.managers.DataManager;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Daniel Vidmar aka creatorfromhell
 *
 */
public class MySQL extends SQLDatabase {

  public MySQL(DataManager manager) {
    super(manager);
  }

  @Override
  public String getDriver() {
    return "com.mysql.jdbc.Driver";
  }

  @Override
  public Boolean dataSource() {
    return false;
  }

  @Override
  public String dataSourceURL() {
    return "com.mysql.jdbc.jdbc2.optional.MysqlDataSource";
  }

  @Override
  public String getURL(String file, String host, int port, String database) {
    return "jdbc:mysql://" + host + ":" + port + "/" +
        database;
  }

  @Override
  public Map<String, Object> hikariProperties() {
    Map<String, Object> properties = new HashMap<>();
    properties.put("autoReconnect", true);
    properties.put("cachePrepStmts", true);
    properties.put("prepStmtCacheSize", 250);
    properties.put("prepStmtCacheSqlLimit", 2048);
    properties.put("rewriteBatchedStatements", true);
    properties.put("useServerPrepStmts", true);
    properties.put("cacheResultSetMetadata", true);
    properties.put("cacheServerConfiguration", true);
    properties.put("useSSL", manager.isSsl());
    return properties;
  }
}