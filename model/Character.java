package model;

import java.util.*;

public interface Character {

    final static Random MY_RANDOM = new Random();

    int hitPoints = 0;

    int atkSpd = 0;

    float theLowChance = 0.0f;

    float theHighChance = 100.0f;

    float hitChance = generateHitChance(theLowChance, theHighChance);

    int minDmg = 0;

    int maxDmg = 0;

    int specialCooldown = 0;

    public static float generateHitChance(float theLowChance, float theHighChance) {
        return theLowChance + MY_RANDOM.nextFloat(theHighChance - theLowChance + 1);
    }

    /* Will be worked on later */
    public default int basicAtk() {
        if(hitPoints < 0)
        {
            throw new IllegalArgumentException("Hit Points cannot be less than zero");
        }

        getHitChance();

        if(getHitChance() > 75) {
            // Attack successful
            setHitPoints(getHitPoints() - getMinDamage());
        } else { // Will be worked on later, planned to be an error message
            // Attack failed
        }

        return minDmg;
    }

    public default int getMinDamage() {
        return minDmg;
    }

    public default void setMinDmg(int theDmg) {
        theDmg = minDmg;
    }

    public default int getMaxDamage() {
        return maxDmg;
    }

    public default int getAtkSpd() {
        return atkSpd;
    }

    public default void setAtkSpd(int theSpd) {
        if(theSpd < 0 || theSpd > 1000) {
            throw new IllegalArgumentException("Attack Speed cannot be less than 0 or greater than 500.");
        }
        theSpd = atkSpd;
    }

    public default void setMaxDmg(int theDmg) {
        theDmg = maxDmg;
    }

    /* Will be worked on later */
    public default int specialAtk() {
        if(hitPoints < 0)
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

        return maxDmg;
    }
    public default int getHitPoints() {
        return hitPoints;
    }

    public default float getHitChance() {
        return hitChance;
    }

    public default void setHitChance(float theChance) {

        theChance = hitChance;
    }

    public default void setHitPoints(int hp) {
        if(hp < 0) { // Will need to be looked at later
            throw new IllegalArgumentException("Hit Points cannot be less than zero");
        }
        hp = hitPoints;
    }

    public default int getSpecialCooldown() {
        return specialCooldown;
    }

    public default void setSpecialCooldown(int theCooldown) {
        theCooldown = specialCooldown;
    }



}
