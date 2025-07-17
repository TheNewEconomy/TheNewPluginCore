package net.tnemc.plugincore.core.io.storage;

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

import net.tnemc.plugincore.PluginCore;
import net.tnemc.plugincore.core.compatibility.log.DebugLevel;
import net.tnemc.plugincore.core.compatibility.scheduler.ChoreExecution;
import net.tnemc.plugincore.core.compatibility.scheduler.ChoreTime;
import net.tnemc.plugincore.core.io.redis.TNEJedisManager;
import net.tnemc.plugincore.core.io.storage.connect.SQLConnector;
import net.tnemc.plugincore.core.io.storage.engine.StorageSettings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * The manager, which manages everything related to storage. Manages: - Loading - Saving - Caching -
 * Connections
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public class StorageManager {

  private static StorageManager instance;
  private final StorageSettings settings;
  private final TNEJedisManager jedisManager;
  private StorageProvider provider;


  public StorageManager(final String engine, final StorageProvider provider,
                        final StorageSettings settings, final JedisPool pool) {

    instance = this;
    this.settings = settings;

    if(pool == null || settings.proxyType().equalsIgnoreCase("bungee")) {
      this.jedisManager = null;
    } else {
      this.jedisManager = new TNEJedisManager(pool);
    }

    this.provider = provider;

    initialize(engine);
  }

  public static StorageManager instance() {

    return instance;
  }

  public boolean meetsRequirement() {

    if(this.provider instanceof SQLConnector) {
      return ((SQLConnector)this.provider.connector()).checkVersion();
    }
    return true;
  }

  public void initialize(final String engine) {

    //Initialize our connection.
    this.provider.initialize(engine);
    this.provider.connector().initialize();
    this.provider.engine().initialize(this.provider.connector());
    this.provider.initialize();
  }

  public void sendProxyMessage(final String channel, final byte[] data) {

    switch(settings.proxyType().toLowerCase()) {
      case "redis" -> {
        if(jedisManager != null) {
          jedisManager.publish(channel, data);
        }
      }
      default -> PluginCore.server().proxy().send(channel, data);
    }
  }

  /**
   * Used to load this object.
   *
   * @param object     The class of the object to be loaded.
   * @param identifier The identifier used to identify the object to load.
   *
   * @return The object to load.
   */
  public <T> Optional<T> load(Class<? extends T> object, @NotNull final String identifier) {

    final Datable<T> data = (Datable<T>)provider.engine().datables().get(object);
    if(data != null) {
      return data.load(provider.connector(), identifier);
    }
    return Optional.empty();
  }

  /**
   * Used to load all objects of this type.
   *
   * @param object     The class of the object to be loaded.
   * @param identifier The identifier used to load objects, if they relate to a specific identifier,
   *                   otherwise this will be null.
   *
   * @return A collection containing the objects loaded.
   */
  public <T> Collection<T> loadAll(Class<? extends T> object, @Nullable final String identifier) {

    final Datable<T> data = (Datable<T>)provider.engine().datables().get(object);
    if(data != null) {
      return data.loadAll(provider.connector(), identifier);
    }
    return new ArrayList<>();
  }


  /**
   * Used to store this object.
   *
   * @param object     The object to be stored.
   * @param identifier An optional identifier for loading this object. Note: some Datables may
   *                   require this identifier.
   */
  public <T> void store(T object, @Nullable String identifier) {

    PluginCore.log().inform("Storing Datable of type: " + object.getClass().getName(), DebugLevel.DEVELOPER);
    final Datable<T> data = (Datable<T>)provider.engine().datables().get(object.getClass());
    if(data != null) {
      PluginCore.server().scheduler().createDelayedTask(()->data.store(provider.connector(), object, identifier),
                                                        new ChoreTime(0), ChoreExecution.SECONDARY);
    }
  }

  /**
   * Used to store all data for an identifier in TNE. This method is not switched over to a
   * secondary thread automatically. Please make sure to use wisely.
   */
  public void storeAll(@NotNull final String identifier) {

    provider.storeAll(identifier);
  }

  /**
   * Used to store all data in TNE.
   */
  public void storeAll() {

    provider.storeAll();
  }

  /**
   * Used to purge TNE data.
   */
  public void purge() {

    for(Datable<?> data : provider.engine().datables().values()) {
      PluginCore.server().scheduler().createDelayedTask(()->data.purge(provider.connector()), new ChoreTime(0), ChoreExecution.SECONDARY);
    }

    provider.purge();
  }

  /**
   * Used to reset all data in TNE.
   */
  public void reset() {
    //call the reset method for all modules.
    PluginCore.loader().getModules().values().forEach((moduleWrapper->moduleWrapper.getModule().reset(this)));

    PluginCore.server().scheduler().createDelayedTask(()->provider.engine().reset(provider.connector()), new ChoreTime(0), ChoreExecution.SECONDARY);

    provider.reset();
  }

  /**
   * Used to back up data that is currently in the database.
   *
   * @return True if the backup was successful, otherwise false.
   */
  public boolean backup() {
    //call the backup method for all modules.
    PluginCore.loader().getModules().values().forEach((moduleWrapper->moduleWrapper.getModule().backup(this)));

    PluginCore.server().scheduler().createDelayedTask(()->provider.engine().backup(provider.connector()), new ChoreTime(0), ChoreExecution.SECONDARY);

    provider.backup();
    return true;
  }

  public StorageEngine getEngine() {

    return provider.engine();
  }

  public StorageConnector<?> getConnector() {

    return provider.connector();
  }

  public StorageSettings settings() {

    return settings;
  }
}