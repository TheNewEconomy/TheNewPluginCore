package net.tnemc.plugincore.core.storage;

/*
 * The New Plugin Core
 * Copyright (C) 2022 - 2026 Daniel "creatorfromhell" Vidmar
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

import cc.carm.lib.easysql.api.SQLManager;
import net.tnemc.plugincore.api.storage.StorageProvider;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * SQLStorageProvider
 *
 * @author creatorfromhell
 * @since 2.0.0.0
 */
public class SQLStorageProvider implements StorageProvider {

  private final String type;
  private final SQLManager manager;

  public SQLStorageProvider(final String type, final SQLManager manager) {

    this.type = type;
    this.manager = manager;
  }

  @Override
  public String type() {

    return type;
  }

  @Override
  public boolean available() {

    try(final Connection connection = manager.getConnection()) {

      return !connection.isClosed();
    } catch(final Exception ignored) {

      return false;
    }
  }

  public SQLManager manager() {

    return manager;
  }

  @Override
  public void close() {

    try {

      if (manager.getConnection().isClosed()) {
        return;
      }

      manager.getConnection().close();
    } catch(final SQLException ignored) {

    }
  }
}