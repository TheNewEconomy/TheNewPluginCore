package net.tnemc.plugincore.paper;
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
import net.tnemc.plugincore.core.Platform;
import net.tnemc.plugincore.core.PluginEngine;
import net.tnemc.plugincore.core.api.CallbackProvider;
import net.tnemc.plugincore.core.compatibility.ServerConnector;
import net.tnemc.plugincore.core.io.message.TranslationProvider;
import net.tnemc.plugincore.paper.impl.PaperLogProvider;
import net.tnemc.plugincore.paper.impl.PaperServerProvider;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * PaperPluginCore
 *
 * @author creatorfromhell
 * @since 0.0.1.0
 */
public class PaperPluginCore extends PluginCore {

  private final JavaPlugin plugin;

  public PaperPluginCore(final JavaPlugin plugin, final PluginEngine engine, final TranslationProvider provider,
                         final CallbackProvider callbackProvider) {

    this(plugin, engine, new PaperServerProvider(), provider, callbackProvider);
  }

  public PaperPluginCore(final JavaPlugin plugin, final PluginEngine engine, final ServerConnector connector,
                         final TranslationProvider provider, final CallbackProvider callbackProvider) {

    super(engine, connector, new PaperLogProvider(plugin.getLogger()), provider, callbackProvider, Platform.PAPER,
          Bukkit.getServer().getBukkitVersion().split("-")[0]);

    setInstance(this);
    this.plugin = plugin;
  }

  public static PaperPluginCore instance() {

    return (PaperPluginCore)PluginCore.instance();
  }

  @Override
  protected void onLoad() {

    this.directory = plugin.getDataFolder();

    super.onLoad();
  }

  /**
   * Used to enable the core. This should contain things that can't be initialized until after the
   * server software is operational.
   */
  @Override
  protected void onEnable() {

    super.onEnable();
  }

  public JavaPlugin getPlugin() {

    return plugin;
  }
}