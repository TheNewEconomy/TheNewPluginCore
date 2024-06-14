package net.tnemc.plugincore.core.io.storage;
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

import com.vdurmont.semver4j.Semver;

/**
 * Dialect
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public interface Dialect {

  /**
   * Provides the specific requirement for this dialect.
   * @return The version requirement for this dialect, or "none" if no requirement is required.
   */
  String requirement();

  /**
   * Used to parse version strings. Some dialects, such as Maria need their versions parsed because they format them in
   * a specific manner.
   * @param version The version to parse.
   * @return The parsed version.
   */
  default String parseVersion(final String version) {
    return version;
  }

  /**
   * Used to check if the provided version meets the requirement we need.
   * @param version The version to check.
   * @return True if the provided version meets our requirement.
   */
  default boolean checkRequirement(final String version) {
    return new Semver(version, Semver.SemverType.LOOSE).isGreaterThanOrEqualTo(requirement());
  }
}