package java.lang.model;

public abstract class Hero implements java.lang.Character {



    protected Hero(int theHitPoints, int theAtkSpd, float theHitChance, int theMinDmg, int theMaxDmg, int theCooldown) {

        setHitPoints(theHitPoints);
        setAtkSpd(theAtkSpd);
        setHitChance(theHitChance);
        setMinDmg(theMinDmg);
        setMaxDmg(theMaxDmg);
        setSpecialCooldown(theCooldown);
    }


    public String toString() {
        return "[H]";
    }
}
