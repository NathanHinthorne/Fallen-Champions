package model;

public class MonsterFactory {

    public Monster buildMonster(int theValue) {

        Monster theMonster = null;

        if (theValue == 0) {
            theMonster = new Skeleton();
        } else if (theValue == 1) {
            theMonster = new Ogre();
        } else if (theValue == 2) {
            theMonster = new Gremlin();
        } else if (theValue == 3) {
            theMonster = new Warlock();
        } else {
            /*
             * Temporary default value of the monster if
             * theValue is out of range.
             */
            theMonster = new Skeleton();
        }

        return theMonster;

    }

}
