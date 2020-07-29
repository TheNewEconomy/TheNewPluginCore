package net.tnemc.plugincore.core.providers.uuid;

import java.util.UUID;

/**
 * Class that represents a Name & UUID pair.
 */
public class UUIDPair {

  private final UUID identifier;
  private String username;

  public UUIDPair(UUID identifier, String username) {
    this.identifier = identifier;
    this.username = username;
  }

  public UUID getIdentifier() {
    return identifier;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}