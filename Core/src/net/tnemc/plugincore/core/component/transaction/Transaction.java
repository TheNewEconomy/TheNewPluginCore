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

import java.util.concurrent.CompletableFuture;

/**
 * Transaction
 *
 * @author creatorfromhell
 * @since 1.0.0.2
 */
public interface Transaction {

  /**
   * Retrieve the identifier associated with this transaction.
   *
   * @return The identifier of the transaction.
   */
  String identifier();

  /**
   * Creates a new transaction with the provided parameters.
   *
   * @param parameters The transaction parameters specifying the details of the new transaction.
   *
   * @return A new Transaction object with the given parameters.
   */
  Transaction with(final TransactionParameters parameters);

  /**
   * Executes the transaction and returns the result.
   *
   * @return The result of executing the transaction.
   */
  TransactionResult execute();

  /**
   * Asynchronously executes the transaction and sets the result in the provided CompletableFuture.
   *
   * @param result The CompletableFuture to hold the result of executing the transaction.
   */
  void execute(final CompletableFuture<TransactionResult> result);
}