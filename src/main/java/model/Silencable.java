package model;

public interface Silencable {
    /**
     * Causes the other character to be silenced, removing their ability to use special abilities.
     * @param theOther The character to silence.
     */
    void silence(DungeonCharacter theOther, int theDuration);

    boolean isSilenced();
}
