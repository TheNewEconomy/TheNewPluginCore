package net.tnemc.plugincore.core.api;
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

import net.tnemc.plugincore.core.api.callback.Callback;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * CallbackManager used to manage specific callbacks.
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public class CallbackManager {

  protected final Map<String, CallbackEntry> callbacks = new HashMap<>();

  public CallbackManager(final CallbackProvider provider) {

    provider.init(this);
    initDefaultCallbacks();
  }

  protected void initDefaultCallbacks() {
  }

  /**
   * Used to add a new callback into the system.
   * @param identifier The identifier of the callback to add.
   * @param entry The {@link CallbackEntry}, which manages the consumers.
   */
  public void addCallback(final String identifier, CallbackEntry entry) {
    this.callbacks.put(identifier, entry);
  }

  /**
   * Used to add a consumer for this callback.
   * @param name The name of the callback to attach this consumer to.
   * @param consumer The consumer to add for this callback. This is an implementation of the
   *                 {@link Function} interface, which accepts an {@link Callback} parameter and
   *                 returns a boolean value, which indicates if the "event" should be cancelled or
   *                 not.
   */
  public void addConsumer(final String name, Function<Callback, Boolean> consumer) {

    if(this.callbacks.containsKey(name)) {
      this.callbacks.get(name).addConsumer(consumer);
    }
  }

  /**
   * Used to call all the consumers for this callback.
   * @param callback The callback class for this consumer.
   *
   * @return This returns a boolean value, which equates to whether this event is cancelled or not.
   */
  public boolean call(Callback callback) {

    if(this.callbacks.containsKey(callback.name())) {
      return this.callbacks.get(callback.name()).call(callback);
    }
    return false;
  }
}