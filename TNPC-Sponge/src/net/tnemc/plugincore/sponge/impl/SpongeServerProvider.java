package net.tnemc.plugincore.sponge.impl;

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

import net.tnemc.item.AbstractItemStack;
import net.tnemc.plugincore.core.compatibility.CmdSource;
import net.tnemc.plugincore.core.compatibility.LogProvider;
import net.tnemc.plugincore.core.compatibility.PlayerProvider;
import net.tnemc.plugincore.core.compatibility.ProxyProvider;
import net.tnemc.plugincore.core.compatibility.ServerConnector;
import net.tnemc.plugincore.core.compatibility.helper.CraftingRecipe;
import net.tnemc.plugincore.core.compatibility.scheduler.SchedulerProvider;
import net.tnemc.plugincore.sponge.SpongePluginCore;
import net.tnemc.plugincore.sponge.impl.scheduler.SpongeScheduler;
import net.tnemc.sponge.SpongeItemCalculationsProvider;
import net.tnemc.sponge.SpongeItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.util.Nameable;
import org.spongepowered.api.world.DefaultWorldKeys;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.sponge.actor.SpongeCommandActor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * SpongeServerProvider
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public class SpongeServerProvider implements ServerConnector {

  private final SpongeItemCalculationsProvider calc = new SpongeItemCalculationsProvider();
  private final SpongeProxyProvider proxy = new SpongeProxyProvider();

  private final SpongeScheduler scheduler;

  protected String world = null;

  public SpongeServerProvider() {

    this.scheduler = new SpongeScheduler();
  }

  @Override
  public String name() {

    return "sponge";
  }

  /**
   * Used to replace placeholders from a string.
   *
   * @param player  The player to use for the placeholder replacement.
   * @param message The message to replace placeholders in.
   *
   * @return The string after placeholders have been replaced.
   */
  @Override
  public String replacePlaceholder(final UUID player, final String message) {

    return message;
  }

  /**
   * The proxy provider to use for this implementation.
   *
   * @return The proxy provider to use for this implementation.
   */
  @Override
  public ProxyProvider proxy() {

    return proxy;
  }

  /**
   * Used to convert an {@link CommandActor} to a {@link CmdSource}.
   *
   * @param actor The command actor.
   *
   * @return The {@link CmdSource} for this actor.
   */
  @Override
  public CmdSource<?> source(@NotNull final CommandActor actor) {

    return new SpongeCMDSource((SpongeCommandActor)actor);
  }

  /**
   * Retrieves a set of online player names.
   *
   * @return Set of online player names.
   */
  @Override
  public Set<String> onlinePlayersList() {

    return Sponge.server().onlinePlayers().stream()
            .map(Nameable::name)
            .collect(Collectors.toSet());
  }

  /**
   * Used to get the amount of online players.
   *
   * @return The amount of online players.
   */
  @Override
  public int onlinePlayers() {

    return Sponge.server().onlinePlayers().size();
  }

  /**
   * Attempts to find a {@link PlayerProvider player} based on an {@link UUID identifier}.
   *
   * @param identifier The identifier
   *
   * @return An Optional containing the located {@link PlayerProvider player}, or an empty Optional
   * if no player is located.
   */
  @Override
  public Optional<PlayerProvider> findPlayer(@NotNull final UUID identifier) {

    final Optional<ServerPlayer> player = Sponge.server().player(identifier);
    return player.map(value->new SpongePlayerProvider(value.user(), SpongePluginCore.instance().getContainer()));
  }

  /**
   * This is used to return an instance of an {@link PlayerProvider player} based on the provided
   * instance's player object.
   *
   * @param player The instance of the player.
   *
   * @return The initialized {@link PlayerProvider player object}.
   */
  @Override
  public PlayerProvider initializePlayer(@NotNull final Object player) {

    if(player instanceof final ServerPlayer playerObj) {
      return new SpongePlayerProvider(playerObj.user(), SpongePluginCore.instance().getContainer());
    }
    return null;
  }

  /**
   * Used to determine if this player has played on this server before.
   *
   * @param identifier The {@link UUID} that is associated with the player.
   *
   * @return True if the player has played on the server before, otherwise false.
   */
  @Override
  public boolean playedBefore(final UUID identifier) {

    final Optional<ServerPlayer> player = Sponge.server().player(identifier);
    return player.map(ServerPlayer::hasPlayedBefore).orElse(false);
  }

  /**
   * Used to determine if a player with the specified username has played before.
   *
   * @param name The username to search for.
   *
   * @return True if someone with the specified username has played before, otherwise false.
   */
  @Override
  public boolean playedBefore(final String name) {

    final Optional<ServerPlayer> player = Sponge.server().player(name);
    return player.map(ServerPlayer::hasPlayedBefore).orElse(false);
  }

  /**
   * Used to determine if a player with the specified username is online.
   *
   * @param name The username to search for.
   *
   * @return True if someone with the specified username is online.
   */
  @Override
  public boolean online(final String name) {

    try {

      final Optional<ServerPlayer> player = Sponge.server().player(UUID.fromString(name));
      return player.map(ServerPlayer::isOnline).orElse(false);
    } catch(final Exception e) {

      final Optional<ServerPlayer> player = Sponge.server().player(name);
      return player.map(ServerPlayer::isOnline).orElse(false);
    }
  }

  @Override
  public Optional<UUID> fromName(final String name) {

    return Optional.empty();
  }

  /**
   * Used to locate a username for a specific name. This could be called from either a primary or
   * secondary thread, and should not call back to the Mojang API ever.
   *
   * @param id The {@link UUID} to use for the search.
   *
   * @return An optional containing the name if exists, otherwise false.
   */
  @Override
  public Optional<String> fromID(final UUID id) {

    return Optional.empty();
  }

  /**
   * Returns the name of the default world.
   *
   * @return The name of the default world.
   */
  @Override
  public String defaultWorld() {

    if(world == null) {
      world = Sponge.server().worldManager().world(DefaultWorldKeys.DEFAULT).get().key().asString();
    }
    return world;
  }

  /**
   * Used to replace colour codes in a string.
   *
   * @param string The string to format.
   * @param strip  If true, the color codes are striped from the string.
   *
   * @return The formatted string.
   */
  @Override
  public String replaceColours(final String string, final boolean strip) {

    return string;
  }

  @Override
  public AbstractItemStack<?> stackBuilder() {

    return new SpongeItemStack();
  }

  @Override
  public void saveResource(String resourcePath, final boolean replace) {

    if(resourcePath != null && !resourcePath.isEmpty()) {

      resourcePath = resourcePath.replace('\\', '/');

      final LogProvider logger = SpongePluginCore.log();
      final InputStream in = this.getResource(resourcePath);
      if(in == null) {

        throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found in the jar.");
      } else {

        final File outFile = new File(SpongePluginCore.directory(), resourcePath);
        final int lastIndex = resourcePath.lastIndexOf(47);
        final File outDir = new File(SpongePluginCore.directory(), resourcePath.substring(0, Math.max(lastIndex, 0)));
        if(!outDir.exists()) {
          outDir.mkdirs();
        }

        try {
          if(outFile.exists() && !replace) {
          } else {

            final OutputStream out = new FileOutputStream(outFile);
            final byte[] buf = new byte[1024];

            int len;
            while((len = in.read(buf)) > 0) {
              out.write(buf, 0, len);
            }

            out.close();
            in.close();
          }
        } catch(final IOException ignore) {
          logger.error("Could not save " + outFile.getName() + " to " + outFile);
        }

      }
    } else {
      throw new IllegalArgumentException("ResourcePath cannot be null or empty");
    }
  }

  /**
   * Provides this implementation's {@link SchedulerProvider scheduler}.
   *
   * @return The scheduler for this implementation.
   */
  @Override
  public SpongeScheduler scheduler() {

    return scheduler;
  }

  /**
   * Used to register a crafting recipe to the server.
   *
   * @param key    The key for the crafting recipe to be registered.
   * @param recipe The crafting recipe to register.
   *
   * @see CraftingRecipe
   */
  @Override
  public void registerCrafting(@NotNull final String key, @NotNull final CraftingRecipe recipe) {
    //TODO: Sponge Register crafting
  }

  @Override
  public SpongeItemCalculationsProvider calculations() {

    return calc;
  }

  @Override
  public @Nullable InputStream getResource(@NotNull final String filename) {

    try {
      final URL url = this.getClass().getClassLoader().getResource(filename);
      if(url == null) {
        return null;
      } else {
        final URLConnection connection = url.openConnection();
        connection.setUseCaches(false);
        return connection.getInputStream();
      }
    } catch(final IOException var4) {
      return null;
    }
  }
}
