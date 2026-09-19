package net.tnemc.plugincore.api.logging;

/*
 * The New Economy Minecraft Server Plugin
 * <p>
 * Created by creatorfromhell on 2/20/2022.
 * <p>
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 * Created by creatorfromhell on 06/30/2017.
 */

/**
 * Used to outline the various debugging levels that could be utilized within the logging layer.
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public enum DebugLevel {
    OFF(0, "Off"),
    STANDARD(1, "Standard"),
    DETAILED(2, "Detailed"),
    DEVELOPER(3, "Developer");

    private final int priority;
    private final String identifier;

    DebugLevel(final int priority, final String identifier) {
        this.priority = priority;
        this.identifier = identifier;
    }

    public static DebugLevel fromID(final String identifier) {
        for (final DebugLevel level : values()) {
            if (level.identifier.equalsIgnoreCase(identifier)) return level;
        }
        return STANDARD;
    }

    public boolean compare(final DebugLevel compare) {
        return priority <= compare.priority;
    }

    public int priority() {
        return priority;
    }

    public String identifier() {
        return identifier;
    }
}
