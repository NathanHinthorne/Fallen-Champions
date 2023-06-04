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

        if(getHitChance() >= getLowHitChance() && getHitChance() <= getHighHitChance()) {
            // Attack successful
            theOther.setHitPoints(theOther.getHitPoints() - getMaxDamage());
            return getMaxDamage();
        } else { // Will be worked on later, planned to be an error message
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
