package net.tnemc.plugincore.core.config;

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

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.settings.Settings;
import net.tnemc.plugincore.PluginCore;
import net.tnemc.plugincore.core.compatibility.log.DebugLevel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a configuration file.
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public abstract class Config {

  protected YamlDocument yaml;

  protected final File file;
  protected final String defaults;

  protected final List<String> nodes = new ArrayList<>();

  protected Settings[] settings;

  public Config(final String fileName, final String defaults, final List<String> nodes, final Settings... settings) {
    this.defaults = defaults;
    this.nodes.addAll(nodes);
    file = new File(PluginCore.directory(), fileName);

    this.settings = settings;

    if(!file.exists()) {
      PluginCore.log().error("Configuration doesn't exist! File Name:" + fileName, DebugLevel.OFF);
    }
  }

  public boolean load() {

    try(final InputStream in = getResource(defaults)) {

      if(in != null) {
        this.yaml = YamlDocument.create(file, in, settings);

        return true;
      }
    } catch (final IOException e) {

      PluginCore.log().error("Error while creating config \"" + file.getName() + "\".", e, DebugLevel.OFF);
      return false;
    }
    return false;
  }

  public YamlDocument getYaml() {
    return yaml;
  }

  public void setYaml(final YamlDocument yaml) {
    this.yaml = yaml;
  }

  public boolean save() {
    try {
      yaml.save(file);
      return true;
    } catch(final IOException e) {
      PluginCore.log().error("Error while saving config \"" + nodes.get(0) + "\".", e, DebugLevel.OFF);
      return false;
    }
  }

  public void setComment(final String route, final List<String> comments) {

    if(yaml.contains(route)) {
      yaml.getBlock(route).setComments(comments);
    }
  }

  public void setComment(final String route, final String comment) {

    setComment(route, Collections.singletonList(comment));
  }

  public @Nullable InputStream getResource(@NotNull final String filename) {

    try {
      final URL url = this.getClass().getClassLoader().getResource(filename);
      if (url == null) {
        return null;
      } else {

        final URLConnection connection = url.openConnection();
        connection.setUseCaches(false);

        return connection.getInputStream();
      }
    } catch (final IOException var4) {
      return null;
    }
  }
}