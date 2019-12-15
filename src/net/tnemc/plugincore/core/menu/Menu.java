package net.tnemc.plugincore.core.menu;

import org.bukkit.entity.Player;

public abstract class Menu<T> {

  public abstract Menu display(Player... player);

  public abstract T build();

  public abstract Menu refresh();

  public abstract void close();
}