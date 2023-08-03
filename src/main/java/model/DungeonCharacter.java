package model;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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
     * The initial cooldown
     */
    protected int myInitialCooldown;

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
     * The special ability sfx
     */
    private File mySpecialSFX;

    /**
     * The basic ability sfx
     */
    private File myBasicSFX;

    /**
     * The debuffs currently taking effect on the character
     */
    private Map<Debuff, Integer> myActiveDebuffs;

    /**
     * The attack result (a miss or hit)
     */
    protected String myAttackResult;


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
                            int theMinDmg, int theMaxDmg, int theCooldown, int theMaxCooldown, int theInitialCooldown) {

        myHealth = theHealth;
        mySpeed = theSpeed;
        myMaxHealth = theHealth;
        myBasicChance = theBasicChance;
        mySpecialChance = theSpecialChance;
        myMinDmg = theMinDmg;
        myMaxDmg = theMaxDmg;
        myCooldown = theCooldown;
        myMaxCooldown = theMaxCooldown;
        myInitialCooldown = theInitialCooldown;
        myActiveDebuffs = new HashMap<>();
        myAttackResult = "";
    }

    /**
     * Allows for the hero or monster to attack
     * @return the basic attack
     */
    public abstract int basicAtk(DungeonCharacter theOther);

    /**
     * Allows the character to summon their special attack
     */
    public abstract int specialAtk(DungeonCharacter theOther);

    public String getAttackResult() {
        return myAttackResult;
    }
    public void setAttackResult(final String theAttackResult) {
        myAttackResult = theAttackResult;
    }

    /**
     * Resets the special ability cooldown
     */
    public void resetCooldown() {
        myCooldown = myMaxCooldown;
    }

    public void startCooldownAtInitial() {
        myCooldown = myInitialCooldown;
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



    public abstract String[] getBasicName();
    public abstract String[] getSpecialName();
    public abstract String[] getPassiveName();

    public abstract String getBasicSelectMsg(final DungeonCharacter theOther);
    public abstract String getSpecialSelectMsg(final DungeonCharacter theOther);

//    public boolean doesDamageOnSpecial() { // default is true
//        return true;
//    }

    public abstract String[] getDescription();


    private void setSpecialChance(final double theChance) {
        mySpecialChance = theChance;
    }

    private void setBasicChance(final double theChance) {
        myBasicChance = theChance;
    }


    /**
     * Sets the debuff on the current character.
     *
     * @param theDebuff The debuff to apply.
     * @param theDuration The remaining duration of the debuff.
     */
    public void inflictDebuff(final Debuff theDebuff, final int theDuration) {
        myActiveDebuffs.put(theDebuff, theDuration);
    }

    /**
     * Decrements any active debuffs on the character.
     */
    public void decreaseDebuffDuration() {
        Iterator<Debuff> iterator = myActiveDebuffs.keySet().iterator();

        while (iterator.hasNext()) {
            Debuff debuff = iterator.next();
            int remainingDuration = myActiveDebuffs.get(debuff);
            if (remainingDuration > 0) {
                myActiveDebuffs.put(debuff, remainingDuration - 1);
            } else {
                iterator.remove(); // instead of myActiveDebuffs.remove(debuff);. concurrent modification exception
            }                      // Use iterator's remove() method to safely remove the element
        }
    }

    /**
     * Checks if the character is affected by the debuff.
     *
     * @param theDebuff The debuff to check.
     * @return True if the character is affected by the debuff, false otherwise.
     */
    public boolean hasDebuff(final Debuff theDebuff) {
        return myActiveDebuffs.containsKey(theDebuff);
    }

    public Map<Debuff, Integer> getActiveDebuffs() {
        return new HashMap<>(myActiveDebuffs);
    }

    public void hurt(final int theDamage) {
        if (myHealth - theDamage < 0) {
            myHealth = 0;
        } else {
            myHealth = myHealth - theDamage;
        }
    }

}
