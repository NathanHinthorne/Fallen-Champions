package FallenChampions.model.dungeon;

import FallenChampions.model.potions.HealthPotion;
import FallenChampions.model.potions.Potion;
import FallenChampions.model.potions.VisionPotion;
import FallenChampions.model.characters.monsters.Monster;
import FallenChampions.model.characters.monsters.MonsterFactory;
import FallenChampions.model.characters.monsters.MonsterTypes;

import java.util.List;
import java.util.Set;

/**
 * Represents a room in the dungeon.
 * A room can have a wall, an exit, a monster, a potion, a pillar, a pit, and a hero.
 * A room's contents can also be visible or invisible.
 *
 * CHEAT SHEET for dungeon symbols:
 *     ' ' = empty room
 *     '*' = wall
 *     '◉' = hero
 *     'M' = monster
 *     'X' = pit
 *     '▮' = exit
 *     'p' = potion
 *     'A' = abstraction pillar
 *     'I' = inheritance pillar
 *     'P' = polymorphism pillar
 *     'E' = encapsulation pillar
 *     '&' = multiple items in the same room
 *
 * @author Nathan Hinthorne
 * @version 1.0
 */
public class Room implements java.io.Serializable {

    // static fields for toString

    /**
     * Empty room
     */
    public static final String EMPTY = " ";

    /**
     * Wall
     */
    public static final String WALL = "*";

    /**
     * Room with a wall
     */
    public static final String MULTIPLE = "&"; // multiple objects in one room

    /**
     * Hero symbol
     */
    public static final String HERO = "□";

    /**
     * Exit symbol
     */
    public static final String EXIT = "▓";

    /**
     * chance for the potion to be a health potion
     */
    public static final double HEALTH_POTION_CHANCE = 0.65;
    // vision potion chance is 0.35


    /**
     * that the contents of the room are visible
     */
    private boolean myVisible;

    /**
     * that the room has been visited
     */
    private boolean myVisited;

    /**
     * Y coordinate of the room
     */
    private int myY;

    /**
     * X coordinate of the room
     */
    private int myX;

    /**
     * The room's wall
     */
    private boolean myWall;

    /**
     * The room's exit
     */
    private boolean myExit; //private Exit myExit;

    /**
     * The room's monster
     */
    private Monster myMonster;

    /**
     * The room's potion
     */
    private Potion myPotion;

    /**
     * The room's pillar
     */
    private Pillars myPillar;

    /**
     * The room's pit
     */
    private Pit myPit;

    /**
     * The room's hero
     */
    private boolean myHero; // just a boolean since we don't have access to a hero object



    /**
     * Constructs a room with the given coordinates
     * @param theY the y coordinate
     * @param theX the x coordinate
     */
    public Room(final int theY, final int theX) {
        myY = theY;
        myX = theX;
        myWall = false;
        myExit = false; //   myExit = null
        myMonster = null;
        myPotion = null;
        myPillar = null;
        myPit = null;
        myHero = false;
    }

    /**
     * gets the x coordinate of the room
     * @return the x coordinate
     */
    public int getX() {
        return myX;
    }

    /**
     * gets the y coordinate of the room
     * @return the y coordinate
     */
    public int getY() {
        return myY;
    }

    /**
     * checks if the room has a wall
     * @return true if the room has a wall, false otherwise
     */
    public boolean hasWall() {
        return myWall;
    }

    /**
     * checks if the room has an exit
     * @return true if the room has an exit, false otherwise
     */
    public boolean hasExit() {
        return myExit;
    } //return myExit != null

    /**
     * checks if the room has a potion
     * @return true if the room has a potion, false otherwise
     */
    public boolean hasPotion() {
        return myPotion != null;
    }

    /**
     * checks if the room has a pillar
     * @return true if the room has a pillar, false otherwise
     */
    public boolean hasPillar() {
        return myPillar != null;
    }

    /**
     * checks if the room has a pit
     * @return true if the room has a pit, false otherwise
     */
    public boolean hasPit() {
        return myPit != null;
    }

