package net.tnemc.plugincore.core.module;
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

import net.tnemc.menu.core.MenuHandler;
import net.tnemc.plugincore.PluginCore;
import net.tnemc.plugincore.core.api.CallbackEntry;
import net.tnemc.plugincore.core.api.CallbackManager;
import net.tnemc.plugincore.core.api.callback.Callback;
import net.tnemc.plugincore.core.io.storage.StorageManager;
import revxrsal.commands.CommandHandler;
import revxrsal.commands.orphan.OrphanCommand;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Module represents an add-on module for TNE.
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public interface Module {

  /**
   * Called after the {@link PluginCore#enable()} method is called.
   */
  void enable(PluginCore core);

  /**
   * Called when the {@link PluginCore#onDisable()} method is called.
   */
  void disable(PluginCore core);

  /**
   * Called when the configurations are initialized and loaded.
   *
   * @param directory The plugin's configuration directory.
   */
  void initConfigurations(File directory);

  /**
   * Called when the {@link StorageManager storage manager} runs its backup method.
   * @param manager The {@link StorageManager storage manager} instance.
   */
  void backup(StorageManager manager);

  /**
   * Called when the {@link StorageManager storage manager} runs its reset method.
   * @param manager The {@link StorageManager storage manager} instance.
   */
  void reset(StorageManager manager);

  /**
   * Called when the {@link StorageManager storage manager} is enabled, and a connection is established.
   * @param manager The {@link StorageManager storage manager} instance.
   */
  void enableSave(StorageManager manager);

  /**
   * Called when the {@link MenuHandler menu handler} is enabled.
   * @param menuHandler The menu handler.
   */
  void enableMenu(MenuHandler menuHandler);

  /**
   * Called after the default TNE Commands are registered.
   * @param handler The {@link CommandHandler} that the commands are registered to.
   */
  void registerCommands(CommandHandler handler);

  /**
   * Used to register sub commands onto the exist /money command set.
   */
  List<OrphanCommand> registerMoneySub();

  /**
   * Used to register sub commands onto the exist /transaction command set.
   */
  List<OrphanCommand> registerTransactionSub();

  /**
   * Used to register sub commands onto the exist /tne command set.
   */
  List<OrphanCommand> registerAdminSub();
  
  /**
   * Called after the {@link CallbackManager} is initialized. This method will
   * register new callbacks with the manager automatically.
   *
   * @return A map containing the callbacks to register where the key is the callback name and the
   * value is the {@link CallbackEntry} function.
   */
  default Map<String, CallbackEntry> registerCallbacks() {
    return new HashMap<>();
  }

  /**
   * Called after the {@link CallbackManager} is initialized. This method will
   * register the callback listeners with the manager automatically.
   *
   * @return A map containing the listeners to register where the key is the callback name and the
   * value is the listener function.
   */
  default Map<String, Function<Callback, Boolean>> registerListeners() {
    return new HashMap<>();
  }
}