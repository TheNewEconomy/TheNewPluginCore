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

import dev.dejvokep.boostedyaml.YamlDocument;

/**
 * Language
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public final class Language {

  private final String name;
  private final YamlDocument document;

  public Language(final String name, final YamlDocument document) {

    this.name = name;
    this.document = document;
  }

  public String name() {
    return name;
  }

  public boolean contains(final String node) {
    return document.contains(node);
  }

  public String translation(final String node) {
    return document.getString(node);
  }
}