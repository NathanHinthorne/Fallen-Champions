package model;

public class Potion {


    public String inventoryTextDisplay() {
        return "Debug Potion";
    }
    public int effect(Hero thePlayer) {
        // For a health potion, it will return the healing amount.
        // For a vision potion, it will return the visible radius.
        return 0;
    }
}
