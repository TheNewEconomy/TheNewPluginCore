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

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import net.tnemc.plugincore.api.storage.StorageProvider;
import org.bson.BsonDocument;
import org.bson.BsonInt64;

/**
 * MongoDBStorageProvider
 *
 * @author creatorfromhell
 * @since 2.0.0.0
 */
public class MongoDBStorageProvider implements StorageProvider {

  private final MongoClient client;
  private final MongoDatabase database;

  public MongoDBStorageProvider(final MongoClient client, final MongoDatabase database) {

    this.client = client;
    this.database = database;
  }

  @Override
  public String type() {

    return "mongodb";
  }

  @Override
  public boolean available() {

    try {

      database.runCommand(new BsonDocument("ping", new BsonInt64(1)));
      return true;
    } catch(final MongoException ignored) {

      return false;
    }
  }

  public MongoClient client() {

    return client;
  }

  public MongoDatabase database() {

    return database;
  }

  @Override
  public void close() {

    client.close();
  }
}