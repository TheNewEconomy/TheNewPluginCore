package net.tnemc.plugincore.core.io.storage.connect;
/*
 * The New Plugin Core
 * Copyright (C) 2022 - 2025 Daniel "creatorfromhell" Vidmar
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

import com.datastax.oss.driver.api.core.CqlSession;
import net.tnemc.plugincore.core.io.storage.StorageConnector;
import net.tnemc.plugincore.core.io.storage.StorageManager;

import java.net.InetSocketAddress;

/**
 * CassandraConnector
 *
 * @author creatorfromhell
 * @since 1.0.0.2
 */
public class CassandraConnector implements StorageConnector<CqlSession> {

  private CqlSession session;

  @Override
  public void initialize() {

    session = CqlSession.builder()
            .addContactPoint(new InetSocketAddress(StorageManager.instance().settings().host(),
                                                   StorageManager.instance().settings().port()))
            .withLocalDatacenter("datacenter1")
            .build();
  }

  @Override
  public CqlSession connection() {

    return session;
  }
}