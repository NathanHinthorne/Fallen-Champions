package model;

public class Hero_Warrior extends Hero{

    private int special2Cooldown;


    public Hero_Warrior() {
        super(350, 4, 45, 85, 65, 50, 110, 20);
        setSpecial2Cooldown(50);
    }

    public void setSpecial2Cooldown(int thespecial2Cooldown) {
        thespecial2Cooldown = special2Cooldown;
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
            // Attack failedd
        }
    }

}
