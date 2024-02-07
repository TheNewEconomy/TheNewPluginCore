package net.tnemc.plugincore.core;
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

import net.tnemc.menu.core.MenuHandler;
import net.tnemc.plugincore.PluginCore;
import net.tnemc.plugincore.core.api.CallbackManager;
import net.tnemc.plugincore.core.io.storage.StorageManager;
import net.tnemc.plugincore.core.utils.UpdateChecker;
import revxrsal.commands.CommandHandler;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.command.ExecutableCommand;

/**
 * PluginEngine
 *
 * @author creatorfromhell
 * @since 0.0.1.0
 */
public abstract class PluginEngine {

  protected StorageManager storage;
  protected CommandHandler command;

  protected MenuHandler menuHandler;

  protected UpdateChecker updateChecker = null;

  //Phase-related methods.
  //TODO: Early onEnable, middle, end

  //TODO: onDisable


  /**
   * @return The current version for this plugin.
   */
  public abstract String version();

  /**
   * @return The build for this version.
   */
  public abstract String build();

  public abstract void registerConfigs();

  public abstract void registerPluginChannels();

  public abstract void registerStorage();

  /**
   * Used to register the command handlers.
   */
  public abstract void registerCommandHandler();

  public abstract String commandHelpWriter(ExecutableCommand command, CommandActor actor);

  /**
   * Used to register commands.
   */
  public abstract void registerCommands();

  public abstract void registerMenuHandler();

  /**
   * Used to register {@link net.tnemc.plugincore.core.api.callback.Callback Callbacks} during initialization.
   */
  public abstract void registerCallbacks(CallbackManager callbackManager);

  public void registerUpdateChecker() {
    this.updateChecker = new UpdateChecker();

    PluginCore.log().inform("Build Stability: " + this.updateChecker.stable());

    if(this.updateChecker.needsUpdate()) {
      PluginCore.log().inform("Update Available! Latest: " + this.updateChecker.getBuild());
    }
  }

  public StorageManager storage() {
    return storage;
  }

  public CommandHandler command() {
    return command;
  }

  public MenuHandler menu() {
    return menuHandler;
  }

  public UpdateChecker update() {
    return updateChecker;
  }

  public void postConfigs() {
  }

  public void postStorage() {
  }

  public void postCommands() {
  }

  public void postEnable() {
  }

  public void postDisable() {
  }
}