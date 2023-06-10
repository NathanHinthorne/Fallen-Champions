package model;

/**
 * Represents the exit of the dungeon.
 * The player must reach the exit with all 4 pillars of OO to win the game.
 *
 * @author Nathan Hinthorne
 * @version 1.0
 */
public class Exit implements java.io.Serializable {

    @Override
    public String toString() {
        return "▮";
    }
}
