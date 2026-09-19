package net.tnemc.plugincore.core.serialization;

import org.json.simple.JSONObject;

import java.util.UUID;

public class JSONHelper {

  private JSONObject object;

  public JSONHelper(final JSONObject object) {

    this.object = object;
  }

  public boolean has(final String identifier) {

    return object.containsKey(identifier);
  }

  public boolean isNull(final String identifier) {

    return object.get(identifier) == null;
  }

  public JSONHelper getHelper(final String identifier) {

    return new JSONHelper(getJSON(identifier));
  }

  public JSONObject getJSON(final String identifier) {

    return (JSONObject)object.get(identifier);
  }

  public Short getShort(final String identifier) {

    return Short.valueOf(getString(identifier));
  }

  public Double getDouble(final String identifier) {

    return Double.valueOf(getString(identifier));
  }

  public Integer getInteger(final String identifier) {

    return Integer.valueOf(getString(identifier));
  }

  public Boolean getBoolean(final String identifier) {

    return Boolean.valueOf(getString(identifier));
  }

  public String getString(final String identifier) {

    return object.get(identifier).toString();
  }

  public UUID getUUID(final String identifier) {

    return UUID.fromString(object.get(identifier).toString());
  }

  public JSONObject getObject() {

    return object;
  }

  public void setObject(final JSONObject object) {

    this.object = object;
  }
}