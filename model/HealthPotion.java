package model;

import java.util.Random;

public class HealthPotion extends Potion implements Healable {

    private int myMinHeal = 5;
    private int myMaxHeal = 10;
    private double myHealChance = 50.0;
    private static Random RANDOMIZER;


    public void heal() {

        /* generate a random int between 0 and the difference between
         * max and min, since you can't have a lower and upper bound,
         * then add the minimum back to bump it back into that range.
         */
        int healAmt = RANDOMIZER.nextInt(myMaxHeal - myMinHeal);
        healAmt += myMinHeal;

        // Get Hero HP and heal

    }

    @Override
    public void effect() {

    }

    @Override
    public String toString() {
        return "Health Potion";
    }

}
