package model;

/**
 * A potion is an item that the player can consume to gain a special ability.
 *
 * @author Nathan Hinthorne
 * @author Brendan Smith
 * @author Austin Roaf
 */
public abstract class Potion implements java.io.Serializable {

    /**
     * Gets the details of the potion
     * @param thePlayer the player to give the potion
     * @return 0
     */
    public int getDetail(Hero thePlayer) {
        return 0;
    }

    /**
     * The potion type
     * @return the potion type
     */
    public abstract String type();

    /**
     * The potion effect
     * @param thePlayer the potion effect for the Hero to consume
     */
    public abstract void effect(final Hero thePlayer);
}
