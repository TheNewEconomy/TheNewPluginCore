package net.tnemc.plugincore.core.providers;

import net.tnemc.plugincore.PluginCore;
import net.tnemc.plugincore.core.providers.uuid.UUIDPair;

import java.util.Optional;
import java.util.UUID;


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

/**
 * This class provides UUID-related functions, and has a concept of
 * "live" UUIDs and "stored" UUIDs.
 *
 * live UUIDs are any UUID that is directly associated with a Player account.
 *
 * stored UUIDs are any UUID that is generated for use inside the plugin
 * only. This could be a fake account related to an NPC account, or related
 * to a name that has never been registered on Minecraft.net.
 */
public interface UUIDProvider {

  /**
   * Used to resolve a username to an {@link UUIDPair}
   * @param name The username to resolve.
   * @return An optional containing the {@link UUIDPair}. This should
   * never return null as it should return {@link #generatePair(String)} if
   * no {@link UUIDPair} is found by any other means.
   */
  default UUIDPair resolve(final String name) {
    Optional<UUIDPair> pair = Optional.empty();
    if(validate(name)) {
      pair = retrievePlayer(name);

      if(pair.isPresent()) return pair.get();
    }

    pair = retrieve(name);

    return pair.orElseGet(()->generatePair(name));
  }

  /**
   * Used to generate a new {@link UUID} and return it in an {@link UUIDPair}.
   * @param name The username to use.
   * @return The {@link UUIDPair} with the specified username, and
   * the generated {@link UUID}.
   */
  default UUIDPair generatePair(final String name) {
    final UUIDPair pair = new UUIDPair(UUID.randomUUID(), name);

    //Store the newly created UUIDPair
    store(pair);

    return pair;
  }

  /**
   * Used to determine if a player with the specified username has played
   * before.
   * @param name The username to search for.
   * @return True if someone with the specified username has played before,
   * otherwise false.
   */
  boolean playedBefore(final String name);

  /**
   * Used to determine if a player with the specified username is online.
   * @param name The username to search for.
   * @return True if someone with the specified username is online.
   */
  boolean online(final String name);

  /**
   * Used to retrieve a {@link UUIDPair} from a player object if a
   * player with the specified username has played before, or is online.
   * @param name The username of the pair.
   * @return An optional containing the pair if found, otherwise an empty
   * optional.
   */
  Optional<UUIDPair> retrievePlayer(final String name);


  /**
   * Used to retrieve a {@link UUIDPair} from a map/database.
   * @param name The username of the pair.
   * @return An optional containing the pair if found, otherwise an empty
   * optional.
   */
  Optional<UUIDPair> retrieve(final String name);

  /**
   * Used to store a Username & UUID pair. This could be to a map, or to
   * a database for persistent usage or to both.
   * @param pair The {@link UUIDPair}
   */
  void store(final UUIDPair pair);

  /**
   * Used to determine in a string is a valid minecraft username or not.
   * @param name The name to check.
   * @return True if the name is a valid minecraft username, otherwise false.
   */
  default boolean validate(final String name) {
    if(name.length() >= 3 && name.length() <= 16) {
      return PluginCore.USERNAME_PATTERN.matcher(name).matches();
    }
    return false;
  }
}