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
 * MigrationContext
 *
 * @author creatorfromhell
 * @since 2.0.0.0
 */
public interface MigrationContext {

  /**
   * Retrieves the database dialect associated with the migration context.
   *
   * @return the database dialect as a String
   */
  String dialect();

  /**
   * Executes a given SQL statement within the context of the migration process.
   *
   * @param statement the SQL statement to be executed
   * @throws Exception if an error occurs during the execution of the statement
   */
  void execute(String statement) throws Exception;
}