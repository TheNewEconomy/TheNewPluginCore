package net.tnemc.plugincore.core.menuredux.icon;

import net.tnemc.plugincore.core.listeners.IconClickListener;

import java.util.function.Consumer;

public interface IconType {

  String name();

  Consumer<IconClickListener> onClick();
}