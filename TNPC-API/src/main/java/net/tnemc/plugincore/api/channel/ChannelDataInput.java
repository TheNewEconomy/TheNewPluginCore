package net.tnemc.plugincore.api.channel;

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

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * ChannelData
 *
 * @author creatorfromhell
 * @since 2.0.0.0
 */
public interface ChannelDataInput extends AutoCloseable {

  /**
   * Reads a short value from the underlying data source.
   *
   * @return the short value read from the data source.
   * @throws IOException if an I/O error occurs while reading the value.
   */
  short readShort() throws IOException;

  /**
   * Reads an integer value from the underlying data source.
   *
   * @return the integer value read from the data source.
   * @throws IOException if an I/O error occurs while reading the value.
   */
  int readInt() throws IOException;

  /**
   * Reads a long value from the underlying data source.
   *
   * @return the long value read from the data source.
   * @throws IOException if an I/O error occurs while reading the value.
   */
  long readLong() throws IOException;

  /**
   * Reads a boolean value from the underlying data source.
   *
   * @return the boolean value read from the data source.
   * @throws IOException if an I/O error occurs while reading the value.
   */
  boolean readBoolean() throws IOException;

  /**
   * Reads a UTF-8 encoded string from the underlying data source.
   *
   * @return the UTF-8 encoded string read from the data source.
   * @throws IOException if an I/O error occurs while reading the string.
   */
  String readUTF() throws IOException;

  /**
   * Reads a {@code UUID} value from the underlying data source.
   *
   * @return the {@code UUID} value read from the data source.
   * @throws IOException if an I/O error occurs while reading the value.
   */
  UUID readUUID() throws IOException;

  /**
   * Reads a {@code BigDecimal} value from the underlying data source.
   *
   * @return the {@code BigDecimal} value read from the data source.
   * @throws IOException if an I/O error occurs while reading the value.
   */
  BigDecimal readBigDecimal() throws IOException;

  /**
   * Closes this {@code ChannelData} instance and releases any underlying resources.
   * This method should be called when the instance is no longer needed to ensure
   * proper resource management.
   *
   * @throws IOException if an I/O error occurs during the closing process.
   */
  @Override
  void close() throws IOException;
}