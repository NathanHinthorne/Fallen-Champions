package model.test;

import model.DungeonCharacter;
import model.Hero;
import model.HeroTypes;
/**
 * Test Hero Character Class.
 *
 * @author Austin Roaf
 * @version 1.0
 */
public class TestHero extends Hero {
    /**
     * Min special
     */
    private int myMinSpecial;
    /**
     * Max special
     */
    private int myMaxSpecial;

    /**
     * Super constructor for test hero
     */
    public TestHero() {
        /* For Reference */
        //super(theHitPoints, theAtkSpd, theHitChance, theMinDmg, theMaxDmg, theCooldown);
        super(100,
                8,
                50,
                80,
                50,
                20,
                70,
                0,
                HeroTypes.WARRIOR);
        setMyMinSpecial(50);
        setMyMaxSpecial(100);
    }

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

    public void setMyMinSpecial(int minSpecial) {
        minSpecial = myMinSpecial;
    }

    public void setMyMaxSpecial(int maxSpecial) {
        maxSpecial = myMaxSpecial;
    }

}
