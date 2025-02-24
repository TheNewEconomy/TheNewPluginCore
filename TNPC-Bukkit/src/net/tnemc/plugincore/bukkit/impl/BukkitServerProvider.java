package net.tnemc.plugincore.bukkit.impl;

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
import net.tnemc.item.bukkit.BukkitCalculationsProvider;
import net.tnemc.item.bukkit.BukkitItemStack;
import net.tnemc.plugincore.PluginCore;
import net.tnemc.plugincore.bukkit.BukkitPluginCore;
import net.tnemc.plugincore.bukkit.hook.PAPIParser;
import net.tnemc.plugincore.bukkit.impl.scheduler.BukkitScheduler;
import net.tnemc.plugincore.core.compatibility.CmdSource;
import net.tnemc.plugincore.core.compatibility.PlayerProvider;
import net.tnemc.plugincore.core.compatibility.ProxyProvider;
import net.tnemc.plugincore.core.compatibility.ServerConnector;
import net.tnemc.plugincore.core.compatibility.helper.CraftingRecipe;
import net.tnemc.plugincore.core.compatibility.scheduler.SchedulerProvider;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.command.CommandActor;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * BukkitServerProvider
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public class BukkitServerProvider implements ServerConnector {

  protected final BukkitCalculationsProvider calc = new BukkitCalculationsProvider();
  protected final BukkitProxyProvider proxy = new BukkitProxyProvider();

  protected final BukkitScheduler scheduler;

  protected String world = null;

  public BukkitServerProvider() {
    this.scheduler = new BukkitScheduler();
  }

  public BukkitServerProvider(final BukkitScheduler scheduler) {
    this.scheduler = scheduler;
  }

  public void setDefaultWorld(final String world) {
    this.world = world;
  }

  @Override
  public String name() {
    return "bukkit";
  }

  /**
   * Used to replace placeholders from a string.
   *
   * @param player The player to use for the placeholder replacement.
   * @param message The message to replace placeholders in.
   *
   * @return The string after placeholders have been replaced.
   */
  @Override
  public String replacePlaceholder(final UUID player, final String message) {

    if(player == null) return message;

    final Optional<PlayerProvider> playerOpt = PluginCore.server().findPlayer(player);
    if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI") && playerOpt.isPresent()
            && playerOpt.get() instanceof final BukkitPlayerProvider bukkitPlayer) {

      return PAPIParser.parse(bukkitPlayer, message);
    }
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
    return new BukkitCMDSource((BukkitCommandActor)actor);
  }

  /**
   * Used to get the amount of online players.
   *
   * @return The amount of online players.
   */
  @Override
  public int onlinePlayers() {
    return Bukkit.getOnlinePlayers().size();
  }

  /**
   * Attempts to find a {@link PlayerProvider player} based on an {@link UUID identifier}.
   *
   * @param identifier The identifier
   *
   * @return An Optional containing the located {@link PlayerProvider player}, or an empty
   * Optional if no player is located.
   */
  @Override
  public Optional<PlayerProvider> findPlayer(@NotNull final UUID identifier) {

    return Optional.of(BukkitPlayerProvider.find(identifier.toString()));
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
    if(player instanceof final Player playerObj) {
      return new BukkitPlayerProvider(playerObj);
    }
    return null;
  }

  /**
   * Used to determine if this player has played on this server before.
   *
   * @param uuid The {@link UUID} that is associated with the player.
   *
   * @return True if the player has played on the server before, otherwise false.
   */
  @Override
  public boolean playedBefore(final UUID uuid) {

    final OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
    return player.hasPlayedBefore();
  }

  /**
   * Used to determine if a player with the specified username has played
   * before.
   *
   * @param name The username to search for.
   *
   * @return True if someone with the specified username has played before,
   * otherwise false.
   */
  @Override
  public boolean playedBefore(final String name) {

    final OfflinePlayer player = Bukkit.getOfflinePlayer(name);
    return player.hasPlayedBefore();
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

      final UUID id = UUID.fromString(name);
      return Bukkit.getPlayer(id) != null;
    } catch(final Exception ignore) {
      return Bukkit.getPlayer(name) != null;
    }
  }

  @Override
  public Optional<UUID> fromName(final String name) {
    for(final OfflinePlayer player : Bukkit.getServer().getOfflinePlayers()) {
      if(player.getName() == null) continue;
      if(player.getName().equalsIgnoreCase(name)) {
        return Optional.of(player.getUniqueId());
      }
    }
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
    for(final OfflinePlayer player : Bukkit.getServer().getOfflinePlayers()) {
      if(player.getUniqueId().equals(id)) {
        return Optional.ofNullable(player.getName());
      }
    }
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
      world = Bukkit.getServer().getWorlds().get(0).getName();
    }
    return world;
    /*

    if(world == null) {

      final Properties props = new Properties();
      try {

        props.load(new FileInputStream(new File(".", "server.properties")));
      } catch(IOException ignore) {
      }
      world = props.getProperty("level-name");
    }
    return world;*/
  }

  /**
   * Determines if a plugin with the correlating name is currently installed.
   *
   * @param name The name to use for our check.
   *
   * @return True if a plugin with that name exists, otherwise false.
   */
  @Override
  public boolean pluginAvailable(final String name) {
    return Bukkit.getPluginManager().isPluginEnabled(name);
  }

  /**
   * Used to replace colour codes in a string.
   * @param string The string to format.
   * @param strip If true, the color codes are striped from the string.
   * @return The formatted string.
   */
  @Override
  public String replaceColours(final String string, final boolean strip) {
    if(strip) {
      return ChatColor.stripColor(string);
    }
    return ChatColor.translateAlternateColorCodes('&', string);
  }

  @Override
  public AbstractItemStack<?> stackBuilder() {
    return new BukkitItemStack();
  }

  @Override
  public void saveResource(final String path, final boolean replace) {
    BukkitPluginCore.instance().getPlugin().saveResource(path, replace);
  }

  /**
   * Retrieves an input stream for the specified filename from the resources.
   *
   * @param filename The name of the file to retrieve from the resources. Cannot be null.
   *
   * @return An input stream for the specified file, or null if the file is not found in the
   * resources.
   */
  @Override
  public @Nullable InputStream getResource(@NotNull final String filename) {
    return BukkitPluginCore.instance().getPlugin().getResource(filename);
  }

  /**
   * Provides this implementation's {@link SchedulerProvider scheduler}.
   *
   * @return The scheduler for this implementation.
   */
  @Override
  public SchedulerProvider<?> scheduler() {
    return scheduler;
  }

  /**
   * Used to register a crafting recipe to the server.
   *
   * @param key The key for the crafting recipe to be registered.
   * @param recipe The crafting recipe to register.
   *
   * @see CraftingRecipe
   */
  @Override
  public void registerCrafting(@NotNull final String key, @NotNull final CraftingRecipe recipe) {
    if(recipe.isShaped()) {
      ShapedRecipe shaped;

      try {
        shaped = new ShapedRecipe(new NamespacedKey(BukkitPluginCore.instance().getPlugin(), key), (ItemStack)recipe.getResult().locale());
      } catch(final Exception ignore) {
        shaped = new ShapedRecipe((ItemStack)recipe.getResult().locale());
      }

      shaped.shape(recipe.getRows());

      for(final Map.Entry<Character, String> ingredient : recipe.getIngredients().entrySet()) {
        shaped.setIngredient(ingredient.getKey(), Material.valueOf(ingredient.getValue()));
      }
      Bukkit.getServer().addRecipe(shaped);
    } else {
      ShapelessRecipe shapeless;

      try {
        shapeless = new ShapelessRecipe(new NamespacedKey(BukkitPluginCore.instance().getPlugin(), key), (ItemStack)recipe.getResult().locale());
      } catch(final Exception ignore) {
        shapeless = new ShapelessRecipe((ItemStack)recipe.getResult().locale());
      }

      for(final Map.Entry<Character, String> ingredient : recipe.getIngredients().entrySet()) {
        shapeless.addIngredient(1, Material.valueOf(ingredient.getValue()));
      }
      Bukkit.getServer().addRecipe(shapeless);
    }
  }

  @Override
  public BukkitCalculationsProvider calculations() {
    return calc;
  }
}