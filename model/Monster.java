package model;

public abstract class Monster extends DungeonCharacter implements Healable {

    private int minHeal;

    private int maxHeal;

    double healChance;

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

    public int getMinHeal() {
        return minHeal;
    }

    public void setMinHeal(int theHeal) {
        theHeal = minHeal;
    }

    public int getMaxHeal() {
        return maxHeal;
    }

    public void setMaxHeal(int theHeal) {
        theHeal = maxHeal;
    }

    public double getHealChance() {
        return healChance;
    }

    public void setHealChance(double theChance) {
        theChance = healChance;
    }

    public void heal() {
        //TODO heal the monster by increasing hp determined by the healChance

    }

    public void setType(MonsterTypes theVal) {
        myType = theVal;
    }

    public MonsterTypes getType() {
        return myType;
    }

    public String toString() {
        return "M";
    }

}
