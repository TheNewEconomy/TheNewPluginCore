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

import net.tnemc.menu.sponge8.SpongePlayer;
import net.tnemc.plugincore.core.compatibility.PlayerProvider;
import net.tnemc.plugincore.core.io.message.MessageData;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.server.ServerWorld;
import org.spongepowered.plugin.PluginContainer;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The Sponge implementation of the {@link PlayerProvider}.
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public class SpongePlayerProvider extends SpongePlayer implements PlayerProvider {

  public SpongePlayerProvider(final User user, final PluginContainer container) {

    super(user, container);
  }

  /**
   * Used to get the name of this player.
   *
   * @return The name of this player.
   */
  @Override
  public String getName() {

    return user.name();
  }

  /**
   * Used to get the location of this player.
   *
   * @return The location of this player.
   */
  @Override
  public Optional<net.tnemc.plugincore.core.compatibility.Location> getLocation() {

    if(user.isOnline()) {

      final Optional<ServerPlayer> player = user.player();
      if(player.isPresent()) {
        final Location<?, ?> locale = player.get().location();

        return Optional.of(new net.tnemc.plugincore.core.compatibility.Location(((ServerWorld)locale.world()).key().value(),
                                                                                locale.x(), locale.y(),
                                                                                locale.z()));
      }
    }
    return Optional.empty();
  }

  @Override
  public String world() {

    //TODO: Default world.
    String world = "world";

    if(user.isOnline()) {

      final Optional<ServerPlayer> player = user.player();
      if(player.isPresent()) {
        world = ((ServerWorld)player.get().location().world()).key().value();
      }
    }
    return world;
  }

  @Override
  public String biome() {

    String biome = "world";

    if(user.isOnline()) {

      final Optional<ServerPlayer> player = user.player();
      if(player.isPresent()) {
        biome = player.get().location().biome().toString();
      }
    }
    return biome;
  }

  /**
   * Used to get the amount of experience this player has.
   *
   * @return The amount of levels this player has.
   */
  @Override
  public int getExp() {

    if(user.player().isPresent()) {
      return user.player().get().get(Keys.EXPERIENCE).orElse(0);
    }
    return 0;
  }

  /**
   * Used to set the amount of experience this player has.
   *
   * @param exp The amount of experience to set for this player.
   */
  @Override
  public void setExp(final int exp) {

    if(user.player().isPresent()) {
      user.player().get().offer(Keys.EXPERIENCE, exp);
    }
  }

  /**
   * Used to get the amount of experience levels this player has.
   *
   * @return The amount of experience levels this player has.
   */
  @Override
  public int getExpLevel() {

    if(user.player().isPresent()) {
      return user.player().get().get(Keys.EXPERIENCE_LEVEL).orElse(0);
    }
    return 0;
  }

  /**
   * Used to set the amount of experience levels this player has.
   *
   * @param level The amount of experience levels to set for this player.
   */
  @Override
  public void setExpLevel(final int level) {

    if(user.player().isPresent()) {
      user.player().get().offer(Keys.EXPERIENCE_LEVEL, level);
    }
  }

  @Override
  public SpongeInventoryProvider inventory() {

    return new SpongeInventoryProvider(identifier(), container);
  }

  /**
   * Method for retrieving player permissions.
   *
   * @return A list of permission strings.
   */
  @Override
  public List<String> getEffectivePermissions() {

    return user.subjectData().allPermissions().values().stream()
            .flatMap(map->map.keySet().stream())
            .collect(Collectors.toList());
  }

  /**
   * Used to send a message to this command source.
   *
   * @param messageData The message data to utilize for this translation.
   */
  @Override
  public void message(final MessageData messageData) {

  }

  @Override
  public void message(final String message) {

    message(new MessageData(message));
  }
}