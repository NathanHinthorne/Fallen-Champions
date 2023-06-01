package model;

import org.sqlite.SQLiteDataSource;

import java.awt.Point;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public abstract class DungeonBuilder {

    // include as parameters to buildDungeon if we want these to change with difficulty levels
    static final double ROOM_BRANCH_OFF_CHANCE = 0.40;
    static final double ROOM_LEFT_OR_RIGHT_CHANCE = 0.50;
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

    protected Queue<Monster> readMonsters(final String difficulty) {
        Queue<Monster> unplacedMonsters = new LinkedList<>();

        //TODO access SQLite and fill up the queue
        // make sure to access the correct table containing the monsters required for EASY difficulty

        SQLiteDataSource ds = null;

        try {
            ds = new SQLiteDataSource();
            ds.setUrl("jdbc:sqlite:Monster_Database.db");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        System.out.println("Opened database successfully");

        String query = "SELECT * FROM " + difficulty;

        try (Connection conn = ds.getConnection();
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(query);

            while ( rs.next() ) {
                String monsterType = rs.getString( "TYPE" );

                if(monsterType.equals("GREMLIN")) {
                    unplacedMonsters.add(new Monster_Gremlin());

                } else if (monsterType.equals("OGRE")) {
                    unplacedMonsters.add(new Monster_Ogre());

                } else if (monsterType.equals("SKELETON")) {
                    unplacedMonsters.add(new Monster_Skeleton());

                } else if (monsterType.equals("WARLOCK")) {
                    unplacedMonsters.add(new Monster_Warlock());

                } else if (monsterType.equals("BOSS")) {
                    unplacedMonsters.add(new Monster_Boss());

                } else {
                    System.out.println("Invalid monster type");
                    System.exit(0);
                }

            }
        } catch ( SQLException e ) {
            System.out.println("Could not access database");
            e.printStackTrace();
            System.exit( 0 );
        }

        return unplacedMonsters;
    }

    protected void setMaze(final Room[][] theMaze) {
        myMaze = theMaze;
    }

    protected void setMazeWidth(final int theMazeWidth) {
        myMazeWidth = theMazeWidth;
    }

    protected void setMazeHeight(final int theMazeHeight) {
        myMazeHeight = theMazeHeight;
    }


    protected void fillWithWalls() {
        for (int y = 0; y < myMazeHeight; y++) {
            for (int x = 0; x < myMazeWidth; x++) {
                myMaze[y][x] = new Room(y, x);
                myMaze[y][x].placeWall();
            }
        }
    }


    protected void fillWithEmptyRooms() {
        myMaze[1][myMazeWidth/2].removeWall(); // make sure the room near the  is empty
        fillWithEmptyRooms(myMazeWidth/2-1, myMazeWidth/2); // start generating rooms from the

        for (int y = 0; y < myMazeHeight; y++) {
            System.out.println();
            for (int x = 0; x < myMazeWidth; x++) {
                System.out.print(myMaze[y][x] + "  ");
            }
        }
    }

    /**
     * Recursive helper method to fill the maze with empty rooms.
     *
     * @param theY the y coordinate of the current room
     * @param theX the x coordinate of the current room
     */
    private static void fillWithEmptyRooms(final int theY, final int theX) {

        Room room = myMaze[theY][theX];

        // base case
        if (!withinBounds(theY, theX)) return;

        // find the direction the path is getting generated in
        Direction traversalDirection = findTraversalDirection(theY, theX);

        room.removeWall();
//        System.out.println("DEBUG_PLACEROOMS - placed empty room at " + theX + ", " + theY);

        // leave a chance for paths of empty rooms to branch off
        if (Math.random() < ROOM_BRANCH_OFF_CHANCE) {

            // 50% chance for a room to branch off to the left or right
            if (Math.random() < ROOM_LEFT_OR_RIGHT_CHANCE) { // branch right
                if (traversalDirection == Direction.NORTH) {
                    fillWithEmptyRooms(theY, theX+1);
                } else if (traversalDirection == Direction.EAST) {
                    fillWithEmptyRooms(theY+1, theX);
                } else if (traversalDirection == Direction.SOUTH) {
                    fillWithEmptyRooms(theY, theX-1);
                } else { // traversalDirection == Direction.WEST
                    fillWithEmptyRooms(theY-1, theX);
                }

            } else {                                        // branch left
                if (traversalDirection == Direction.NORTH) {
                    fillWithEmptyRooms(theY, theX-1);
                } else if (traversalDirection == Direction.EAST) {
                    fillWithEmptyRooms(theY-1, theX);
                } else if (traversalDirection == Direction.SOUTH) {
                    fillWithEmptyRooms(theY, theX+1);
                } else { // traversalDirection == Direction.WEST
                    fillWithEmptyRooms(theY+1, theX);
                }
            }
        }

        // continue generating the path of empty rooms
        if (traversalDirection == Direction.NORTH) {
            fillWithEmptyRooms(theY-1, theX);
        } else if (traversalDirection == Direction.EAST) {
            fillWithEmptyRooms(theY, theX+1);
        } else if (traversalDirection == Direction.SOUTH) {
            fillWithEmptyRooms(theY+1, theX);
        } else { // traversalDirection == Direction.WEST
            fillWithEmptyRooms(theY, theX-1);
        }

//        System.out.println();
//        for (int y = 0; y < myMazeHeight; y++) {
//            System.out.println();
//            for (int x = 0; x < myMazeWidth; x++) {
//                System.out.print(myMaze[y][x] + "  ");
//            }
//        }

    }

    private static Direction findTraversalDirection(final int theY, final int theX) {
        Direction traversalDirection;

        if (myMaze[theY+1][theX].isEmpty()) { // check if the room below is empty
            traversalDirection = Direction.NORTH;
        } else if (myMaze[theY][theX-1].isEmpty()) { // check if the room to the left is empty
            traversalDirection = Direction.EAST;
        } else if (myMaze[theY-1][theX].isEmpty()) { // check if the room above is empty
            traversalDirection = Direction.SOUTH;
        } else { // the room to the right must be empty
            traversalDirection = Direction.WEST;
        }

        return traversalDirection;
    }

    private static boolean withinBounds(final int theY, final int theX) {
        Room room = myMaze[theY][theX];

        //inside maze and non visited room
        return theY >= 1 && theY < myMazeHeight-1
                && theX >= 1 && theX < myMazeWidth-1;
    }



    protected void fillWithObjects(final Queue<Monster> theUnplacedMonsters, final Set<Pillars> thePlacedPillars) {

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
        //before this method, make sure there are all 4 pillars present within the dungeon

        boolean isTraversable = traverse(myEntranceY, myEntranceX);
        return isTraversable;
    }

    /**
     * Recursive method to check if the dungeon is traversable.
     *
     * @param theY the y coordinate of the room to check
     * @param theX the x coordinate of the room to check
     * @return true if the dungeon is traversable, false otherwise.
     */
    private static boolean traverse(final int theY, final int theX) {

        Room room = myMaze[theY][theX];

        boolean success = false;

//        System.out.println("DEBUG_ISTRAVERSABLE - tried to move to " + theX + ", " + theY);
        if (validMove(theY, theX)) {

            // base case
            if (room.hasExit())
                return true;

            // not at exit so need to try other directions
            success = traverse(theY+1, theX); //down
            if (!success)
                success = traverse(theY, theX+1); //right
            if (!success)
                success = traverse(theY-1, theX); //up
            if (!success)
                success = traverse(theY, theX-1); //left
        }

        return success;
    }

    private static boolean validMove(final int theY, final int theX) {
        Room room = myMaze[theY][theX];

        //inside maze and non visited room
        return theY >= 1 && theY < myMazeHeight-1
                && theX >= 1 && theX < myMazeWidth-1
                && !room.hasWall();
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

}