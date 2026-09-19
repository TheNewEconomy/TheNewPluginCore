package net.tnemc.plugincore.core.paste;
/*
 * The New Plugin Core
 * Copyright (C) 2022 - 2025 Daniel "creatorfromhell" Vidmar
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
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Scanner;

/**
 * PasteeClient
 *
 * @author creatorfromhell
 * @since 1.0.0.2
 */
public class PasteeClient implements PasteClient {

  private static final String PASTEEE_API_URL = "https://api.paste.ee/v1/pastes";
  private final String apiKey;

  public PasteeClient(final String apiKey) {

    this.apiKey = apiKey;
  }

  /**
   * Retrieves the identifier associated with this object.
   *
   * @return The identifier as a String.
   */
  @Override
  public Key key() {

    return Key.key("tnpc", "paste.ee");
  }

  @Override
  public Optional<URI> createSingle(final Pasteable pasteable) {

    return createBatchPaste(pasteable);
  }

  @Override
  public Collection<URI> createMultiple(final Pasteable... pasteables) {

    return createBatchPaste(pasteables).map(Collections::singleton).orElse(Collections.emptySet());
  }

  private Optional<URI> createBatchPaste(final Pasteable... pasteables) {

    try {
      final JSONObject requestBody = new JSONObject();
      requestBody.put("description", "Batch Paste");

      final JSONArray sectionsArray = new JSONArray();
      for(final Pasteable pasteable : pasteables) {

        final JSONObject section = new JSONObject();
        section.put("name", pasteable.fileName() + "." + pasteable.extension());
        section.put("syntax", pasteable.syntax());
        section.put("contents", pasteable.content());
        sectionsArray.put(section);
      }

      requestBody.put("sections", sectionsArray);

      final HttpURLConnection connection = (HttpURLConnection)new URL(PASTEEE_API_URL).openConnection();
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Type", "application/json");
      connection.setRequestProperty("X-Auth-Token", apiKey);
      connection.setDoOutput(true);

      try(final OutputStream os = connection.getOutputStream()) {

        os.write(requestBody.toString().getBytes(StandardCharsets.UTF_8));
      }

      try(final Scanner scanner = new Scanner(connection.getInputStream(), StandardCharsets.UTF_8)) {

        final String response = scanner.useDelimiter("\\A").next();
        final JSONObject jsonResponse = new JSONObject(response);

        return Optional.of(URI.create(jsonResponse.getString("link")));
      }
    } catch(final IOException | IllegalArgumentException e) {

      e.printStackTrace();
      return Optional.empty();
    }
  }
}