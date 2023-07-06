package model;

import java.util.Random;

/**
 * A health potion that heals the player
 * @author Nathan Hinthorne
 * @author Brendan Smith
 */
public class HealthPotion extends PotionDefensive implements Healable {
    /**
     * Randomizes the health potion amt
     */
    private static Random random = new Random();
    /**
     * The Min Heal
     */
    private int myMinHeal;
    /**
     * The max heal
     */
    private int myMaxHeal;
    /**
     * The heal chance
     */
    private double myHealChance;
    /**
     * The amount of health healed from the potion
     */
    private int myHealAmount;

    /**
     * constructor for health potion
     */
    public HealthPotion() {
        myMinHeal = 150;
        myMaxHeal = 250;
        myHealChance = 70.0;
        myHealAmount = random.nextInt(myMaxHeal - myMinHeal) + myMinHeal;
    }

    /**
     * Heals the player using the health potion effect
     * @param thePlayer the potion effect for the Hero to consume
     */
    @Override
    public void effect(final Hero thePlayer) {
        heal(thePlayer);

    }

    @Override
    public boolean canUseDuringBattle() {
        return true;
    }

    @Override
    public boolean canUseOutsideBattle() {
        return true;
    }


    /**
     * Heals the player
     * @param theCharacter the player to heal
     * @return the heal amt
     */
    public void heal(final DungeonCharacter theCharacter) {

        /* generate a random int between 0 and the difference between
         * max and min, since you can't have a lower and upper bound,
         * then add the minimum back to bump it back into that range.
         */
//        int healAmt = random.nextInt(myMaxHeal - myMinHeal);
//        healAmt += myMinHeal;

        if ((theCharacter.getMaxHealth() - theCharacter.getHealth()) < myHealAmount) {
            theCharacter.setHealth(theCharacter.getMaxHealth());
            myHealAmount = theCharacter.getMaxHealth() - theCharacter.getHealth();
        } else {
            theCharacter.setHealth(theCharacter.getHealth() + myHealAmount);
        }
    }

    /**
     * Gets the details of the potion and player
     * @return the details of the potion and player
     */
    @Override
    public String useMsg() {
        return "Restored " + myHealAmount + " HP!";
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
        return "Health Potion";
    }

    /**
     * Gets the healing amount
     * @return the healing amount
     */
    public int getHealingAmount() {
        return myHealAmount;
    }

}
