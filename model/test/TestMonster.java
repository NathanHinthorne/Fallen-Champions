package model.test;

import model.Monster;

public class TestMonster extends Monster {

    private int myMinSpecial;

    private int myMaxSpecial;

    public TestMonster() {
        /* For Reference */
        //super(theHitPoints,theAtkSpd,theHitChance,theMinDmg,theMaxDmg,theCooldown,theMinHeal,theMaxHeal,theHealChance);
        super(250, 8, 50, 50, 50, 20, 70, 10, 10, 45, 45);
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
