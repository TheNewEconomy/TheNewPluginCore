package net.tnemc.plugincore.core.providers;

import net.tnemc.plugincore.core.menu.Menu;
import net.tnemc.plugincore.core.menu.button.ButtonType;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface MenuProvider {

  Map<String, Menu> menus();

  Map<String, ButtonType> buttonTypes();

  Optional<Menu> getMenu(final String name);

  boolean inMenu(final UUID identifier);

  boolean open(final UUID identifier, final String menu);

  void shut(final UUID identifier);

  Optional<ButtonType> findType(final String name);
}