package net.tnemc.plugincore.core.module;

import java.net.URL;
import java.net.URLClassLoader;

public class ModuleClassLoader extends URLClassLoader {

  public ModuleClassLoader(URL url, ClassLoader parent) {
    super(new URL[]{url}, parent);
  }

  @Override
  protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
    try {
      return super.loadClass(name, resolve);
    } catch (ClassNotFoundException ignore) {
      return null;
    }
  }

  @Override
  protected void finalize() throws Throwable {
    super.finalize();

    System.out.println("Module Class Loader has been GC'd");
  }
}