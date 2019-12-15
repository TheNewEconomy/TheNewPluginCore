package net.tnemc.plugincore.core.common.utils;

import net.tnemc.plugincore.core.TNPCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class IDFinder {

  public static UUID ecoID(String username) {
    return ecoID(username, false);
  }

  public static UUID ecoID(String username, boolean skip) {
    if(TNPCore.uuidManager().hasID(username)) {
      return TNPCore.uuidManager().getID(username);
    }
    UUID eco = (skip)? genUUID() : genUUID(username);
    //TNPCore.uuidManager().addUUID(username, eco);
    return eco;
  }

  public static String getUsername(String identifier) {
    if(isUUID(identifier)) {
      UUID id = UUID.fromString(identifier);
      OfflinePlayer player = Bukkit.getOfflinePlayer(id);
      if(player == null) {
        return MojangAPI.getPlayerUsername(id);
      }
      return player.getName();
    }
    return identifier;
  }

  public static UUID genUUID(String name) {
    UUID id = Bukkit.getOfflinePlayer(name).getUniqueId();
    if(id != null) return id;

    id = MojangAPI.getPlayerUUID(name);
    if(id != null) return id;

    return genUUID();
  }

  public static UUID genUUID() {
    UUID id = UUID.randomUUID();
    while(TNPCore.uuidManager().containsUUID(id)) {
      //This should never happen, but we'll play it safe
      id = UUID.randomUUID();
    }
    return id;
  }

  public static String ecoToUsername(UUID id) {
    return (TNPCore.uuidManager().containsUUID(id))? TNPCore.uuidManager().getUsername(id) : getUsername(id.toString());
  }

  public static UUID getID(CommandSender sender) {
    if(!(sender instanceof Player)) {
      return getID(TNPCore.settings().getConsoleName());
    }
    return getID((Player)sender);
  }

  public static UUID getID(Player player) {
    return getID(player.getName());
  }

  public static UUID getID(OfflinePlayer player) {
    if(!TNPCore.settings().useUUID()) {
      return ecoID(player.getName());
    }
    return player.getUniqueId();
  }

  public static Player getPlayer(String identifier) {
    UUID id = getID(identifier);
    if(!TNPCore.settings().useUUID()) {
      return Bukkit.getPlayer(IDFinder.ecoToUsername(id));
    }
    if(!Bukkit.getServer().getOnlineMode()) {
      return Bukkit.getPlayer(IDFinder.ecoToUsername(id));
    }
    return Bukkit.getPlayer(id);
  }

  private static OfflinePlayer getOffline(String identifier, boolean username) {
    if(username) return Bukkit.getOfflinePlayer(identifier);
    UUID id = getID(identifier);

    return Bukkit.getOfflinePlayer(id);
  }

  private static OfflinePlayer getOffline(UUID id) {
    return Bukkit.getOfflinePlayer(id);
  }

  public static UUID getID(String identifier) {
    identifier = ChatColor.stripColor(identifier.replaceAll("\\[.*?\\] ?", "")).trim();
    TNPCore.debug("GETID: " + identifier);
    if(isUUID(identifier)) {
      return UUID.fromString(identifier);
    }

    if(identifier.contains(TNPCore.instance().getPrefix("faction-"))) {
      TNPCore.debug("Faction");
      UUID id = ecoID(identifier);
      checkSpecial(id);
      return id;
    }

    if(identifier.contains("towny-war-chest")) {
      TNPCore.debug("Towny War Chest");
      UUID id = ecoID(identifier);
      checkSpecial(id);
      return id;
    }

    if(identifier.contains(TNPCore.instance().getPrefix("town-"))) {
      TNPCore.debug("Towny Town");
      UUID id = ecoID(identifier);
      checkSpecial(id);
      return id;
    }

    if(identifier.contains(TNPCore.instance().getPrefix("nation-"))) {
      TNPCore.debug("Towny Nation");
      UUID id = ecoID(identifier);
      checkSpecial(id);
      return id;
    }

    UUID mojangID = (identifier.equalsIgnoreCase(TNPCore.settings().getConsoleName()))? null : Bukkit.getOfflinePlayer(identifier).getUniqueId();
    if(mojangID == null) {
      TNPCore.debug("MOJANG API RETURNED NULL VALUE");
      return ecoID(identifier);
    }
    //TNPCore.uuidManager().addUUID(identifier, mojangID);
    return mojangID;
  }

  private static void checkSpecial(UUID id) {
    if(!TNPCore.uuidManager().isNonPlayer(id)) {
      TNPCore.uuidManager().addNonPlayer(id);
    }
  }

  public static boolean isUUID(String lookup) {
    try {
      UUID.fromString(lookup);
      return true;
    } catch (Exception ex) {
      return false;
    }
  }
}