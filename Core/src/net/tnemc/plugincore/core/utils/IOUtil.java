package net.tnemc.plugincore.core.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;

public final class IOUtil {

  private IOUtil() {
  }

  public static Optional<Path> findJarInsensitive(final String name, final Path directory) {

    if(!Files.isDirectory(directory)) {
      return Optional.empty();
    }

    try(final Stream<Path> files = Files.list(directory)) {

      final String expected = name.toLowerCase(Locale.ROOT) + ".jar";

      return files.filter(Files::isRegularFile)
              .filter(path ->path.getFileName().toString().toLowerCase(Locale.ROOT).equals(expected))
              .findFirst();

    } catch(final IOException e) {

      return Optional.empty();
    }
  }
}