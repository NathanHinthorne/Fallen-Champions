package model;

public class VisionPotion extends Potion {

    public VisionPotion() {

    }

    @Override
    public String inventoryTextDisplay() {
        return "Vision Potion";
    }

    @Override
    public int effect(final Hero thePlayer) {
        // TODO normally, the controller would call getView() when getting the 3x3 area for the View package
        // TODO make the controller call the getExpandedView() method from Dungeon instead
        return 0;
    }

    @Override
    public String toString() {
        return "p";
    } // change char later
}
