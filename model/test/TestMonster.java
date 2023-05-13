package java.lang.model.test;

import java.lang.model.Monster;

public class TestMonster extends java.lang.Monster {

    private int myMinSpecial;

    private int myMaxSpecial;

    protected TestMonster(int theHitPoints, int theAtkSpd, float theHitChance, int theMinDmg, int theMaxDmg, int theCooldown,
                          int theMinHeal, int theMaxHeal, float theHealChance) {
        //super(theHitPoints,theAtkSpd,theHitChance,theMinDmg,theMaxDmg,theCooldown,theMinHeal,theMaxHeal,theHealChance);
        super(300, 5, 50.0f, 20, 40, 10, 6, 8, 80.0f);
        setMyMinSpecial(50);
        setMyMaxSpecial(100);
    }

    public void setMyMinSpecial(int minSpecial) {
        minSpecial = myMinSpecial;
    }

    public void setMyMaxSpecial(int maxSpecial) {
        maxSpecial = myMaxSpecial;
    }

}
