package net.tnemc.plugincore.core.connections.economy;

import java.math.BigDecimal;

/**
 * Created by creatorfromhell.
 *
 * The New Plugin Core Minecraft Server Plugin
 *
 * All rights reserved.
 *
 * Some Details about what is acceptable use of this software:
 *
 * This project accepts user contributions.
 *
 * Direct redistribution of this software is not allowed without written permission. However,
 * compiling this project into your software to utilize it as a library is acceptable as long as
 * it's not used for commercial purposes.
 *
 * Commercial usage is allowed if a commercial usage license is bought and verification of the
 * purchase is able to be provided by both parties.
 *
 * By contributing to this software you agree that your rights to your contribution are handed over
 * to the owner of the project.
 */
public interface EconomyConnector {

  /**
   * Used to create an economy account
   *
   * @param identifier The identifier of the account. This could be a username or a UUID valid
   *                   String.
   * @param world      The world to create the account for.
   *
   * @return True if the account was created.
   */
  boolean createAccount(String identifier, String world);

  /**
   * Used to delete an economy account
   *
   * @param identifier The identifier of the account. This could be a username or a UUID valid
   *                   String.
   * @param world      The world to delete the account from.
   *
   * @return True if the account was deleted.
   */
  boolean deleteAccount(String identifier, String world);

  /**
   * @return The global default currency.
   */
  String getCurrency();

  /**
   * @param world The world to use for the search.
   *
   * @return The default currency for the world.
   */
  String getCurrency(String world);

  /**
   * Used to check if the specified currency exists.
   *
   * @param currency The name of the currency to look for.
   *
   * @return True if the currency exists, otherwise false.
   */
  boolean hasCurrency(String currency);

  /**
   * Used to check if the specified currency exists in the specific world.
   *
   * @param currency The name of the currency to look for.
   * @param world    The name of the world to use in the search
   *
   * @return True if the currency exists, otherwise false.
   */
  boolean hasCurrency(String currency, String world);

  BigDecimal getHoldings(String identifier, String world);

  BigDecimal getHoldings(String identifier, String world, String currency);

  boolean hasHoldings(String identifier, String world, String currency, BigDecimal amount);

  void addHoldings(String identifier, String world, String currency, BigDecimal amount);

  void setHoldings(String identifier, String world, String currency, BigDecimal amount);

  void removeHoldings(String identifier, String world, String currency, BigDecimal amount);
}