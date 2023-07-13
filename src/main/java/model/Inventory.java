package model;

import java.util.ArrayList;

/**
 * The Inventory class holds items that the player
 * is able to use at any time such as potions.
 *
 * @author Brendan Smith
 * @author Nathan Hinthorne
 * @version 1.0 - 5/15/23
 */
public class Inventory implements java.io.Serializable {

    /**
     * Used to store items in an ArrayList of a defined
     * size.
     */
    private final ArrayList<Potion> myInventory;
    /**
     * Used ONLY to store pillars that the player acquires
     * in the dungeon, used to see if the player is
     * able to exit the dungeon.
     */
    private final ArrayList<Character> myPillars;

    private int myPillarCount;
    private int myItemCount;
    public static final int MAX_ITEM_CAPACITY = 4;
    public static final int MAX_PILLAR_CAPACITY = 4;


    /**
     * Constructs the default size of the Inventory, which
     * is 4 slots.
     */
    public Inventory() {
        myInventory = new ArrayList<Potion>(4);
        myPillars = new ArrayList<Character>(4);
        myPillarCount = 0;
    }

    /**
     * Adds an item to the player inventory, represented
     * as an Arraylist.
     * @param theItem The item to be added
     */
    public void addToInventory(Potion theItem) {

        if (myItemCount == MAX_ITEM_CAPACITY) {
            // Inventory full!
        } else {
            myInventory.add(theItem);
            myItemCount++;
        }

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

    /**
     * For when the player uses (consumes) an item in the game,
     * it is removed from the inventory.
     * @param theSlotIndex The index of the item to be removed.
     */
    public void removeItem(final int theSlotIndex) {
        myInventory.remove(theSlotIndex);
    }


    /**
     * Gets the item at the specified index.
     *
     * @param theSlotIndex The index of the item to be retrieved.
     * @return The item at the specified index.
     */
    public Potion getItem(final int theSlotIndex) {
        return myInventory.get(theSlotIndex);
    }

    /**
     * This is used to check whether the
     * player has every pillar or not.
     */
    public boolean hasAllPillars() {
        if (myPillarCount == 4) {
            return true;
        }
        return false;
    }

    public boolean isFull() {
        return getCurrentSize() == MAX_ITEM_CAPACITY;
    }

    /**
     * Returns the size of the Inventory the player has.
     * @return the ArrayList Size.
     */
    public int getCurrentSize() {
        return myInventory.size();
    }

    public int getMaxSize() {
        return MAX_ITEM_CAPACITY;
    }

    public int getMyPillarCount() {
        return myPillarCount;
    }

    public int getMyItemCount() {
        return myItemCount;
    }

    public ArrayList<Potion> getPotionInventory() { return myInventory; }

    public String getItemInventory() {
        StringBuilder sb = new StringBuilder();

        sb.append("\n╔═════════════════════════════════");
        sb.append("\n║  Player Inventory:\n");
        for (int i = 0; i < getMaxSize(); i++) {
            if (i >= myInventory.size()) {
                sb.append("╠  [" + (i+1) + " - Empty]\n");
            } else {
                sb.append("╠  [" + (i + 1) + " - " + myInventory.get(i).type() + "]\n");
            }
        }
        sb.append("╚═════════════════════════════════");
        sb.append("\n");

        return sb.toString();
    }

    /**
     * Represents the current player inventory in
     * String format.
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("\n╔═════════════════════════════════");
        sb.append("\n║  Current Pillars: ");
        for (int i = 0; i < 4; i++) {
            if (i >= myPillars.size()) {
                sb.append("[ ]");
            } else {
                sb.append("[" + myPillars.get(i).toString() + "]");
            }
        }
        sb.append("\n║");

        sb.append("\n║  Player Inventory:\n");
        for (int i = 0; i < getMaxSize(); i++) {
            if (i >= myInventory.size()) {
                sb.append("╠  [" + (i+1) + " - Empty]\n");
            } else {
                sb.append("╠  [" + (i + 1) + " - " + myInventory.get(i).type() + "]\n");
            }
        }
        sb.append("║\n");
        sb.append("║  Items:   " + myItemCount + "/" + MAX_ITEM_CAPACITY + "\n");
        sb.append("║  Pillars: " + myPillarCount + "/" + MAX_PILLAR_CAPACITY + "\n");
        sb.append("╚═════════════════════════════════");
        sb.append("\n");
        return sb.toString();
    }

}