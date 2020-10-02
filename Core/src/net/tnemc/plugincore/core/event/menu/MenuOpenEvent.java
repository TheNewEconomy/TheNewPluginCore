package net.tnemc.plugincore.core.event.menu;

import net.tnemc.plugincore.core.menu.Menu;

import java.util.UUID;

public class MenuOpenEvent {

  private Menu menu;
  private UUID clicker;

  public MenuOpenEvent(Menu menu, UUID clicker) {
    this.menu = menu;
    this.clicker = clicker;
  }

  public Menu getMenu() {
    return menu;
  }

  public void setMenu(Menu menu) {
    this.menu = menu;
  }

  public UUID getClicker() {
    return clicker;
  }

  public void setClicker(UUID clicker) {
    this.clicker = clicker;
  }
}