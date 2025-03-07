package net.tnemc.plugincore.core.paste;
/*
 * The New Plugin Core
 * Copyright (C) 2022 - 2025 Daniel "creatorfromhell" Vidmar
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

/**
 * Pastable
 *
 * @author creatorfromhell
 * @since 1.0.0.2
 */
class PasteableText implements IPasteable {

  private final String identifier;
  private final String fileName;
  private final String extension;
  private final String syntax;
  private final String content;

  public PasteableText(final String identifier, final String fileName, final String extension, final String syntax, final String content) {
    this.identifier = identifier;
    this.fileName = fileName;
    this.extension = extension;
    this.syntax = syntax;
    this.content = content;
  }

  @Override
  public String identifier() {
    return identifier;
  }

  @Override
  public String fileName() {
    return fileName;
  }

  @Override
  public String extension() {
    return extension;
  }

  /**
   * Retrieves the syntax associated with this object.
   *
   * @return The syntax as a String.
   */
  @Override
  public String syntax() {
    return syntax;
  }

  @Override
  public String content() {
    return content;
  }
}