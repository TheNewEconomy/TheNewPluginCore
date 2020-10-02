package net.tnemc.plugincore.core.menu.session;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SessionViewer {

  private List<Integer> allowedSlots = new ArrayList<>();

  private UUID id;

  private boolean limited = false;

  public SessionViewer(UUID id) {
    this.id = id;
  }

  public SessionViewer(List<Integer> allowedSlots, UUID id) {
    this.allowedSlots = allowedSlots;
    this.id = id;
  }

  public List<Integer> getAllowedSlots() {
    return allowedSlots;
  }

  public void setAllowedSlots(List<Integer> allowedSlots) {
    this.allowedSlots = allowedSlots;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public boolean isLimited() {
    return limited;
  }

  public void setLimited(boolean limited) {
    this.limited = limited;
  }
}