    /**
     * checks if the room has a monster
     * @return true if the room has a monster, false otherwise
     */
    public boolean hasMonster() {
        return myMonster != null;
    }

    /**
     * checks if the room has a hero
     * @return true if the room has a hero, false otherwise
     */
    public boolean hasHero() {
        return myHero;
    }

    /**
     * gets the room's potion if it has one
     * @return the room's potion
     */
    public Potion getPotion() {
        return myPotion;
    }

    /**
     * gets the room's pillar if it has one
     * @return the room's pillar
     */
    public Pillars getPillar() {
        return myPillar;
    }

    /**
     * gets the room's pit if it has one
     * @return the room's pit
     */
    public Pit getPit() {
        return myPit;
    }

    /**
     * gets the room's monster if it has one
     * @return the room's monster
     */
    public Monster getMonster() {
        return myMonster;
    }

    /**
     * checks if the room is empty
     * @return true if the room is empty, false otherwise
     */
    public boolean isEmpty() {
        return !hasWall() && !hasExit() && !hasMonster() &&
                !hasPotion() && !hasPillar() && !hasPit();
    }

    /**
     * sets if the room's contents are visible
     * @param theVisible true if the room is visible, false otherwise
     */
    public void setVisible(final boolean theVisible) {
        myVisible = theVisible;
    }

    /**
     * sets if the room has been visited
     * @param theVisited true if the room has been visited, false otherwise
     */
    public void setVisited(final boolean theVisited) {
        myVisited = theVisited;
    }

    /**
     * checks if the room was visited
     * @return true if the room was visited, false otherwise
     */
    public boolean wasVisited() {
        return myVisited;
    }


    // 'place' methods have factors determining *which* type of object to place
    // the logic for *when* to place the object is found in the Dungeon class

    /**
     * places a wall in the room
     */
    public void placeWall() {
        myWall = true;
    }

    /**
     * places an exit in the room
     */
    public void placeExit() {
        myExit = true;
    } //myExit = new Exit()

    /**
     * places a monster in the room
     * @param theDifficulty the difficulty of dungeon
     */
    public void placeMonster(Difficulty theDifficulty) { //TODO re-write this to simply place a monster, not containing the logic for which monster to place.
                                                         // That logic should be in each concrete dungeon builder class.

        double skeletonChance = 0.0;
        double gremlinChance = 0.0;
        double spiderChance = 0.0;
        double ogreChance = 0.0;
        double warlockChance = 0.0;

        if (theDifficulty == Difficulty.EASY) {
            skeletonChance = 0.30; // 30%
            gremlinChance = 0.60;  // 30%
            spiderChance = 0.80;   // 20%
            ogreChance = 0.100;    // 20%
            warlockChance = 0.0;   // 0%

        } else if (theDifficulty == Difficulty.MEDIUM) {
            skeletonChance = 0.20; // 20%
            gremlinChance = 0.45;  // 25%
            spiderChance = 0.65;   // 20%
            ogreChance = 0.85;     // 20%
            warlockChance = 0.100; // 15%

        } else if (theDifficulty == Difficulty.HARD) {
            skeletonChance = 0.3;
            gremlinChance = 0.5;
            spiderChance = 0.0;
            ogreChance = 0.2;
            warlockChance = 0.100;
        }

        double theChance = Math.random();

        if (theChance <= skeletonChance) {
            myMonster = MonsterFactory.buildMonster(MonsterTypes.SKELETON);

        } else if (theChance > skeletonChance && theChance <= gremlinChance) {
            myMonster = MonsterFactory.buildMonster(MonsterTypes.GREMLIN);

        } else if (theChance > gremlinChance && theChance <= spiderChance) {
            myMonster = MonsterFactory.buildMonster(MonsterTypes.SPIDER);

        } else if (theChance > spiderChance && theChance <= ogreChance) {
            myMonster = MonsterFactory.buildMonster(MonsterTypes.OGRE);

        } else if (theChance > ogreChance && theChance <= warlockChance) {
            myMonster = MonsterFactory.buildMonster(MonsterTypes.WARLOCK);
        }
    }

