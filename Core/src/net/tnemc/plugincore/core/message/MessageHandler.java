package net.tnemc.plugincore.core.message;

/*
 * The New Plugin Core
 * Copyright (C) 2022 - 2026 Daniel "creatorfromhell" Vidmar
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
import net.tnemc.plugincore.api.message.MessageData;
import net.tnemc.plugincore.api.message.PlaceholderProvider;
import net.tnemc.plugincore.api.message.TranslationProvider;

import java.util.UUID;

/**
 * The core class for translating plugin messages
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public class MessageHandler {

  private final TranslationProvider translator;
  private final PlaceholderProvider placeholders;
  private final MiniMessage miniMessage;

  public MessageHandler(final TranslationProvider translator, final PlaceholderProvider placeholders) {

    this(translator, placeholders, MiniMessage.miniMessage());
  }

  public MessageHandler(final TranslationProvider translator, final PlaceholderProvider placeholders, final MiniMessage miniMessage) {

    this.translator = translator;
    this.placeholders = placeholders;
    this.miniMessage = miniMessage;
  }

  /**
   * Translates and processes a message based on the provided {@code MessageData} and a unique
   * identifier, applying replacements and placeholders before deserializing the message into
   * a {@link Component}.
   *
   * @param messageData The {@link MessageData} object containing the base message, placeholders,
   *                    and arguments used for dynamic content replacement.
   * @param identifier  The unique {@link UUID} representing the identifier for the message
   *                    translation and placeholder mapping.
   * @return A {@link Component} representing the fully translated and processed message, ready
   *         for use in the target output context.
   */
  public Component grab(final MessageData messageData, final UUID identifier) {

    String message = translator.translate(identifier, messageData);

    message = messageData.apply(message);

    message = placeholders.replace(identifier, message);

    return miniMessage.deserialize(message);
  }

  /**
   * Translates a message using the provided {@code MessageData} and resolves the message for
   * a specific identifier, sending the resulting message to a single audience.
   *
   * @param messageData The {@link MessageData} object containing the base message, placeholders,
   *                    and arguments for dynamic content replacement.
   * @param identifier  The unique {@link UUID} used to identify the appropriate message translation.
   * @param audience    The {@link Audience} instance to whom the resolved message will be sent.
   */
  public void translate(final MessageData messageData, final UUID identifier, final Audience audience) {

    final Component message = grab(messageData, identifier);

    audience.sendMessage(message);
  }

  /**
   * Translates a message using the provided {@code MessageData}, resolves the message for a specific
   * identifier, and sends the resulting message to multiple audiences.
   *
   * @param messageData The {@link MessageData} object containing the base message and any
   *                    placeholders or arguments for dynamic content replacement.
   * @param identifier  The unique {@link UUID} used to identify the appropriate message translation.
   * @param audiences   The array of {@link Audience} instances to whom the resolved message will
   *                    be sent.
   */
  public void translate(final MessageData messageData, final UUID identifier, final Audience... audiences) {

    final Component message = grab(messageData, identifier);

    for(final Audience audience : audiences) {

      audience.sendMessage(message);
    }
  }

  public MiniMessage miniMessage() {

    return miniMessage;
  }

  public TranslationProvider translator() {

    return translator;
  }
}