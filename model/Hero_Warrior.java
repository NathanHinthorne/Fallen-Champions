package model;

/**
 * Hero Warrior Character Class.
 *
 * @author Austin Roaf
 * @version 1.0
 */
public class Hero_Warrior extends Hero implements java.io.Serializable{

    private int special2Cooldown;

    /**
     * Super constructor for hero warrior
     */
    public Hero_Warrior() {
        super(350, 4, 45, 85, 65, 50, 110, 0, HeroTypes.WARRIOR);
//        setSpecial2Cooldown(50);
    }
//
//    /**
//     * Set second special cooldown
//     * @param thespecial2Cooldown the cooldown
//     */
//    public void setSpecial2Cooldown(int thespecial2Cooldown) {
//
//        thespecial2Cooldown = special2Cooldown;
//    }

    /**
     * Second special attack
     */
    @Override
    public int specialAtk(DungeonCharacter theOther) {

        this.setHitChance(generateHitChance(getLowHitChance(), getHighHitChance()));
        int pointer = MY_RANDOM.nextInt(80);

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

}
