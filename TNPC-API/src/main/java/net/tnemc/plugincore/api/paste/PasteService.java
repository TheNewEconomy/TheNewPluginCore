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

import java.util.Collection;
import java.util.Optional;

/**
 * PasteService
 *
 * @author creatorfromhell
 * @since 2.0.0.0
 */
public interface PasteService {

  /**
   * Retrieves the PasteClient associated with the specified key.
   *
   * @param key the unique identifier used to locate the corresponding PasteClient
   * @return an Optional containing the corresponding PasteClient if found, or an empty Optional if no match is found
   */
  Optional<PasteClient> client(Key key);

  /**
   * Retrieves a collection of all available PasteClient instances.
   *
   * @return A collection containing all registered PasteClient objects.
   */
  Collection<PasteClient> clients();
}