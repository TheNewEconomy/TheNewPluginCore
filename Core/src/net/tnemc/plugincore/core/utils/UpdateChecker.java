package net.tnemc.plugincore.core.utils;
/*
 * The New Plugin Core
 * Copyright (C) 2022 - 2024 Daniel "creatorfromhell" Vidmar
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

import com.vdurmont.semver4j.Semver;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

/**
 * UpdateChecker
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public class UpdateChecker {

  private final URI endpoint;
  private final Semver currentVersion;
  private final HttpClient client;

  public UpdateChecker(final URI endpoint, final String currentVersion) {

    this.endpoint = endpoint;
    this.currentVersion = new Semver(currentVersion, Semver.SemverType.LOOSE);

    this.client = HttpClient.newHttpClient();
  }

  public Optional<Semver> latest() {

    final HttpRequest request = HttpRequest.newBuilder(endpoint).GET().build();
    try {

      final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      if(response.statusCode() != 200) {
        return Optional.empty();
      }

      return Optional.of(new Semver(response.body().trim(), Semver.SemverType.LOOSE));

    } catch(final IOException e) {

      return Optional.empty();

    } catch(final InterruptedException e) {

      Thread.currentThread().interrupt();
      return Optional.empty();
    }
  }
}