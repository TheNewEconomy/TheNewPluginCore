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
import net.tnemc.plugincore.core.io.storage.engine.StandardSQL;

public class Oracle extends StandardSQL {

  public Oracle(Dialect dialect) {

    super(dialect);
  }

  /**
   * The name of this engine.
   *
   * @return The engine name.
   */
  @Override
  public String name() {

    return "oracle";
  }

  @Override
  public String[] driver() {

    return new String[]{
            "oracle.jdbc.driver.OracleDriver"
    };
  }

  @Override
  public String[] dataSource() {

    return new String[]{
            "oracle.jdbc.pool.OracleDataSource"
    };
  }

  @Override
  public String url(String file, String host, int port, String database) {

    return "jdbc:oracle:thin:@" + host + ":" + port + ":" + database;
  }
}