package net.tnemc.plugincore.core.builders.util;

import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Pattern;

public class MessageFormatter {

  private final LinkedList<String> messages;

  private final Map<String, String> variables;

  public MessageFormatter(LinkedList<String> messages, Map<String, String> variables) {
    this.messages = messages;
    this.variables = variables;
  }

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