package model;


/**
 * Hero Character Class.
 *
 * @author Austin Roaf
 * @author Nathan Hinthorne
 * @version 1.0
 */
public abstract class Hero extends DungeonCharacter {

    public static final int LEVEL_1_XP = 100;
    public static final int LEVEL_2_XP = 200;
    public static final int LEVEL_3_XP = 300;
    public static final int LEVEL_4_XP = 400;
    public static final int LEVEL_5_XP = 500;

    /**
     * The hero type
     */
    private final HeroTypes myType;

    /**
     * Whether the given attack was a critical hit
     */
    protected boolean critHit;

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
     * The experience points
     */
    protected int myXP;

    /**
     * The level of the hero
     */
    protected int myLevel;

    /**
     * The name of the basic ability
     */
    protected String myBasicName;

    /**
     * The name of the special ability
     */
    protected String mySpecialName;

    /**
     * The name of the passive ability
     */
    protected String myPassiveName;


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
    public Hero(final HeroTypes theType, final int theHealth, final int theSpeed, final double theBasicChance,
                final double theSpecialChance, final int theMinDmg, final int theMaxDmg, final int theCooldown,
                final int theMaxCooldown) {

        super(theHealth, theSpeed, theBasicChance, theSpecialChance, theMinDmg, theMaxDmg,
                theCooldown, theMaxCooldown);

        myType = theType;
        myInventory = new Inventory(); // change parameter for scientist who has larger inventory?
        myUsingVisionPotion = false;
        myXP = 0;
        myLevel = 0;
        critHit = false;
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

    public void setName(String theName) {
        myName = theName;
    }
    public String getName() {
        return myName;
    }

    public void gainXP(final int theXP) {
        if (myLevel == 5) { // don't gain xp if max level
            return;
        }

        myXP += theXP;
    }

    public boolean wasCritHit() {
        return critHit;
    }

    public int getXP() {
        return myXP;
    }

    public int getLevel() {
        return myLevel;
    }

    public void levelUp() {
        myXP = 0;
        myLevel++;

        myMaxHealth += 10;
        myHealth = myMaxHealth;
        myMinDmg += 5;
        myMaxDmg += 5;
    }

    public abstract boolean isUnlocked();

    @Override
    public String toString() {
        return "□";
    }

    public int getXPToLevel() {
        if (myLevel == 0) {
            return LEVEL_1_XP;
        } else if (myLevel == 1) {
            return LEVEL_2_XP;
        } else if (myLevel == 2) {
            return LEVEL_3_XP;
        } else if (myLevel == 3) {
            return LEVEL_4_XP;
        } else if (myLevel == 4) {
            return LEVEL_5_XP;
        } else {
            return 0;
        }
    }
}
