package model;
import controller.DungeonGame;

import java.util.Random;

public class HealthPotion extends Potion implements Healable {

    private int minHeal = 5;
    private int maxHeal = 10;
    private double healChance = 50.0;
    private static Random RANDOMIZER;


    public void heal() {

        /* generate a random int between 0 and the difference between
         * max and min, since you can't have a lower and upper bound,
         * then add the minimum back to bump it back into that range.
         */
        int healAmt = RANDOMIZER.nextInt(maxHeal-minHeal);
        healAmt += minHeal;

        // Get Hero HP and heal

    }

    public void effect() {

    }

}
