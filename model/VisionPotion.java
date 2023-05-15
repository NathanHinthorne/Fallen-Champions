package model;

public class VisionPotion extends Potion {

    Room visibleRooms[][];

    public VisionPotion() {
        visibleRooms = new Room[3][3];
    }

    @Override
    public void effect() {

    }

    @Override
    public String toString() {
        return "Vision Potion";
    }
}
