package net.tnemc.plugincore;

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
import net.tnemc.plugincore.core.api.CallbackManager;
import net.tnemc.plugincore.core.api.CallbackProvider;
import net.tnemc.plugincore.core.channel.ChannelMessageManager;
import net.tnemc.plugincore.core.compatibility.LogProvider;
import net.tnemc.plugincore.core.compatibility.ServerConnector;
import net.tnemc.plugincore.core.compatibility.log.DebugLevel;
import net.tnemc.plugincore.core.id.UUIDProvider;
import net.tnemc.plugincore.core.id.impl.provider.BaseUUIDProvider;
import net.tnemc.plugincore.core.io.message.MessageHandler;
import net.tnemc.plugincore.core.io.message.TranslationProvider;
import net.tnemc.plugincore.core.io.storage.StorageManager;
import net.tnemc.plugincore.core.module.ModuleLoader;
import net.tnemc.plugincore.core.module.cache.ModuleFileCache;
import net.tnemc.plugincore.core.utils.UpdateChecker;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.CommandHandler;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.command.ExecutableCommand;

import java.io.File;
import java.util.UUID;
import java.util.regex.Pattern;
public abstract class PluginCore {

  /*
   * Core final variables utilized within TNPC.
   */
  public static final Pattern UUID_MATCHER_PATTERN = Pattern.compile("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})");
  public static final Pattern USERNAME_MATCHER_PATTERN = Pattern.compile("^\\w*$");

  /* Core non-final variables utilized within TNPC as settings */
  protected File directory;

  //The DebugLevel that the server is currently running in.
  protected DebugLevel level = DebugLevel.STANDARD;

  /* Key Managers and Object instances utilized with TNE */

  //General Key Object Instances
  protected LogProvider logger;

  //Manager Instances
  protected ServerConnector server;
  protected StorageManager storage;
  protected UUIDProvider uuidProvider;
  protected CommandHandler command;
  private final MessageHandler messenger;

  protected MenuHandler menuHandler;

  /* Plugin Instance */
  private static PluginCore instance;

  protected CallbackManager callbackManager;
  protected ChannelMessageManager channelMessageManager;

  protected ModuleLoader loader;
  protected ModuleFileCache moduleCache;

  private boolean enabled = false;

  protected UpdateChecker updateChecker = null;

  protected UUID serverID;

  public PluginCore(ServerConnector server, LogProvider logger, TranslationProvider provider, CallbackProvider callbackProvider) {
    this.server = server;
    this.logger = logger;
    this.messenger = new MessageHandler(provider);
    this.callbackManager = new CallbackManager(callbackProvider);
  }

  public static void setInstance(PluginCore core) {
    if(instance == null) {
      instance = core;
    } else {
      throw new IllegalStateException("PluginCore has already been initiated. Please refrain from attempting" +
              "to modify the instance variable.");
    }
  }

  public void enable() {
    if(!enabled) {

      this.enabled = true;
      this.loader = new ModuleLoader();
      onEnable();

    } else {
      throw new IllegalStateException("PluginCore has already been enabled!");
    }
  }

