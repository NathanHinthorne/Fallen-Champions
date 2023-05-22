package model;
import java.util.*;

//import javafx.event.EventHandler;
//import javafx.scene.Scene;
//import javafx.scene.input.KeyCode;
//import javafx.scene.input.KeyEvent;

public class Dungeon {

    // make non-static if we add difficulty levels
    public static final double NEW_ROOM_CHANCE = 0.20;
    public static final double EXTENDED_ROOM_CHANCE = 0.70;
    public static final double ENEMY_CHANCE = 0.20;
    public static final double POTION_CHANCE = 0.10;
    public static final double PILLAR_CHANCE = 0.01;
    public static final double PIT_CHANCE = 0.10;
    public static final Random rand = new Random();
    private static Dungeon thisDungeon; // for singleton


    // fields
    final private Room[][] myDungeon;
    final private int myDungeonWidth;
    final private int myDungeonHeight;
    private int myHeroX;
    private int myHeroY;
    private int myEntranceX;
    private int myEntranceY;
    private int myExitX;
    private int myExitY;
    final private Set<Pillars> myPlacedPillars;
    final private Queue<Monster> myUnplacedMonsters;

//    Scene myScene;


    /**
     * Creates a new dungeon with the given dimensions and starting position.
     *
     * @param theDungeonWidth the width of the dungeon in rooms
     * @param theDungeonHeight the height of the dungeon in rooms
     * @param theHeroX the starting x position of the hero
     * @param theHeroY the starting y position of the hero
     */
    public Dungeon(final int theDungeonWidth, final int theDungeonHeight, final int theHeroX, final int theHeroY) {

        // initialize fields
        myDungeonWidth = setDungeonWidth(theDungeonWidth);
        myDungeonHeight = setDungeonHeight(theDungeonHeight);
        myHeroX = setHeroX(theHeroX);
        myHeroY = setHeroY(theHeroY);
        myEntranceX = 0;
        myEntranceY = 0;
        myExitX = 2;
        myExitY = 0;
        myDungeon = new Room[theDungeonWidth][theDungeonHeight];
        myPlacedPillars = new HashSet<Pillars>();
        myUnplacedMonsters = new LinkedList<Monster>();

        generateDungeon();
    }

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


    private int setDungeonWidth(int theDungeonWidth) {
        if (theDungeonWidth <= 0) {
            throw new IllegalArgumentException();
        }
        return theDungeonWidth;
    }
    private int setDungeonHeight(int theDungeonHeight) {
        if (theDungeonHeight <= 0) {
            throw new IllegalArgumentException();
        }
        return theDungeonHeight;
    }
    private int setHeroX(int theHeroX) {
        if (theHeroX <= 0) {
            throw new IllegalArgumentException();
        }
        return theHeroX;
    }
    private int setHeroY(int theHeroY) {
        if (theHeroY <= 0) {
            throw new IllegalArgumentException();
        }
        return theHeroY;
    }

    /**
     * Fills the dungeon with rooms and walls
     */
    private void generateDungeon() { //! don't return dungeon

        // step 1: fill the dungeon COMPLETELY with walls
        fillWithWalls();

        // step 2: randomly place empty rooms and rooms with items
        addEmptyRooms(); //TODO generate items in diff method?

        // step 3: add entrance and exit
        addEntrance();
        addExit();

        // keep generating dungeons until we get one that is traversable
        while(!isTraversable()) {
            generateDungeon();
        }
    }

    /**
     * Fills the entire dungeon with walls.
     */
    private void fillWithWalls() {
        for (int y = 0; y < myDungeonHeight; y++) {
            for (int x = 0; x < myDungeonWidth; x++) {
                myDungeon[y][x] = new Room(y, x);
                myDungeon[y][x].placeWall();
            }
        }
    }

