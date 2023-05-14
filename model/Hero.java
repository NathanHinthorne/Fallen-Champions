package model;

public abstract class Hero extends Character {


    // should this be protected or public? - Nathan
    // should the constructor just be calling super() with the parameters of super() containing the given fields? - Nathan

    protected Hero(int theHitPoints, int theAtkSpd, float theHitChance, int theMinDmg, int theMaxDmg, int theCooldown) {

        setHitPoints(theHitPoints);
        setSpd(theAtkSpd);
        setHitChance(theHitChance);
        setMinDmg(theMinDmg);
        setMaxDmg(theMaxDmg);
        setSpecialCooldown(theCooldown);
    }


    public String toString() {
        return "[H]";
    }
}
