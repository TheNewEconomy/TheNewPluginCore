package net.tnemc.plugincore.core.menuredux.icon;

import net.tnemc.item.AbstractItemStack;
import net.tnemc.plugincore.PluginCore;
import net.tnemc.plugincore.core.managers.MenuManager;
import net.tnemc.plugincore.core.menu.ConvertableData;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class Icon {
  Map<String, ConvertableData> apply = new HashMap<>();

  private int slot;
  private String type;
  private IconType typeInstance;
  private AbstractItemStack item;


  public Optional<IconType> type() {
    return Optional.of(typeInstance);
  }
}