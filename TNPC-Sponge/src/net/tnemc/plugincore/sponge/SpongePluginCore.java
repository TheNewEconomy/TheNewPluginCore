package net.tnemc.plugincore.sponge;
/*
 * The New Kings
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

import com.google.inject.Inject;
import net.tnemc.plugincore.PluginCore;
import net.tnemc.plugincore.core.api.CallbackProvider;
import net.tnemc.plugincore.core.io.message.TranslationProvider;
import net.tnemc.plugincore.sponge.impl.SpongeLogProvider;
import net.tnemc.plugincore.sponge.impl.SpongeServerProvider;
import org.apache.logging.log4j.Logger;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.plugin.PluginContainer;
import revxrsal.commands.sponge.SpongeCommandHandler;

import java.nio.file.Path;

/**
 * SpongePluginCore
 *
 * @author creatorfromhell
 * @since 0.0.1.0
 */
public abstract class SpongePluginCore extends PluginCore {


  protected final PluginContainer container;

  private SpongePluginCore core;
  @Inject
  @ConfigDir(sharedRoot = false)
  private Path configDir;

  @Inject
  public SpongePluginCore(final PluginContainer container, final Logger log,
                          TranslationProvider provider, CallbackProvider callbackProvider) {
    super(new SpongeServerProvider(), new SpongeLogProvider(log), provider, callbackProvider);

    this.container = container;
    this.logger = new SpongeLogProvider(log);
    this.core = this;
    command = SpongeCommandHandler.create(container);
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