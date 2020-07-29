package net.tnemc.plugincore.core.builders;

import net.tnemc.plugincore.PluginCore;
import net.tnemc.plugincore.core.builders.util.MessageFormatter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;

public abstract class MessageBuilder {

  private LinkedList<String> messages = new LinkedList<>();

  //The formatted messages. This is for caching-purposes and is only
  //filled after using one of the send methods, and wiped afterwards.
  private LinkedList<String> formatted = new LinkedList<>();

  private Map<String, String> variables = new HashMap<>();

  private PluginCore core;

  private StringBuilder workingMessage = new StringBuilder();

  public MessageBuilder(PluginCore core) {
    this.core = core;
  }

  //Abstract Methods.

  /**
   * Used to send the message to the specified recipient. This is post
   * formatting.
   * @param recipient The UUID of the recipient.
   */
  protected abstract void sendTo(UUID recipient);

  /**
   * Used to send the message based on permission node. This is post
   * formatting.
   * @param permission The permission node required to receive.
   */
  protected abstract void sendTo(String permission);

  //General already implemented methods.
  /**
   * Transfers the workingMessage to the message list.
   */
  public void transferWorking() {
    if(workingMessage.length() > 0)
      messages.add(workingMessage.toString());
  }

  public void resetWorking() {
    workingMessage.setLength(0);
  }

  /**
   * @return The LinkedList of Strings that make up the message we're building.
   */
  LinkedList<String> getMessage() {
    return messages;
  }

  /**
   * Adds a variable to the variables map.
   * @param name The name of the variable, which is a unique identifier.
   * @param value The value of the variable.
   */
  public void addVariable(String name, String value) {
    variables.put(name, value);
  }

  /**
   * Used to add a string to the LinkedList for our message.
   * @param toAdd The string to add.
   */
  public void addMessageString(String toAdd) {
    messages.add(toAdd);
  }

  /**
   * @return The working message instance.
   */
  public StringBuilder workingMessage() {
    return workingMessage;
  }

  /**
   * Used to set the current working message.
   * @param workingMessage The StringBuilder to set the currnt working message to.
   */
  public void setWorkingMessage(StringBuilder workingMessage) {
    this.workingMessage = workingMessage;
  }

  /**
   * Used to add a new line to the message.
   * @return The updated instance of the active {@link MessageBuilder}
   */
  public MessageBuilder newline() {
    transferWorking();
    resetWorking();
    return this;
  }

  /**
   * Used to append text to the current working message line.
   * @param text The text to append.
   * @return The updated instance of the active {@link MessageBuilder}
   */
  public MessageBuilder append(String text) {
    workingMessage.append(text);
    return this;
  }

  /**
   * Appends text in the color black to the current working message line.
   * @param text The text to append.
   * @return The updated instance of the active {@link MessageBuilder}
   */
  public MessageBuilder BLACK(String text) {
    workingMessage.append(core.color().BLACK());
    workingMessage.append(text);
    workingMessage.append(core.color().RESET());
    return this;
  }

  /**
   * Appends text in the color dark blue to the current working message line.
   * @param text The text to append.
   * @return The updated instance of the active {@link MessageBuilder}
   */
  public MessageBuilder DARK_BLUE(String text) {
    workingMessage.append(core.color().DARK_BLUE());
    workingMessage.append(text);
    workingMessage.append(core.color().RESET());
    return this;
  }

  /**
   * Appends text in the color dark green to the current working message line.
   * @param text The text to append.
   * @return The updated instance of the active {@link MessageBuilder}
   */
  public MessageBuilder DARK_GREEN(String text) {
    workingMessage.append(core.color().DARK_GREEN());
    workingMessage.append(text);
    workingMessage.append(core.color().RESET());
    return this;
  }

  /**
   * Appends text in the color dark aqua to the current working message line.
   * @param text The text to append.
   * @return The updated instance of the active {@link MessageBuilder}
   */
  public MessageBuilder DARK_AQUA(String text) {
    workingMessage.append(core.color().DARK_AQUA());
    workingMessage.append(text);
    workingMessage.append(core.color().RESET());
    return this;
  }

  /**
   * Appends text in the color dark red to the current working message line.
   * @param text The text to append.
   * @return The updated instance of the active {@link MessageBuilder}
   */
  public MessageBuilder DARK_RED(String text) {
    workingMessage.append(core.color().DARK_RED());
    workingMessage.append(text);
    workingMessage.append(core.color().RESET());
    return this;
  }

  /**
   * Appends text in the color dark purple to the current working message line.
   * @param text The text to append.
   * @return The updated instance of the active {@link MessageBuilder}
   */
  public MessageBuilder DARK_PURPLE(String text) {
    workingMessage.append(core.color().DARK_PURPLE());
    workingMessage.append(text);
    workingMessage.append(core.color().RESET());
    return this;
  }

  /**
   * Appends text in the color dark gray to the current working message line.
   * @param text The text to append.
   * @return The updated instance of the active {@link MessageBuilder}
   */
  public MessageBuilder DARK_GRAY(String text) {
    workingMessage.append(core.color().DARK_GRAY());
    workingMessage.append(text);
    workingMessage.append(core.color().RESET());
    return this;
  }

  /**
   * Appends text in the color gold to the current working message line.
   * @param text The text to append.
   * @return The updated instance of the active {@link MessageBuilder}
   */
  public MessageBuilder GOLD(String text) {
    workingMessage.append(core.color().GOLD());
    workingMessage.append(text);
    workingMessage.append(core.color().RESET());
    return this;
  }

