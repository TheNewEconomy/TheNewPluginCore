package net.tnemc.plugincore.core.callback;
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


import net.tnemc.plugincore.api.callback.CallbackListener;
import net.tnemc.plugincore.api.callback.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * CallbackEntry represents a class that serves as an entry for a specific callback.
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public class CallbackEntry<T extends Callback> {

  private final List<CallbackListener<T>> listeners = new ArrayList<>();

  private final Class<T> type;

  CallbackEntry(final Class<T> type) {

    this.type = type;
  }

  void addListener(final CallbackListener<T> listener) {

    listeners.add(listener);
  }

  void call(final Callback callback) {

    if(!type.isInstance(callback)) {
      return;
    }

    final T typed = type.cast(callback);

    for(final CallbackListener<T> listener : listeners) {
      listener.handle(typed);
    }
  }

  Class<T> type() {

    return type;
  }
}