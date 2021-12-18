package net.tnemc.plugincore.core.io.db.model;

public class Column {

  //The name of the column
  private String name;

  //The string-representation of the column type(e.g. varchar, text, etc)
  private String type;

  private long length = -1;
  private long precision = -1;
  private long scale = -1;

  public Column(String name, String type) {
    this.name = name;
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public long getLength() {
    return length;
  }

  public void setLength(long length) {
    this.length = length;
  }

  public long getPrecision() {
    return precision;
  }

  public void setPrecision(long precision) {
    this.precision = precision;
  }

  public long getScale() {
    return scale;
  }

  public void setScale(long scale) {
    this.scale = scale;
  }
}