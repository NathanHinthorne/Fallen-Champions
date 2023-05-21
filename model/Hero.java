package model;

public abstract class Hero extends DungeonCharacter {


    // should this be protected or public? - Nathan
    // should the constructor just be calling super() with the parameters of super() containing the given fields? - Nathan

    private HeroTypes myType;

    protected Hero(int theHitPoints, int theAtkSpd,
                   float theLowHitChance,
                   float theHighHitChance, float theHitChance, int theMinDmg, int theMaxDmg, int theCooldown, HeroTypes theType) {

        super(theHitPoints, theAtkSpd, theLowHitChance, theHighHitChance, theHitChance, theMinDmg, theMaxDmg, theCooldown);
        setType(theType);
    }

    public void setType(HeroTypes theVal) {
        myType = theVal;
    }

    public HeroTypes getType() {
        return myType;
    }


    public String toString() {
        return "H";
    }
}
