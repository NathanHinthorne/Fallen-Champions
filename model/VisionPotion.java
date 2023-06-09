package model;

public class VisionPotion extends Potion implements java.io.Serializable {


    public VisionPotion() { }

    @Override
    public void effect(final Hero thePlayer) {
        thePlayer.setUsingVisionPotion(true);
    }

    @Override
    public String toString() {
        return "p";
    }

    @Override
    public String type() {
        return "Vision Potion";
    }
}
