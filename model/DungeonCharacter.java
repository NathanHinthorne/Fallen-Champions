package model;

import java.util.*;

public abstract class DungeonCharacter {

    final static Random MY_RANDOM = new Random();
    public final int MAX_SPECIAL_COOLDOWN = 3;

    private int myHitPoints;
    private int mySpd;
    private float myLowHitChance;
    private float myHighHitChance;
    private int myMinDmg;
    private int myMaxDmg;
    private int mySpecialCooldown;
    private float myHitChance;

    public DungeonCharacter() {
        myHitPoints = 10;
        mySpd = 10;
        myLowHitChance = 0.0f;
        myHighHitChance = 100.0f;
        myHitChance = 0.0f;
        myMinDmg = 1;
        myMaxDmg = 10;

        myHitChance = generateHitChance(myLowHitChance, myHighHitChance);
    }

    public static float generateHitChance(float theLowChance, float theHighChance) {
        return theLowChance + MY_RANDOM.nextFloat(theHighChance - theLowChance + 1);
    }

    public void takeTurn() {
        // I don't all that will be in here yet, but it will contain all the logic for the turn
        // We at least know the cooldown should be decremented

        if(mySpecialCooldown > 0) {
            mySpecialCooldown--;
        }
    }

    /* Will be worked on later */
    public int basicAtk() {
        if(myHitPoints <= 0)
        {
            throw new IllegalArgumentException("Hit Points cannot be less than or equal to zero");
        }


        if(myHitChance > 75.0f) {
            // Attack successful
            setHitPoints(getHitPoints() - getMinDamage());
        } else { // Will be worked on later, planned to be an error message
            // Attack failed
        }

        return myMinDmg;
    }

    public int getMinDamage() {
        return myMinDmg;
    }

    public void setMinDmg(int theDmg) {
        theDmg = myMinDmg;
    }

    public int getMaxDamage() {
        return myMaxDmg;
    }

    public int getSpd() {
        return mySpd;
    }

    public void setSpd(int theSpd) {
        if(theSpd < 0 || theSpd > 1000) {
            throw new IllegalArgumentException("Attack Speed cannot be less than 0 or greater than 500.");
        }
        theSpd = mySpd;
    }

    public void setMaxDmg(int theDmg) {
        theDmg = myMaxDmg;
    }

    /* Will be worked on later */
    public int specialAtk() {
        if(myHitPoints < 0)
        {
            throw new IllegalArgumentException("Hit Points cannot be less than zero");
        }

        getHitChance();

        if(getHitChance() > 75) {
            // Attack successful
            setHitPoints(getHitPoints() - getMaxDamage());
        } else { // Will be worked on later, planned to be an error message
            // Attack failed
        }

        mySpecialCooldown = MAX_SPECIAL_COOLDOWN; // reset the cooldown

        return myMaxDmg;
    }
    public int getHitPoints() {
        return myHitPoints;
    }

    public float getHitChance() {
        return myHitChance;
    }

    public void setHitChance(float theChance) {

        theChance = myHitChance;
    }

    public void setHitPoints(int hp) {
        if(hp < 0) { // Will need to be looked at later
            throw new IllegalArgumentException("Hit Points cannot be less than zero");
        }
        hp = myHitPoints;
    }

    public int getSpecialCooldown() {
        return mySpecialCooldown;
    }

    public void setSpecialCooldown(int theCooldown) {
        theCooldown = mySpecialCooldown;
    }



}