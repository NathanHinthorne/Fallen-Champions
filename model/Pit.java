package model;

import java.util.Random;

public class Pit extends DungeonCharacter implements java.io.Serializable {

    final static Random MY_RANDOM = new Random();

    // Checks if the pit is visible
    private boolean myIsVisible;


    public Pit(final boolean theIsVisible) { // for testing purposes, make all pits visible
        myIsVisible = theIsVisible;
    }

    public Pit() {
        myIsVisible = false;
    }

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
    public void fall(final Hero theHero) {

        myIsVisible = true;

        int theLowHealth = 20;
        int theHighHealth = 80;

        int fallHealth = generateNewHealth(theLowHealth, theHighHealth); // don't know if this works
        theHero.setHitPoints(getHitPoints() - fallHealth);
    }

    public String toString() {
        if (myIsVisible) {
            return "X";
        } else {
            return Room.EMPTY;
        }
    }
}
