package model;

public abstract class Monster implements Character, Healable {

    private int minHeal;

    private int maxHeal;

    float healChance = Character.generateHitChance(theLowChance, theHighChance);

    protected Monster(int theHitPoints, int theAtkSpd, float theHitChance, int theMinDmg, int theMaxDmg, int theCooldown,
                 int theMinHeal, int theMaxHeal, float theHealChance) {
            setHitPoints(theHitPoints);
            setAtkSpd(theAtkSpd);
            setHitChance(theHitChance);
            setMinDmg(theMinDmg);
            setMaxDmg(theMaxDmg);
            setSpecialCooldown(theCooldown);
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

    public float getHealChance() {
        return healChance;
    }

    public void setHealChance(float theChance) {
        theChance = healChance;
    }

    public void heal() {
        //TODO heal the monster
    }

    public String toString() {
        return "[M]";
    }

}
