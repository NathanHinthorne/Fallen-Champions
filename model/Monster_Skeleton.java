package model;

/**
 * Monster Skeleton Character Class.
 *
 * @author Austin Roaf
 * @version 1.0
 */
public class Monster_Skeleton extends Monster implements java.io.Serializable {
    /**
     * Super constructor for monster skeleton
     */
    public Monster_Skeleton() {

        super(100,
                10,
                30,
                60,
                50,
                5,
                35,
                5,
                45,
                75,
                5,
                MonsterTypes.SKELETON);
    }
}
