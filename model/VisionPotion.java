package model;

public class VisionPotion extends Potion {

    Room visibleRooms[][];

    public VisionPotion() {
        visibleRooms = new Room[3][3];
    }

    @Override
    public void effect() {
        /* TODO
         * find way to reveal every surrounding room, this may pertain
         * only to view, but we don't know yet.
         */
    }

    @Override
    public String toString() {
        return "Vision Potion";
    }
}
