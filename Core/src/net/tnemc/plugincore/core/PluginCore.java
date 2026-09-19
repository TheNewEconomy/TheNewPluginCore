package net.tnemc.plugincore.core;

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

import net.tnemc.plugincore.api.PluginContext;
import net.tnemc.plugincore.api.callback.CallbackService;
import net.tnemc.plugincore.api.id.UUIDProvider;
import net.tnemc.plugincore.core.module.ModuleLoader;

/**
 * RevampedPluginCore
 *
 * @author creatorfromhell
 * @since 2.0.0.0
 */
public class PluginCore implements AutoCloseable {

  private final PluginContext context;
  private final PluginEngine engine;

  private final UUIDProvider uuidProvider;
  private final CallbackService callbackService;

  private final ModuleLoader moduleLoader;

  private boolean loaded;
  private boolean enabled;

  public PluginCore(final PluginContext context, final PluginEngine engine, final UUIDProvider uuidProvider,
                    final CallbackService callbackService, final ModuleLoader moduleLoader) {

    this.context = context;
    this.engine = engine;
    this.uuidProvider = uuidProvider;
    this.callbackService = callbackService;
    this.moduleLoader = moduleLoader;
  }

  public void load() {

    if(loaded) {
      throw new IllegalStateException("PluginCore has already been loaded!");
    }

    loaded = true;
  }

  public void enable() {

    if(!loaded) {
      throw new IllegalStateException("PluginCore must be loaded before it can be enabled!");
    }

    if(enabled) {
      throw new IllegalStateException("PluginCore has already been enabled!");
    }

    moduleLoader.loadAll();

    enabled = true;
  }

  @Override
  public void close() {

    if(!enabled) {
      return;
    }

    moduleLoader.close();

    enabled = false;
  }

  public PluginContext context() {

    return context;
  }

  public UUIDProvider uuidProvider() {

    return uuidProvider;
  }

  public CallbackService callbacks() {

    return callbackService;
  }

  public ModuleLoader modules() {

    return moduleLoader;
  }
}