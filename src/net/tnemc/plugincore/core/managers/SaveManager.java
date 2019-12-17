package net.tnemc.plugincore.core.managers;


import net.tnemc.plugincore.core.TNPCore;

import java.io.File;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Created by creatorfromhell on 8/29/2017.
 * All rights reserved.
 **/
public class SaveManager {

  protected LinkedList<Double> versions = new LinkedList<>();

  protected DataManager manager;
  protected Double saveVersion = 0.0;
  protected File file;

  public SaveManager(DataManager manager) {
    this.manager = manager;
  }

  public void addVersion(Double version) {
    addVersion(version, false);
  }

  public void addVersion(Double version, boolean current) {
    if(current) TNPCore.settings().setCurrentSaveVersion(version);
    versions.add(version);
  }

  public void initialize() throws SQLException {
    if(manager.getDb().first()) {
      manager.getDb().initialize();
      return;
    }
    saveVersion = manager.getDb().version();
    TNPCore.instance().getLogger().info("Save file of version: " + saveVersion + " detected.");
    load();
  }

  public void load() throws SQLException {
    if(saveVersion < TNPCore.settings().getCurrentSaveVersion() && saveVersion != 0) {
      manager.getDb().update(saveVersion);
      TNPCore.instance().getLogger().info("Saved data has been updated!");
    }
    Double version = (saveVersion != 0.0) ? saveVersion : TNPCore.settings().getCurrentSaveVersion();
    manager.getDb().load(version);
    TNPCore.instance().getLogger().info("Finished loading data!");
  }

  public void save() throws SQLException {
    manager.getDb().save(TNPCore.settings().getCurrentSaveVersion());
    TNPCore.instance().getLogger().info("Finished saving data!");
  }

  public DataManager getDataManager() {
    return manager;
  }
}