  /**
   * Appends text in the color gray to the current working message line.
   * @param text The text to append.
   * @return The updated instance of the active {@link MessageBuilder}
   */
  public MessageBuilder GRAY(String text) {
    workingMessage.append(core.color().GRAY());
    workingMessage.append(text);
    workingMessage.append(core.color().RESET());
    return this;
  }

  /**
   * Appends text in the color blue to the current working message line.
   * @param text The text to append.
   * @return The updated instance of the active {@link MessageBuilder}
   */
  public MessageBuilder BLUE(String text) {
    workingMessage.append(core.color().BLUE());
    workingMessage.append(text);
    workingMessage.append(core.color().RESET());
    return this;
  }

  /**
   * Appends text in the color green to the current working message line.
   * @param text The text to append.
   * @return The updated instance of the active {@link MessageBuilder}
   */
  public MessageBuilder GREEN(String text) {
    workingMessage.append(core.color().GREEN());
    workingMessage.append(text);
    workingMessage.append(core.color().RESET());
    return this;
  }

  /**
   * Appends text in the color aqua to the current working message line.
   * @param text The text to append.
   * @return The updated instance of the active {@link MessageBuilder}
   */
  public MessageBuilder AQUA(String text) {
    workingMessage.append(core.color().AQUA());
    workingMessage.append(text);
    workingMessage.append(core.color().RESET());
    return this;
  }

  /**
   * Appends text in the color red to the current working message line.
   * @param text The text to append.
   * @return The updated instance of the active {@link MessageBuilder}
   */
  public MessageBuilder RED(String text) {
    workingMessage.append(core.color().RED());
    workingMessage.append(text);
    workingMessage.append(core.color().RESET());
    return this;
  }

  /**
   * Appends text in the color light purple to the current working message line.
   * @param text The text to append.
   * @return The updated instance of the active {@link MessageBuilder}
   */
  public MessageBuilder LIGHT_PURPLE(String text) {
    workingMessage.append(core.color().LIGHT_PURPLE());
    workingMessage.append(text);
    workingMessage.append(core.color().RESET());
    return this;
  }

  /**
   * Appends text in the color yellow to the current working message line.
   * @param text The text to append.
   * @return The updated instance of the active {@link MessageBuilder}
   */
  public MessageBuilder YELLOW(String text) {
    workingMessage.append(core.color().YELLOW());
    workingMessage.append(text);
    workingMessage.append(core.color().RESET());
    return this;
  }

  /**
   * Appends text in the color white to the current working message line.
   * @param text The text to append.
   * @return The updated instance of the active {@link MessageBuilder}
   */
  public MessageBuilder WHITE(String text) {
    workingMessage.append(core.color().WHITE());
    workingMessage.append(text);
    workingMessage.append(core.color().RESET());
    return this;
  }

  /**
   * Appends magic text to the current working message line.
   * @param text The text to append.
   * @return The updated instance of the active {@link MessageBuilder}
   */
  public MessageBuilder MAGIC(String text) {
    workingMessage.append(core.color().MAGIC());
    workingMessage.append(text);
    workingMessage.append(core.color().RESET());
    return this;
  }

  /**
   * Appends bold text to the current working message line.
   * @param text The text to append.
   * @return The updated instance of the active {@link MessageBuilder}
   */
  public MessageBuilder BOLD(String text) {
    workingMessage.append(core.color().BOLD());
    workingMessage.append(text);
    workingMessage.append(core.color().RESET());
    return this;
  }

  /**
   * Appends strikethrough text to the current working message line.
   * @param text The text to append.
   * @return The updated instance of the active {@link MessageBuilder}
   */
  public MessageBuilder STRIKETHROUGH(String text) {
    workingMessage.append(core.color().STRIKETHROUGH());
    workingMessage.append(text);
    workingMessage.append(core.color().RESET());
    return this;
  }

  /**
   * Appends underline text to the current working message line.
   * @param text The text to append.
   * @return The updated instance of the active {@link MessageBuilder}
   */
  public MessageBuilder UNDERLINE(String text) {
    workingMessage.append(core.color().UNDERLINE());
    workingMessage.append(text);
    workingMessage.append(core.color().RESET());
    return this;
  }

  /**
   * Appends italic text to the current working message line.
   * @param text The text to append.
   * @return The updated instance of the active {@link MessageBuilder}
   */
  public MessageBuilder ITALIC(String text) {
    workingMessage.append(core.color().ITALIC());
    workingMessage.append(text);
    workingMessage.append(core.color().RESET());
    return this;
  }

  /**
   * Used to send the message to the recipients with the specified UUIDs.
   * @param recipients The UUID(s) of the recipient(s).
   */
  public void send(UUID... recipients) {
    if(messages.size() <= 0)
      format();

    for(UUID recipient : recipients) {
      sendTo(recipient);
    }

    formatted.clear();
  }

  /**
   * Used to send the message to the recipients with the specified
   * permission node.
   * @param permission The permission node.
   */
  public void send(String permission) {
    if(messages.size() <= 0)
      format();

    sendTo(permission);

    formatted.clear();
  }

  /**
   * Formats the message by replacing all variables, and colour codes.
   */
  protected void format() {
    formatted = new MessageFormatter(messages, variables).format();
  }
}