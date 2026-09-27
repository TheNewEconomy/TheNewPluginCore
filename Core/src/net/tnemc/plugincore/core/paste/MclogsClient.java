package net.tnemc.plugincore.core.paste;
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

import net.kyori.adventure.key.Key;
import net.tnemc.plugincore.api.paste.PasteClient;
import net.tnemc.plugincore.api.paste.Pasteable;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Scanner;

/**
 * MCLogsClient
 *
 * @author creatorfromhell
 * @since 1.0.0.2
 */
public class MclogsClient implements PasteClient {

  private static final String MCLOGS_API_URL = "https://api.mclo.gs/1/log";

  /**
   * Retrieves the identifier associated with this object.
   *
   * @return The identifier as a String.
   */
  @Override
  public Key key() {

    return Key.key("tnpc", "mclo.gs");
  }

  public String apiKey() {

    return "N/A"; // mclo.gs does not require an API key
  }

  @Override
  public Optional<URI> createSingle(final Pasteable pasteable) {

    return createPaste(pasteable);
  }

  @Override
  public Collection<URI> createMultiple(final Pasteable... pasteables) {

    return Arrays.stream(pasteables)
            .map(this::createPaste)
            .flatMap(Optional::stream)
            .toList();
  }

  private Optional<URI> createPaste(final Pasteable pasteable) {

    try {

      final JSONObject requestBody = new JSONObject();
      requestBody.put("content", pasteable.content());

      final HttpURLConnection connection = (HttpURLConnection)new URL(MCLOGS_API_URL).openConnection();
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Type", "application/json");
      connection.setDoOutput(true);

      try(final OutputStream os = connection.getOutputStream()) {

        os.write(requestBody.toString().getBytes(StandardCharsets.UTF_8));
      }

      try(final Scanner scanner = new Scanner(connection.getInputStream(), StandardCharsets.UTF_8)) {

        final String response = scanner.useDelimiter("\\A").next();
        final JSONObject jsonResponse = new JSONObject(response);

        return Optional.of(URI.create(jsonResponse.getString("url")));
      }
    } catch(final IOException | IllegalArgumentException e) {

      e.printStackTrace();
      return Optional.empty();
    }
  }
}