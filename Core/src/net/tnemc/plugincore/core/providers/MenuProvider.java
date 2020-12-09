package net.tnemc.plugincore.core.providers;

import net.tnemc.plugincore.core.menu.Menu;
import net.tnemc.plugincore.core.menu.button.ButtonType;

import java.util.Map;
import java.util.Optional;
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
public interface MenuProvider {

  Map<String, Menu> menus();

  Map<String, ButtonType> buttonTypes();

  Optional<Menu> getMenu(final String name);

  boolean inMenu(final UUID identifier);

  boolean open(final UUID identifier, final String menu);

  void shut(final UUID identifier);

  Optional<ButtonType> findType(final String name);
}