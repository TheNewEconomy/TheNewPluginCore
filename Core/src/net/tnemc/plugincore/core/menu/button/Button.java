package net.tnemc.plugincore.core.menu.button;

import net.tnemc.item.AbstractItemStack;
import net.tnemc.plugincore.core.menu.ConvertableData;

import java.util.Map;


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
 * purchase is able to be provided by both the using party and the creator of the project.
 *
 * By contributing to this software you agree that your rights to your contribution are handed
 * over to the owner of the project.
 */
public interface Button {

  /**
   *
   * @return The slot number associated with this button.
   */
  int slot();

  AbstractItemStack item();

  String type();

  ButtonType typeInstance();

  /**
   * @return A map containing the data that should be applied to the menu instance's data
   * which is able to be used in other menus/layouts within a menu session.
   */
  Map<String, ConvertableData> dataToApply();
}