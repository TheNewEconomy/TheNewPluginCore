package net.tnemc.plugincore.core.utils;

/*
 * The New Plugin Core
 * Copyright (C) 2022 - 2026 Daniel "creatorfromhell" Vidmar
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


import net.tnemc.plugincore.PluginCore;
import net.tnemc.plugincore.api.logging.DebugLevel;
import net.tnemc.plugincore.api.logging.Logger;

/**
 * Timings
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public class Timings implements AutoCloseable {

  private final Logger logger;

  private String statement = "Timings: ";
  private long start;

  public Timings(final Logger logger) {

    this.logger = logger;
  }

  /**
   * Starts our timings in order to measure duration of methods or actions.
   *
   * @return The timings object.
   */
  public Timings start() {

    start = System.nanoTime();
    return this;
  }

  /**
   * Used to build our timings with a statement for logging purposes.
   *
   * @param statement The statement to use.
   *
   * @return The timings instance.
   */
  public Timings withStatement(final String statement) {

    this.statement = statement;
    return this;
  }

  /**
   * Stops the timings and returns the duration.
   *
   * @return Return the duration this timing lasted.
   */
  public long stop() {

    return System.nanoTime() - start;
  }

  /**
   * Stops the timings and logs it to the console and server log.
   *
   * @param level The DebugLevel to use for this.
   */
  public void stopLog(final DebugLevel level) {

    logger.debug(statement + stop(), level);
  }

  @Override
  public void close() throws Exception {

    stopLog(DebugLevel.DETAILED);
  }
}