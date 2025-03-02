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

import net.tnemc.plugincore.core.Platform;
import net.tnemc.plugincore.core.PluginEngine;
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
import revxrsal.commands.Lamp;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.orphan.Orphans;

import java.io.File;
import java.util.UUID;
import java.util.regex.Pattern;

public class PluginCore {

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

  protected PluginEngine engine;

  //Manager Instances
  protected ServerConnector server;
  protected UUIDProvider uuidProvider;
  private final MessageHandler messenger;

  /* Plugin Instance */
  private static PluginCore instance;

  protected CallbackManager callbackManager;
  protected ChannelMessageManager channelMessageManager;

  protected ModuleLoader loader;
  protected ModuleFileCache moduleCache;

  private boolean enabled = false;

  protected UUID serverID;

  protected Platform platform;
  protected String version;

  public PluginCore(final PluginEngine engine, final ServerConnector server, final LogProvider logger,
                    final TranslationProvider provider, final CallbackProvider callbackProvider,
                    final Platform platform, final String version) {
    this.server = server;
    this.logger = logger;
    this.engine = engine;
    this.messenger = new MessageHandler(provider);
    this.callbackManager = new CallbackManager(callbackProvider);

    this.platform = platform;
    this.version = version;
  }

  public static void setInstance(final PluginCore core) {
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

    this.engine.registerConfigs();

    this.engine.initComponents(platform, version);
    this.engine.initRegistries(platform, version);

    //Load our modules
    loader.load();

    //Call onEnable for all modules loaded.
    loader.getModules().values().forEach((moduleWrapper -> moduleWrapper.getModule().enable(this)));

    //Call initConfigurations for all modules loaded.
    loader.getModules().values().forEach((moduleWrapper -> moduleWrapper.getModule().initConfigurations(directory)));

    this.engine.registerCallbacks(callbackManager);

    //Register the callback listeners and callbacks for the modules
    loader.getModules().values().forEach((moduleWrapper ->{
      moduleWrapper.getModule().registerCallbacks().forEach((key, entry)->{
        callbackManager.addCallback(key, entry);
      });

      moduleWrapper.getModule().registerListeners().forEach((key, function)->{
        callbackManager.addConsumer(key, function);
      });
    }));

    this.engine.postConfigs();

    this.channelMessageManager = new ChannelMessageManager();

    this.engine.registerPluginChannels();

    this.channelMessageManager.register();

    this.engine.registerStorage();
    if(this.engine.storage() == null) {
      logger.warning("Storage engine not initialized, proceeding without storage!", DebugLevel.OFF);
    } else {
      if(!storage().meetsRequirement()) {
        logger.error("This server does not meet SQL requirements needed!", DebugLevel.OFF);
        return;
      }
    }

    this.engine.postStorage();

    //Call the enableSave method for all modules loaded.
    loader.getModules().values().forEach((moduleWrapper -> moduleWrapper.getModule().enableSave(this.engine.storage())));

    //register our commands
    this.engine.registerCommandHandler();

    //Register our help writer.
    //command().setHelpWriter(engine::commandHelpWriter);

    //Register our commands.
    this.engine.registerCommands();

    //Call our command methods for the modules.
    loader.getModules().values().forEach((moduleWrapper ->{
      moduleWrapper.getModule().registerCommands(this.engine.command());
    }));


    this.engine.postCommands();

    this.engine.registerMenuHandler();

    //Call enableMenu for all modules loaded.
    loader.getModules().values().forEach((moduleWrapper -> moduleWrapper.getModule().enableMenu(this.engine.menu())));

    this.moduleCache = new ModuleFileCache();

    this.engine.registerUpdateChecker();

    this.engine.postEnable();
  }

  public void registerModuleCommands(final Lamp<?> lamp) {
    loader.getModules().values().forEach((moduleWrapper -> moduleWrapper.getModule().registerAdminSub().forEach(orphanCommand -> {
      lamp.register(Orphans.path("tne").handler(orphanCommand));
    })));

    loader.getModules().values().forEach((moduleWrapper -> moduleWrapper.getModule().registerMoneySub().forEach(orphanCommand -> {
      lamp.register(Orphans.path("money").handler(orphanCommand));
    })));

    loader.getModules().values().forEach((moduleWrapper -> moduleWrapper.getModule().registerTransactionSub().forEach(orphanCommand -> {
      lamp.register(Orphans.path("transaction").handler(orphanCommand));
    })));
  }

  public void onDisable() {

    loader.getModules().values().forEach((moduleWrapper -> moduleWrapper.getModule().disable(this)));

    this.engine.postDisable();
  }

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
    return instance.engine.storage();
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
    return instance.engine.update();
  }

  public ModuleFileCache moduleCache() {
    return moduleCache;
  }

  public DebugLevel getLevel() {
    return level;
  }

  public void setLevel(final DebugLevel level) {
    this.level = level;
  }

  public Lamp.Builder<? extends CommandActor> command() {
    return engine.command();
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

  public void setServerID(final UUID serverID) {
    this.serverID = serverID;
  }

  public void setCallbackManager(final CallbackManager callbackManager) {
    this.callbackManager = callbackManager;
  }

  public static PluginEngine engine() {
    return instance.engine;
  }
}