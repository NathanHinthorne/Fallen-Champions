package model;

public abstract class Hero extends DungeonCharacter {


    // should this be protected or public? - Nathan
    // should the constructor just be calling super() with the parameters of super() containing the given fields? - Nathan

    protected Hero(int theHitPoints, int theAtkSpd,
                   float theLowHitChance,
                   float theHighHitChance, float theHitChance, int theMinDmg, int theMaxDmg, int theCooldown) {

        super(theHitPoints, theAtkSpd, theLowHitChance, theHighHitChance, theHitChance, theMinDmg, theMaxDmg, theCooldown);
    }


    public String toString() {
        return "H";
    }
}
