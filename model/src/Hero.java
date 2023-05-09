public abstract class Hero implements Character {



    void hero(int theHitPoints, int theAtkSpd, float theHitChance, int theMinDmg, int theMaxDmg, int theCooldown) {

        setHitPoints(theHitPoints);
        setAtkSpd(theAtkSpd);
        setHitChance(theHitChance);
        setMinDmg(theMinDmg);
        setMaxDmg(theMaxDmg);
        setSpecialCooldown(theCooldown);
    }
}
