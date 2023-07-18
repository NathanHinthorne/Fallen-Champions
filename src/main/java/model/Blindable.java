package model;

public interface Blindable {
    /**
     * Causes the other character to be blinded, removing their ability to see.
     * @param theOther The character to blind.
     */
    void blind(DungeonCharacter theOther, int theDuration);

    boolean isBlind();
}
