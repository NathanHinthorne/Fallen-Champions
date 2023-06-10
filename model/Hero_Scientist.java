package model;


/**
 * Hero Scientist Character Class.
 *
 * @author Austin Roaf
 * @version 1.0
 */
public class Hero_Scientist extends Hero implements java.io.Serializable {
    // The second cooldown
    private int special2Cooldown;
    // The third cooldown
    private int special3Cooldown;
    /**
     * Super constructor for hero scientist
     */
    public Hero_Scientist() {
        super(100, 5, 75, 80, 78.5f, 35, 95, 0, HeroTypes.SCIENTIST);
//        setSpecial2Cooldown(40);
//        setSpecial3Cooldown(50);
    }
//
//    /**
//     * Gets the second special cooldown
//     * @param thespecial2Cooldown the cooldown
//     */
//    public void setSpecial2Cooldown(int thespecial2Cooldown) {
//        thespecial2Cooldown = special2Cooldown;
//    }
//
//    /**
//     * Gets the third special cooldown
//     * @param thespecial3Cooldown the cooldown
//     */
//    public void setSpecial3Cooldown(int thespecial3Cooldown) {
//        thespecial3Cooldown = special3Cooldown;
//    }

    /**
     * The second special attack
     */
    @Override
    public int specialAtk(DungeonCharacter theOther) {

        this.setHitChance(generateHitChance(getLowHitChance(), getHighHitChance()));
        int pointer = MY_RANDOM.nextInt(140);

        if(getHitChance() >= pointer) {
            // Attack lands
            if (getSpecialCooldown() < 1) {
                theOther.setHitPoints(theOther.getHitPoints() - getMaxDamage());
                this.setSpecialCooldown(MAX_SPECIAL_COOLDOWN); // reset the cooldown
                return getMaxDamage();
            } else {
                return -1;
            }
        } else {
            // Attack failed
            return 0;
        }

        //getSpecialCooldown() = MAX_SPECIAL_COOLDOWN; // reset the cooldown
    }

//    /**
//     * The third special attack
//     */
//    public void specialAtk3() {
//        if(getHitPoints() <= 0)
//        {
//            throw new IllegalArgumentException("Hit Points cannot be less than zero");
//        }
//
//        if(getHitChance() > getLowHitChance() && getHitChance() < getHighHitChance()) {
//            // Attack successful
//            setHitPoints(getHitPoints() - getMaxDamage());
//        } else { // Will be worked on later, planned to be an error message
//            // Attack failed
//            System.out.println("Attack Failed!");
//
//        }
//
//        //mySpecialCooldown = MAX_SPECIAL_COOLDOWN; // reset the cooldown
//    }
}
