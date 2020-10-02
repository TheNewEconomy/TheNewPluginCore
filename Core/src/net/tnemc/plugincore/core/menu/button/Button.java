package net.tnemc.plugincore.core.menu.button;

import net.tnemc.item.AbstractItemStack;
import net.tnemc.plugincore.core.menu.ConvertableData;

import java.util.Map;

public interface Button {

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