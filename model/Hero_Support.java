package model;

public class Hero_Support extends Hero implements Healable {

    private int minHeal;

    private int maxHeal;

    private float healChance;

    private int special2Cooldown;



    protected Hero_Support() {
        super(85,4,35,75,55,35, 85, 3, HeroTypes.SUPPORT);
        setSpecial2Cooldown(15);

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
            // Attack failed
            System.out.println("Attack Failed!");

        }

        //mySpecialCooldown = MAX_SPECIAL_COOLDOWN; // reset the cooldown
    }

    @Override
    public void heal() {
        //TODO heal the support hero
    }
}
