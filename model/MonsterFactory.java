package model;

public class MonsterFactory {

    Monster myMonster;

    public Monster buildMonster(String theValue) {

        if (theValue.equalsIgnoreCase("Skeleton")) {
            myMonster = new Monster_Skeleton();
        } else if (theValue.equalsIgnoreCase("Ogre")) {
            myMonster = new Monster_Ogre();
        } else if (theValue.equalsIgnoreCase("Gremlin")) {
            myMonster = new Monster_Gremlin();
        } else if (theValue.equalsIgnoreCase("Warlock")) {
            myMonster = new Monster_Warlock();
        } else {
            /*
             * Temporary default value of the monster if
             * theValue doesn't equal.
             */
            myMonster = new Monster_Skeleton();
        }

        return myMonster;

    }

}
