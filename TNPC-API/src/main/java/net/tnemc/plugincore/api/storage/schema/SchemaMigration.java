package net.tnemc.plugincore.api.storage.schema;

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
 * SchemaMigration
 *
 * @author creatorfromhell
 * @since 2.0.0.0
 */
public interface SchemaMigration {

  /**
   * Retrieves the version of the schema migration.
   *
   * @return the version number of the schema migration as an integer
   */
  int version();

  /**
   * Retrieves the description associated with the schema migration.
   *
   * @return the description of the schema migration as a String
   */
  String description();

  /**
   * Executes the schema migration process using the provided migration context.
   *
   * @param context the migration context containing utility methods and details about the environment
   * @throws Exception if an error occurs during the migration process
   */
  void migrate(MigrationContext context) throws Exception;
}