package model.test;

import model.Hero;

public class TestHero extends Hero {

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
