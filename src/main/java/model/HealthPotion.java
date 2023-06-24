package model;

import java.util.Random;

/**
 * A health potion that heals the player
 * @author Nathan Hinthorne
 * @author Brendan Smith
 */
public class HealthPotion extends Potion implements Healable {
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
     * The heal amt
     */
    private int myHealingAmount;

    /**
     * Super constructor for health potion
     */
    public HealthPotion() {
        myMinHeal = 150;
        myMaxHeal = 250;
        myHealChance = 70.0;
        myHealingAmount = random.nextInt(myMaxHeal - myMinHeal) + myMinHeal;
    }

    /**
     * Heals the player using the health potion effect
     * @param thePlayer the potion effect for the Hero to consume
     */
    @Override
    public void effect(final Hero thePlayer) {
        heal(thePlayer);

    }

    /**
     * Heals the player
     * @param theCharacter the player to heal
     * @return the heal amt
     */
    public int heal(final DungeonCharacter theCharacter) {

        /* generate a random int between 0 and the difference between
         * max and min, since you can't have a lower and upper bound,
         * then add the minimum back to bump it back into that range.
         */
//        int healAmt = random.nextInt(myMaxHeal - myMinHeal);
//        healAmt += myMinHeal;

        if ((theCharacter.getMaxHealth() - theCharacter.getHealth()) < myHealingAmount) {
            theCharacter.setHealth(theCharacter.getMaxHealth());
            myHealingAmount = theCharacter.getMaxHealth() - theCharacter.getHealth();
            return myHealingAmount;
        } else {
            theCharacter.setHealth(theCharacter.getHealth() + myHealingAmount);
            return myHealingAmount;
        }
    }

    /**
     * Gets the details of the potion and player
     * @param thePlayer the player to give the potion
     * @return the heal amt
     */
    @Override
    public int getDetail(Hero thePlayer) {
        if ((thePlayer.getMaxHealth() - thePlayer.getHealth()) < myHealingAmount) {
            myHealingAmount = thePlayer.getMaxHealth() - thePlayer.getHealth();
            return myHealingAmount;
        }
        return myHealingAmount;
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
        return myHealingAmount;
    }

}
