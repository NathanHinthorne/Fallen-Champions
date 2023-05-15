package model;

public class VisionPotion extends Potion {

    Room visibleRooms[][];

    public VisionPotion() {
        visibleRooms = new Room[3][3];
        visibleRooms[1][1] = Dungeon.getMyCurrRoom();
    }

    public void effect() {

    }
}
