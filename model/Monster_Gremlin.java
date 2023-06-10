package model;
/**
 * Monster Gremlin Character Class.
 *
 * @author Austin Roaf
 * @version 1.0
 */
public class Monster_Gremlin extends Monster implements java.io.Serializable {

    /**
     * Super constructor for monster gremlin
     */
    public Monster_Gremlin() {

        super(140,
                5,
                25,
                90,
                57.5f,
                65,
                100,
                55,
                35,
                95,
                45,
                MonsterTypes.GREMLIN);
    }
}
