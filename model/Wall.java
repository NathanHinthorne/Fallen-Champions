package model;

/**
 * A wall is a type of room feature that prevents the player from moving through it.
 * It is represented by an asterisk in the dungeon map.
 *
 * @author Nathan Hinthorne
 * @version 1.0
 */
public class Wall implements java.io.Serializable {

    @Override
    public String toString() {
        return "*";
    }
}
