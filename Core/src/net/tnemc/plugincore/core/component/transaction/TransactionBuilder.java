package net.tnemc.plugincore.core.component.transaction;
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

import java.util.concurrent.CompletableFuture;

/**
 * TransactionBuilder
 *
 * @author creatorfromhell
 * @since 1.0.0.2
 */
public class TransactionBuilder implements ComponentBuilder {

  protected Transaction transaction;

  public TransactionBuilder() {
  }

  public TransactionBuilder(final Transaction transaction) {
    this.transaction = transaction;
  }

  /**
   * Returns a new TransactionBuilder instance for the specified type.
   *
   * @param type the type of transaction
   * @return a new TransactionBuilder instance
   */
  public static TransactionBuilder of(final String type) {
    return new TransactionBuilder(PluginCore.engine().transactions().get(type));
  }

  /**
   * Sets the provided TransactionParameters for the current TransactionBuilder instance.
   *
   * @param parameters the TransactionParameters to set for the builder
   * @return the current TransactionBuilder instance with the provided parameters set
   */
  public TransactionBuilder with(final TransactionParameters parameters) {
    transaction.with(parameters);
    return this;
  }

  /**
   * Sets the provided transaction for the current TransactionBuilder instance.
   *
   * @param transaction the transaction to set for the builder
   * @return the current TransactionBuilder instance with the provided transaction set
   */
  public TransactionBuilder with(final Transaction transaction) {
    this.transaction = transaction;
    return this;
  }

  /**
   * Executes the transaction and returns the result.
   *
   * @return The result of executing the transaction.
   */
  TransactionResult execute() {
    return transaction.execute();
  }

  /**
   * Asynchronously executes the transaction and sets the result in the provided CompletableFuture.
   *
   * @param result The CompletableFuture to hold the result of executing the transaction.
   */
  void execute(final CompletableFuture<TransactionResult> result) {
    transaction.execute(result);
  }

  /**
   * Returns the identifier of the component.
   *
   * @return the identifier of the component as a string.
   */
  @Override
  public String identifier() {
    return "transaction";
  }

  /**
   * Returns a component builder instance that can be used to build components.
   *
   * @return a ComponentBuilder instance.
   */
  @Override
  public ComponentBuilder builder() {
    return new TransactionBuilder();
  }
}