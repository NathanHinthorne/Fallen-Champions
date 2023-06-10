package model;

public class MonsterFactory {
    /**
     * My monster
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
