package net.tnemc.plugincore.core;
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

import net.tnemc.item.providers.HelperMethods;
import net.tnemc.menu.core.MenuHandler;
import net.tnemc.plugincore.PluginCore;
import net.tnemc.plugincore.core.api.CallbackManager;
import net.tnemc.plugincore.core.component.Component;
import net.tnemc.plugincore.core.component.ComponentBuilder;
import net.tnemc.plugincore.core.io.storage.StorageManager;
import net.tnemc.plugincore.core.utils.UpdateChecker;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.CommandHandler;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.command.ExecutableCommand;

import java.util.HashMap;
import java.util.Map;

/**
 * PluginEngine
 *
 * @author creatorfromhell
 * @since 0.0.1.0
 */
public abstract class PluginEngine {

  protected Map<String, Component> components = new HashMap<>();
  protected Map<String, ComponentBuilder> builders = new HashMap<>();

  protected StorageManager storage;
  protected CommandHandler command;

  protected MenuHandler menuHandler;
  protected HelperMethods helperMethods;

  protected UpdateChecker updateChecker = null;

  //Phase-related methods.
  //TODO: Early onEnable, middle, end

  //TODO: onDisable

  public abstract String versionCheckSite();

  /**
   * @return The current version for this plugin.
   */
  public abstract String version();

  /**
   * @return The build for this version.
   */
  public abstract String build();

  public abstract void registerConfigs();

  /**
   * Initializes all components with the provided platform and version.
   *
   * @param platform the platform to initialize the components for
   * @param version the Minecraft version string to initialize the components with
   */
  public void initComponents(final Platform platform, final String version) {

    for(final Component component : components.values()) {

      component.initialize(platform, version);

      //register our builders during initialization
      for(final ComponentBuilder builder : component.initBuilders(platform, version)) {

        builders.put(builder.identifier(), builder);
      }
    }
  }

  /**
   * Initializes the registries for all components with the provided platform and version.
   *
   * @param platform the platform to initialize the registries for
   * @param version the Minecraft version string to initialize the registries with
   */
  public void initRegistries(final Platform platform, final String version) {

    for(final Component component : components.values()) {

      component.initRegistries(platform, version);
    }
  }

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

  public Map<String, Component> components() {
    return components;
  }

  public Map<String, ComponentBuilder> builders() {
    return builders;
  }

  /**
   * Retrieves the ComponentBuilder associated with the provided name, and returns a new instance for
   * building from.
   *
   * @param name the name of the ComponentBuilder to retrieve
   * @return the new instance of the ComponentBuilder if present in the builders map to start building
   * from, null otherwise
   */
  public @Nullable ComponentBuilder builder(final String name) {
    if(builders.containsKey(name)) {

      return builders.get(name).builder();
    }
    return null;
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

  public HelperMethods helper() {
    return helperMethods;
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