package net.tnemc.plugincore.core.providers.log;

public enum DebugLevel {
  /**
   * Standard debug level. This should include any inform calls and any error calls containing exceptions.
   */
  STANDARD("S"),

  /**
   * Detailed debug level. This should contain more detailed error calls and debug calls.
   */
  DETAILED("DE"),

  /**
   * Developer debug level. This should contain all calls.
   */
  DEVELOPER("DV");

  private String identifier;

  DebugLevel(String identifier) {
    this.identifier = identifier;
  }

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }
}