package net.tnemc.plugincore.core;

import net.tnemc.plugincore.core.common.utils.CoreSettings;
import net.tnemc.plugincore.core.managers.SaveManager;
import net.tnemc.plugincore.core.managers.UUIDManager;
import net.tnemc.plugincore.core.managers.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class TNPCore extends JavaPlugin {

  //Our pattern and date variables.
  public static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss.S");
  public static final Pattern uuidCreator = Pattern.compile("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})");

  //Our instance
  protected static TNPCore instance;

  //Our Managers
  protected WorldManager worldManager;
  protected UUIDManager uuidManager;
  protected SaveManager saveManager;

  //Our settings-related variables
  protected Map<String, String> uuidPrefixes = new HashMap<>();
  protected CoreSettings settings = new CoreSettings();

  public void onEnable() {
    instance = this;

    //Our UUID-related things
    uuidPrefixes.put("town-", "town-");
    uuidPrefixes.put("nation-", "nation-");
    uuidPrefixes.put("faction-", "faction-");
    uuidPrefixes.put("kingdom-", "kingdom-");
    uuidPrefixes.put("village-", "village-");
    uuidPrefixes.put("pact-", "pact-");

    //Our world-related things
    String defaultWorld = "world";
    if(Bukkit.getWorlds().size() >= 1) {
      defaultWorld = Bukkit.getServer().getWorlds().get(0).getName();
    }

    worldManager = new WorldManager(defaultWorld);
  }

  public static TNPCore instance() {
    return instance;
  }

  public String getPrefix(String name) {
    return uuidPrefixes.getOrDefault(name, name);
  }

  public static CoreSettings settings() {
    return instance.settings;
  }

  public static UUIDManager uuidManager() {
    return instance.uuidManager;
  }

  public static WorldManager worldManager() {
    return instance.worldManager;
  }

  public static SaveManager saveManager() {
    return instance.saveManager;
  }

  public static void debug(String message) {
    if(TNPCore.settings().debug()) {
      TNPCore.instance().getLogger().info("[DEBUG MODE]" + message);
    }
  }

  public static void debug(StackTraceElement[] stack) {
    for(StackTraceElement element : stack) {
      debug(element.toString());
    }
  }

  public static void debug(Exception e) {
    if(TNPCore.settings().debug()) {
      e.printStackTrace();
    }
  }
}