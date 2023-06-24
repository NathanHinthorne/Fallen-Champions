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
     * Heal the character
     * @param theCharacter the character to heal
     * @return a healed character
     */
    int heal(final DungeonCharacter theCharacter);
}
