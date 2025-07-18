package net.tnemc.plugincore.core.io.message;


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

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.tnemc.plugincore.PluginCore;
import net.tnemc.plugincore.core.compatibility.PlayerProvider;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * The core class for translating plugin messages
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public class MessageHandler {

  public static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();
  private static MessageHandler instance;
  final TranslationProvider translator;

  public MessageHandler(final TranslationProvider translator) {

    this.translator = translator;

    instance = this;
  }

  public MessageHandler(final TranslationProvider translator, final TagResolver... resolvers) {

    this.translator = translator;

    instance = this;
  }

  /**
   * Used to translate a message for a player and return the translated {@link Component}.
   *
   * @param messageData The message data to utilize for this translation.
   * @param player      The {@link PlayerProvider player} to translate this for.
   *
   * @return The {@link Component} that is the result of the translation process of the message for
   * the given player.
   */
  public static Component grab(final MessageData messageData, @NotNull final PlayerProvider player) {

    return MINI_MESSAGE.deserialize(PluginCore.server().replacePlaceholder(player.identifier(), instance.translator.translate(player.identifier(), messageData)));
  }

  /**
   * Used to translate a message for a player and return the translated {@link Component}.
   *
   * @param messageData The message data to utilize for this translation.
   * @param id          The {@link UUID unique identifier} of the player to translate this for.
   *
   * @return The {@link Component} that is the result of the translation process of the message for
   * the given player.
   */
  public static Component grab(final MessageData messageData, @NotNull final UUID id) {

    return MINI_MESSAGE.deserialize(PluginCore.server().replacePlaceholder(id, instance.translator.translate(id, messageData)));
  }

  /**
   * Used to translate a message for an {@link Audience}.
   *
   * @param messageData The message data to utilize for this translation.
   * @param audience    The audience that should receive the translated message.
   */
  public static void translate(final MessageData messageData, final UUID identifier, final Audience audience) {

    final String msg = PluginCore.server().replacePlaceholder(identifier, instance.translator.translateNode(messageData, "default"));

    if(identifier == null) {

      if(!msg.isEmpty()) {

        audience.sendMessage(MINI_MESSAGE.deserialize(msg));
      }
      return;
    }

    if(!msg.isEmpty()) {

      audience.sendMessage(MINI_MESSAGE.deserialize(msg));
    }
  }

  /**
   * Used to translate a message for numerous players.
   *
   * @param messageData The message data to utilize for this translation.
   * @param audiences   The audiences that should receive the translated message.
   */
  public static void translate(final MessageData messageData, final UUID identifier, final Audience... audiences) {

    final String msg = PluginCore.server().replacePlaceholder(identifier, instance.translator.translateNode(messageData, "default"));
    if(msg.isEmpty()) {
      return;
    }

    for(final Audience a : audiences) {
      a.sendMessage(MINI_MESSAGE.deserialize(msg));
    }
  }

  public static MessageHandler getInstance() {

    return instance;
  }

  public MiniMessage getMini() {

    return MINI_MESSAGE;
  }

  public TranslationProvider getTranslator() {

    return translator;
  }
}