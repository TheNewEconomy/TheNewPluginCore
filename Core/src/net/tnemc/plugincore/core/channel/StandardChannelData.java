package net.tnemc.plugincore.core.channel;
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

import net.tnemc.plugincore.api.channel.ChannelData;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

/**
 * ChannelBytesWrapper represents a utility wrapper for a ByteArray stream.
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public final class StandardChannelData implements ChannelData {

  private final DataInputStream input;

  public StandardChannelData(final byte[] data) {

    this.input = new DataInputStream(
            new ByteArrayInputStream(data)
    );
  }

  @Override
  public short readShort() throws IOException {

    return input.readShort();
  }

  @Override
  public int readInt() throws IOException {

    return input.readInt();
  }

  @Override
  public long readLong() throws IOException {

    return input.readLong();
  }

  @Override
  public boolean readBoolean() throws IOException {

    return input.readBoolean();
  }

  @Override
  public String readUTF() throws IOException {

    return input.readUTF();
  }

  @Override
  public UUID readUUID() throws IOException {

    return UUID.fromString(input.readUTF());
  }

  @Override
  public BigDecimal readBigDecimal() throws IOException {

    return new BigDecimal(input.readUTF());
  }

  @Override
  public void close() throws IOException {

    input.close();
  }
}