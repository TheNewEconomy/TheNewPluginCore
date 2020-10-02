package net.tnemc.plugincore.core.menu.session;

import java.util.Map;
import java.util.UUID;

public interface MenuSession {

  Map<UUID, SessionViewer> viewers();
}