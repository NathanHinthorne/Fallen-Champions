package model;


/**
 *
 */
public class Hero_Enforcer extends Hero implements java.io.Serializable {

    private int special2Cooldown;

    private int special3Cooldown;

    /**
     * Super constructor for hero enforcer
     */
    public Hero_Enforcer() {
        super(350, 4, 45, 120, 160, 20, 90, 0, HeroTypes.ENFORCER);
//        setSpecial2Cooldown(50);
//        setSpecial3Cooldown(65);
    }

//    /**
//     * Sets the second special cooldown
//     * @param thespecial2Cooldown the second special cooldown
//     */
//    public void setSpecial2Cooldown(int thespecial2Cooldown) {
//        thespecial2Cooldown = special2Cooldown;
//    }
//
//    /**
//     * Sets the third special cooldown
//     * @param thespecial3Cooldown the third special cooldown
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
        int pointer = MY_RANDOM.nextInt(100);

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
//    public void specialAtk2(DungeonCharacter theOther) {
//        if(getHitPoints() <= 0)
//        {
//            throw new IllegalArgumentException("Hit Points cannot be less than zero");
//        }
//
//        if(getHitChance() > getLowHitChance() && getHitChance() < getHighHitChance()) {
//            // Attack successful
//            theOther.setHitPoints(theOther.getHitPoints() - getMaxDamage());
//        } else { // Will be worked on later, planned to be an error message
//            // Attack failed
//            System.out.println("Attack Failed!");
//        }
//
//        //getSpecialCooldown() = MAX_SPECIAL_COOLDOWN; // reset the cooldown
//    }

    
}
