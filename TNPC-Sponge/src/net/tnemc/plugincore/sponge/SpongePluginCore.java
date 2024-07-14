package net.tnemc.plugincore.sponge;
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
import net.tnemc.plugincore.core.PluginEngine;
import net.tnemc.plugincore.core.api.CallbackProvider;
import net.tnemc.plugincore.core.compatibility.ServerConnector;
import net.tnemc.plugincore.core.io.message.TranslationProvider;
import net.tnemc.plugincore.sponge.impl.SpongeLogProvider;
import net.tnemc.plugincore.sponge.impl.SpongeServerProvider;
import org.apache.logging.log4j.Logger;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.plugin.PluginContainer;

/**
 * SpongePluginCore
 *
 * @author creatorfromhell
 * @since 0.0.1.0
 */
public class SpongePluginCore extends PluginCore {


  protected final PluginContainer container;

  public SpongePluginCore(final PluginContainer container, PluginEngine engine, final Logger log,
                          TranslationProvider provider, CallbackProvider callbackProvider) {
    this(container, engine, new SpongeServerProvider(), log, provider, callbackProvider);
  }

  public SpongePluginCore(final PluginContainer container, PluginEngine engine, ServerConnector connector,
                          final Logger log, TranslationProvider provider, CallbackProvider callbackProvider) {
    super(engine, connector, new SpongeLogProvider(log), provider, callbackProvider);

    setInstance(this);
    this.container = container;
    this.logger = new SpongeLogProvider(log);
    engine.registerCommandHandler();
    engine.registerCommands();
  }

  public static SpongePluginCore instance() {
    return (SpongePluginCore)PluginCore.instance();
  }

  public PluginContainer getContainer() {
    return container;
  }

  public static ResourceKey key(final String key) {
    final String[] split = key.split("\\:");

    final String namespace = (split.length >= 2)? split[0] : "minecraft";
    final String value = (split.length >= 2)? split[1] : split[0];
    return ResourceKey.of(namespace, value);
  }
}