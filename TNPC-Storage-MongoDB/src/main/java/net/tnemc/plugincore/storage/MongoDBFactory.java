package net.tnemc.plugincore.storage;

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

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import net.tnemc.plugincore.api.storage.StorageFactory;
import net.tnemc.plugincore.api.storage.StorageProvider;

/**
 * MongoDBFactory
 *
 * @author creatorfromhell
 * @since 2.0.0.0
 */
public class MongoDBFactory implements StorageFactory<MongoDBStorageConfiguration> {

  @Override
  public String identifier() {

    return "mongodb";
  }

  @Override
  public StorageProvider create(final MongoDBStorageConfiguration configuration) {

    final MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(new ConnectionString(configuration.connectionString())).build();
    final MongoClient client = MongoClients.create(settings);

    return new MongoDBStorageProvider(client, client.getDatabase(configuration.database()));
  }
}