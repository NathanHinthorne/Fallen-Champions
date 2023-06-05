package model;

import java.util.Random;

public class HealthPotion extends Potion implements Healable {

    private static Random random = new Random();

    private int myMinHeal;
    private int myMaxHeal;
    private double myHealChance;
    private int myHealingAmount;

    public HealthPotion() {
        myMinHeal = 25;
        myMaxHeal = 35;
        myHealChance = 70.0;
        myHealingAmount = random.nextInt(myMaxHeal - myMinHeal) + myMinHeal;
    }


    public int heal(final DungeonCharacter thePlayer) {

        /* generate a random int between 0 and the difference between
         * max and min, since you can't have a lower and upper bound,
         * then add the minimum back to bump it back into that range.
         */
//        int healAmt = random.nextInt(myMaxHeal - myMinHeal);
//        healAmt += myMinHeal;

        if ((thePlayer.getMaxHitPoints() - thePlayer.getHitPoints()) < myHealingAmount) {
            thePlayer.setHitPoints(thePlayer.getMaxHitPoints());
            myHealingAmount = thePlayer.getMaxHitPoints() - thePlayer.getHitPoints();
            return myHealingAmount;
        } else {
            thePlayer.setHitPoints(thePlayer.getHitPoints() + myHealingAmount);
            return myHealingAmount;
        }
    }

    @Override
    public int getDetail(Hero thePlayer) {
        if ((thePlayer.getMaxHitPoints() - thePlayer.getHitPoints()) < myHealingAmount) {
            myHealingAmount = thePlayer.getMaxHitPoints() - thePlayer.getHitPoints();
            return myHealingAmount;
        }
        return myHealingAmount;
    }

    @Override
    public String inventoryTextDisplay() {
        return "Health Potion";
    }

    @Override
    public int effect(final Hero thePlayer) {
       return heal(thePlayer);
    }

    @Override
    public String toString() {
        return "p";
    }

    @Override
    public String type() {
        return "Vision Potion";
    }

}
