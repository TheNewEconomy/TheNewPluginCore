package net.tnemc.plugincore.core.io.storage.engine;

public record StorageSettings(String fileName, String host, int port, String database, String user,
                              String password,
                              String prefix, boolean publicKey, boolean ssl, String poolName,
                              int maxPool, long maxLife, long timeout,
                              String proxyType) {
}