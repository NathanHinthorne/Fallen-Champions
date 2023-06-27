package model;

import java.io.Serializable;

/**
 * A potion is an item that the player can consume to gain a special ability.
 *
 * @author Nathan Hinthorne
 * @author Brendan Smith
 * @author Austin Roaf
 */
public interface Potion extends Serializable { // prev: implements java.io.Serializable

    /**
     * Gets the details of the potion
     * @return the details of the potion
     */
    String useMsg();

    /**
     * The potion type
     * @return the potion type
     */
    String type();

    /**
     * indicates whether the potion can be used during battle
     * @return true if the potion can be used during battle
     */
    boolean canUseDuringBattle();

    /**
     * indicates whether the potion can be used outside of battle
     * @return true if the potion can be used outside of battle
     */
    boolean canUseOutsideBattle();
}
