package model;

import java.util.ArrayList;

public class Inventory {

    private ArrayList<Collectable> myInventory;
    private ArrayList<Character> myPillars;
    private int myPillarCount;

    // Custom inventory size based on class (maybe)
    public Inventory(int theSize) {
        myInventory = new ArrayList<Collectable>(theSize);
        myPillars = new ArrayList<Character>(4);
        myPillarCount = 0;
    }

    // Default inventory size of 4
    // PILLARS DO NOT AFFECT INVENTORY SIZE
    public Inventory() {
        myInventory = new ArrayList<Collectable>(4);
        myPillars = new ArrayList<Character>(4);
        myPillarCount = 0;
    }

    public void addToInventory(Collectable theItem) {

        myInventory.add(theItem);
    }

    /**
     * Storing pillars in a separate ArrayList allows the
     * player to carry usable items separately for a more
     * fair inventory system.
     *
     * @param thePillar The pillar that was picked up by the player
     *                  and will be stored in the ArrayList.
     */
    public void addPillar(Pillars thePillar) {

        myPillarCount++;
        if (thePillar == Pillars.ABSTRACTION) {
            myPillars.add('A');
        } else if (thePillar == Pillars.INHERITANCE) {
            myPillars.add('I');
        } else if (thePillar == Pillars.ENCAPSULATION) {
            myPillars.add('E');
        } else if (thePillar == Pillars.POLYMORPHISM) {
            myPillars.add('P');
        }

    }

    /* When an item (potion) is used, it is consumed */
    public void consumeItem(Collectable theItem) {
        myInventory.remove(theItem);
    }

    /* This will be used to check whether or not the
     * player has every pillar.
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
