package net.tnemc.plugincore.core.menu;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class MenuHolder implements InventoryHolder {

  private String menu;
  private String page;

  @Override
  public @NotNull Inventory getInventory() {
    return null;
  }
}