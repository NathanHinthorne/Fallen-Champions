package model;

import java.util.Random;

public class HealthPotion extends Potion implements Healable {

    private int myMinHeal = 5;
    private int myMaxHeal = 10;
    private double myHealChance = 50.0;
    private static Random random = new Random();



    public void heal() {

        /* generate a random int between 0 and the difference between
         * max and min, since you can't have a lower and upper bound,
         * then add the minimum back to bump it back into that range.
         */
        int healAmt = random.nextInt(myMaxHeal - myMinHeal);
        healAmt += myMinHeal;

        thePlayer.setHitPoints(thePlayer.getHitPoints() + healAmt);
    }

    @Override
    public void effect(final Hero thePlayer) {
       heal(thePlayer);
    }

    @Override
    public String toString() {
        return "p";
    } // change char later

}
