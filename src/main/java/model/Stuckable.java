package model;

public interface Stuckable {
    /**
     * Causes the other character to be stuck, skipping their turn
     * @param theOther The character to make stuck.
     */
    void stuckify(DungeonCharacter theOther, int theDuration);

    boolean isStuck();
}
