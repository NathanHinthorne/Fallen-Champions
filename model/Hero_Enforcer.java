package model;

public class Hero_Enforcer extends Hero {

    private int special2Cooldown;

    private int special3Cooldown;

    public Hero_Enforcer() {
        super(350, 4, 45, 120, 160, 20, 90, 6, HeroTypes.ENFORCER);
        setSpecial2Cooldown(50);
        setSpecial3Cooldown(65);
    }

    /**
     * Sets the second special cooldown
     * @param thespecial2Cooldown the second special cooldown
     */
    public void setSpecial2Cooldown(int thespecial2Cooldown) {
        thespecial2Cooldown = special2Cooldown;
    }

    /**
     * Sets the third special cooldown
     * @param thespecial3Cooldown the third special cooldown
     */
    public void setSpecial3Cooldown(int thespecial3Cooldown) {
        thespecial3Cooldown = special3Cooldown;
    }

    /**
     * The second special attack
     */
    public void specialAtk2(DungeonCharacter theOther) {
        if(getHitPoints() <= 0)
        {
            throw new IllegalArgumentException("Hit Points cannot be less than zero");
        }

        if(getHitChance() > getLowHitChance() && getHitChance() < getHighHitChance()) {
            // Attack successful
            theOther.setHitPoints(theOther.getHitPoints() - getMaxDamage());
        } else { // Will be worked on later, planned to be an error message
            // Attack failed
        }

        //getSpecialCooldown() = MAX_SPECIAL_COOLDOWN; // reset the cooldown

    }

    /**
     * The third special attack
     */
    public void specialAtk3(DungeonCharacter theOther) {
        if(getHitPoints() <= 0)
        {
            throw new IllegalArgumentException("Hit Points cannot be less than zero");
        }

        if(getHitChance() > getLowHitChance() && getHitChance() < getHighHitChance()) {
            // Attack successful
            theOther.setHitPoints(theOther.getHitPoints() - getMaxDamage());
        } else { // Will be worked on later, planned to be an error message
            // Attack failed
            System.out.println("Attack Failed!");
        }

        //getSpecialCooldown() = MAX_SPECIAL_COOLDOWN; // reset the cooldown
    }

    
}
