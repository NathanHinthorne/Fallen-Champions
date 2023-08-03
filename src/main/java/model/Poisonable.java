package model;

/**
 * Interface for characters that can be poisoned (like monsters)
 */
public interface Poisonable {

    /**
     * poison the character
     */
    void poison(DungeonCharacter theCharacter, int theDuration);

    boolean isPoisoned();
}
