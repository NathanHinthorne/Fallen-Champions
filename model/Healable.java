package model;

/**
 * An interface for healing characters
 *
 * @author Nathan Hinthorne
 * @author Brendan Smith
 * @version 1.0
 */
public interface Healable {
    /**
     * The min heal
     */
    int myMinHeal = 5;
    /**
     * The max heal
     */
    int myMaxHeal = 10;
    /**
     * The heal chance
     */
    double healChance = 50.0;

    /**
     * Heal the character
     * @param theChar the character to heal
     * @return a healed character
     */
    public int heal(final DungeonCharacter theChar);
}
