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
     * The amount of health healed from the potion
     */
    private int myHealAmount;

    /**
     * constructor for health potion
     */
    public HealthPotion() {
        myMinHeal = 50;
        myMaxHeal = 100;
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
        return "Restored " + myHealAmount + " Health!";
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
