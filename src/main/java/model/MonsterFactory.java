package model;

/**
 * Class responsible for constructing monsters
 * when they are needed in the game.
 *
 * @author Brendan Smith
 * @author Nathan Hinthorne
 * @version 5/15/23
 */
public class MonsterFactory {

    private static Monster myMonster;

    MonsterFactory() { // package level access only
    }

    /**
     * Builds the monster
     * @param theValue the monster to build
     * @return the built monster
     */
    public static Monster buildMonster(MonsterTypes theValue) {

        if (theValue == MonsterTypes.SKELETON) {
            myMonster = new MonsterSkeleton();
        }
        else if (theValue == MonsterTypes.OGRE) {
            myMonster = new MonsterOgre();
        }
        else if (theValue == MonsterTypes.GREMLIN) {
            myMonster = new MonsterGremlin();
        }
        else if (theValue == MonsterTypes.WARLOCK) {
            myMonster = new MonsterWarlock();
        }
        else if (theValue == MonsterTypes.SPIDER) {
            myMonster = new MonsterSpider();
        }
//        else if (theValue == MonsterTypes.DRAGON) {
//            myMonster = new MonsterDragon();
//        }

        return myMonster;
    }

}
