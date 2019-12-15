package net.tnemc.plugincore.core.managers;

import java.util.UUID;

public abstract class UUIDManager {

  public abstract boolean hasUsername(UUID id);
  public abstract boolean hasID(String username);
  public abstract boolean containsUUID(UUID id);
  public abstract void addUUID(String username, UUID id);
  public abstract UUID getID(String username);
  public abstract String getIdentifier(String username);
  public abstract String getUsername(UUID id);
  public abstract String getUsername(String id);
  public abstract void remove(String username);

  public abstract boolean isNonPlayer(UUID id);
  public abstract void addNonPlayer(UUID id);
}