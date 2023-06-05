package model;

public class Potion implements java.io.Serializable {


    public String inventoryTextDisplay() {
        return "Debug Potion";
    }

    public int getDetail(Hero thePlayer) {
        return 0;
    }

    public int effect(Hero thePlayer) {
        // For a health potion, it will return the healing amount.
        // For a vision potion, it will return the visible radius.
        return 0;
    }
}
