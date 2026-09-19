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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents data for a message translation to be sent to a sender.
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public class MessageData {

  private final Map<String, String> replacements = new HashMap<>();
  private final List<String> arguments = new ArrayList<>();

  private final String node;

  public MessageData(final String node) {

    this.node = node;
  }

  public MessageData(final String node, final Object... arguments) {

    this.node = node;

    arguments(arguments);
  }

  /**
   * Adds a single key-value pair to the internal replacements map. The key and value are converted
   * to their string representations and stored for later use in dynamic message replacement.
   *
   * @param search      The key to be stored in the internal replacements map.
   * @param replacement The value to be associated with the specified key, which will be
   *                    converted to its string representation.
   *
   * @return The current instance of {@code MessageData}, allowing for method chaining.
   */
  public MessageData replacement(final String search, final Object replacement) {

    replacements.put(search, String.valueOf(replacement));

    return this;
  }

  /**
   * Adds multiple key-value pairs to the internal replacements map. Each key-value pair provided in
   * the input map will be converted to a string representation and stored for later use in dynamic
   * message replacement.
   *
   * @param replacements A map containing the search keys and corresponding replacement values. Each
   *                     key and value will be converted to its string representation and stored in
   *                     the internal replacements map.
   *
   * @return The current instance of {@code MessageData}, allowing for method chaining.
   */
  public MessageData replacements(final Map<String, ?> replacements) {

    replacements.forEach((search, replacement)->this.replacements.put(search, String.valueOf(replacement)));

    return this;
  }

  /**
   * Adds a single argument to the internal list of arguments by converting it to a string.
   *
   * @param argument The object to be added to the internal arguments list. The object will be
   *                 converted to its string representation and stored.
   *
   * @return The current instance of {@code MessageData}, allowing for method chaining.
   */
  public MessageData argument(final Object argument) {

    arguments.add(String.valueOf(argument));

    return this;
  }

  /**
   * Adds the specified arguments to the internal list of arguments by converting each to a string.
   *
   * @param arguments The varargs of objects to be added to the internal arguments list. Each object
   *                  will be converted to its string representation and stored.
   *
   * @return The current instance of {@code MessageData}, allowing for method chaining.
   */
  public MessageData arguments(final Object... arguments) {

    for(final Object argument : arguments) {

      this.arguments.add(String.valueOf(argument));
    }

    return this;
  }

  /**
   * Applies a series of text replacements and argument substitutions to the provided message.
   * Replacements are based on mappings stored in the internal replacements map, and arguments
   * are substituted in the order they are added to the internal list. Placeholders in the form
   * of keys or indexed arguments are replaced with their corresponding values.
   *
   * @param message The input message containing placeholders to be replaced. Placeholders
   *                for replacements should match the keys stored in the internal map, and
   *                indexed placeholders (e.g., {0}, {1}) map to arguments in order of their
   *                position in the internal arguments list.
   *
   * @return The processed message with all applicable replacements and argument substitutions
   *         applied.
   */
  public String apply(final String message) {

    String result = message;
    for(final Map.Entry<String, String> replacement : replacements.entrySet()) {

      result = result.replace(replacement.getKey(), replacement.getValue());
    }

    for(int i = 0; i < arguments.size(); i++) {

      result = result.replace("{" + i + "}", arguments.get(i));
    }

    return result;
  }

  public String node() {

    return node;
  }

  public Map<String, String> replacements() {

    return Collections.unmodifiableMap(replacements);
  }

  public List<String> arguments() {

    return Collections.unmodifiableList(arguments);
  }
}