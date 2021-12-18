package net.tnemc.plugincore.core.builders.util;

import java.util.LinkedList;
import java.util.Map;
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
public class MessageFormatter {

  private final LinkedList<String> messages;

  private final Map<String, String> variables;

  public MessageFormatter(LinkedList<String> messages, Map<String, String> variables) {
    this.messages = messages;
    this.variables = variables;
  }

  /**
   * @return A {@link LinkedList} of Strings that represent lines that have been formatted.
   */
  public LinkedList<String> format() {
    LinkedList<String> formatted = new LinkedList<>();

    for(String str : formatted) {
      String[] parsed = str.split("\\<newline\\>");

      for(String parse : parsed) {

        String working = parse;

        for(String key : variables.keySet()) {
          working.replaceAll(Pattern.quote(key), variables.get(key));
        }
        formatted.add(working);
      }
    }
    return formatted;
  }
}