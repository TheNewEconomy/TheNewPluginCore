package net.tnemc.plugincore.core.common.utils;

public class CoreSettings {

  private String consoleName = "Server Name";
  private boolean useUUID = true;
  private boolean debug = false;

  //Database settings
  private String connectionPoolName = "TNE_Pool";
  private boolean useDatasource = false;

  //Version Settings
  private Double currentSaveVersion = 0.0;

  public String getConsoleName() {
    return consoleName;
  }

  public void setConsoleName(String consoleName) {
    this.consoleName = consoleName;
  }

  public boolean useUUID() {
    return useUUID;
  }

  public void setUseUUID(boolean useUUID) {
    this.useUUID = useUUID;
  }

  public boolean debug() {
    return debug;
  }

  public void setDebug(boolean debug) {
    this.debug = debug;
  }

  public String getConnectionPoolName() {
    return connectionPoolName;
  }

  public void setConnectionPoolName(String connectionPoolName) {
    this.connectionPoolName = connectionPoolName;
  }

  public boolean isUseDatasource() {
    return useDatasource;
  }

  public void setUseDatasource(boolean useDatasource) {
    this.useDatasource = useDatasource;
  }

  public Double getCurrentSaveVersion() {
    return currentSaveVersion;
  }

  public void setCurrentSaveVersion(Double currentSaveVersion) {
    this.currentSaveVersion = currentSaveVersion;
  }
}