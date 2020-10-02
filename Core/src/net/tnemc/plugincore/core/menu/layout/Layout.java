package net.tnemc.plugincore.core.menu.layout;

import net.tnemc.plugincore.core.menu.button.Button;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a menu layout, which is any of the {@link Button} objects
 * in a menu.
 */
public class Layout {

  private Map<Integer, Button> buttons = new HashMap<>();

  private String name;

}