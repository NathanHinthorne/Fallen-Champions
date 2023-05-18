package model.test;

import model.Monster;

public class TestMonster extends Monster {

    private int myMinSpecial;

    private int myMaxSpecial;

    public TestMonster() {
        /* For Reference */
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
