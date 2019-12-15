package net.tnemc.plugincore.core.menu;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

public class ConvertableData {

  private String identifier;
  private Object value;

  public ConvertableData(String identifier, Object value) {
    this.identifier = identifier;
    this.value = value;
  }

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public Integer getInteger() {
    return (Integer)value;
  }

  public Short getShort() {
    return (Short)value;
  }

  public String getString() {
    return (String)value;
  }

  public Double getDouble() {
    return (Double)value;
  }

  public Long getLong() {
    return (Long)value;
  }

  public BigDecimal getDecimal() {
    return (BigDecimal)value;
  }

  public BigInteger getBigInteger() {
    return (BigInteger)value;
  }

  public UUID getUUID() {
    return (UUID)value;
  }

  public Boolean getBoolean() {
    return (Boolean)value;
  }
}