    /**
     * Removes certain walls to form walkways using a random walk algorithm.
     */
    private void addEmptyRooms() {

        for (int y = 1; y < myDungeonHeight-1; y++) { // skip over the edges of the dungeon
            for (int x = 1; x < myDungeonWidth-1; x++) {

                Room room = myDungeon[y][y];

                //TODO Two choices to determine where to place room
                // 1. perlin noise
                // 2. simple if statements like below
                int numberOfEmptyRooms = numberOfEmptyRooms(x, y);
                if (numberOfEmptyRooms == 0) {
                    if (Math.random() < NEW_ROOM_CHANCE) {
                        room.removeWall();
                    }
                } else if (numberOfEmptyRooms == 1) {
                    if (Math.random() < EXTENDED_ROOM_CHANCE) {
                        room.removeWall();
                    }
                }

                if (room.isEmpty()) { // provided the wall was removed, place items in the room

                    if (Math.random() < ENEMY_CHANCE) { //TODO limit the number of enemies in the dungeon - probably not
                        room.placeMonster(myUnplacedMonsters);             //TODO use a list to accomplish this?
                    }
                    if (Math.random() < POTION_CHANCE) {
                        room.placePotion();
                    }
                    if (Math.random() < PILLAR_CHANCE) {
                        room.placePillar(myPlacedPillars);
                    }
                    if (Math.random() < PIT_CHANCE) {
                        room.placePit();
                    }
                }
            }
        }
    }

    /**
     * Adds an entrance to the dungeon.
     */
    private void addEntrance() {
        int x;
        int y;

        // keep generating coords for entrance until we get a room that is empty
        do {
            x = rand.nextInt(myDungeonWidth);
            y = rand.nextInt(myDungeonHeight);

        } while (!myDungeon[y][x].isEmpty());

        myDungeon[y][x].placeEntrance();
        myEntranceX = x;
        myEntranceY = y;
    }

    /**
     * Adds an exit to the dungeon.
     */
    private void addExit() {
        int x;
        int y;

        // keep generating coords for an exit until we get a room that is empty
        do {
            x = rand.nextInt(myDungeonWidth);
            y = rand.nextInt(myDungeonHeight);

        } while (!myDungeon[y][x].isEmpty());

        myDungeon[y][x].placeExit();
        myExitX = x;
        myExitY = y;
    }

    /**
     * Determines the number of empty rooms adjacent (left/right/up/down) to the given room.
     *
     * @param theX the x coordinate of the room
     * @param theY the y coordinate of the room
     * @return the number of empty rooms adjacent to the given room
     */
    private int numberOfEmptyRooms(final int theX, final int theY) {

        int count = 0;

        // check top
        if (myDungeon[theY-1][theX].isEmpty()) {
            count++;
        }

        // check left
        if (myDungeon[theY][theX-1].isEmpty()) {
            count++;
        }

        // check right
        if (myDungeon[theY][theX+1].isEmpty()) {
            count++;
        }

        // check bottom
        if (myDungeon[theY+1][theX].isEmpty()) {
            count++;
        }

        return count;
    }

    /**
     * Determines the number of walls adjacent (left/right/up/down) to the given room.
     *
     * @param theX the x coordinate of the room
     * @param theY the y coordinate of the room
     * @return the number of walls adjacent to the given room
     */
    private int numberOfWalls(final int theX, final int theY) { //! refactor for duplicate code after iteration?

        int count = 0;

        // check top
        if (myDungeon[theY-1][theX].hasWall()) {
            count++;
        }

        // check left
        if (myDungeon[theY][theX-1].hasWall()) {
            count++;
        }

        // check right
        if (myDungeon[theY][theX+1].hasWall()) {
            count++;
        }

        // check bottom
        if (myDungeon[theY+1][theX].hasWall()) {
            count++;
        }

        return count;
    }

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
     * Allows the player to move into a specified direction
     *
     * @param dir the direction to move the player
     */
    public void movePlayer(Direction dir) {

        if (dir == Direction.NORTH) {
            myHeroY--;
        } else if (dir == Direction.EAST) {
            myHeroX++;
        } else if (dir == Direction.SOUTH) {
            myHeroY++;
        } else if (dir == Direction.WEST) {
            myHeroX--;
        } else {
            System.out.println("Invalid direction was given.\n" +
                    "Make sure movePlayer() receives one of these:\n" +
                    "NORTH, EAST, SOUTH, WEST");
        }
    }

//        Scanner sn = new Scanner(System.in);
//        String inp;

