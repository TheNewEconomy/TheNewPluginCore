package net.tnemc.plugincore.core.connections.economy;

import java.math.BigDecimal;

/**
 * Created by creatorfromhell.
 *
 * The New Kings Minecraft Server Plugin
 *
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0
 * International License. To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/
 * or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */
public interface EconomyConnector {

  BigDecimal getHoldings(String identifier, String world, String currency);

  boolean hasHoldings(String identifier, String world, String currency, BigDecimal amount);

  void removeHoldings(String identifier, String world, String currency, BigDecimal amount);

  void addHoldings(String identifier, String world, String currency, BigDecimal amount);

  void setHoldings(String identifier, String world, String currency, BigDecimal amount);

  /**
   * Used to create an economy account
   * @param identifier The identifier of the account.
   * @param world The world to create the account for.
   * @return True if the account was created.
   */
  boolean createAccount(String identifier, String world);

  /**
   * Used to delete an economy account
   * @param identifier The identifier of the account.
   * @param world The world to delete the account from.
   * @return True if the account was deleted.
   */
  boolean deleteAccount(String identifier, String world);
}