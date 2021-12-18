package net.tnemc.plugincore.core.io.db.query;

import net.tnemc.plugincore.core.io.db.IDTable;
import net.tnemc.plugincore.core.io.db.UserTable;
import net.tnemc.plugincore.core.io.db.model.Table;
import net.tnemc.plugincore.core.io.db.query.dialect.KeyWords;
import net.tnemc.plugincore.core.io.db.query.dialect.SQLDialect;
import net.tnemc.plugincore.core.io.db.query.dialect.impl.StandardDialect;

import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Optional;

/**
 * Created by creatorfromhell.
 *
 * The New Plugin Core Minecraft Server Plugin
 *
 * All rights reserved.
 *
 * Some Details about what is acceptable use of this software:
 *
 * This project accepts user contributions.
 *
 * Direct redistribution of this software is not allowed without written permission. However,
 * compiling this project into your software to utilize it as a library is acceptable as long as
 * it's not used for commercial purposes.
 *
 * Commercial usage is allowed if a commercial usage license is bought and verification of the
 * purchase is able to be provided by both parties.
 *
 * By contributing to this software you agree that your rights to your contribution are handed over
 * to the owner of the project.
 *
 * ResultSet results = Connector.result(QueryBuilder.create(Type.SELECT, columns, values).WHERE(id, player.getUniquedID().build()))
 */
public class QueryBuilder {

  private StringBuilder query = new StringBuilder();

  private SQLDialect dialect;
  private KeyWords type;
  private LinkedHashMap<String, Object> columns = new LinkedHashMap<>();
  private LinkedHashMap<String, Object> where = new LinkedHashMap<>();
  private boolean parameterized = true;


  public QueryBuilder(SQLDialect dialect) {
    Table userTable = new UserTable(new StandardDialect());
    Table idTable = new IDTable(new StandardDialect());
    Optional<ResultSet> results = userTable.union(idTable, "uuid");
    Optional<ResultSet> queryResults = userTable.query(new QueryBuilder(new StandardDialect()).select(userTable, "uuid", "username", "lastJoined"));
    this.dialect = dialect;
  }

  public QueryBuilder type(KeyWords type) {
    this.type = type;
    return this;
  }

  public QueryBuilder columns(LinkedHashMap<String, Object> columns) {
    this.columns = columns;
    return this;
  }

  public QueryBuilder where(LinkedHashMap<String, Object> where) {
    this.where = where;
    return this;
  }

  public QueryBuilder parameterized(boolean parameterized) {
    this.parameterized = parameterized;
    return this;
  }

  public QueryBuilder select(Table table, String... columns) {
    return this;
  }

  public String build() {
    return query.toString();
  }
}