package model;

public interface Weakenable {
    /**
     * Causes the other character to be weakened, reducing the damage they deal by 50% next turn.
     * @param theOther The character to weaken.
     */
    void weaken(DungeonCharacter theOther, int theDuration);

    boolean isWeakened();
}
