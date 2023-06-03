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
    static final double ROOM_LEFT_OR_RIGHT_CHANCE = 0.50;
    static final double ENEMY_CHANCE = 0.40;
    static final double POTION_CHANCE = 0.30;
    static final double PILLAR_CHANCE = 0.15;
    static final double PIT_CHANCE = 0.20;
    static final Random rand = new Random();

    static Room[][] maze;
    static int mazeWidth;
    static int mazeHeight;
    static int entranceX;
    static int entranceY;
    static int exitX;
    static int exitY; // !!! make all fields private or protected?
    static double maxRoomBranchOffChance;
    static int numberOfEmptyRooms = 0;

    abstract public Dungeon buildDungeon();

    protected Queue<Monster> readMonsters(final String difficulty) {
        Queue<Monster> unplacedMonsters = new LinkedList<>();

        SQLiteDataSource ds = null;

        try {
            ds = new SQLiteDataSource();
            ds.setUrl("jdbc:sqlite:Monster_Database.db");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

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
        maze = theMaze;
    }

    protected void setMazeWidth(final int theMazeWidth) {
        mazeWidth = theMazeWidth;
    }

    protected void setMazeHeight(final int theMazeHeight) {
        mazeHeight = theMazeHeight;
    }

    protected void setMaxBranchOffChance(final double maxBranchOffChance) {
        maxRoomBranchOffChance = maxBranchOffChance;
    }


    protected void fillWithWalls() {
        numberOfEmptyRooms = 0;
        for (int y = 0; y < mazeHeight; y++) {
            for (int x = 0; x < mazeWidth; x++) {
                maze[y][x] = new Room(y, x);
                maze[y][x].placeWall();
            }
        }
    }


    protected void fillWithEmptyRooms() {
        numberOfEmptyRooms = 0; // reset the number of empty rooms

        fillWithEmptyRooms(mazeWidth /2, mazeWidth /2, maxRoomBranchOffChance); // start generating rooms from the center

        int numberOfRooms = (mazeWidth -2) * (mazeHeight -2);
        double roomPercentage = (double) numberOfEmptyRooms / numberOfRooms;

        if (roomPercentage < 0.80 || roomPercentage > 0.85) {
            fillWithWalls();
            fillWithEmptyRooms(); // try again with the original branch off chance
        }

//        printThis(); // DEBUG METHOD
    }

//    private void printThis() { // DEBUG METHOD
//        for (int y = 0; y < myMazeHeight; y++) {
//            System.out.println();
//            for (int x = 0; x < myMazeWidth; x++) {
//                System.out.print(myMaze[y][x] + "  ");
//            }
//        }
//        System.out.println();
//        System.out.println("---------------------------------------------------------------------");
//        System.out.println();
//    }

    /**
     * Recursive helper method to fill the maze with empty rooms.
     *
     * @param theY the y coordinate of the current room
     * @param theX the x coordinate of the current room
     */
    private static void fillWithEmptyRooms(final int theY, final int theX, double roomBranchOffChance) {

        Room room = maze[theY][theX];

        // base case
        if (!withinBounds(theY, theX)) return;

        // find the direction the path is getting generated in
        Direction traversalDirection = findTraversalDirection(theY, theX);

        room.removeWall();
        numberOfEmptyRooms++;

        // leave a chance for paths of empty rooms to branch off
        if (Math.random() < roomBranchOffChance) {

            roomBranchOffChance -= 0.03; // branch less and less frequently in the future

            // 50% chance for a room to branch off to the left or right
            if (Math.random() < ROOM_LEFT_OR_RIGHT_CHANCE) { // branch right
                if (traversalDirection == Direction.NORTH) {
                    fillWithEmptyRooms(theY, theX+1, roomBranchOffChance);
                } else if (traversalDirection == Direction.EAST) {
                    fillWithEmptyRooms(theY+1, theX, roomBranchOffChance);
                } else if (traversalDirection == Direction.SOUTH) {
                    fillWithEmptyRooms(theY, theX-1, roomBranchOffChance);
                } else { // traversalDirection == Direction.WEST
                    fillWithEmptyRooms(theY-1, theX, roomBranchOffChance);
                }

            } else {                                        // branch left
                if (traversalDirection == Direction.NORTH) {
                    fillWithEmptyRooms(theY, theX-1, roomBranchOffChance);
                } else if (traversalDirection == Direction.EAST) {
                    fillWithEmptyRooms(theY-1, theX, roomBranchOffChance);
                } else if (traversalDirection == Direction.SOUTH) {
                    fillWithEmptyRooms(theY, theX+1, roomBranchOffChance);
                } else { // traversalDirection == Direction.WEST
                    fillWithEmptyRooms(theY+1, theX, roomBranchOffChance);
                }
            }
        }

        // continue generating the path of empty rooms
        if (traversalDirection == Direction.NORTH) {
            fillWithEmptyRooms(theY-1, theX, roomBranchOffChance);
        } else if (traversalDirection == Direction.EAST) {
            fillWithEmptyRooms(theY, theX+1, roomBranchOffChance);
        } else if (traversalDirection == Direction.SOUTH) {
            fillWithEmptyRooms(theY+1, theX, roomBranchOffChance);
        } else { // traversalDirection == Direction.WEST
            fillWithEmptyRooms(theY, theX-1, roomBranchOffChance);
        }

//        roomBranchOffChance -= 0.03; // branch less and less frequently in the future
    }

    private static Direction findTraversalDirection(final int theY, final int theX) {
        Direction traversalDirection;

        if (maze[theY+1][theX].isEmpty()) { // check if the room below is empty
            traversalDirection = Direction.NORTH;
        } else if (maze[theY][theX-1].isEmpty()) { // check if the room to the left is empty
            traversalDirection = Direction.EAST;
        } else if (maze[theY-1][theX].isEmpty()) { // check if the room above is empty
            traversalDirection = Direction.SOUTH;
        } else { // the room to the right must be empty
            traversalDirection = Direction.WEST;
        }

        return traversalDirection;
    }

    private static boolean withinBounds(final int theY, final int theX) {
        Room room = maze[theY][theX];

        //inside maze and non visited room
        return theY >= 1 && theY < mazeHeight -1
                && theX >= 1 && theX < mazeWidth -1;
    }



    protected void fillWithObjects(final Queue<Monster> theUnplacedMonsters, final Set<Pillars> thePlacedPillars) {

        for (int y = 1; y < mazeHeight - 1; y++) { // skip over the edges of the dungeon
            for (int x = 1; x < mazeWidth - 1; x++) {

                Room room = maze[y][x];

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

        // confirm the maze contains all 4 pillars
//        if (thePlacedPillars.size() != 4) {
//            fillWithObjects(the);
//        }

        printObjects(); // debug
    }



    public void printObjects() {

        for (int i = 0; i < mazeHeight; i++) {
            System.out.println();
            for (int j = 0; j < mazeWidth; j++) {
                System.out.print(maze[i][j].toString() + "  ");
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
            x = rand.nextInt(mazeWidth);
            y = rand.nextInt(mazeHeight);

        } while (!maze[y][x].isEmpty());

        maze[y][x].placeEntrance();
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
            x = rand.nextInt(mazeWidth);
            y = rand.nextInt(mazeHeight);

        } while (!maze[y][x].isEmpty());

        maze[y][x].placeExit();
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
            x = rand.nextInt(mazeWidth);
            y = rand.nextInt(mazeHeight);

        } while (!maze[y][x].isEmpty());

        return new Point(x, y);
    }


    /**
     * Checks if the dungeon is traversable.
     *
     * @return true if the dungeon is traversable, false otherwise.
     */
    protected boolean isTraversable() {
        //before this method, make sure there are all 4 pillars present within the dungeon

        boolean isTraversable = traverse(entranceY, entranceX);
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

        Room room = maze[theY][theX];

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
        Room room = maze[theY][theX];

        //inside maze and non visited room
        return theY >= 1 && theY < mazeHeight -1
                && theX >= 1 && theX < mazeWidth -1
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
        if (maze[theY - 1][theX].isEmpty()) {
            count++;
        }

        // check left
        if (maze[theY][theX - 1].isEmpty()) {
            count++;
        }

        // check right
        if (maze[theY][theX + 1].isEmpty()) {
            count++;
        }

        // check bottom
        if (maze[theY + 1][theX].isEmpty()) {
            count++;
        }

        return count;
    }

}