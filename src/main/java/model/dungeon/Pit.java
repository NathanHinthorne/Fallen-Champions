package model.dungeon;

import model.characters.heroes.Hero;

import java.util.Random;

/**
 * Represent a pit in the dungeon.
 * The hero can fall into a pit and lose health.
 *
 * @author Nathan Hinthorne
 * @version 1.0
 */
public class Pit implements java.io.Serializable {

    /**
     * The random number generator
     */
    final static Random MY_RANDOM = new Random();


    /**
     * Gernerates new health
     * @param theLowHealth the low health
     * @param theHighHealth the high health
     * @return the new health amount
     */
    public static int generateNewHealth(int theLowHealth, int theHighHealth) {
        return theLowHealth + MY_RANDOM.nextInt(theHighHealth - theLowHealth + 1);
    }

    /**
     * Makes the hero lose health by falling into a hole
     */
    public int fall(final Hero theHero) {

        int theLowHealth = 5;
        int theHighHealth = 10;

        int fallDamage = generateNewHealth(theLowHealth, theHighHealth); // don't know if this works
        theHero.setHealth(theHero.getHealth() - fallDamage);

        return fallDamage;
    }

    @Override
    public String toString() {
        return "X";
    }
}
