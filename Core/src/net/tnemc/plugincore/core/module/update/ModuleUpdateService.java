package net.tnemc.plugincore.core.module.update;

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

import com.vdurmont.semver4j.Semver;
import net.tnemc.plugincore.api.logging.DebugLevel;
import net.tnemc.plugincore.api.logging.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.HexFormat;
import java.util.Locale;
import java.util.Optional;

/**
 * ModuleUpdateService
 *
 * @author creatorfromhell
 * @since 2.0.0.0
 */
public final class ModuleUpdateService {

  private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(10);

  private final ModuleRepository repository;
  private final HttpClient client;
  private final Path moduleDirectory;
  private final Logger logger;

  public ModuleUpdateService(final ModuleRepository repository, final Path moduleDirectory, final Logger logger) {

    this(repository,
         moduleDirectory,
         logger,
         HttpClient.newBuilder()
                 .connectTimeout(REQUEST_TIMEOUT)
                 .followRedirects(HttpClient.Redirect.NORMAL)
                 .build()
        );
  }

  public ModuleUpdateService(final ModuleRepository repository, final Path moduleDirectory, final Logger logger, final HttpClient client) {

    this.repository = repository;
    this.moduleDirectory = moduleDirectory;
    this.logger = logger;
    this.client = client;
  }

  /**
   * Checks whether an update exists for a module.
   *
   * @param module         The module name.
   * @param currentVersion The currently installed version.
   *
   * @return The newer artifact, or empty if no update exists.
   */
  public Optional<ModuleArtifact> check(final String module, final String currentVersion) {

    final Optional<ModuleArtifact> artifact = repository.latest(module);

    if(artifact.isEmpty()) {
      return Optional.empty();
    }

    final ModuleArtifact latest = artifact.get();

    try {

      final Semver current = new Semver(currentVersion, Semver.SemverType.LOOSE);
      final Semver available = new Semver(latest.version(), Semver.SemverType.LOOSE);
      if(available.isGreaterThan(current)) {

        return Optional.of(latest);
      }

    } catch(final RuntimeException e) {

      logger.error("Unable to compare versions for module: " + module, e, DebugLevel.STANDARD);
    }

    return Optional.empty();
  }

  /**
   * Downloads a module artifact into the module directory.
   *
   * @param artifact The artifact to download.
   *
   * @return The downloaded file, or empty if the download failed.
   */
  public Optional<Path> download(final ModuleArtifact artifact) {

    try {

      Files.createDirectories(moduleDirectory);

      final Path temporary = Files.createTempFile(moduleDirectory, ".module-", ".tmp");

      try {

        final HttpRequest request = HttpRequest.newBuilder(artifact.download()).timeout(REQUEST_TIMEOUT).GET().build();
        final HttpResponse<Path> response = client.send(request, HttpResponse.BodyHandlers.ofFile(temporary));
        if(response.statusCode() < 200 || response.statusCode() >= 300) {

          logger.warning("Unable to download module " + artifact.name() + ". HTTP status: " + response.statusCode(), DebugLevel.STANDARD);
          return Optional.empty();
        }

        if(!verifyChecksum(temporary, artifact.sha256())) {

          logger.warning("Checksum verification failed for module " + artifact.name() + ".", DebugLevel.STANDARD);
          return Optional.empty();
        }

        final Path destination = moduleDirectory.resolve(fileName(artifact.download(), artifact.name()));
        Files.move(temporary, destination, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);

        return Optional.of(destination);

      } finally {

        Files.deleteIfExists(temporary);
      }

    } catch(final InterruptedException e) {

      Thread.currentThread().interrupt();

      logger.error("Interrupted while downloading module: " + artifact.name(), e, DebugLevel.STANDARD);

    } catch(final IOException e) {

      logger.error("Unable to download module: " + artifact.name(), e, DebugLevel.STANDARD);
    }

    return Optional.empty();
  }

  private String fileName(final URI uri, final String module) {

    final String path = uri.getPath();
    if(path != null && !path.isBlank()) {

      final int separator = path.lastIndexOf('/');
      final String name = (separator >= 0)? path.substring(separator + 1) : path;
      if(!name.isBlank() && name.endsWith(".jar")) {

        return name;
      }
    }

    return module + ".jar";
  }

  private boolean verifyChecksum(final Path file, final String expected) throws IOException {

    try {

      final MessageDigest digest = MessageDigest.getInstance("SHA-256");
      try(final InputStream input = Files.newInputStream(file)) {

        final byte[] buffer = new byte[8192];

        int read;
        while((read = input.read(buffer)) != -1) {

          digest.update(buffer, 0, read);
        }
      }

      final String actual = HexFormat.of().formatHex(digest.digest());
      return MessageDigest.isEqual(actual.getBytes(StandardCharsets.US_ASCII),
                                   expected.trim().toLowerCase(Locale.ROOT).getBytes(StandardCharsets.US_ASCII));

    } catch(final NoSuchAlgorithmException e) {

      throw new IllegalStateException("SHA-256 is unavailable.", e);
    }
  }
}