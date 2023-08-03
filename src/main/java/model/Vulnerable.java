package model;

public interface Vulnerable {
    /**
     * Causes the other character to be vulnerable, increasing the damage they take by 3x next turn.
     * @param theOther The character to vulnerate.
     */
    void vulnerate(final DungeonCharacter theOther, final int theDuration);

    boolean isVulnerable();
}
