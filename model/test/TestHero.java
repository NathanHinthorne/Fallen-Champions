package java.lang.model.test;

import java.lang.model.Hero;

public class TestHero extends java.lang.Hero {

    private int myMinSpecial;

    private int myMaxSpecial;

    protected TestHero(int theHitPoints, int theAtkSpd, float theHitChance, int theMinDmg, int theMaxDmg,
                       int theCooldown) {
        super(theHitPoints, theAtkSpd, theHitChance, theMinDmg, theMaxDmg, theCooldown);
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
