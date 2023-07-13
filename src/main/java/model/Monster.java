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
     * The amount of XP the monster is worth
     */
    private int XPWorth;


//    protected String myDeathMsg;

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
                   final int theMaxCooldown, final int theMinHeal, final int theMaxHeal, final double theHealChance,
                   final int theXP) {

        super(theHealth, theSpeed, theBasicChance, theSpecialChance, theMinDmg, theMaxDmg, theCooldown, theMaxCooldown);

        myType = theType;
        myMinHeal = theMinHeal;
        myMaxHeal = theMaxHeal;
        myHealChance = theHealChance;
        XPWorth = theXP;
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


    /**
     * Gets the monster type
     * @return the monster type
     */
    public MonsterTypes getType() {
        return myType;
    }

    public String getName() {
        return myType.toString();
    }

    public String toString() {
        return "M";
    }

    public int getXPWorth() {
        return XPWorth;
    }


    public abstract String[] getDescription();

    public abstract String[] getDeathMsg();

}