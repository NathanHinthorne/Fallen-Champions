package model;


/**
 * Hero Character Class.
 *
 * @author Austin Roaf
 * @version 1.0
 */
public abstract class Hero extends DungeonCharacter {


    /**
     * The hero type
     */
    private HeroTypes myType;
    /**
     * The inventory
     */
    private final Inventory myInventory;
    /**
     * The player using vision potion
     */
    private boolean myPlayerUsingVisionPotion;

    /**
     * Super constructor for hero
     * @param theHitPoints the hit points
     * @param theAtkSpd the attack speed
     * @param theLowHitChance the low hit chance
     * @param theHighHitChance the high hit chance
     * @param theHitChance the hit chance
     * @param theMinDmg the min damage
     * @param theMaxDmg the max damage
     * @param theCooldown the cooldown
     * @param theType the type
     */
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

    /**
     * Gets the inventory
     * @return the inventory
     */
    public Inventory getMyInventory() {
        return myInventory;
    }

    /**
     * Sets the player using vision potion
     * @param thePlayerUsingVisionPotion the player using vision potion
     */
    public void setUsingVisionPotion(boolean thePlayerUsingVisionPotion) {
        myPlayerUsingVisionPotion = thePlayerUsingVisionPotion;
    }

    /**
     * Gets the player using vision potion
     * @return the player using vision potion
     */
    public boolean usingVisionPotion() {
        return myPlayerUsingVisionPotion;
    }

    @Override
    public String toString() {
        return "H";
    }
}
