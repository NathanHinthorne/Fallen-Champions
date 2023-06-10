package model.test;

import model.DungeonCharacter;
import model.Monster;
import model.MonsterTypes;
/**
 * Test Monster Character Class.
 *
 * @author Austin Roaf
 * @version 1.0
 */
public class TestMonster extends Monster {
    /**
     * Min special
     */
    private int myMinSpecial;
    /**
     * Max special
     */
    private int myMaxSpecial;
    /**
     * Super constructor for test monster
     */
    public TestMonster() {
        /* For Reference */
        //super(theHitPoints,theAtkSpd,theHitChance,theMinDmg,theMaxDmg,theCooldown,theMinHeal,theMaxHeal,theHealChance);
        super(100,
                8,
                50,
                80,
                50,
                20,
                70,
                10,
                10,
                45,
                45,
                MonsterTypes.GREMLIN);
        setMyMinSpecial(50);
        setMyMaxSpecial(100);
    }

    /**
     * The special attach
     * @param theOther the other character
     * @return the damage
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

    /**
     * Set the min special
     * @param minSpecial the min special
     */
    public void setMyMinSpecial(int minSpecial) {
        minSpecial = myMinSpecial;
    }

    /**
     * Set the max special
     * @param maxSpecial the max special
     */
    public void setMyMaxSpecial(int maxSpecial) {
        maxSpecial = myMaxSpecial;
    }

}
