package model.test;

import model.DungeonCharacter;
import model.Monster;
import model.MonsterTypes;

public class TestMonster extends Monster {

    private int myMinSpecial;

    private int myMaxSpecial;

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

    public void setMyMinSpecial(int minSpecial) {
        minSpecial = myMinSpecial;
    }

    public void setMyMaxSpecial(int maxSpecial) {
        maxSpecial = myMaxSpecial;
    }

}
