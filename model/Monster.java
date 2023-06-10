package model;

/**
 * Hero Warrior Character Class.
 *
 * @author Austin Roaf
 * @version 1.0
 */
public abstract class Monster extends DungeonCharacter implements Healable {
    /**
     * The minimum heal amount
     */
    private int minHeal;
    /**
     * The maximum heal amount
     */
    private int maxHeal;
    /**
     * The Heal Chance
     */
    double healChance;
    /**
     * The Monster type
     */
    private MonsterTypes myType;

    /**
     * Super constructor for Monster
     * @param theHitPoints the hit points
     * @param theAtkSpd the attack speed
     * @param theLowHitChance the low hit chance
     * @param theHighHitChance the high hit chance
     * @param theHitChance the hit chance
     * @param theMinDmg the minimum damage
     * @param theMaxDmg the maximum damage
     * @param theCooldown the cooldown
     * @param theMinHeal the minimum heal
     * @param theMaxHeal the maximum heal
     * @param theHealChance the heal chance
     * @param theType the monster type
     */
    protected Monster(int theHitPoints, int theAtkSpd,
                        float theLowHitChance, float theHighHitChance, float theHitChance, int theMinDmg, int theMaxDmg, int theCooldown,
                        int theMinHeal, int theMaxHeal, float theHealChance, MonsterTypes theType) {
            super(theHitPoints,theAtkSpd,theLowHitChance,theHighHitChance,theHitChance,theMinDmg,theMaxDmg,theCooldown);
            setType(theType);
            setMinHeal(theMinHeal);
            setMaxHeal(theMaxHeal);
            setHealChance(theHealChance);
    }

    /**
     * Get the min heal
     * @return the min heal
     */
    public int getMinHeal() {
        return minHeal;
    }

    /**
     * Sets the min heal
     * @param theHeal the min heal
     */
    public void setMinHeal(int theHeal) {
        minHeal = theHeal;
    }

    /**
     * Gets the max heal
     * @return the max heal
     */
    public int getMaxHeal() {
        return maxHeal;
    }

    /**
     * Sets the max heal
     * @param theHeal the max heal
     */
    public void setMaxHeal(int theHeal) {
        maxHeal = theHeal;
    }

    /**
     * Gets the heal chance
     * @return the heal chance
     */
    public double getHealChance() {
        return healChance;
    }

    /**
     * Sets the heal chance
     * @param theChance the heal chance
     */
    public void setHealChance(double theChance) {
        healChance = theChance;
    }

    /**
     * Generates the heal chance
     * @param theLowChance the low heal chance
     * @param theHighChance the high heal chance
     * @return the heal chance
     */
    public static int generateHealChance(int theLowChance, int theHighChance) {
        return theLowChance + MY_RANDOM.nextInt(theHighChance - theLowChance + 1);
    }

    /**
     * Heal the monster
     */
    public int heal(final DungeonCharacter theCharacter) {
        //TODO heal the monster by increasing hp determined by the healChance
        int healAmt = generateHealChance(minHeal, maxHeal);
        int hp = getHitPoints();
//        setHitPoints(healAmt + getHitPoints());
        hp += healAmt;
        setHitPoints(hp);
        return healAmt;
    }

    /**
     * Sets the type of monster
     * @param theVal the monster type
     */
    public void setType(MonsterTypes theVal) {
        myType = theVal;
    }

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
