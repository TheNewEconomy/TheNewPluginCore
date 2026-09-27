package net.tnemc.plugincore.api.message;

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

import java.util.UUID;

/**
 * Represents a class that provides translation services.
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public interface TranslationProvider {

  /**
   * Retrieves the language string associated with the given unique identifier.
   *
   * @param identifier The unique identifier of the user or entity for which the language is being retrieved.
   * @return The language string associated with the specified identifier.
   */
  String language(UUID identifier);

  /**
   * Translates the given message data into the specified language.
   *
   * @param message  The {@code MessageData} containing the message node, arguments, and replacements
   *                 to be processed during the translation.
   * @param language The target language into which the message should be translated.
   * @return A string representing the translated message, with all replacements and argument substitutions applied.
   */
  String translate(MessageData message, String language);

  /**
   * Translates a message based on the language associated with a specific identifier.
   *
   * @param identifier The unique identifier used to determine the corresponding language for translation.
   * @param message    The message data containing the node, arguments, and replacements to be used in the translation process.
   * @return The translated message as a string, customized with the appropriate language and message data.
   */
  default String translate(final UUID identifier, final MessageData message) {

    return translate(message, language(identifier));
  }
}