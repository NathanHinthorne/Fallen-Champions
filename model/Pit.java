package model;

import java.util.Random;

public class Pit extends DungeonCharacter implements java.io.Serializable {

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

        int theLowHealth = 10;
        int theHighHealth = 40;

        int fallHealth = generateNewHealth(theLowHealth, theHighHealth); // don't know if this works
        theHero.setHitPoints(getHitPoints() - fallHealth);
    }

    public String toString() {
        return "X";
    }
}
