package model;

public class Hero_Robot extends Hero {

    private int special2Cooldown;

    private int special3Cooldown;

    private int special4Cooldown;

    private int special5Cooldown;


    public Hero_Robot() {
        super(500, 2, 35, 75, 55, 105, 165, 25, HeroTypes.ROBOT);
        setSpecial2Cooldown(35);
        setSpecial3Cooldown(40);
        setSpecial4Cooldown(55);
        setSpecial5Cooldown(100);
    }

    public void setSpecial2Cooldown(int thespecial2Cooldown) {
        thespecial2Cooldown = special2Cooldown;
    }

    public void setSpecial3Cooldown(int thespecial3Cooldown) {
        thespecial3Cooldown = special3Cooldown;
    }

    public void setSpecial4Cooldown(int thespecial4Cooldown) {
        thespecial4Cooldown = special4Cooldown;
    }

    public void setSpecial5Cooldown(int thespecial5Cooldown) {
        thespecial5Cooldown = special5Cooldown;
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
    }

    /* Will be worked on later */
    public void specialAtk4() {
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
    }

    /* Will be worked on later */
    public void specialAtk5() {
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
    }

}
