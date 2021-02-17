package net.tnemc.plugincore.core.menu.button;

import net.tnemc.plugincore.core.event.button.ButtonClickedEvent;

import java.util.function.Consumer;


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
public interface ButtonType {

  /**
   * @return The unique identifier for this button type.
   */
  String identifier();

  /**
   * Used to call the consumer to the on click event for the button.
   * @return The consumer listening to the onClick event for this button.
   */
  Consumer<ButtonClickedEvent> onClick();
}