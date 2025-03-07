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

import java.util.Optional;

/**
 * PasteClient
 *
 * @author creatorfromhell
 * @since 1.0.0.2
 */
public interface IPasteClient {

  /**
   * Retrieves the identifier associated with this object.
   *
   * @return The identifier as a String.
   */
  String identifier();

  /**
   * Retrieves the endpoint associated with this client.
   *
   * @return The endpoint as a String.
   */
  String endpoint();

  /**
   * Provides the API key associated with this client.
   *
   * @return The API key as a String.
   */
  String apiKey();

  /**
   * Creates a single paste based on the provided IPasteable object.
   *
   * @param pasteable the IPasteable object containing the details of the paste to create
   * @return an Optional of String representing the URL of the created paste, or an empty Optional if creation fails
   */
  Optional<String> createSingle(IPasteable pasteable);

  /**
   * Creates multiple pastes based on the provided IPasteable objects.
   *
   * @param pasteables an array of IPasteable objects containing the details of the pastes to create
   * @return an Optional of String representing the URL of the last created paste, or an empty Optional if creation fails or no pastes were created
   */
  Optional<String> createMultiple(IPasteable... pasteables);
}