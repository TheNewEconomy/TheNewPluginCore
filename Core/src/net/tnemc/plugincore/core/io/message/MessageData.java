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

import net.kyori.adventure.text.Component;
import net.tnemc.plugincore.core.compatibility.PlayerProvider;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Represents data for a message translation to be sent to a sender.
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public class MessageData {

  private final Map<String, String> replacements = new HashMap<>();

  private final String node;

  public MessageData(String node) {
    this.node = node;
  }

  public void addReplacement(final String search, final String replacement) {
    this.replacements.put(search, replacement);
  }

  public void addReplacement(final String search, final BigDecimal replacement) {
    this.replacements.put(search, replacement.toPlainString());
  }

  public void addReplacement(final String search, final Integer replacement) {
    this.replacements.put(search, String.valueOf(replacement));
  }

  public void addReplacement(final String search, final Double replacement) {
    this.replacements.put(search, String.valueOf(replacement));
  }

  public void addReplacement(final String search, final Boolean replacement) {
    this.replacements.put(search, String.valueOf(replacement));
  }

  public void addReplacement(final String search, final UUID replacement) {
    this.replacements.put(search, replacement.toString());
  }

  public void addReplacements(final String[] search, final String[] replacements) {
    if(search.length != replacements.length) return;

    for(int i = 0; i < search.length; i++) {

      this.replacements.put(search[i], replacements[i]);
    }
  }

  /**
   * Used to translate a message for a player and return the translated {@link Component}.
   *
   * @param player The {@link PlayerProvider player} to translate this for.
   *
   * @return The {@link Component} that is the result of the translation process of the message for
   * the given player.
   */
  public Component grab(@NotNull PlayerProvider player) {
    return MessageHandler.grab(this, player);
  }


  /**
   * Used to translate a message for a player and return the translated {@link Component}.
   *
   * @param id The {@link UUID unique identifier} of the player to translate this for.
   *
   * @return The {@link Component} that is the result of the translation process of the message for
   * the given player.
   */
  public Component grab(@NotNull UUID id) {
    return MessageHandler.grab(this, id);
  }

  public Map<String, String> getReplacements() {
    return replacements;
  }

  public String getNode() {
    return node;
  }
}