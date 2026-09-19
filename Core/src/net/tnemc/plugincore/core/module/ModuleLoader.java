package net.tnemc.plugincore.core.module;

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
import net.tnemc.plugincore.api.PluginContext;
import net.tnemc.plugincore.api.logging.DebugLevel;
import net.tnemc.plugincore.api.logging.Logger;
import net.tnemc.plugincore.api.module.Module;
import net.tnemc.plugincore.api.module.ModuleInfo;
import net.tnemc.plugincore.core.exception.ModuleDependencyException;
import net.tnemc.plugincore.core.module.update.ModuleArtifact;
import net.tnemc.plugincore.core.module.update.ModuleUpdateService;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.stream.Stream;

/**
 * ModuleLoader
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public final class ModuleLoader implements AutoCloseable {

  private final Map<String, ModuleContainer> modules = new LinkedHashMap<>();

  private final Path directory;
  private final PluginContext plugin;
  private final Logger logger;
  private final String pluginVersion;
  private final ModuleUpdateService updates;
  private final ClassLoader parentClassLoader;

  public ModuleLoader(final Path directory, final PluginContext plugin, final String pluginVersion, final ModuleUpdateService updates) {

    this(directory, plugin, pluginVersion, updates, ModuleLoader.class.getClassLoader());
  }

  public ModuleLoader(final Path directory, final PluginContext plugin, final String pluginVersion,
                      final ModuleUpdateService updates, final ClassLoader parentClassLoader) {

    this.directory = directory;
    this.plugin = plugin;
    this.logger = plugin.logger();
    this.pluginVersion = pluginVersion;
    this.updates = updates;
    this.parentClassLoader = parentClassLoader;
  }

  /**
   * Discovers and loads all modules.
   */
  public void loadAll() {

    try {
      Files.createDirectories(directory);

      final Map<String, ModuleContainer> discovered = discover();
      final List<ModuleContainer> ordered = resolveDependencies(discovered);

      for(final ModuleContainer container : ordered) {
        enable(container);
      }
    } catch(final IOException e) {
      logger.error("Unable to access module directory.", e, DebugLevel.STANDARD);
    } catch(final ModuleDependencyException e) {
      logger.error("Unable to resolve module dependencies: " + e.getMessage(), e, DebugLevel.STANDARD);
    }
  }

  /**
   * Loads a single module.
   *
   * @param file The module JAR.
   *
   * @return True if the module was loaded.
   */
  public boolean load(final Path file) {

    ModuleContainer container = null;

    try {
      container = discover(file);

      final String name = normalize(container.info().name());
      if(modules.containsKey(name)) {

        logger.warning("Module is already loaded: " + container.info().name(), DebugLevel.STANDARD);
        container.close();
        return false;
      }

      if(!validatePluginVersion(container.info())) {

        container.close();
        return false;
      }

      if(!dependenciesLoaded(container.info())) {

        container.close();
        return false;
      }

      return enable(container);
    } catch(final Exception e) {

      if(container != null) {

        closeQuietly(container);
      }

      logger.error("Unable to load module: " + file.getFileName(), e, DebugLevel.STANDARD);
      return false;
    }
  }

  /**
   * Unloads a module.
   *
   * @param moduleName The module name.
   *
   * @return True if the module was unloaded.
   */
  public boolean unload(final String moduleName) {

    final String name = normalize(moduleName);
    final ModuleContainer container = modules.get(name);

    if(container == null) {

      return false;
    }

    final List<String> dependents = dependentsOf(name);
    if(!dependents.isEmpty()) {

      logger.warning("Unable to unload module " + container.info().name() + ". Required by: " + String.join(", ", dependents), DebugLevel.STANDARD);
      return false;
    }

    return unloadContainer(container);
  }

  /**
   * Checks for and hot swaps a module to its latest version.
   *
   * @param moduleName The module name.
   *
   * @return True if the update was completed.
   */
  public synchronized boolean update(final String moduleName) {

    final String name = normalize(moduleName);
    final ModuleContainer current = modules.get(name);

    if(current == null) {
      logger.warning("Unable to update unloaded module: " + moduleName, DebugLevel.STANDARD);
      return false;
    }

    final Optional<ModuleArtifact> available = updates.check(current.info().name(), current.info().version());
    if(available.isEmpty()) {

      return false;
    }

    final ModuleArtifact artifact = available.get();
    final Optional<Path> downloaded = updates.download(artifact);
    if(downloaded.isEmpty()) {

      logger.warning("Unable to download update for module: " + current.info().name(), DebugLevel.STANDARD);
      return false;
    }

    return hotSwap(name, downloaded.get());
  }

  /**
   * Replaces a loaded module with another module JAR.
   *
   * @param moduleName The loaded module name.
   * @param replacement The replacement JAR.
   *
   * @return True if replacement succeeded.
   */
  public synchronized boolean hotSwap(final String moduleName, final Path replacement) {

    final String name = normalize(moduleName);
    final ModuleContainer current = modules.get(name);
    if(current == null) {

      return false;
    }

    ModuleContainer candidate = null;

    try {

      candidate = discover(replacement);
      if(!normalize(candidate.info().name()).equals(name)) {

        logger.warning("Replacement module does not match " + current.info().name() + ". Found: " + candidate.info().name(), DebugLevel.STANDARD);
        candidate.close();
        return false;
      }

      if(!validatePluginVersion(candidate.info())) {

        candidate.close();
        return false;
      }

      if(!dependenciesAvailableForReplacement(candidate.info(), name)) {

        candidate.close();
        return false;
      }

      final List<String> dependents = transitiveDependents(name);
      Collections.reverse(dependents);
      final List<Path> reload = new ArrayList<>();
      for(final String dependent : dependents) {

        final ModuleContainer container = modules.get(dependent);
        if(container != null) {
          reload.add(container.file());

          if(!unloadContainer(container, false)) {
            candidate.close();

            logger.error("Unable to unload dependent module: " + container.info().name(), null, DebugLevel.STANDARD);

            restoreModules(reload);
            return false;
          }
        }
      }

      final Path originalFile = current.file();
      if(!unloadContainer(current, false)) {

        candidate.close();
        restoreModules(reload);
        return false;
      }

      candidate.close();
      candidate = null;

      if(!load(replacement)) {

        logger.error("Unable to load replacement for module: " + current.info().name(), null, DebugLevel.STANDARD);

        if(Files.exists(originalFile)) {

          load(originalFile);
        }

        Collections.reverse(reload);
        restoreModules(reload);
        return false;
      }

      Collections.reverse(reload);
      restoreModules(reload);

      logger.inform("Updated module " + current.info().name() + " from " + current.info().version() + " to " + artifactVersion(name));

      return true;
    } catch(final Exception e) {

      if(candidate != null) {

        closeQuietly(candidate);
      }

      logger.error("Unable to hot swap module: " + moduleName, e, DebugLevel.STANDARD);
      return false;
    }
  }

  /**
   * Returns a loaded module.
   *
   * @param name The module name.
   *
   * @return The loaded module.
   */
  public Optional<Module> module(final String name) {

    final ModuleContainer container = modules.get(normalize(name));

    return (container == null)? Optional.empty() : Optional.of(container.module());
  }

  /**
   * Returns all loaded module information.
   *
   * @return The loaded module information.
   */
  public Collection<ModuleInfo> modules() {

    return modules.values().stream().map(ModuleContainer::info).toList();
  }

  /**
   * Determines whether a module is loaded.
   *
   * @param name The module name.
   *
   * @return True if loaded.
   */
  public boolean hasModule(final String name) {

    return modules.containsKey(normalize(name));
  }

  private Map<String, ModuleContainer> discover() throws IOException {

    final Map<String, ModuleContainer> discovered = new HashMap<>();
    try(final Stream<Path> files = Files.list(directory)) {

      final List<Path> jars = files.filter(Files::isRegularFile).filter(path->path.getFileName().toString().toLowerCase(Locale.ROOT).endsWith(".jar")).toList();
      for(final Path jar : jars) {

        try {

          final ModuleContainer container = discover(jar);
          final String name = normalize(container.info().name());
          if(discovered.containsKey(name)) {

            logger.warning("Duplicate module detected: " + container.info().name(), DebugLevel.STANDARD);
            container.close();
            continue;
          }

          if(!validatePluginVersion(container.info())) {

            container.close();
            continue;
          }

          discovered.put(name, container);
        } catch(final Exception e) {

          logger.error("Unable to discover module: " + jar.getFileName(), e, DebugLevel.STANDARD);
        }
      }
    }

    return discovered;
  }

  private ModuleContainer discover(final Path file) throws Exception {

    final URL url = file.toUri().toURL();
    final ModuleClassLoader classLoader = new ModuleClassLoader(url, parentClassLoader);
    try {

      final ServiceLoader<Module> serviceLoader = ServiceLoader.load(Module.class, classLoader);
      final List<Module> found = serviceLoader.stream().map(ServiceLoader.Provider::get).toList();
      if(found.isEmpty()) {

        throw new IllegalStateException("No Module implementation found in " + file.getFileName());
      }

      if(found.size() > 1) {

        throw new IllegalStateException("Multiple Module implementations found in " + file.getFileName());
      }

      final Module module = found.get(0);
      final ModuleInfo info = module.getClass().getAnnotation(ModuleInfo.class);

      if(info == null) {

        throw new IllegalStateException("Module " + module.getClass().getName() + " is missing @ModuleInfo.");
      }

      return new ModuleContainer(info, module, classLoader, file);
    } catch(final Exception e) {
      classLoader.close();
      throw e;
    }
  }

  private boolean enable(final ModuleContainer container) {

    final String name = normalize(container.info().name());

    if(modules.containsKey(name)) {
      return false;
    }

    try {

      final Path dataDirectory = directory.resolve(container.info().name());
      Files.createDirectories(dataDirectory);

      final StandardModuleContext context = new StandardModuleContext(plugin, container.info(), dataDirectory);

      container.module().initialize(context);
      container.module().enable();

      modules.put(name, container);

      logger.inform("Enabled module: " + container.info().name() + " " + container.info().version());
      return true;
    } catch(final Exception e) {
      logger.error("Unable to enable module: " + container.info().name(), e, DebugLevel.STANDARD);
      closeQuietly(container);
      return false;
    }
  }

  private boolean unloadContainer(final ModuleContainer container) {

    return unloadContainer(container, true);
  }

  private boolean unloadContainer(final ModuleContainer container, final boolean checkDependents) {

    final String name = normalize(container.info().name());

    if(checkDependents) {

      final List<String> dependents = dependentsOf(name);
      if(!dependents.isEmpty()) {

        logger.warning("Unable to unload module " + container.info().name() + ". Required by: " + String.join(", ", dependents), DebugLevel.STANDARD);
        return false;
      }
    }

    try {

      container.module().disable();
    } catch(final Exception e) {

      logger.error("Module threw an exception while disabling: " + container.info().name(), e, DebugLevel.STANDARD);
    }

    modules.remove(name);

    try {

      container.close();
    } catch(final IOException e) {

      logger.error("Unable to close class loader for module: " + container.info().name(), e, DebugLevel.STANDARD);
      return false;
    }

    logger.inform("Disabled module: " + container.info().name());
    return true;
  }

  private List<ModuleContainer> resolveDependencies(final Map<String, ModuleContainer> discovered) {

    final List<ModuleContainer> ordered = new ArrayList<>();
    final Set<String> visited = new HashSet<>();
    final Set<String> visiting = new HashSet<>();
    for(final String module : discovered.keySet()) {

      resolve(module, discovered, visited, visiting, ordered);
    }

    return ordered;
  }

  private void resolve(final String name, final Map<String, ModuleContainer> discovered,
                       final Set<String> visited, final Set<String> visiting,
                       final List<ModuleContainer> ordered) {

    if(visited.contains(name)) {

      return;
    }

    if(!visiting.add(name)) {

      throw new ModuleDependencyException("Circular dependency involving: " + name);
    }

    final ModuleContainer container = discovered.get(name);

    if(container == null) {

      throw new ModuleDependencyException("Missing module: " + name);
    }

    for(final String dependency : container.info().dependencies()) {

      final String dependencyName = normalize(dependency);
      if(modules.containsKey(dependencyName)) {

        continue;
      }

      if(!discovered.containsKey(dependencyName)) {

        throw new ModuleDependencyException("Module " + container.info().name() + " requires missing dependency " + dependency);
      }

      resolve(dependencyName, discovered, visited, visiting, ordered);
    }

    visiting.remove(name);
    visited.add(name);
    ordered.add(container);
  }

  private boolean dependenciesLoaded(final ModuleInfo info) {

    for(final String dependency : info.dependencies()) {

      if(!modules.containsKey(normalize(dependency))) {

        logger.warning("Unable to load module " + info.name() + ". Missing dependency: " + dependency, DebugLevel.STANDARD);
        return false;
      }
    }

    return true;
  }

  private boolean dependenciesAvailableForReplacement(final ModuleInfo info, final String replacing) {

    for(final String dependency : info.dependencies()) {

      final String dependencyName = normalize(dependency);
      if(dependencyName.equals(replacing)) {

        logger.warning("Module " + info.name() + " cannot depend on itself.", DebugLevel.STANDARD);
        return false;
      }

      if(!modules.containsKey(dependencyName)) {

        logger.warning("Replacement module " + info.name() + " requires missing dependency: " + dependency, DebugLevel.STANDARD);
        return false;
      }
    }

    return true;
  }

  private List<String> dependentsOf(final String module) {

    final List<String> dependents = new ArrayList<>();
    for(final Map.Entry<String, ModuleContainer> entry : modules.entrySet()) {

      for(final String dependency : entry.getValue().info().dependencies()) {

        if(normalize(dependency).equals(module)) {

          dependents.add(entry.getKey());
          break;
        }
      }
    }

    return dependents;
  }

  private List<String> transitiveDependents(final String module) {

    final List<String> result = new ArrayList<>();
    collectDependents(module, result, new HashSet<>());
    return result;
  }

  private void collectDependents(final String module, final List<String> result, final Set<String> visited) {

    for(final String dependent : dependentsOf(module)) {

      if(!visited.add(dependent)) {

        continue;
      }

      result.add(dependent);
      collectDependents(dependent, result, visited);
    }
  }

  private boolean validatePluginVersion(final ModuleInfo info) {

    final String required = info.minimumVersion();

    if(required == null || required.isBlank() || required.equals("0.0.0")) {
      return true;
    }

    try {

      final Semver current = new Semver(pluginVersion, Semver.SemverType.LOOSE);
      final Semver minimum = new Semver(required, Semver.SemverType.LOOSE);
      if(current.isLowerThan(minimum)) {

        logger.warning("Unable to load module " + info.name() + ". Requires TNPC " + required + " or newer. Current version: " + pluginVersion, DebugLevel.STANDARD);
        return false;
      }

      return true;
    } catch(final RuntimeException e) {

      logger.error("Unable to validate version for module: " + info.name(), e, DebugLevel.STANDARD);
      return false;
    }
  }

  private void restoreModules(final List<Path> files) {

    for(final Path file : files) {
      if(!Files.exists(file)) {

        logger.warning("Unable to restore module. File does not exist: " + file, DebugLevel.STANDARD);
        continue;
      }

      if(!load(file)) {

        logger.warning("Unable to restore module: " + file.getFileName(), DebugLevel.STANDARD);
      }
    }
  }

  private String artifactVersion(final String module) {

    final ModuleContainer container = modules.get(module);
    return (container == null)? "unknown" : container.info().version();
  }

  private void closeQuietly(final ModuleContainer container) {

    try {

      container.close();
    } catch(final IOException ignored) {
    }
  }

  private String normalize(final String name) {

    return name.toLowerCase(Locale.ROOT);
  }

  /**
   * Disables all modules in reverse load order.
   */
  @Override
  public synchronized void close() {

    final List<ModuleContainer> loaded = new ArrayList<>(modules.values());
    Collections.reverse(loaded);
    for(final ModuleContainer container : loaded) {

      unloadContainer(container, false);
    }

    modules.clear();
  }
}