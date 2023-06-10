package model;

public class VisionPotion extends Potion implements java.io.Serializable {


    public VisionPotion() { }

    /**
     * The vision potion effect
     * @param thePlayer the potion effect for the Hero to consume
     */
    @Override
    public void effect(final Hero thePlayer) {
        thePlayer.setUsingVisionPotion(true);
    }

    @Override
    public String toString() {
        return "p";
    }

    /**
     * The vision potion type
     * @return the vision potion type
     */
    @Override
    public String type() {
        return "Vision Potion";
    }
}
