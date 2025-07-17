package net.tnemc.plugincore.core.component.builder;
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

import net.tnemc.plugincore.PluginCore;
import net.tnemc.plugincore.core.component.ComponentBuilder;
import net.tnemc.plugincore.core.component.query.Query;
import net.tnemc.plugincore.core.component.query.QueryParameters;
import net.tnemc.plugincore.core.component.query.QueryResult;
import net.tnemc.plugincore.core.component.transaction.TransactionResult;

import java.util.concurrent.CompletableFuture;

/**
 * QueryBuilder
 *
 * @author creatorfromhell
 * @since 1.0.0.2
 */
public class QueryBuilder implements ComponentBuilder {

  protected Query query;

  public QueryBuilder() {

  }

  public QueryBuilder(final Query query) {

    this.query = query;
  }

  /**
   * Creates a QueryBuilder instance with the provided query string.
   *
   * @param query the query string to create the QueryBuilder with
   *
   * @return a new QueryBuilder instance
   */
  public static QueryBuilder of(final String query) {

    return new QueryBuilder(PluginCore.engine().queries().get(query));
  }

  /**
   * Adds the provided QueryParameters to the current query being built.
   *
   * @param parameters the QueryParameters to be added to the query
   *
   * @return a reference to this QueryBuilder instance
   */
  public QueryBuilder with(final QueryParameters parameters) {

    query.with(parameters);
    return this;
  }

  /**
   * Sets the query for this QueryBuilder instance.
   *
   * @param query the query to be set for this QueryBuilder
   *
   * @return a reference to this QueryBuilder instance
   */
  public QueryBuilder with(final Query query) {

    this.query = query;
    return this;
  }

  /**
   * Executes the query and returns the result synchronously.
   *
   * @return the result of executing the query
   */
  QueryResult execute() {

    return query.execute();
  }

  /**
   * Asynchronously executes the transaction and sets the result in the provided CompletableFuture.
   *
   * @param result The CompletableFuture to hold the result of executing the transaction.
   */
  void execute(final CompletableFuture<TransactionResult> result) {

    query.execute(result);
  }

  /**
   * Returns the identifier of the component.
   *
   * @return the identifier of the component as a string.
   */
  @Override
  public String identifier() {

    return "query";
  }

  /**
   * Returns a component builder instance that can be used to build components.
   *
   * @return a ComponentBuilder instance.
   */
  @Override
  public ComponentBuilder builder() {

    return new QueryBuilder();
  }
}