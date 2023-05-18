package model;

public class Boss extends Monster {


    public Boss(int theHitPoints, int theAtkSpd, float theHitChance, int theMinDmg, int theMaxDmg, int theCooldown, int theMinHeal, int theMaxHeal, float theHealChance) {
        super(1500, 1, 97, 90, 230, 40, 130, 290, 99);
    }

    public void specialAtk2() {
        if(getHitPoints() < 0)
        {
            throw new IllegalArgumentException("Hit Points cannot be less than zero");
        }

        getHitChance();

        if(getHitChance() > 50) {
            // Attack successful
            setHitPoints(getHitPoints() - getMaxDamage());
        } else { // Will be worked on later, planned to be an error message
            // Attack failed
        }

        //getSpecialCooldown() = MAX_SPECIAL_COOLDOWN; // reset the cooldown
    }

    /* Will be worked on later */
    public void specialAtk3() {
        if(getHitPoints() < 0)
        {
            throw new IllegalArgumentException("Hit Points cannot be less than zero");
        }

        getHitChance();

        if(getHitChance() > 50) {
            // Attack successful
            setHitPoints(getHitPoints() - getMaxDamage());
        } else { // Will be worked on later, planned to be an error message
            // Attack failed
        }

        //getSpecialCooldown() = MAX_SPECIAL_COOLDOWN; // reset the cooldown
    }

    /* Will be worked on later */
    public void specialAtk4() {
        if(getHitPoints() < 0)
        {
            throw new IllegalArgumentException("Hit Points cannot be less than zero");
        }

        getHitChance();

        if(getHitChance() > 50) {
            // Attack successful
            setHitPoints(getHitPoints() - getMaxDamage());
        } else { // Will be worked on later, planned to be an error message
            // Attack failed
        }

        //getSpecialCooldown() = MAX_SPECIAL_COOLDOWN; // reset the cooldown
    }

    /* Will be worked on later */
    public void specialAtk5() {
        if(getHitPoints() < 0)
        {
            throw new IllegalArgumentException("Hit Points cannot be less than zero");
        }

        getHitChance();

        if(getHitChance() > 50) {
            // Attack successful
            setHitPoints(getHitPoints() - getMaxDamage());
        } else { // Will be worked on later, planned to be an error message
            // Attack failed
        }

        //getSpecialCooldown() = MAX_SPECIAL_COOLDOWN; // reset the cooldown
    }
}
