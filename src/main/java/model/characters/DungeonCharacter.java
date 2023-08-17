package model.characters;

import java.io.File;
import java.util.*;

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
     * The defense (0% - 100%)
     */
    protected float myDefense;

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
    protected double myBasicAccuracy;

    /**
     * The chance for the special ability to succeed
     */
    protected double mySpecialAccuracy;

    /**
     * The constant basic accuracy
     */
    private final double myConstantBasicAccuracy;

    /**
     * The constant special accuracy
     */
    private final double myConstantSpecialAccuracy;

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
    protected Map<Debuff, Integer> myActiveDebuffs;

    /**
     * The debuffs applied 1 turn ago, to be consumed by inflictDebuff()
     */
    protected Map<Debuff, Integer> myNewDebuffs;

    /**
     * The messages that appear after the character is attacked
     */
    protected Queue<String> myPassiveMsgs;

    /**
     * The messages that appear after the character attacks another
     */
    protected List<String> myOffensiveMsgs;

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
        myDefense = 0;
        myMaxHealth = theHealth;
        myBasicAccuracy = theBasicChance;
        mySpecialAccuracy = theSpecialChance;
        myConstantBasicAccuracy = theBasicChance;
        myConstantSpecialAccuracy = theSpecialChance;
        myMinDmg = theMinDmg;
        myMaxDmg = theMaxDmg;
        myCooldown = theCooldown;
        myMaxCooldown = theMaxCooldown;
        myInitialCooldown = theInitialCooldown;
        myActiveDebuffs = new HashMap<>();
        myNewDebuffs = new HashMap<>();
        myPassiveMsgs = new LinkedList<>();
        myOffensiveMsgs = new ArrayList<>();
        myAttackResult = "";
    }

    /**
     * Allows for the hero or monster to attack
     * @return the basic attack
     */
    public abstract int basicAtk(final DungeonCharacter theOther);

    /**
     * Allows the character to summon their special attack
     */
    public abstract int specialAtk(final DungeonCharacter theOther);

    public String getAttackResult() {
        return myAttackResult;
    }
    public void setAttackResult(final String theAttackResult) {
        myAttackResult = theAttackResult;
    }

    // default implementation
    public void initializeCharacterPerBattle() {
        myCooldown = myInitialCooldown;
        myActiveDebuffs.clear();
    }

    // default implementation
    public void uninitializeCharacterPerBattle() {

    }

    // default implementation
    public void initializeCharacterPerTurn() {

    }

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
     * Gets the defense
     * @return the defense
     */
    public float getDefense() {

        return myDefense;
    }

    /**
     * Gets the basic hit chance
     * @return the basic hit chance
     */
    public double getBasicAccuracy() {

        return myBasicAccuracy;
    }

    /**
     * Gets the special chance
     * @return the special chance
     */
    public double getSpecialAccuracy() {

        return mySpecialAccuracy;
    }

    public void resetBasicAccuracy() {
        myBasicAccuracy = myConstantBasicAccuracy;
    }

    public void resetSpecialAccuracy() {
        mySpecialAccuracy = myConstantSpecialAccuracy;
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


    public void setBasicAccuracy(final double theChance) {
        if (theChance < 0 || theChance > 1) {
            throw new IllegalArgumentException("Accuracy must be between 0 and 1");
        }
        myBasicAccuracy = theChance;
    }
    public void setSpecialAccuracy(final double theChance) {
        if (theChance < 0 || theChance > 1) {
            throw new IllegalArgumentException("Accuracy must be between 0 and 1");
        }
        mySpecialAccuracy = theChance;
    }



    /**
     * Sets the debuff on the current character.
     *
     * @param theDebuff The debuff to apply.
     * @param theDuration The remaining duration of the debuff.
     */
    public void inflictDebuff(final Debuff theDebuff, final int theDuration) {
        if (theDuration < 0) {
            throw new IllegalArgumentException("Duration must be greater than 0");
        }
        if (myActiveDebuffs.containsKey(theDebuff)) {
            myActiveDebuffs.remove(theDebuff);
        }
        myActiveDebuffs.put(theDebuff, theDuration);
        myNewDebuffs.put(theDebuff, theDuration);
    }

    public void tickIndividualDebuff(final Debuff theDebuff) {
        if (myActiveDebuffs.containsKey(theDebuff)) {
            int remainingDuration = myActiveDebuffs.get(theDebuff);
            if (remainingDuration > 1) {
                myActiveDebuffs.put(theDebuff, remainingDuration - 1);
            } else {
                myActiveDebuffs.remove(theDebuff);
            }
        } else {
            System.out.println("DEBUG: Character does not have the debuff to tick.");
        }
    }

    /**
     * Decrements any active debuffs on the character.
     */
    public void tickDebuffs() {
        Iterator<Debuff> iterator = myActiveDebuffs.keySet().iterator();

        while (iterator.hasNext()) {
            Debuff debuff = iterator.next();
            int remainingDuration = myActiveDebuffs.get(debuff);
            if (debuff != Debuff.VULNERATE) { // tick all debuffs EXCEPT VULNERATE
                if (remainingDuration > 1) {
                    myActiveDebuffs.put(debuff, remainingDuration - 1);
                } else {
                    iterator.remove(); // instead of myActiveDebuffs.remove(debuff);. concurrent modification exception
                }                      // Use iterator's remove() method to safely remove the element
            }
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
    public Map<Debuff, Integer> getNewDebuffs() {
        return myNewDebuffs; // not a copy
    }

    public Queue<String> getPassiveMsgs() {
        return myPassiveMsgs;
    }

//    /**
//     * Deals damage to the character, factoring in defense.
//     * @param theDamage The damage to deal.
//     */
//    public void hurt(final int theDamage) {
//        int damageDealt = (int) (theDamage * (1 - myDefense)); // factor in the defense
//
//        if (myHealth - damageDealt < 0) {
//            myHealth = 0;
//        } else {
//            myHealth = myHealth - damageDealt;
//        }
//    }

    public int calculateDamageDealt(final int theDamage, final DungeonCharacter theOther) {
        int trueDamage = (int) (theDamage * (1 - theOther.getDefense()));
        if (theOther.hasDebuff(Debuff.VULNERATE)) {
            trueDamage *= 2;
        }
        return trueDamage;
    }

    /**
     * Deals true damage to the character without factoring in defense.
     * @param theDamage The damage to deal.
     */
    public void damage(final int theDamage) { // don't factor in defense
        if (myHealth - theDamage < 0) {
            myHealth = 0;
        } else {
            myHealth = myHealth - theDamage;
        }
    }

}
