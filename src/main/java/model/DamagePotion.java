package model;

import java.util.Random;

public class DamagePotion extends PotionOffensive {

    /**
     * Randomizes the health potion amt
     */
    private static final Random RANDOM = new Random();

    /**
     * The damage amount
     */
    private int myDamageAmount;


    /**
     * constructor for damage potion
     */
    public DamagePotion() {
        myDamageAmount = RANDOM.nextInt(10) + 30;
    }

    @Override
    public void effect(Monster theMonster) {

    }

    @Override
    public boolean canUseDuringBattle() {
        return true;
    }

    @Override
    public boolean canUseOutsideBattle() {
        return false;
    }


    /**
     * Gets the details of the potion and player
     * @return the details of the potion and player
     */
    @Override
    public String useMsg() {
        return "You threw a damage potion at the monster! It inflicted " + myDamageAmount + " damage.";
    }

    @Override
    public String toString() {
        return "p";
    }

    /**
     * The potion type
     * @return the potion type
     */
    @Override
    public String type() {
        return "Damage Potion";
    }
}
