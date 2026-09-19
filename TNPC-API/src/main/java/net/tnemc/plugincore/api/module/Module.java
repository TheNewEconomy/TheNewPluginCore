package net.tnemc.plugincore.api.module;
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

/**
 * Module represents an add-on module for a plugin.
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public interface Module {

  /**
   * Initializes the module with the provided context.
   *
   * This method is typically used to perform one-time setup operations for the module.
   * It may include initializing dependencies, preparing resources, or registering components
   * with the plugin's context. The provided {@code ModuleContext} supplies necessary information
   * about the plugin and environment in which the module operates.
   *
   * @param context The {@link ModuleContext} containing information and dependencies for the module.
   */
  default void initialize(final ModuleContext context) {
  }

  /**
   * Enables the module and performs any startup tasks necessary for its operation.
   *
   * This method is responsible for initializing the module after it has been loaded.
   * It can include actions such as setting up resources, registering listeners,
   * or preparing the module to function as part of the larger system.
   * Implementations can override this method to include module-specific enable logic.
   */
  default void enable() {
  }

  /**
   * Disables the module and performs any necessary cleanup operations.
   *
   * This method should be called when the module is being unloaded
   * or deactivated. Implementations can override this to handle
   * resource deallocation, deregistration of listeners, or other cleanup
   * logic required when the module is no longer in use.
   */
  default void disable() {
  }
}