package net.tnemc.plugincore.core.paste.impl;
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

import net.tnemc.plugincore.core.paste.IPasteable;
import net.tnemc.plugincore.core.paste.IPasteClient;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

/**
 * MCLogsClient
 *
 * @author creatorfromhell
 * @since 1.0.0.2
 */
public class MclogsClient implements IPasteClient {
  private static final String MCLOGS_API_URL = "https://api.mclo.gs/1/log";

  @Override
  public String identifier() {
    return "mclo.gs";
  }

  @Override
  public String endpoint() {
    return MCLOGS_API_URL;
  }

  @Override
  public String apiKey() {
    return "N/A"; // mclo.gs does not require an API key
  }

  @Override
  public Optional<String> createSingle(final IPasteable pasteable) {
    return createPaste(pasteable);
  }

  @Override
  public Optional<String> createMultiple(final IPasteable... pasteables) {
    return Arrays.stream(pasteables)
            .map(this::createPaste)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .reduce((first, second) -> second); // Return the last created paste's URL
  }

  private Optional<String> createPaste(final IPasteable pasteable) {
    try {

      final JSONObject requestBody = new JSONObject();
      requestBody.put("content", pasteable.content());

      final HttpURLConnection connection = (HttpURLConnection) new URL(MCLOGS_API_URL).openConnection();
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Type", "application/json");
      connection.setDoOutput(true);

      try(final OutputStream os = connection.getOutputStream()) {

        os.write(requestBody.toString().getBytes(StandardCharsets.UTF_8));
      }

      try(final Scanner scanner = new Scanner(connection.getInputStream(), StandardCharsets.UTF_8)) {

        final String response = scanner.useDelimiter("\\A").next();
        final JSONObject jsonResponse = new JSONObject(response);

        return Optional.of(jsonResponse.getString("url"));
      }
    } catch(final IOException e) {

      e.printStackTrace();
      return Optional.empty();
    }
  }
}