package no.conduct.totalconquest;

/**
 * Enum som beskriver innholdet i en rute.
 */
public enum Sample {
    /**
     * Ruten inneholder en vegg. Det er ikke mulig å flytte en tank hit.
     */
    WALL,

    /**
     * Ruten inneholder en fiendtlig tank. Her kan du slå.
     */
    FOE,

    /**
     * Ruten ineholder en tank fra samme lag som deg.
     */
    FRIEND,

    /**
     * Ruten inneholder ingenting. Hit kan du flytte.
     */
    FREE;
}
