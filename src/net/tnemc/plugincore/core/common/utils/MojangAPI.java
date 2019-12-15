package net.tnemc.plugincore.core.common.utils;

import net.tnemc.plugincore.core.TNPCore;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class MojangAPI {

  public static UUID getPlayerUUID(String name) {
    if(TNPCore.uuidManager().hasID(name)) {
      return TNPCore.uuidManager().getID(name);
    }
    JSONObject object = send("https://api.mojang.com/users/profiles/minecraft/" + name);
    UUID id = (object != null && object.containsKey("id")) ? UUID.fromString(dashUUID(object.get("id").toString())) : IDFinder.ecoID(name, true);


    if(id != null) {
      TNPCore.uuidManager().addUUID(name, id);
    }

    return id;
  }

  public static String getPlayerUsername(UUID id) {
    if(TNPCore.uuidManager().containsUUID(id)) {
      return TNPCore.uuidManager().getUsername(id);
    }
    JSONObject object = send("https://sessionserver.mojang.com/session/minecraft/profile/" + id.toString().replace("-", ""));
    String name = object.get("name").toString();

    if(name != null) {
      TNPCore.uuidManager().addUUID(name, id);
    }
    return name;
  }

  private static JSONObject send(String url) {
    return (JSONObject) JSONValue.parse(sendGetRequest(url));
  }

  private static String dashUUID(String undashed) {
    return undashed.replaceAll(TNPCore.uuidCreator.pattern(), "$1-$2-$3-$4-$5");
  }

  private static String sendGetRequest(String URL) {
    StringBuilder builder = new StringBuilder();
    try {
      HttpURLConnection connection = (HttpURLConnection) new URL(URL).openConnection();
      connection.setRequestMethod("GET");
      BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String response;
      while((response = reader.readLine()) != null) {
        builder.append(response);
      }
      reader.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return builder.toString();
  }
}