       //inp = sn.next();
        //switch(inp) {
        //    case "w":
        //        playerMove(Direction.NORTH);
        //    case "a":
        //        playerMove(Direction.EAST);
        //    case "s":
        //        playerMove(Direction.SOUTH);
        //    case "d":
        //        playerMove(Direction.WEST);
        //    default:
        //        System.out.println("Wrong Key, please select W,A,S,D");
        //}

//        myScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent keyEvent) {
//                if(keyEvent.getCode() == KeyCode.W) {
//                    playerMove(Direction.NORTH);
//                } else if (keyEvent.getCode() == KeyCode.A) {
//                    playerMove(Direction.EAST);
//                } else if (keyEvent.getCode() == KeyCode.S) {
//                    playerMove(Direction.SOUTH);
//                } else if (keyEvent.getCode() == KeyCode.D) {
//                    playerMove(Direction.WEST);
//                } else {
//                    System.out.println("Wrong Key, please select W,A,S,D");
//                }
//            }
//        });
//    }

    /**
     * Checks if the dungeon is traversable.
     *
     * @return true if the dungeon is traversable, false otherwise.
     */
    private boolean isTraversable() {

        boolean isTraversable = false;

        //before this method, make sure there are all 4 pillars present within the dungeon

        //pick a random direction to act as forward (as long as forward leads to empty room)
        isTraversable = traverse(myEntranceX, myEntranceY, Direction.NORTH); //TODO change Direction.NORTH

        return isTraversable;
    }


    private boolean traverse(final int theX, final int theY, final Direction theForward) {

        final Direction theRight;
        final Direction theLeft;

        // determine what left and right should be based on forward
        if (theForward == Direction.NORTH) {
            theRight = Direction.EAST;
            theLeft = Direction.WEST;
        } else if (theForward == Direction.EAST) {
            theRight = Direction.SOUTH;
            theLeft = Direction.NORTH;
        } else if (theForward == Direction.SOUTH) {
            theRight = Direction.WEST;
            theLeft = Direction.EAST;
        } else { // forward must be west
            theRight = Direction.NORTH;
            theLeft = Direction.SOUTH;
        }

        // base case - successful
        if (myDungeon[theX][theY].hasExit()) {
            System.out.println("Exit found!");

            return true; // does this stop all of the method calls? will this one return statement solve the problem?
        }

        // base case and recursive case
        Room forwardRoom = walk(theX, theY, theForward);
        Room rightRoom = walk(theX, theY, theRight);
        Room leftRoom = walk(theX, theY, theLeft);
        int newY;
        int newX;

        if (!forwardRoom.hasWall()) {
            newX = forwardRoom.getX();
            newY = forwardRoom.getY();
            traverse(newX, newY, theForward); //refactor to make traverse accept rooms instead of directions?
        }                                                   // ex: forwardRoom instead of theForward
        if (!rightRoom.hasWall()) {
            newX = rightRoom.getX();
            newY = rightRoom.getY();
            traverse(newX, newY, theRight);
        }
        if (!leftRoom.hasWall()) {
            newX = leftRoom.getX();
            newY = leftRoom.getY();
            traverse(newX, newY, theLeft);
        }

        return false; // we did not find an exit
    }

    private Room walk(final int theX, final int theY, final Direction dir) {
        Room newRoom;

        if (dir == Direction.NORTH) {
            newRoom = myDungeon[theY-1][theX];
        } else if (dir == Direction.EAST) {
            newRoom = myDungeon[theY][theX+1];
        } else if (dir == Direction.SOUTH) {
            newRoom = myDungeon[theY+1][theX];
        } else if (dir == Direction.WEST) {
            newRoom = myDungeon[theY][theX-1];
        } else {
            newRoom = myDungeon[theY][theX];
        }

        return newRoom;
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
                view[x][y] = myDungeon[x][y];
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
                view[x][y] = myDungeon[x][y];
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

        for (int i = 0; i < myDungeonHeight; i++) {
            for (int j = 0; j < myDungeonWidth; j++) {
                sb.append(myDungeon[i][j].toString());
            }
        }
        return sb.toString();
    }

}
