package net.tnemc.plugincore.core.managers;

import net.tnemc.plugincore.core.menu.session.SessionViewer;
import net.tnemc.plugincore.core.menuredux.Menu;
import net.tnemc.plugincore.core.menuredux.icon.IconType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MenuManager {

  private Map<String, Menu> menus = new HashMap<>();
  private Map<String, IconType> iconTypes = new HashMap<>();
  private ConcurrentHashMap<UUID, SessionViewer> viewers = new ConcurrentHashMap<>();

  private static MenuManager instance;

  public MenuManager() {
    instance = this;
  }

  /**
   * Used to get a menu based on its name.
   * @param name The name of the menu we're looking for.
   * @return An optional containing the menu, if it exists, otherwise an empty
   * optional.
   */
  public Optional<Menu> getMenu(String name) {
    return Optional.of(menus.get(name));
  }

  /**
   * Used to get a {@link IconType} based on its name.
   * @param name The name of the {@link IconType} we're looking for.
   * @return An optional containing the {@link IconType}, if it exists, otherwise an empty
   * optional.
   */
  public Optional<IconType> getType(String name) {
    return Optional.of(iconTypes.get(name));
  }

  public static MenuManager getInstance() {
    return instance;
  }
}