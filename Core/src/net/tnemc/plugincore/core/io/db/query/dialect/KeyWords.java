package net.tnemc.plugincore.core.io.db.query.dialect;

public interface KeyWords {

  default String select() {
    return "SELECT %COLUMN% FROM %TABLE%";
  }

  default String update() {
    return "UPDATE";
  }

  default String insert() {
    return "INSERT INTO";
  }

  default String where() {
    return "WHERE";
  }

  default String star() {
    return "*";
  }
}