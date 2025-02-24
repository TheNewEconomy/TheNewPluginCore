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

import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import net.tnemc.plugincore.PluginCore;
import net.tnemc.plugincore.core.compatibility.log.DebugLevel;
import net.tnemc.plugincore.core.io.storage.StorageConnector;
import org.bson.Document;

import java.util.Optional;

/**
 * MongoConnector
 *
 * @author creatorfromhell
 * @since 1.0.0.2
 */
public class MongoConnector implements StorageConnector<MongoDatabase> {

  private MongoClient client;
  private MongoDatabase database;
  private final String connectionString;
  private final String databaseName;

  public MongoConnector(final String connectionString, final String databaseName) {
    this.connectionString = connectionString;
    this.databaseName = databaseName;
  }

  @Override
  public void initialize() {
    if(client != null) {

      return;
    }

    try {

      client = MongoClients.create(connectionString);
      database = client.getDatabase(databaseName);
    } catch(final Exception e) {

      PluginCore.log().error("Failed to initialize MongoDB connection.", e, DebugLevel.STANDARD);
    }
  }

  @Override
  public MongoDatabase connection() {
    return database;
  }

  public Optional<InsertOneResult> insertData(final String collectionName, final Document document) {
    try {

      final MongoCollection<Document> collection = database.getCollection(collectionName);
      return Optional.of(collection.insertOne(document));
    } catch (final MongoWriteException e) {

      return Optional.empty();
    }
  }

  public Optional<Document> getData(final String collectionName, final String key, final Object value) {

    final MongoCollection<Document> collection = database.getCollection(collectionName);
    return Optional.ofNullable(collection.find(new Document(key, value)).first());
  }
}
