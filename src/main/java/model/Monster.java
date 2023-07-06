package model;

/**
 * Abstract monster class.
 *
 * @author Austin Roaf
 * @author Nathan Hinthorne
 * @version 1.0
 */
public abstract class Monster extends DungeonCharacter implements Healable {

    /**
     * The minimum heal amount
     */
    private int myMinHeal;

    /**
     * The maximum heal amount
     */
    private int myMaxHeal;

    /**
     * The Heal Chance
     */
    private double myHealChance;

    /**
     * The Monster type
     */
    private MonsterTypes myType;

    /**
     * The amount of health the monster healed this turn
     */
    private int myHealAmount;

    /**
     * constructor for monster
     *
     * @param theHealth the health
     * @param theSpeed the attack speed
     * @param theBasicChance the hit chance
     * @param theMinDmg the minimum damage
     * @param theMaxDmg the maximum damage
     * @param theCooldown the cooldown
     * @param theMaxCooldown the max cooldown
     * @param theType the hero type
     */
    public Monster(final MonsterTypes theType, final int theHealth, final int theSpeed, final double theBasicChance,
                   final double theSpecialChance, final int theMinDmg, final int theMaxDmg, final int theCooldown,
                   final int theMaxCooldown, String theBasicSelectMsg, String theSpecialSelectMsg,
                   final int theMinHeal, final int theMaxHeal, final double theHealChance) {

        super(theHealth, theSpeed, theBasicChance, theSpecialChance, theMinDmg, theMaxDmg, theCooldown,
                theMaxCooldown, theBasicSelectMsg, theSpecialSelectMsg);

        myType = theType;
        myMinHeal = theMinHeal;
        myMaxHeal = theMaxHeal;
        myHealChance = theHealChance;
    }

    /**
     * Get the min heal
     * @return the min heal
     */
    public int getMinHeal() {
        return myMinHeal;
    }

    /**
     * Gets the max heal
     * @return the max heal
     */
    public int getMaxHeal() {
        return myMaxHeal;
    }

    /**
     * Gets the heal chance
     * @return the heal chance
     */
    public double getHealChance() {
        return myHealChance;
    }

    /**
     * Heal the monster
     *
     * @param theCharacter the character to heal
     */
    public void heal(final DungeonCharacter theCharacter) {

        myHealAmount = RANDOM.nextInt(myMaxHeal - myMinHeal) + myMinHeal;
        setHealth(getHealth() + myHealAmount);
    }

    public int getHealAmount() {
        return myHealAmount;
    }

//    public String healMsg() {
//        return "The " + myType + " healed for " + myHealAmount + " health.";
//    }
//
//    public String healFailMsg() {
//        return "The " + myType + " tried to heal, but failed.";
//    }





    /**
     * Gets the monster type
     * @return the monster type
     */
    public MonsterTypes getType() {
        return myType;
    }

    public String toString() {
        return "M";
    }

}
