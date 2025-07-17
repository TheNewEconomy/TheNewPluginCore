package net.tnemc.plugincore.core.io.storage.engine;
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

import net.tnemc.plugincore.core.io.storage.Datable;
import net.tnemc.plugincore.core.io.storage.Dialect;
import net.tnemc.plugincore.core.io.storage.SQLEngine;
import net.tnemc.plugincore.core.io.storage.StorageConnector;
import net.tnemc.plugincore.core.io.storage.StorageManager;
import net.tnemc.plugincore.core.io.storage.connect.SQLConnector;
import org.intellij.lang.annotations.Language;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * StandardSQL
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public abstract class StandardSQL implements SQLEngine {

  protected final Map<Class<?>, Datable<?>> datables = new HashMap<>();

  protected final Dialect dialect;
  protected final String prefix;

  public StandardSQL(final Dialect dialect) {

    this(StorageManager.instance().settings().prefix(), dialect);
  }

  public StandardSQL(final String prefix, final Dialect dialect) {

    this.dialect = dialect;
    this.prefix = prefix;
  }

  /**
   * Called after the connection is initialized, so we can do any actions that need done immediately
   * after connecting.
   */
  @Override
  public void initialize(final StorageConnector<?> connector) {

  }

  /**
   * The dialiect for this engine. This will be used for query purposes.
   *
   * @return The dialect for the engine.
   */
  @Override
  public Dialect dialect() {

    return dialect;
  }

  /**
   * Used to reset all data for this engine.
   *
   * @param connector The storage connector to use for this transaction.
   */
  @Override
  public void reset(final StorageConnector<?> connector) {

    @Language("SQL") final String truncateAll = "SELECT concat('TRUNCATE TABLE ',table_catalog,'.',table_schema,'.',table_name) AS query" +
                                                "FROM information_schema.tables " +
                                                "WHERE table_name LIKE '" + prefix + "%';";

    if(connector instanceof SQLConnector) {

      try(final ResultSet result = ((SQLConnector)connector).executeQuery(truncateAll, new Object[]{})) {

        while(result.next()) {

          ((SQLConnector)connector).executeUpdate(result.getString("query"), new Object[]{});
        }
      } catch(final SQLException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Used to back up all data in the database for this engine.
   *
   * @param connector The storage connector to use for this transaction.
   */
  @Override
  public void backup(final StorageConnector<?> connector) {

  }

  /**
   * Used to get the {@link Datable} classes for this engine.
   *
   * @return A map with the datables.
   */
  @Override
  public Map<Class<?>, Datable<?>> datables() {

    return datables;
  }
}