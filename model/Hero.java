package model;

public abstract class Hero extends DungeonCharacter {


    // The hero type
    private HeroTypes myType;
    private final Inventory myInventory;

    protected Hero(int theHitPoints, int theAtkSpd,
                   float theLowHitChance,
                   float theHighHitChance, float theHitChance, int theMinDmg, int theMaxDmg, int theCooldown, HeroTypes theType) {

        super(theHitPoints, theAtkSpd, theLowHitChance, theHighHitChance, theHitChance, theMinDmg, theMaxDmg, theCooldown);
        setType(theType);
        myInventory = new Inventory();
    }

    /**
     * Set the type of hero
     * @param theVal the hero type to set
     */
    public void setType(HeroTypes theVal) {
        myType = theVal;
    }

    /**
     * Gets the hero type
     * @return the hero type
     */
    public HeroTypes getType() {
        return myType;
    }

    public Inventory getMyInventory() {
        return myInventory;
    }


    public String toString() {
        return "H";
    }
}
