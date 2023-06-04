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

    /**
     * Sets second special cooldown
     * @param thespecial2Cooldown the cooldown
     */
    public void setSpecial2Cooldown(int thespecial2Cooldown) {
        thespecial2Cooldown = special2Cooldown;
    }
    /**
     * Sets third special cooldown
     * @param thespecial3Cooldown the cooldown
     */
    public void setSpecial3Cooldown(int thespecial3Cooldown) {
        thespecial3Cooldown = special3Cooldown;
    }
    /**
     * Sets fourth special cooldown
     * @param thespecial4Cooldown the cooldown
     */
    public void setSpecial4Cooldown(int thespecial4Cooldown) {
        thespecial4Cooldown = special4Cooldown;
    }
    /**
     * Sets fifth special cooldown
     * @param thespecial5Cooldown the cooldown
     */
    public void setSpecial5Cooldown(int thespecial5Cooldown) {
        thespecial5Cooldown = special5Cooldown;
    }

    /**
     * Second special attack
     */
    @Override
    public int specialAtk(DungeonCharacter theOther) {

        if(getHitChance() > getLowHitChance() && getHitChance() < getHighHitChance()) {
            // Attack successful
            theOther.setHitPoints(theOther.getHitPoints() - getMaxDamage());
        } else { // Will be worked on later, planned to be an error message
            // Attack failed
        }

        return getMaxDamage();

    }

    /**
     * Third special attack
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
    }

    /**
     * Fourth special attack
     */
    public void specialAtk4(DungeonCharacter theOther) {
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
    }

    /**
     * Fifth special attack
     */
    public void specialAtk5(DungeonCharacter theOther) {
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
    }

}
