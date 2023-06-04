package model;

import java.util.*;

public abstract class DungeonCharacter {

    final static Random MY_RANDOM = new Random();
    public final int MAX_SPECIAL_COOLDOWN = 3;

    private int myHitPoints;
    private int myMaxHitPoints;
    private int mySpd;
    private float myLowHitChance;
    private float myHighHitChance;
    private int myMinDmg;
    private int myMaxDmg;
    private int mySpecialCooldown;
    private float myHitChance;

    public DungeonCharacter() {
        setHitPoints(myHitPoints);
        myMaxHitPoints = myHitPoints;
        setSpd(mySpd);
        setLowHitChance(myLowHitChance);
        setHighHitChance(myHighHitChance);
        setHitChance(myHitChance);
        setMinDmg(myMinDmg);
        setMaxDmg(myMaxDmg);
        setSpecialCooldown(mySpecialCooldown);

        myHitChance = generateHitChance(getLowHitChance(), getHighHitChance());
    }

    public DungeonCharacter(int theHitPoints, int theAtkSpd, float theLowHitChance,
                            float theHighHitChance, float theHitChance, int theMinDmg, int theMaxDmg, int theCooldown) {
        setHitPoints(theHitPoints);
        myMaxHitPoints = myHitPoints;
        setSpd(theAtkSpd);
        setLowHitChance(theLowHitChance);
        setHighHitChance(theHighHitChance);
        setHitChance(theHitChance);
        setMinDmg(theMinDmg);
        setMaxDmg(theMaxDmg);
        setSpecialCooldown(theCooldown);
        myHitChance = generateHitChance(getLowHitChance(), getHighHitChance());

    }

    /**
     * Generates the hit chance
     * @param theLowChance the low chance
     * @param theHighChance the high chance
     * @return the hit chance
     */
    public static float generateHitChance(float theLowChance, float theHighChance) {
        return theLowChance + MY_RANDOM.nextFloat(theHighChance - theLowChance + 1);
    }

    /**
     * Allows hero and monster to take turns
     */
    public void takeTurn() {
        // I don't all that will be in here yet, but it will contain all the logic for the turn
        // We at least know the cooldown should be decremented

        if(mySpecialCooldown > 0) {
            mySpecialCooldown--;
        }
    }

    /**
     * Allows for the hero or monster to attack
     * @return the basic attack
     */
    public int basicAtk(DungeonCharacter theOther) {
        if(myHitPoints <= 0)
        {
            throw new IllegalArgumentException("Hit Points cannot be less than or equal to zero");
        }

        if(myHitChance > getHitChance()) {
            // Attack successful
            theOther.setHitPoints(theOther.getHitPoints() - this.getMinDamage());
        } else { // Will be worked on later, planned to be an error message
            // Attack failed
            System.out.println("Attack Failed!");
        }

        return myMinDmg;
    }

    /**
     * Allows the character to summon their special attack
     */
    public void specialAtk(DungeonCharacter theOther) {}

    /**
     * Gets the minimum hit damage
     * @return the hit damage
     */
    public int getMinDamage() {

        return myMinDmg;
    }

    /**
     * The Min Damage
     * @param theDmg the damage to set
     */
    public void setMinDmg(int theDmg) {

        myMinDmg = theDmg;
    }

    /**
     * Gets the maximum damage
     * @return the maximum damage
     */
    public int getMaxDamage() {

        return myMaxDmg;
    }

    /**
     * Gets the speed of the character
     * @return the character speed
     */
    public int getSpd() {

        return mySpd;
    }

    /**
     * Set the character speed
     * @param theSpd the characer speed
     */
    public void setSpd(int theSpd) {
        if(theSpd < 0 || theSpd > 1000) {
            throw new IllegalArgumentException("Attack Speed cannot be less than 0 or greater than 500.");
        }
        mySpd = theSpd;
    }

    /**
     * Set the maximum damage
     * @param theDmg the damage to set
     */
    public void setMaxDmg(int theDmg) {

        myMaxDmg = theDmg;
    }

    /**
     * Gets the hit points
     * @return the hit points
     */
    public int getHitPoints() {

        return myHitPoints;
    }

    /**
     * Gets the hit chance
     * @return the hit chance
     */
    public float getHitChance() {

        return myHitChance;
    }

    /**
     * Sets the hit chance
     * @param theChance the hit chance
     */
    public void setHitChance(float theChance) {

        myHitChance = theChance;
    }

    /**
     * Sets the hit points
     * @param hp the hit points
     */
    public void setHitPoints(int hp) {
        if(hp < 0) { // Will need to be looked at later
            throw new IllegalArgumentException("Hit Points cannot be less than zero");
        }
        myHitPoints = hp;
    }

    /**
     * Gets the special cooldown
     * @return the special cooldown
     */
    public int getSpecialCooldown() {

        return mySpecialCooldown;
    }

    /**
     * Sets the special cooldown
     * @param theCooldown the special cooldown
     */
    public void setSpecialCooldown(int theCooldown) {

        mySpecialCooldown = theCooldown;
    }

    /**
     * Gets the low hit chance
     * @return the low hit chance
     */
    public float getLowHitChance() {

        return myLowHitChance;
    }

    /**
     * Sets the low hit chance
     * @param theLowChance the low hit chance
     */
    public void setLowHitChance(float theLowChance) {

        myLowHitChance = theLowChance;
    }

    /**
     * Gets the high hit chance
     * @return the high hit chance
     */
    public float getHighHitChance() {

        return myHighHitChance;
    }

    /**
     * Sets the high hit chance
     * @param theHighChance the high hit chance
     */
    public void setHighHitChance(float theHighChance) {

        myHighHitChance = theHighChance;
    }

    /**
     * Returns the maximum HP for a character.
     * @return the max HP.
     */
    public int getMaxHitPoints() {
        return myMaxHitPoints;
    }



}
