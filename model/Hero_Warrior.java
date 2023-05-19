package model;

public class Hero_Warrior extends Hero{

    private int special2Cooldown;


    public Hero_Warrior() {
        super(350, 4, 45, 120, 160, 20);
        setSpecial2Cooldown(50);
    }

    public void setSpecial2Cooldown(int thespecial2Cooldown) {
        thespecial2Cooldown = special2Cooldown;
    }


    /* Will be worked on later */
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
    }

}
