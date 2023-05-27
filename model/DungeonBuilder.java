package model;

import java.awt.Point;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

public abstract class DungeonBuilder {

    // include as parameters to buildDungeon if we want these to change with difficulty levels
    static final double NEW_ROOM_CHANCE = 0.20;
    static final double EXTENDED_ROOM_CHANCE = 0.70;
    static final double ENEMY_CHANCE = 0.20;
    static final double POTION_CHANCE = 0.10;
    static final double PILLAR_CHANCE = 0.01;
    static final double PIT_CHANCE = 0.10;
    static final Random rand = new Random();

    static Room[][] myMaze;
    static int myMazeWidth;
    static int myMazeHeight;
    static int myEntranceX;
    static int myEntranceY;
    static int myExitX;
    static int myExitY;


    abstract public Dungeon buildDungeon();

    abstract public Queue<Monster> readMonsters(); //read different monsters in from SQLite depending on difficulty

    protected void setMaze(Room[][] theMaze) {
        myMaze = theMaze;
    }

    protected void setMazeWidth(int theMazeWidth) {
        myMazeWidth = theMazeWidth;
    }

    protected void setMazeHeight(int theMazeHeight) {
        myMazeHeight = theMazeHeight;
    }



    // get methods?


    protected void fillWithWalls() {
        for (int y = 0; y < myMazeHeight; y++) {
            for (int x = 0; x < myMazeWidth; x++) {
                myMaze[y][x] = new Room(y, x);
                myMaze[y][x].placeWall();
            }
        }
    }

    protected void fillWithEmptyRooms() {
        for (int y = 1; y < myMazeHeight - 1; y++) { // skip over the edges of the dungeon
            for (int x = 1; x < myMazeWidth - 1; x++) {

                Room room = myMaze[y][y];

                //TODO Three choices to determine where to place room
                // 1. perlin noise
                // 2. simple if statements like below
                // 3. recursive method
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
            }
        }
    }

    protected void fillWithObjects(Queue<Monster> theUnplacedMonsters, Set<Pillars> thePlacedPillars) {

        for (int y = 1; y < myMazeHeight - 1; y++) { // skip over the edges of the dungeon
            for (int x = 1; x < myMazeWidth - 1; x++) {

                Room room = myMaze[y][y];

                if (room.isEmpty()) { // provided the wall was removed, place items in the room

                    if (Math.random() < ENEMY_CHANCE) { //TODO limit the number of enemies in the dungeon - probably not
                        room.placeMonster(theUnplacedMonsters);             //TODO use a list to accomplish this?
                    }
                    if (Math.random() < POTION_CHANCE) {
                        room.placePotion();
                    }
                    if (Math.random() < PILLAR_CHANCE) {
                        room.placePillar(thePlacedPillars);
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
    protected Point addEntrance() {
        int x;
        int y;

        // keep generating coords for entrance until we get a room that is empty
        do {
            x = rand.nextInt(myMazeWidth);
            y = rand.nextInt(myMazeHeight);

        } while (!myMaze[y][x].isEmpty());

        myMaze[y][x].placeEntrance();
        return new Point(x, y);
    }

    /**
     * Adds an exit to the dungeon.
     */
    protected Point addExit() {
        int x;
        int y;

        // keep generating coords for an exit until we get a room that is empty
        do {
            x = rand.nextInt(myMazeWidth);
            y = rand.nextInt(myMazeHeight);

        } while (!myMaze[y][x].isEmpty());

        myMaze[y][x].placeExit();
        return new Point(x, y);
    }

    /**
     * Determines a valid starting point for the hero.
     */
    protected Point findStartingPoint() {
        int x;
        int y;

        // keep generating coords for hero until we get a room that is empty
        do {
            x = rand.nextInt(myMazeWidth);
            y = rand.nextInt(myMazeHeight);

        } while (!myMaze[y][x].isEmpty());

        return new Point(x, y);
    }


    /**
     * Checks if the dungeon is traversable.
     *
     * @return true if the dungeon is traversable, false otherwise.
     */
    protected boolean isTraversable() {

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
        if (myMaze[theX][theY].hasExit()) {
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
            newRoom = myMaze[theY - 1][theX];
        } else if (dir == Direction.EAST) {
            newRoom = myMaze[theY][theX + 1];
        } else if (dir == Direction.SOUTH) {
            newRoom = myMaze[theY + 1][theX];
        } else if (dir == Direction.WEST) {
            newRoom = myMaze[theY][theX - 1];
        } else {
            newRoom = myMaze[theY][theX];
        }

        return newRoom;
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
        if (myMaze[theY - 1][theX].isEmpty()) {
            count++;
        }

        // check left
        if (myMaze[theY][theX - 1].isEmpty()) {
            count++;
        }

        // check right
        if (myMaze[theY][theX + 1].isEmpty()) {
            count++;
        }

        // check bottom
        if (myMaze[theY + 1][theX].isEmpty()) {
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
        if (myMaze[theY - 1][theX].hasWall()) {
            count++;
        }

        // check left
        if (myMaze[theY][theX - 1].hasWall()) {
            count++;
        }

        // check right
        if (myMaze[theY][theX + 1].hasWall()) {
            count++;
        }

        // check bottom
        if (myMaze[theY + 1][theX].hasWall()) {
            count++;
        }

        return count;
    }
}