package model;

import java.util.List;
import java.util.Set;

public class Room {

    // static fields for toString

    public static final String EMPTY = " ";
    public static final String MULTIPLE = "&"; // multiple objects in one room
    public static final String HERO = "H";
    public static final String ENTRANCE = "o";
    public static final String EXIT = "O";


    // make non-static if we add difficulty levels
    public static final double HEALTH_POTION_CHANCE = 0.65;
    // vision potion chance is 0.35


    // fields
    private int myY;
    private int myX;
    private Wall myWall;
    private Entrance myEntrance;
    private Exit myExit;
    private Monster myMonster;
    private Potion myPotion;
    private Pillars myPillar;
    private Pit myPit;
    private boolean myHero; // just a boolean since we don't have access to a hero object


    public Room(final int theY, final int theX) {
        myY = theY;
        myX = theX;
        myWall = null;
        myEntrance = null;
        myExit = null;
        myMonster = null;
        myPotion = null;
        myPillar = null;
        myPit = null;
        myHero = false;
    }

    public int getX() {
        return myX;
    }

    public int getY() {
        return myY;
    }


    public boolean hasWall() {
        return myWall != null;
    }
    public boolean hasEntrance() {
        return myEntrance != null;
    }
    public boolean hasExit() {
        return myExit != null;
    }
    public boolean hasPotion() {
        return myPotion != null;
    }
    public boolean hasPillar() {
        return myPillar != null;
    }
    public boolean hasPit() {
        return myPit != null;
    }
    public boolean hasMonster() {
        return myMonster != null;
    }
    public boolean hasHero() {
        return myHero;
    }

    public boolean isEmpty() {
        return !hasWall() && !hasEntrance() && !hasExit() &&
                !hasMonster() && !hasPotion() && !hasPillar() && !hasPit();
    }


    // 'place' methods have factors determining *which* type of object to place
    // the logic for *when* to place the object is found in the Dungeon class
    public void placeWall() {
        myWall = new Wall();
    }
    public void placeEntrance() {
        myEntrance = new Entrance();
    } //TODO make entrance a singleton
    public void placeExit() {
        myExit = new Exit();
    } //TODO make exit a singleton
    public void placeMonster(List<Monster> theUnplacedMonsters) {
        //read from myMonsters to determine which monster to place
        myMonster = theUnplacedMonsters.remove(0); // remove the first monster from the list
    }
    public void placePotion() {

        // randomly choose between placing a health potion and a vision potion
        if (Math.random() < HEALTH_POTION_CHANCE) {
            myPotion = new HealthPotion();
        } else {
            myPotion = new VisionPotion();
        }
    }
    public void placePillar(Set<Pillars> thePlacedPillars) {
        //TODO (maybe) randomize what type of pillar to place.
        //TODO for example, instead of ALWAYS placing the abstraction pillar first, randomly choose one of the 4.

        //determine if the pillar we're about to place is inside a set of placed pillars.

        if (!thePlacedPillars.contains(Pillars.ABSTRACTION)) {
            myPillar = Pillars.ABSTRACTION;
        } else if (!thePlacedPillars.contains(Pillars.ENCAPSULATION)) {
            myPillar = Pillars.ENCAPSULATION;
        } else if (!thePlacedPillars.contains(Pillars.INHERITANCE)) {
            myPillar = Pillars.INHERITANCE;
        } else if (!thePlacedPillars.contains(Pillars.POLYMORPHISM)) {
            myPillar = Pillars.POLYMORPHISM;
        } else {
            System.out.println("Tried to place a new pillar, but all 4 are already in the dungeon!");
        }
    }
    public void placePit() {
        myPit = new Pit();
    }

    public void removeWall() {
        myWall = null;
    }
    public void placeHero() {
        myHero = true;
    }
    public void removeHero() {
        myHero = false;
    }


    @Override
    public String toString() { //! add monster, multiple items (pits)?

        // first determine how many items are in the room
        int numItems = 0;
        if (hasPotion()) { numItems++; }
        if (hasPillar()) { numItems++; }
        if (hasPit()) { numItems++; }
        if (hasMonster()) { numItems++; }

        // now determine what string to return
        String result = "";
        if (hasWall()) {
            result = myWall.toString();
        }  else if (hasHero()) {
            result = HERO;
        } else if (numItems > 1) {
            result = MULTIPLE;
        } else if (hasPotion()) {
            result = myPotion.toString();
        } else if (hasPillar()) {
            result = myPillar.toString();
        } else if (hasPit()) {
            result = myPit.toString();
        } else if (hasExit()) {
            result = EXIT;
        } else if (hasEntrance()) {
            result = ENTRANCE;
        } else if (hasMonster()) {
            result = myMonster.toString();
        } else if (hasEntrance()) {
            result = myEntrance.toString();
        } else if (hasExit()) {
            result = myExit.toString();
        } else {
            result = EMPTY;
        }

        return result;
    }
}
