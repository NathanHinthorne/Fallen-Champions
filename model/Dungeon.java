package model;
import java.util.*;

public class Dungeon {

    //! put all fields only being used in the inner small/medium/large DungeonBuilder classes inside those inner classes
    //! example: might need to move myUnplacedMonsters to inner classes

    private static Dungeon dungeon; // for singleton
    static private Room[][] maze;
    private int mazeWidth;
    private int mazeHeight;
    private static int myHeroX;
    private static int myHeroY;
    private static int myEntranceX;
    private static int myEntranceY;
    private static int myExitX;
    private static int myExitY;
    private static Set<Pillars> myPlacedPillars;
    private static Queue<Monster> myUnplacedMonsters;

    /**
     * Creates a new dungeon with the given dimensions and starting position.
     *
     * @param theDungeonWidth the width of the dungeon in rooms
     * @param theDungeonHeight the height of the dungeon in rooms
     * @param theHeroX the starting x position of the hero
     * @param theHeroY the starting y position of the hero
     */
//    private Dungeon(final DungeonBuilder theDungeonBuilder, final int theDungeonWidth, // constructor should be DELETED after builder is finished
//                   final int theDungeonHeight, final int theHeroX, final int theHeroY) {
//
//        // initialize fields
//        myDungeonWidth = setDungeonWidth(theDungeonWidth);
//        myDungeonHeight = setDungeonHeight(theDungeonHeight);
//        myHeroX = setHeroX(theHeroX);
//        myHeroY = setHeroY(theHeroY);
//        myEntranceX = 0;
//        myEntranceY = 0;
//        myExitX = 2;
//        myExitY = 0;
//        myMaze = new Room[theDungeonWidth][theDungeonHeight];
//        myPlacedPillars = new HashSet<Pillars>();
//        myUnplacedMonsters = new LinkedList<Monster>();
//    }

    //TODO for the singleton if needed
    /**
     * Gives the single instance of dungeon
     *
     * @return The dungeon
     */
//    public static Dungeon getDungeon() {
//        if (thisDungeon != null) {
//            thisDungeon = new Dungeon();
//        }
//    }


    public static class SmallDungeonBuilder extends DungeonBuilder { // put parameters to the Dungeon constructor inside here?
        private static final int SMALL_DUNGEON_WIDTH = 10;
        private static final int SMALL_DUNGEON_HEIGHT = 10;

//        myUnplacedMonsters = readMonsters();

        setMaze(maze); // why is this not recognized from the abstract class?
        setMazeWidth(SMALL_DUNGEON_WIDTH);
        setMazeHeight(SMALL_DUNGEON_HEIGHT);

        @Override
        public Dungeon buildDungeon() {
            if (dungeon != null) { // for singleton
                dungeon = new Dungeon();
            }
            maze = new Room[SMALL_DUNGEON_HEIGHT][SMALL_DUNGEON_WIDTH];

            // step 1: fill the dungeon COMPLETELY with walls
            fillWithWalls();

            // step 2: randomly place empty rooms
            fillWithEmptyRooms();

            // step 3: fill empty rooms with objects
            fillWithObjects();

            // step 4: add entrance and exit
            addEntrance();
            addExit();

            // step 5: find a starting point for the hero
            

            // step 6: keep building dungeons until we find one that's traversable
            while(!isTraversable()) {
                buildDungeon();
            }

            return dungeon;
        }

        @Override
        public Queue<Monster> readMonsters() {
            Queue<Monster> unplacedMonsters = new LinkedList<>();

            //TODO access SQLite and fill up the queue

            return unplacedMonsters;
        }
    }
//    public static class MediumDungeonBuilder implements DungeonBuilder {
//
//    }
//    public static class LargeDungeonBuilder implements DungeonBuilder {
//
//    }

    // for inner classes to use
    private Room[][] getMaze() {
        return maze;
    }
    private Dungeon getDungeon() { return dungeon; }


//    private int setDungeonWidth(int theDungeonWidth) {
//        if (theDungeonWidth <= 0) {
//            throw new IllegalArgumentException();
//        }
//        return theDungeonWidth;
//    }
//    private int setDungeonHeight(int theDungeonHeight) {
//        if (theDungeonHeight <= 0) {
//            throw new IllegalArgumentException();
//        }
//        return theDungeonHeight;
//    }
//    private int setHeroX(int theHeroX) {
//        if (theHeroX <= 0) {
//            throw new IllegalArgumentException();
//        }
//        return theHeroX;
//    }
//    private int setHeroY(int theHeroY) {
//        if (theHeroY <= 0) {
//            throw new IllegalArgumentException();
//        }
//        return theHeroY;
//    }


    // a method in the view will check for keyboard inputs
    // once triggered, it will tell the controller to call this method
    /**
     * Moves the player in the given direction.
     *
     * @param dir the direction to move the player
     */
    public void playerMove(final Direction dir) {
        if (dir == Direction.NORTH) {
            myHeroY--;
        } else if (dir == Direction.EAST) {
            myHeroX++;
        } else if (dir == Direction.SOUTH) {
            myHeroY--;
        } else if (dir == Direction.WEST) {
            myHeroX--;
        } else {
            System.out.println("Invalid direction was given.\n" +
                    "Make sure playerMove() receives one of these:\n" +
                    "NORTH, EAST, SOUTH, WEST");
        }
    }

    /**
     * Returns a 3x3 grid of rooms centered on the hero.
     *
     * @return a 3x3 grid of rooms centered on the hero
     */
    public Room[][] getView() { //! return a string instead?

        Room[][] view = new Room[3][3];

        for (int x = myHeroX - 1; x <= myHeroX + 1; x++) {
            for (int y = myHeroY - 1; y <= myHeroY + 1; y++) {
                view[x][y] = maze[x][y]; // make it access the maze instead
            }
        }
        return view;
    }

    //only call when using vision potion
    /**
     * Returns a 7x7 grid of rooms centered on the hero.
     *
     * @return a 7x7 grid of rooms centered on the hero
     */
    public Room[][] getExpandedView() {

        Room[][] view = new Room[7][7];

        for (int x = myHeroX - 3; x <= myHeroX + 3; x++) {
            for (int y = myHeroY - 3; y <= myHeroY + 3; y++) {
                view[x][y] = maze[x][y];
            }
        }
        return view;
    }

    /**
     * Iterates over the dungeon 2D array.
     * For each room, determines what char to add based on what objects are in the room.
     *
     * @return a string representation of the dungeon.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < mazeHeight; i++) {
            for (int j = 0; j < mazeWidth; j++) {
                sb.append(maze[i][j].toString());
            }
        }
        return sb.toString();
    }

}
