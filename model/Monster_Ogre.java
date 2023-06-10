package model;

/**
 * Monster Ogre Character Class.
 *
 * @author Austin Roaf
 * @version 1.0
 */
public class Monster_Ogre extends Monster implements java.io.Serializable {

    /**
     * Super constructor for monster gremlin
     */
    public Monster_Ogre() {

        super(125,
                6,
                65,
                85,
                75,
                65,
                85,
                15,
                75,
                105,
                35,
                MonsterTypes.OGRE);
    }
}
