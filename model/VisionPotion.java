package model;

public class VisionPotion extends Potion {

    public VisionPotion() {

    }

    @Override
    public void effect() {
        // TODO normally, the controller would call getView() when getting the 3x3 area for the View package
        // TODO make the controller call the getExpandedView() method from Dungeon instead
    }

    @Override
    public String toString() {
        return "Vision Potion";
    }
}
