package model;

import java.util.List;
import java.util.Queue;
import java.util.Set;

public class Room {

    // static fields for toString

    public static final String EMPTY = " ";
    public static final String MULTIPLE = "m";


    // make non-static if we add difficulty levels
    public static final double HEALTH_POTION_CHANCE = 0.65;
    // vision potion chance is 0.35
    public static final double A_PILLAR_CHANCE = 0.25;
    public static final double I_PILLAR_CHANCE = 0.25;
    public static final double P_PILLAR_CHANCE = 0.25;
    // E pillar chance is 0.25

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
    private boolean hasMonster() {
        return myMonster != null;
    }

    public boolean isEmpty() {
        return !hasWall() && !hasEntrance() && !hasExit() &&
                !hasMonster() && !hasPotion() && !hasPillar() && !hasPit();
    }


    // 'place' methods have factors determining which type of object to place
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
    public void placeMonster(Queue<Monster> theUnplacedMonsters) {
        //read from myMonsters to determine which monster to place
        myMonster = theUnplacedMonsters.remove();
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
        //TODO decide what type of pillar to place

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

    @Override
    public String toString() { //! add monster, multiple items (pits)?
        if (hasWall()) {
            return myWall.toString();
        } else if (hasPotion() && hasPillar() || hasPotion() && hasPit()) { // add more cases for multiple
            return MULTIPLE; // doesn't make sense in this context to make a class for multiple
        } else if (hasPotion()) {
            return myPotion.toString();
        } else if (hasPillar()) {
            return myPillar.toString();
        } else if (hasPit()) {
            return myPit.toString();
        } else if (hasExit()) {
            return myExit.toString();
        } else if (hasEntrance()) {
            return myEntrance.toString();
        } else if (hasMonster()) {
            return myMonster.toString();
        } else {
            return EMPTY; // doesn't make sense in this context to make a class for empty
        }

    }

}
