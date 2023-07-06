package model;

import java.io.File;
import java.util.Random;

/**
 * Main Dungeon Character Class.
 *
 * @author Austin Roaf
 * @author Nathan Hinthorne
 * @version 1.0
 */
public abstract class DungeonCharacter implements java.io.Serializable {
    /**
     * The random number generator
     */
    public static final Random RANDOM = new Random();

    /**
     * The cooldown of the special ability
     */
    protected int myCooldown;

    /**
     * Max special cooldown
     */
    protected int myMaxCooldown;

    /**
     * The health
     */
    protected int myHealth;

    /**
     * The max hit points
     */
    protected int myMaxHealth;

    /**
     * The attack speed
     */
    protected int mySpeed;

    /**
     * The min damage
     */
    protected int myMinDmg;

    /**
     * The max damage
     */
    protected int myMaxDmg;

    /**
     * The chance for the basic ability to succeed
     */
    protected double myBasicChance;

    /**
     * The chance for the special ability to succeed
     */
    protected double mySpecialChance;

    /**
     * Whether the attack missed
     */
    protected boolean myAttackWasSuccess;

    /**
     * The message for the character when it performs a basic attack
     */
    protected String myBasicSelectMsg;

    /**
     * The message for the character when it performs a special move
     */
    protected String mySpecialSelectMsg;

    /**
     * The special ability sfx
     */
    private File mySpecialSFX;

    /**
     * The basic ability sfx
     */
    private File myBasicSFX;



    /**
     * constructor for dungeon character
     *
     * @param theHealth the health
     * @param theSpeed the attack speed
     * @param theBasicChance the hit chance
     * @param theMinDmg the minimum damage
     * @param theMaxDmg the maximum damage
     * @param theCooldown the cooldown
     * @param theMaxCooldown the max cooldown
     */
    public DungeonCharacter(int theHealth, int theSpeed, double theBasicChance, double theSpecialChance,
                            int theMinDmg, int theMaxDmg, int theCooldown, int theMaxCooldown,
                            String theBasicSelectMsg, String theSpecialSelectMsg) {

        myHealth = theHealth;
        mySpeed = theSpeed;
        myMaxHealth = theHealth;
        myBasicChance = theBasicChance;
        mySpecialChance = theSpecialChance;
        myMinDmg = theMinDmg;
        myMaxDmg = theMaxDmg;
        myCooldown = theCooldown;
        myMaxCooldown = theMaxCooldown;
        myBasicSelectMsg = theBasicSelectMsg;
        mySpecialSelectMsg = theSpecialSelectMsg;
    }

    /**
     * Allows for the hero or monster to attack
     * @return the basic attack
     */
    public int basicAtk(DungeonCharacter theOther) {

        if(myHealth <= 0) {
            throw new IllegalStateException("Cannot attack when dead.");
        }

        int damage = RANDOM.nextInt(myMaxDmg - myMinDmg) + myMinDmg; // Random number between min and max damage
        double chanceToHit = Math.random();

        if (chanceToHit <= myBasicChance) {
            myAttackWasSuccess = true;

            // set the other character's health
            if (theOther.getHealth() - damage < 0) {
                theOther.setHealth(0);
            } else {
                theOther.setHealth(theOther.getHealth() - damage);
            }
            return damage;

        } else {
            myAttackWasSuccess = false;
            return 0;
        }
    }

    /**
     * Allows the character to summon their special attack
     */
    public abstract int specialAtk(DungeonCharacter theOther); // different for each character

    /**
     * Resets the special ability cooldown
     */
    public void resetCooldown() {
        myCooldown = myMaxCooldown;
    }

    /**
     * Sets the cooldown
     */
    public void setCooldown(final int theCooldown) {
        myCooldown = theCooldown;
    }

    /**
     * Decrements the cooldown
     */
    public void decreaseCooldown() {

        if(myCooldown > 0) {
            myCooldown--;
        }
    }

    /**
     * Gets the minimum hit damage
     * @return the hit damage
     */
    public int getMinDmg() {

        return myMinDmg;
    }

    /**
     * Gets the maximum damage
     * @return the maximum damage
     */
    public int getMaxDmg() {

        return myMaxDmg;
    }

    /**
     * Gets the speed of the character
     * @return the character speed
     */
    public int getSpeed() {

        return mySpeed;
    }

    /**
     * Gets the health
     * @return the health
     */
    public int getHealth() {

        return myHealth;
    }

    /**
     * Gets the hit chance
     * @return the hit chance
     */
    public double getBasicChance() {

        return myBasicChance;
    }

    /**
     * Gets the special chance
     * @return the special chance
     */
    public double getSpecialChance() {

        return mySpecialChance;
    }

    /**
     * Sets the hit points
     * @param theHealth the hit points
     */
    public void setHealth(int theHealth) {
        if(theHealth < 0) {
            throw new IllegalArgumentException("Hit Points cannot be less than zero");
        }
        myHealth = theHealth;
    }

    /**
     * Gets the special cooldown
     * @return the special cooldown
     */
    public int getCooldown() {

        return myCooldown;
    }

    /**
     * Returns the maximum HP for a character.
     * @return the max HP.
     */
    public int getMaxHealth() {
        return myMaxHealth;
    }


    public void setSpecialSFX(String theSFXName) {
        File sfx = null;
        try {
            sfx =  new File(getClass().getResource("/sound/sfx/" + theSFXName).toURI());
        } catch (Exception e) {
            System.out.println("Error locating a character's special ability SFX file." + e);
        }
        mySpecialSFX = sfx;
    }

    public File getSpecialSFX() {
        return mySpecialSFX;
    }

    public void setBasicSFX(String theSFXName) {
        File sfx = null;
        try {
            sfx = new File(getClass().getResource("/sound/sfx/" + theSFXName).toURI());
        } catch (Exception e) {
            System.out.println("Error locating a character's basic ability SFX file." + e);
        }
        myBasicSFX = sfx;
    }

    public File getBasicSFX() {
        return myBasicSFX;
    }

    public abstract String getName();

    public boolean onCooldown() {
        return myCooldown > 0;
    }
    public String getBasicMsg() {
        return myBasicSelectMsg;
    }
    public String getSpecialMsg() {
        return mySpecialSelectMsg;
    }
    public void setMaxHealth(final int theMaxHealth) {
        myMaxHealth = theMaxHealth;
    }
    public void setSpeed(final int theSpeed) {
        mySpeed = theSpeed;
    }
    public void setMinDmg(final int theMinDmg) {
        myMinDmg = theMinDmg;
    }
    public void setMaxDmg(final int theMaxDmg) {
        myMaxDmg = theMaxDmg;
    }
    public boolean attackWasSuccessful() {
        return myAttackWasSuccess;
    }
    public void setAttackSuccess(final boolean theAttackWasSuccess) {
        myAttackWasSuccess = theAttackWasSuccess;
    }


}
