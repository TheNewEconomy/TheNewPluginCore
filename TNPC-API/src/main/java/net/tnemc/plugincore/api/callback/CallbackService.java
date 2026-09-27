package net.tnemc.plugincore.api.callback;

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

/**
 * CallbackService
 *
 * @author creatorfromhell
 * @since 2.0.0.0
 */
public interface CallbackService {

  /**
   * Registers a callback type.
   *
   * @param key  The callback key.
   * @param type The callback type.
   * @param <T>  The callback type.
   */
  <T extends Callback> void register(final Key key, final Class<T> type);

  /**
   * Registers a listener for a callback.
   *
   * @param key      The callback key.
   * @param type     The callback type.
   * @param listener The callback listener.
   * @param <T>      The callback type.
   */
  <T extends Callback> void listen(final Key key, final Class<T> type, final CallbackListener<T> listener);

  /**
   * Dispatches a callback to all registered listeners.
   *
   * @param callback The callback to dispatch.
   */
  void call(final Callback callback);
}