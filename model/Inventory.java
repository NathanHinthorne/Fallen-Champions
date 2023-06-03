package model;

import java.util.ArrayList;

/**
 * The Inventory class holds items that the player
 * is able to use at any time such as potions or different
 * weapons.
 * @author Brendan Smith
 * @version 1.0 - 5/15/23
 */
public class Inventory {

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
    private boolean isFull;

    /**
     * Constructs the default size of the Inventory, which
     * is 4 slots.
     */
    public Inventory() {
        myInventory = new ArrayList<Potion>(4);
        myPillars = new ArrayList<Character>(4);
        myPillarCount = 0;
        isFull = false;
    }

    /**
     * Adds an item to the player inventory, represented
     * as an Arraylist.
     * @param theItem The item to be added
     */
    public void addToInventory(Potion theItem) {

        if (myItemCount == myInventory.size()) {
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
     * @param theIndex The index of the item to be removed.
     */
    public void consumeItem(final Hero thePlayer, int theIndex) {
        myInventory.get(theIndex).effect(thePlayer);
        myInventory.remove(theIndex);
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

    /**
     * Returns the size of the Inventory the player has.
     * @return the ArrayList Size.
     */
    public int getSize() {
        return myInventory.size();
    }

    public int getMyPillarCount() {
        return myPillarCount;
    }

    public int getMyItemCount() {
        return myItemCount;
    }

    /**
     * Represents the current player inventory in
     * String format.
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Player Inventory: [");
        for (int i = 0; i < myInventory.size(); i++) {
            sb.append(i+1 + " - " + myInventory.get(i).toString() + ", ");
        }
        sb.append("]\nCurrent Pillars: [");
        for (int i = 0; i < myPillars.size(); i++) {
            sb.append(myPillars.get(i).toString() + ", ");
        }
        sb.append("]");
        return sb.toString();
    }

}
