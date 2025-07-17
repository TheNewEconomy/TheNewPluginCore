package net.tnemc.plugincore.core.api;

public interface CallbackProvider {

  /**
   * Called when the {@link CallbackManager} is initialized.
   *
   * @param manager The callback manager.
   */
  void init(CallbackManager manager);
}