package net.tnemc.plugincore;

import net.tnemc.menu.core.MenuManager;
import net.tnemc.plugincore.core.api.CallbackManager;
import net.tnemc.plugincore.core.channel.ChannelMessageManager;
import net.tnemc.plugincore.core.compatibility.LogProvider;
import net.tnemc.plugincore.core.compatibility.ServerConnector;
import net.tnemc.plugincore.core.compatibility.log.DebugLevel;
import net.tnemc.plugincore.core.io.message.MessageHandler;
import net.tnemc.plugincore.core.io.message.TranslationProvider;
import net.tnemc.plugincore.core.io.message.translation.BaseTranslationProvider;
import net.tnemc.plugincore.core.io.storage.StorageManager;
import net.tnemc.plugincore.core.module.ModuleLoader;
import net.tnemc.plugincore.core.module.cache.ModuleFileCache;
import net.tnemc.plugincore.core.utils.UpdateChecker;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.CommandHandler;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.command.ExecutableCommand;
import revxrsal.commands.orphan.Orphans;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * Created by creatorfromhell.
 *
 * The New Plugin Core Minecraft Server Plugin
 *
 * All rights reserved.
 *
 * Some Details about what is acceptable use of this software:
 *
 * This project accepts user contributions.
 *
 * Direct redistribution of this software is not allowed without written permission. However,
 * compiling this project into your software to utilize it as a library is acceptable as long
 * as it's not used for commercial purposes.
 *
 * Commercial usage is allowed if a commercial usage license is bought and verification of the
 * purchase is able to be provided by both parties.
 *
 * By contributing to this software you agree that your rights to your contribution are handed
 * over to the owner of the project.
 */
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
  protected CommandHandler command;
  private final MessageHandler messenger;

  /* Plugin Instance */
  private static PluginCore instance;

  protected CallbackManager callbackManager;
  protected ChannelMessageManager channelMessageManager;

  protected ModuleLoader loader;
  protected ModuleFileCache moduleCache;

  private boolean enabled = false;

  protected UpdateChecker updateChecker = null;

  public PluginCore(ServerConnector server, LogProvider logger) {
    this.server = server;
    this.logger = logger;
    this.messenger = new MessageHandler(new BaseTranslationProvider());
  }

  public PluginCore(ServerConnector server, LogProvider logger, TranslationProvider provider) {
    this.server = server;
    this.logger = logger;
    this.messenger = new MessageHandler(provider);
  }

  public static void setInstance(PluginCore core) {
    if(instance == null) {
      instance = core;
    } else {
      throw new IllegalStateException("TNE has already been initiated. Please refrain from attempting" +
              "to modify the instance variable.");
    }
  }

  public void enable() {
    if(!enabled) {

      this.enabled = true;
      this.loader = new ModuleLoader();
      onEnable();

    } else {
      throw new IllegalStateException("TNE has already been enabled!");
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
        logger.error("Failed to create plugin directory. Disabling plugin.");
        return;
      }
    }

    //Load our modules
    loader.load();

    //Call onEnable for all modules loaded.
    loader.getModules().values().forEach((moduleWrapper -> moduleWrapper.getModule().enable(this)));

    //Call initConfigurations for all modules loaded.
    loader.getModules().values().forEach((moduleWrapper -> moduleWrapper.getModule().initConfigurations(directory)));

    this.callbackManager = new CallbackManager();

    registerCallbacks();

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

    this.storage = new StorageManager();

    if(!this.storage.meetsRequirement()) {
      logger.error("This server does not meet SQL requirements needed for TNE!", DebugLevel.OFF);
      return;
    }

    //Call the enableSave method for all modules loaded.
    loader.getModules().values().forEach((moduleWrapper -> moduleWrapper.getModule().enableSave(this.storage)));

    //register our commands
    registerCommandHandler();

    //Register our help writer.
    command.setHelpWriter(this::commandHelpWriter);

    //Register our commands.
    registerCommands();

    //Call our command methods for the modules.
    loader.getModules().values().forEach((moduleWrapper ->{
      moduleWrapper.getModule().registerCommands(command);

      moduleWrapper.getModule().registerMoneySub().forEach((orphan)->command.register(Orphans.path("money"), orphan));
      moduleWrapper.getModule().registerTransactionSub().forEach((orphan)->command.register(Orphans.path("transaction"), orphan));
      moduleWrapper.getModule().registerAdminSub().forEach((orphan)->command.register(Orphans.path("tne"), orphan));
    }));


    new MenuManager();
    /*MenuManager.instance().addMenu(new MyEcoMenu());
    MenuManager.instance().addMenu(new MyCurrencyMenu());
    MenuManager.instance().addMenu(new MyBalMenu());*/

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
}