    /**
     * places a potion in the room
     */
    public void placePotion() {

        // randomly choose between placing a health potion and a vision potion
        if (Math.random() < HEALTH_POTION_CHANCE) {
            myPotion = new HealthPotion();
        } else {
            myPotion = new VisionPotion();
        }
    }

    /**
     * places a pillar in the room
     * @param thePlacedPillars the set of placed pillars
     */
    public void placePillar(Set<Pillars> thePlacedPillars) {
        //TODO (maybe) randomize what type of pillar to place.
        //TODO for example, instead of ALWAYS placing the abstraction pillar first, randomly choose one of the 4.

        //determine if the pillar we're about to place is inside a set of placed pillars.

        if (!thePlacedPillars.contains(Pillars.ABSTRACTION)) {
//            System.out.println("DEBUG: Placing abstraction pillar");
            thePlacedPillars.add(Pillars.ABSTRACTION);
            myPillar = Pillars.ABSTRACTION;
        } else if (!thePlacedPillars.contains(Pillars.ENCAPSULATION)) {
//            System.out.println("DEBUG: Placing encapsulation pillar");
            thePlacedPillars.add(Pillars.ENCAPSULATION);
            myPillar = Pillars.ENCAPSULATION;
        } else if (!thePlacedPillars.contains(Pillars.INHERITANCE)) {
//            System.out.println("DEBUG: Placing inheritance pillar");
            thePlacedPillars.add(Pillars.INHERITANCE);
            myPillar = Pillars.INHERITANCE;
        } else if (!thePlacedPillars.contains(Pillars.POLYMORPHISM)) {
//            System.out.println("DEBUG: Placing polymorphism pillar");
            thePlacedPillars.add(Pillars.POLYMORPHISM);
            myPillar = Pillars.POLYMORPHISM;
        } else {
//            System.out.println("DEBUG: Tried to place a new pillar, but 4 are already in the dungeon!");
        }
    }

    /**
     * places a pit in the room
     */
    public void placePit() {
        myPit = new Pit();
    }

    /**
     * places a hero in the room
     */
    public void placeHero() {
        myHero = true;
    }

    /**
     * removes a wall from the room
     */
    public void removeWall() {
        myWall = false;
    }

    /**
     * removes the hero from the room
     */
    public void removeHero() {
        myHero = false;
    }

    /**
     * removes the monster from the room
     */
    public void removeMonster() {
        myMonster = null;
    }

    /**
     * removes the potion from the room
     */
    public void removePotion() {
        myPotion = null;
    }

    /**
     * removes the pillar from the room
     */
    public void removePillar() {
        myPillar = null;
    }


    /**
     * prints the contents of the room
     */
    public void getContents() {
        System.out.print("Room contents: ");

        if (hasExit()) {
            System.out.print("Exit, ");
        } if (hasPotion()) {
            System.out.print("Potion, ");
        } if (hasPillar()) {
            System.out.print("Pillar, ");
        } if (hasPit()) {
            System.out.print("Pit, ");
        } if (hasMonster()) {
            System.out.print("Monster, ");
        }

        System.out.println();
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
            result = WALL;
        }  else if (hasHero()) {
            result = HERO;
        }

        else if (myVisible) {
            if (numItems > 1) {
                result = MULTIPLE;
            } else if (hasPotion()) {
                result = myPotion.toString();
            } else if (hasPillar()) {
                result = myPillar.toString();
            } else if (hasPit()) {
                result = myPit.toString();
            } else if (hasMonster()) {
                result = myMonster.toString();
            } else if (hasExit()) {
                result = EXIT; // myExit.toString();
            } else {
                result = EMPTY;
            }

        } else if (!myVisible) {
            result = EMPTY;
        }


        return result;
    }
}
