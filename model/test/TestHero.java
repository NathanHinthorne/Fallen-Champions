package model.test;

import model.Hero;

public class TestHero extends Hero {

    private int myMinSpecial;

    private int myMaxSpecial;

    protected TestHero() {
        /* For Reference */
        //super(theHitPoints, theAtkSpd, theHitChance, theMinDmg, theMaxDmg, theCooldown);
        super(250, 8, 50, 30, 60, 10);
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
