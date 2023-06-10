package model;

/**
 * Monster Warlock Character Class.
 *
 * @author Austin Roaf
 * @version 1.0
 */
public class Monster_Warlock extends Monster implements java.io.Serializable {

    /**
     * Super constructor for monster warlock
     */
    public Monster_Warlock() {

        super(300,
                3,
                55,
                96,
                56,
                60,
                125,
                15,
                65,
                200,
                20,
                MonsterTypes.WARLOCK);
    }

    /**
     * Second special Attack for Monster Boss
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
    }
}
