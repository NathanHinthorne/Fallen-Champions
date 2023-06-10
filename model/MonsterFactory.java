package model;

public class MonsterFactory {
    /**
     * Class responsible for constructing monsters
     * when they are needed in the game.
     *
     * @author Brendan Smith
     * @version 1.0 - 5/15/23
     */
    private Monster myMonster;

    /**
     * Builds the monster
     * @param theValue the monster to build
     * @return the built monster
     */
    public Monster buildMonster(MonsterTypes theValue) {

        if (theValue == MonsterTypes.SKELETON) {
            myMonster = new Monster_Skeleton();
        } else if (theValue == MonsterTypes.OGRE) {
            myMonster = new Monster_Ogre();
        } else if (theValue == MonsterTypes.GREMLIN) {
            myMonster = new Monster_Gremlin();
        } else if (theValue == MonsterTypes.WARLOCK) {
            myMonster = new Monster_Warlock();
        } else if (theValue == MonsterTypes.BOSS) {
            myMonster = new Monster_Boss();
        }

        /* TEMPORARY DEFAULT VALUE
        else {

            myMonster = new Monster_Skeleton();
        }
        */

        return myMonster;

    }

}
