package model;


/**
 * Hero Character Class.
 *
 * @author Austin Roaf
 * @author Nathan Hinthorne
 * @version 1.0
 */
public abstract class Hero extends DungeonCharacter {

    /**
     * The hero type
     */
    private HeroTypes myType;

    /**
     * The message for the hero it performs a basic attack
     */
    private String myBasicMsg;

    /**
     * The message for the hero it performs a special move
     */
    private String mySpecialMsg;

    /**
     * The inventory
     */
    private final Inventory myInventory;

    /**
     * The player using vision potion
     */
    private boolean myUsingVisionPotion;

    /**
     * The name of the hero
     */
    private String myName;

    /**
     * constructor for hero
     *
     * @param theHealth the health
     * @param theSpeed the attack speed
     * @param theBasicChance the hit chance
     * @param theMinDmg the minimum damage
     * @param theMaxDmg the maximum damage
     * @param theCooldown the cooldown
     * @param theMaxCooldown the max cooldown
     * @param theType the hero type
     */
    public Hero(HeroTypes theType, int theHealth, int theSpeed, double theBasicChance,
                double theSpecialChance, int theMinDmg, int theMaxDmg, int theCooldown,
                int theMaxCooldown, String theBasicMsg, String theSpecialMsg) {

        super(theHealth, theSpeed, theBasicChance, theSpecialChance, theMinDmg, theMaxDmg,
                theCooldown, theMaxCooldown);
        myBasicMsg = theBasicMsg;
        mySpecialMsg = theSpecialMsg;
        myType = theType;
        myInventory = new Inventory();
        myUsingVisionPotion = false;
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
    public Inventory getInventory() {
        return myInventory;
    }

    /**
     * Sets the player using vision potion
     * @param theUsingVisionPotion the player using vision potion
     */
    public void setUsingVisionPotion(boolean theUsingVisionPotion) {
        myUsingVisionPotion = theUsingVisionPotion;
    }

    /**
     * Gets the player using vision potion
     * @return the player using vision potion
     */
    public boolean usingVisionPotion() {
        return myUsingVisionPotion;
    }

    public String getBasicMsg() {
        return myBasicMsg;
    }

    public String getSpecialMsg() {
        return mySpecialMsg;
    }

    public void setName(String theName) {
        myName = theName;
    }
    public String getName() {
        return myName;
    }

    @Override
    public String toString() {
        return "□";
    }

}
