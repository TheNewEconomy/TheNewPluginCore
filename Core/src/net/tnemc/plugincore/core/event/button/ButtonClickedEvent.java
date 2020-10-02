package net.tnemc.plugincore.core.event.button;

import net.tnemc.plugincore.core.menu.Menu;
import net.tnemc.plugincore.core.menu.button.Button;

import java.util.UUID;

public class ButtonClickedEvent {

  private Menu menu;
  private Button button;
  private UUID clicker;

  public ButtonClickedEvent(Menu menu, Button button, UUID clicker) {
    this.menu = menu;
    this.button = button;
    this.clicker = clicker;
  }

  public Menu getMenu() {
    return menu;
  }

  public void setMenu(Menu menu) {
    this.menu = menu;
  }

  public Button getButton() {
    return button;
  }

  public void setButton(Button button) {
    this.button = button;
  }

  public UUID getClicker() {
    return clicker;
  }

  public void setClicker(UUID clicker) {
    this.clicker = clicker;
  }
}