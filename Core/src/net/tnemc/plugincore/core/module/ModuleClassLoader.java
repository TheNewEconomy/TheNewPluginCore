package net.tnemc.plugincore.core.module;

import net.tnemc.plugincore.PluginCore;

import java.net.URL;
import java.net.URLClassLoader;

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
public class ModuleClassLoader extends URLClassLoader {

  public ModuleClassLoader(URL url) {

    super(new URL[]{ url }, PluginCore.instance().getClass().getClassLoader());
  }

  @Override
  protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {

    try {
      return super.loadClass(name, resolve);
    } catch(ClassNotFoundException e) {
      return null;
    }
  }

  @Override
  protected void finalize() throws Throwable {

    super.finalize();

    PluginCore.log().debug("ModuleOld Class Loader has been GC'd");
  }
}