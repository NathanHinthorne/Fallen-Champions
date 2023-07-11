package model;

/**
 * Class responsible for building a hero at
 * the beginning of the game.
 *
 * @author Brendan Smith
 * @author Nathan Hinthorne
 * @version 5/15/23
 */
public class HeroFactory {

    private static Hero myHero;

    HeroFactory() { // package level access only
    }

    /**
     * Builds the hero
     * @param theValue the hero to build
     * @return the built hero
     */
    public static Hero buildHero(HeroTypes theValue) {
        
        if (theValue == HeroTypes.SWORDSMAN) {
            myHero = new HeroSwordsman();
        }
//        else if (theValue == HeroTypes.THIEF) {
//            myHero = new HeroThief();
//        } else if (theValue == HeroTypes.JUGGERNAUT) {
//            myHero = new HeroJuggernaut();
//        } else if (theValue == HeroTypes.ARCHER) {
//            myHero = new HeroArcher();
//        } else if (theValue == HeroTypes.SCIENTIST) {
//            myHero = new HeroScientist();
//        } else if (theValue == HeroTypes.DOCTOR) {
//            myHero = new HeroDoctor();
//        } else if (theValue == HeroTypes.MAGE) {
//            myHero = new HeroMage();
//        }
//        } else if (theValue == HeroTypes.MICHAEL) {
//
//        } else if (theValue == HeroTypes.GARRET) {
//
//        } else if (theValue == HeroTypes.ETHAN) {
//
//        } else if (theValue == HeroTypes.JADON) {
//
//        } else if (theValue == HeroTypes.NATHAN) {
//
//        } else if (theValue == HeroTypes.THE_ROCK) {
//
//        }

        return myHero;
    }

}
