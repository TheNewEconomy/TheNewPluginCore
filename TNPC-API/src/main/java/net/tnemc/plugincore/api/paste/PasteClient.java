package net.tnemc.plugincore.api.paste;
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

import net.kyori.adventure.key.Key;
import java.net.URI;
import java.util.Collection;import java.util.Optional;

/**
 * PasteClient
 *
 * @author creatorfromhell
 * @since 1.0.0.2
 */
public interface PasteClient {

  /**
   * Retrieves the identifier associated with this object.
   *
   * @return The identifier as a String.
   */
  Key key();

  /**
   * Creates a single paste based on the provided Pasteable object.
   *
   * @param pasteable the Pasteable object containing the details of the paste to create
   *
   * @return an Optional of String representing the URL of the created paste, or an empty Optional
   * if creation fails
   */
  Optional<URI> createSingle(Pasteable pasteable);

  /**
   * Creates multiple pastes based on the provided Pasteable objects.
   *
   * @param pasteables an array of Pasteable objects containing the details of the pastes to create
   * @return a collection of URIs representing the URLs of the created pastes
   */
  Collection<URI> createMultiple(Pasteable... pasteables);
}