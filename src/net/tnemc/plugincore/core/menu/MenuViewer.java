package net.tnemc.plugincore.core.menu;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MenuViewer {

  private ConcurrentHashMap<String, ConvertableData> data = new ConcurrentHashMap<>();

  private UUID identifier;

  public MenuViewer(UUID identifier) {
    this.identifier = identifier;
  }

  public ConcurrentHashMap<String, ConvertableData> getData() {
    return data;
  }

  public void setData(ConcurrentHashMap<String, ConvertableData> data) {
    this.data = data;
  }

  public UUID getIdentifier() {
    return identifier;
  }

  public void setIdentifier(UUID identifier) {
    this.identifier = identifier;
  }
}