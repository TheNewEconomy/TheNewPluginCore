package net.tnemc.plugincore.core.callback;
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

import net.kyori.adventure.key.Key;
import net.tnemc.plugincore.api.callback.Callback;
import net.tnemc.plugincore.api.callback.CallbackListener;
import net.tnemc.plugincore.api.callback.CallbackService;

import java.util.HashMap;
import java.util.Map;

/**
 * CallbackManager used to manage specific callbacks.
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public final class StandardCallbackService implements CallbackService {

  private final Map<Key, CallbackEntry<?>> callbacks = new HashMap<>();

  @Override
  public <T extends Callback> void register(final Key key, final Class<T> type) {

    callbacks.put(key, new CallbackEntry<>(type));
  }

  @Override
  public <T extends Callback> void listen(final Key key, final Class<T> type, final CallbackListener<T> listener) {

    final CallbackEntry<?> entry = callbacks.get(key);

    if(entry == null) {
      throw new IllegalArgumentException("No callback registered for key: " + key.asString());
    }

    if(!entry.type().equals(type)) {
      throw new IllegalArgumentException("Callback type does not match registered type for key: " + key.asString());
    }

    addListener(entry, listener);
  }

  @Override
  public void call(final Callback callback) {

    final CallbackEntry<?> entry = callbacks.get(callback.key());

    if(entry != null) {
      entry.call(callback);
    }
  }

  @SuppressWarnings("unchecked")
  private <T extends Callback> void addListener(final CallbackEntry<?> entry, final CallbackListener<T> listener) {

    ((CallbackEntry<T>)entry).addListener(listener);
  }
}