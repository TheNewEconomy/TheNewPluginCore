package net.tnemc.plugincore.core.menu.button;

import net.tnemc.plugincore.core.event.button.ButtonClickedEvent;

import java.util.function.Consumer;

public interface ButtonType {

  String identifier();

  Consumer<ButtonClickedEvent> onClick();

  void addClickListener(Consumer<ButtonClickedEvent> listener);
}