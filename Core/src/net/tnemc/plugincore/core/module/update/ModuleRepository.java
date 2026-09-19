package net.tnemc.plugincore.core.module.update;

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

import java.util.Optional;

/**
 * ModuleRepository
 *
 * @author creatorfromhell
 * @since 2.0.0.0
 */
public interface ModuleRepository {

  /**
   * Retrieves the most recent artifact associated with the specified module.
   *
   * @param module the name of the module for which to retrieve the latest artifact
   * @return an {@code Optional} containing the latest {@code ModuleArtifact} if available,
   *         or an empty {@code Optional} if no artifact exists for the specified module
   */
  Optional<ModuleArtifact> latest(String module);
}