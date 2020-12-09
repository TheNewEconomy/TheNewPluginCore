package net.tnemc.plugincore.core.event.button;

import net.tnemc.plugincore.core.menu.Menu;
import net.tnemc.plugincore.core.menu.button.Button;

import java.util.UUID;

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
 * compiling this project into your software to utilize it as a library is acceptable as long
 * as it's not used for commercial purposes.
 *
 * Commercial usage is allowed if a commercial usage license is bought and verification of the
 * purchase is able to be provided by both parties.
 *
 * By contributing to this software you agree that your rights to your contribution are handed
 * over to the owner of the project.
 */
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