package model;

public abstract class Monster extends Character implements Healable {

    private int minHeal;

    private int maxHeal;

    double healChance = 0.2; // 20% chance to heal

    // super() is best suited for initializing the fields of the super class. You might want to use that instead. - Nathan
    protected Monster(int theHitPoints, int theAtkSpd, float theHitChance, int theMinDmg, int theMaxDmg, int theCooldown,
                 int theMinHeal, int theMaxHeal, float theHealChance) {
            setHitPoints(theHitPoints);
            setSpd(theAtkSpd);
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

    public double getHealChance() {
        return healChance;
    }

    public void setHealChance(double theChance) {
        theChance = healChance;
    }

    public void heal() {
        //TODO heal the monster by increasing hp determined by the healChance

    }

    public String toString() {
        return "[M]";
    }

}
