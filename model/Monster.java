package model;

public abstract class Monster extends DungeonCharacter implements Healable,java.io.Serializable {
    // The minimum heal amount
    private int minHeal;
    // The maximum heal amount
    private int maxHeal;
    // The Heal Chance
    double healChance;
    // The Monster type
    private MonsterTypes myType;

    // super() is best suited for initializing the fields of the super class. You might want to use that instead. - Nathan
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
        theHeal = minHeal;
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
        theHeal = maxHeal;
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
        theChance = healChance;
    }

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
