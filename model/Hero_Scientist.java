package model;

public class Hero_Scientist extends Hero {

    private int special2Cooldown;

    private int special3Cooldown;



    public Hero_Scientist() {
        super(100, 5, 75, 80, 78.5f, 35, 95, 2);
        setSpecial2Cooldown(40);
        setSpecial3Cooldown(50);
    }

    public void setSpecial2Cooldown(int thespecial2Cooldown) {
        thespecial2Cooldown = special2Cooldown;
    }

    public void setSpecial3Cooldown(int thespecial3Cooldown) {
        thespecial3Cooldown = special3Cooldown;
    }

    /* Will be worked on later */
    public void specialAtk2() {
        if(getHitPoints() <= 0)
        {
            throw new IllegalArgumentException("Hit Points cannot be less than zero");
        }

        if(getHitChance() > getLowHitChance() && getHitChance() < getHighHitChance()) {
            // Attack successful
            setHitPoints(getHitPoints() - getMaxDamage());
        } else { // Will be worked on later, planned to be an error message
            // Attack failed
        }

        //mySpecialCooldown = MAX_SPECIAL_COOLDOWN; // reset the cooldown

    }
    /* Will be worked on later */
    public void specialAtk3() {
        if(getHitPoints() <= 0)
        {
            throw new IllegalArgumentException("Hit Points cannot be less than zero");
        }

        if(getHitChance() > getLowHitChance() && getHitChance() < getHighHitChance()) {
            // Attack successful
            setHitPoints(getHitPoints() - getMaxDamage());
        } else { // Will be worked on later, planned to be an error message
            // Attack failed
        }

        //mySpecialCooldown = MAX_SPECIAL_COOLDOWN; // reset the cooldown
    }
}
