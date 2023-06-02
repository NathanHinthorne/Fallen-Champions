package model.test;

import model.Hero;
import model.HeroTypes;

public class TestHero extends Hero {

    private int myMinSpecial;

    private int myMaxSpecial;

    public TestHero() {
        /* For Reference */
        //super(theHitPoints, theAtkSpd, theHitChance, theMinDmg, theMaxDmg, theCooldown);
        super(250,
                8,
                50,
                50,
                50,
                20,
                70,
                10,
                HeroTypes.WARRIOR);
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
