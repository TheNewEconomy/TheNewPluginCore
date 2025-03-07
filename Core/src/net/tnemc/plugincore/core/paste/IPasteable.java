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
public interface IPasteable {

  /**
   * Retrieves the file name associated with this object.
   *
   * @return The file name as a String.
   */
  String fileName();

  /**
   * Retrieves the extension associated with this object.
   *
   * @return The extension as a String.
   */
  String extension();

  /**
   * Retrieves the syntax associated with this object.
   *
   * @return The syntax as a String.
   */
  String syntax();

  /**
   * Retrieves the content of the object.
   *
   * @return The content as a String.
   */
  String content();
}