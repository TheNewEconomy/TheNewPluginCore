package net.tnemc.plugincore.core.utils;

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

import java.util.UUID;
import java.util.regex.Pattern;

/**
 * PlayerHelper - Utilities relating to all things player-related.
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public final class PlayerUtil {

  private PlayerUtil() {
  }

  private static final Pattern USERNAME = Pattern.compile("^\\w{3,16}$");

  /**
   * Used to determine if a string is a valid minecraft username or not.
   *
   * @param name The name to check.
   *
   * @return True if the name is a valid minecraft username, otherwise false.
   */
  public static boolean validUsername(final String name) {

    return name != null && USERNAME.matcher(name).matches();
  }

    /**
     * Used to determine if a string is a valid UUID.
     *
     * @param identifier The string to check.
     *
     * @return True if the name is a valid UUID, otherwise false.
     */
  public static boolean validUUID(final String identifier) {

    try {

      UUID.fromString(identifier);
      return true;

    } catch(final IllegalArgumentException  ignore) {
      return false;
    }
  }
}