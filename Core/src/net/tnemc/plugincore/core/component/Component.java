package net.tnemc.plugincore.core.component;
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

import net.tnemc.plugincore.core.Platform;

/**
 * Component
 *
 * @author creatorfromhell
 * @since 1.0.0.2
 */
public interface Component {

  /**
   * Returns the identifier associated with this Component.
   *
   * @return the identifier string
   */
  String identifier();

  /**
   * Checks if this method supports the given platform and version.
   *
   * @param platform the Platform to check for support
   * @param version  the version string to check for support
   *
   * @return true if the platform and version are supported, false otherwise
   */
  boolean supports(final Platform platform, final String version);

  /**
   * Retrieves the array of dependencies required by this component.
   *
   * @return an array of strings representing the dependencies needed for this component
   */
  String[] dependencies();

  /**
   * Retrieves an array of library names that this Component requires.
   *
   * @return an array of strings representing the required libraries
   */
  String[] libraries();

  /**
   * Initializes the Component with the provided platform and version.
   *
   * @param platform the platform to initialize the Component for
   * @param version  the Minecraft version string to initialize the Component with
   */
  void initialize(final Platform platform, final String version);

  /**
   * Initializes the registries for the given platform and version.
   *
   * @param platform the platform for which the registries are being initialized
   * @param version  the Minecraft version of the platform
   */
  void initRegistries(final Platform platform, final String version);

  /**
   * Initializes the builders for the given platform and version.
   *
   * @param platform the platform to initialize the builders for
   * @param version  the Minecraft version string to initialize the builders with
   *
   * @see ComponentBuilder
   */
  ComponentBuilder[] initBuilders(final Platform platform, final String version);
}