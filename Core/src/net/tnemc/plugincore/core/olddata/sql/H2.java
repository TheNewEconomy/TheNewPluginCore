package net.tnemc.plugincore.core.olddata.sql;


import net.tnemc.plugincore.core.olddata.SQLDatabase;
import net.tnemc.plugincore.core.managers.DataManager;

public class H2 extends SQLDatabase {

  public H2(DataManager manager) {
    super(manager);
  }

  @Override
  public String getDriver() {
    return "org.h2.Driver";
  }

  @Override
  public Boolean dataSource() {
    return false;
  }

  @Override
  public String dataSourceURL() {
    return "org.h2.jdbcx.JdbcDataSource";
  }

  @Override
  public String getURL(String file, String host, int port, String database) {
    return "jdbc:h2:file:" + file + ";mode=MySQL;DB_CLOSE_ON_EXIT=TRUE;FILE_LOCK=NO";
  }
}