package model;

public class Monster_Warlock extends Monster {


    public Monster_Warlock() {
        super(300, 3, 55, 120, 300, 50, 65, 105, 25);
    }

    public void specialAtk2() {
        if(getHitPoints() < 0)
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

        //getSpecialCooldown() = MAX_SPECIAL_COOLDOWN; // reset the cooldown

    }
}