  /**
   * Used to enable the core. This should contain things that can't be initialized until after the
   * server software is operational.
   */
  protected void onEnable() {

    if(!directory.exists()) {
      final boolean created = directory.mkdir();
      if(!created) {
        logger.error("Failed to create plugin directory. Disabling plugin.", DebugLevel.OFF);
        return;
      }
    }
    this.serverID = UUID.randomUUID();

    this.uuidProvider = new BaseUUIDProvider();

    this.registerConfigs();

    //Load our modules
    loader.load();

    //Call onEnable for all modules loaded.
    loader.getModules().values().forEach((moduleWrapper -> moduleWrapper.getModule().enable(this)));

    //Call initConfigurations for all modules loaded.
    loader.getModules().values().forEach((moduleWrapper -> moduleWrapper.getModule().initConfigurations(directory)));

    this.registerCallbacks();

    //Register the callback listeners and callbacks for the modules
    loader.getModules().values().forEach((moduleWrapper ->{
      moduleWrapper.getModule().registerCallbacks().forEach((key, entry)->{
        callbackManager.addCallback(key, entry);
      });

      moduleWrapper.getModule().registerListeners().forEach((key, function)->{
        callbackManager.addConsumer(key, function);
      });
    }));

    this.channelMessageManager = new ChannelMessageManager();

    this.registerStorage();
    if(this.storage == null) {
      logger.warning("Storage engine not initialized, proceeding without storage!", DebugLevel.OFF);
    } else {
      if(! this.storage.meetsRequirement()) {
        logger.error("This server does not meet SQL requirements needed!", DebugLevel.OFF);
        return;
      }
    }

    //Call the enableSave method for all modules loaded.
    loader.getModules().values().forEach((moduleWrapper -> moduleWrapper.getModule().enableSave(this.storage)));

    //register our commands
    this.registerCommandHandler();

    //Register our help writer.
    command.setHelpWriter(this::commandHelpWriter);

    //Register our commands.
    this.registerCommands();

    //Call our command methods for the modules.
    loader.getModules().values().forEach((moduleWrapper ->{
      moduleWrapper.getModule().registerCommands(command);
    }));


    this.registerMenuHandler();

    this.moduleCache = new ModuleFileCache();

    this.updateChecker = new UpdateChecker();

    logger.inform("Build Stability: " + this.updateChecker.stable());

    if(this.updateChecker.needsUpdate()) {
      logger.inform("Update Available! Latest: " + this.updateChecker.getBuild());
    }
  }

  public void onDisable() {


    loader.getModules().values().forEach((moduleWrapper -> moduleWrapper.getModule().disable(this)));
  }

  /**
   * Used to register the command handlers.
   */
  public abstract void registerCommandHandler();

  public abstract void registerConfigs();

  public abstract void registerMenuHandler();

  public abstract void registerStorage();

  public abstract String commandHelpWriter(ExecutableCommand command, CommandActor actor);

  /**
   * Used to register commands.
   */
  public abstract void registerCommands();

  /**
   * Used to register {@link net.tnemc.plugincore.core.api.callback.Callback Callbacks} during initialization.
   */
  public abstract void registerCallbacks();

  /**
   * @return The current version for this plugin.
   */
  public abstract String version();

  /**
   * @return The build for this version.
   */
  public abstract String build();



  /**
   * The implementation's {@link LogProvider}.
   *
   * @return The log provider.
   */
  public static LogProvider log() {
    return instance.logger;
  }

  /**
   * The {@link StorageManager} we are utilizing.
   *
   * @return The {@link StorageManager}.
   */
  public static StorageManager storage() {
    return instance.storage;
  }

  /**
   * The {@link ServerConnector} for the implementation.
   * @return The {@link ServerConnector} for the implementation.
   */
  public static ServerConnector server() {
    return instance.server;
  }

  public static MessageHandler messenger() {
    return instance.messenger;
  }

  public static File directory() {
    return instance.directory;
  }

  public static CallbackManager callbacks() {
    return instance.callbackManager;
  }

  public ChannelMessageManager getChannelMessageManager() {
    return channelMessageManager;
  }

  public static ModuleLoader loader() {
    return instance.loader;
  }

  @Nullable
  public static UpdateChecker update() {
    return instance.updateChecker;
  }

  public ModuleFileCache moduleCache() {
    return moduleCache;
  }

  public DebugLevel getLevel() {
    return level;
  }

  public void setLevel(DebugLevel level) {
    this.level = level;
  }

  public CommandHandler command() {
    return command;
  }

  public static PluginCore instance() {
    return instance;
  }

  public static UUIDProvider uuidProvider() {
    return instance.uuidProvider;
  }

  public UUID getServerID() {
    return serverID;
  }

  public void setStorage(StorageManager storage) {
    this.storage = storage;
  }

  public void setCallbackManager(CallbackManager callbackManager) {
    this.callbackManager = callbackManager;
  }
}