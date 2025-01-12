package net.tnemc.plugincore.core.component.query;
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

import java.util.concurrent.CompletableFuture;

/**
 * Query
 *
 * @author creatorfromhell
 * @since 1.0.0.2
 */
public interface Query {

  /**
   * Retrieves the identifier associated with this query.
   *
   * @return the identifier of the query
   */
  String identifier();

  /**
   * Creates a new query using the provided QueryParameters.
   *
   * @param parameters the parameters for the query
   * @return a Query object representing the query with the specified parameters
   */
  Query of(QueryParameters parameters);

  /**
   *
   * Executes the query and returns the result synchronously.
   *
   * @return the result of executing the query
   */
  QueryResult execute();

  /**
   * Executes the query asynchronously and returns a CompletableFuture that will hold the QueryResult.
   * The CompletableFuture<QueryResult> can be used to handle the result of the query execution.
   *
   * @return a CompletableFuture that will hold the QueryResult upon completion of the query execution
   */
  CompletableFuture<QueryResult> executeAsync();
}