package model;

import java.util.ArrayList;

public class Inventory {

    private ArrayList<Collectable> myInventory;
    private ArrayList<Pillar> myPillars;
    private int myPillarCount;

    // Custom inventory size based on class (maybe)
    public Inventory(int theSize) {
        myInventory = new ArrayList<Collectable>(theSize);
        myPillars = new ArrayList<Pillar>(4);
        myPillarCount = 0;
    }

    // Default inventory size of 4
    // PILLARS DO NOT AFFECT INVENTORY SIZE
    public Inventory() {
        myInventory = new ArrayList<Collectable>(4);
        myPillars = new ArrayList<Pillar>(4);
        myPillarCount = 0;
    }

    public void addToInventory(Collectable theItem) {
        myInventory.add(theItem);
    }

    public void addPillar(Pillar thePillar) {
        myPillars.add(thePillar);
        myPillarCount++;
    }

    /* When an item (potion) is used, it is consumed */
    public void consumeItem(Collectable theItem) {
        myInventory.remove(theItem);
    }

    /* This will be used to check whether or not the
     * player has every pillar or not.
     */
    public boolean hasAllPillars() {
        if (myPillarCount == 4) {
            return true;
        }
        return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Player Inventory: {");
        for (int i = 0; i < myInventory.size(); i++) {
            sb.append(myInventory.get(i).toString() + ", ");
        }
        sb.append("]    Current Pillars: [");
        for (int i = 0; i < myPillars.size(); i++) {
            sb.append(myPillars.get(i).toString() + ", ");
        }
        return sb.toString();
    }

}
