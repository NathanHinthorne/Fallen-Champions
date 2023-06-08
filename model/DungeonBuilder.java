package model;

import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public abstract class DungeonBuilder implements java.io.Serializable {

    // include as parameters to buildDungeon if we want these to change with difficulty levels
    public static final double ROOM_LEFT_OR_RIGHT_CHANCE = 0.50;
    public static final Random rand = new Random();

    private Set<Pillars> myPlacedPillars;
    private List<Monster> myUnplacedMonsters;
    protected double myPillarChance;
    protected double myMonsterChance;
    protected double myPotionChance;
    protected double myPitChance;
    private Room[][] myMaze;
    private int myMazeWidth;
    private int myMazeHeight;
    private int myHeroX;
    private int myHeroY;
    protected double myMaxRoomBranchOffChance;
    protected int myNumberOfEmptyRooms;
    private String myDifficulty;

    // enforce the usage of the builder for object construction and discourage direct instantiation
    protected DungeonBuilder() { }


    public Dungeon buildDungeon(final String theDifficulty, final int theMazeWidth, final int theMazeHeight,
                                   final double theMaxBranchOffChance, final double thePillarChance,
                                   final double theMonsterChance, final double thePotionChance, final double thePitChance) {

        Dungeon dungeon = new Dungeon();
        myMazeWidth = theMazeWidth;
        myMazeHeight = theMazeHeight;
        myMaze = new Room[myMazeHeight + 2][myMazeWidth + 2];
        myPlacedPillars = new HashSet<>();
        myUnplacedMonsters = new LinkedList<>();
        myMaxRoomBranchOffChance = theMaxBranchOffChance;
        myPillarChance = thePillarChance;
        myMonsterChance = theMonsterChance;
        myPotionChance = thePotionChance;
        myPitChance = thePitChance;
        myDifficulty = theDifficulty;


        readMonsters(myDifficulty);

        // step 1: fill the dungeon COMPLETELY with walls
        fillWithWalls();

        // step 2: randomly place empty rooms
        fillWithEmptyRooms();

        // step 3: fill empty rooms with objects
        fillWithObjects();

        // step 4: add entrance and exit
        addEntrance();
        addExit();

        // step 5: keep building dungeons until we find one that's traversable
//            while(!isTraversable()) {
//                buildDungeon();
//            }

        dungeon.setMaze(myMaze);
        dungeon.setMazeWidth(myMazeWidth);
        dungeon.setMazeHeight(myMazeHeight);
        dungeon.setHeroX(myHeroX);
        dungeon.setHeroY(myHeroY);

        return dungeon;
    }

    private void restartRoomFilling() {
        System.out.println();
        System.out.println("DEBUG: previous bad dungeon:");
        debugPrintObjects();
        System.out.println();

        myMaze = new Room[myMazeHeight + 2][myMazeWidth + 2];
        myPlacedPillars = new HashSet<>();
        myUnplacedMonsters = new LinkedList<>();

        readMonsters(myDifficulty);

        // step 1: fill the dungeon COMPLETELY with walls
        fillWithWalls();

        // step 2: randomly place empty rooms
        fillWithEmptyRooms();
    }

    private void restartObjectPlacing() {
        System.out.println();
        System.out.println("DEBUG: previous bad dungeon:");
        debugPrintObjects();
        System.out.println();

        myMaze = new Room[myMazeHeight + 2][myMazeWidth + 2];
        myPlacedPillars = new HashSet<>();
        myUnplacedMonsters = new LinkedList<>();

        readMonsters(myDifficulty);

        // step 1: fill the dungeon COMPLETELY with walls
        fillWithWalls();

        // step 2: randomly place empty rooms
        fillWithEmptyRooms();

        // step 3: fill empty rooms with objects
        fillWithObjects();
    }

    private void readMonsters(final String difficulty) {

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
                    myUnplacedMonsters.add(new Monster_Gremlin());

                } else if (monsterType.equals("OGRE")) {
                    myUnplacedMonsters.add(new Monster_Ogre());

                } else if (monsterType.equals("SKELETON")) {
                    myUnplacedMonsters.add(new Monster_Skeleton());

                } else if (monsterType.equals("WARLOCK")) {
                    myUnplacedMonsters.add(new Monster_Warlock());

                } else if (monsterType.equals("BOSS")) {
                    myUnplacedMonsters.add(new Monster_Boss());

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

        Collections.shuffle(myUnplacedMonsters); // shuffle the monsters so they are placed randomly, regardless of type
    }

    private void fillWithWalls() {
        myNumberOfEmptyRooms = 0;
        for (int y = 0; y < myMazeHeight; y++) {
            for (int x = 0; x < myMazeWidth; x++) {
                myMaze[y][x] = new Room(y, x);
                myMaze[y][x].placeWall();
            }
        }
    }


    private void fillWithEmptyRooms() {
        myNumberOfEmptyRooms = 0; // reset the number of empty rooms

        fillWithEmptyRooms(myMazeWidth / 2, myMazeWidth / 2, myMaxRoomBranchOffChance); // start generating rooms from the center

        int numberOfRooms = (myMazeWidth -2) * (myMazeHeight -2);
        double roomPercentage = (double) myNumberOfEmptyRooms / numberOfRooms;

        if (roomPercentage < 0.55 || roomPercentage > 0.75) { // (this if statement should barely ever execute)
            restartRoomFilling(); // try again with the original branch off chance
        }

//        debugPrintRooms(); // DEBUG METHOD
    }

    private void debugPrintRooms() { // DEBUG METHOD
        System.out.println("---------------------------------------------------------------------");
        System.out.println("Number of empty rooms: " + myNumberOfEmptyRooms + " out of " + ((myMazeWidth -2) * (myMazeHeight -2)));

        for (int y = 0; y < myMazeHeight; y++) {
            System.out.println();
            for (int x = 0; x < myMazeWidth; x++) {
                System.out.print(myMaze[y][x] + "  ");
            }
        }
        System.out.println();
        System.out.println("---------------------------------------------------------------------");
        System.out.println();
    }

    /**
     * Recursive helper method to fill the maze with empty rooms.
     *
     * @param theY the y coordinate of the current room
     * @param theX the x coordinate of the current room
     */
    private void fillWithEmptyRooms(final int theY, final int theX, double roomBranchOffChance) {

        Room room = myMaze[theY][theX];

        // base case
        if (!withinBounds(theY, theX)) return;

        // find the direction the path is getting generated in
        Direction traversalDirection = findTraversalDirection(theY, theX);

        if (room.hasWall()) { // remember, we might be looking at a room that was already generated
            room.removeWall();
            myNumberOfEmptyRooms++;
        }

        // leave a chance for paths of empty rooms to branch off
        if (Math.random() < roomBranchOffChance) {

            roomBranchOffChance -= 0.05; // branch less and less frequently in the future (REMOVE VARIABLE IF GAPS ARE TOO BIG IN CENTER)

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
    }

    private Direction findTraversalDirection(final int theY, final int theX) {
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

    private boolean withinBounds(final int theY, final int theX) {

        //inside maze and non visited room
        return theY >= 1 && theY < myMazeHeight - 1
                && theX >= 1 && theX < myMazeWidth - 1;
    }



    private void fillWithObjects() {

        for (int y = 1; y < myMazeHeight - 1; y++) { // skip over the edges of the dungeon
            for (int x = 1; x < myMazeWidth - 1; x++) {

                Room room = myMaze[y][x];

                if (room.isEmpty()) { // provided the wall was removed, place items in the room

                    if (Math.random() < myMonsterChance) { //TODO limit the number of enemies in the dungeon - probably not
                        room.placeMonster(myUnplacedMonsters);             //TODO use a list to accomplish this?
                    }
                    if (Math.random() < myPotionChance) {
                        room.placePotion();
                    }
                    if (Math.random() < myPillarChance) {
                        System.out.println("DEBUG: Placed a pillar at " + x + ", " + y);
                        room.placePillar(myPlacedPillars);
                    }
                    if (Math.random() < myPitChance) {
                        room.placePit();
                    }
                }
            }
        }

        // confirm the maze contains all 4 pillars
        if (myPlacedPillars.size() != 4) {
            restartObjectPlacing();
        }

//        debugPrintObjects(); // debug
    }



    public void debugPrintObjects() {

        for (int i = 0; i < myMazeHeight; i++) {
            System.out.println();
            for (int j = 0; j < myMazeWidth; j++) {
                System.out.print(myMaze[i][j].toString() + "  ");
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
            x = rand.nextInt(myMazeWidth);
            y = rand.nextInt(myMazeHeight);

        } while (!myMaze[y][x].isEmpty());

        myMaze[y][x].placeHero();

        myHeroX = x;
        myHeroY = y;
    }

    /**
     * Adds an exit to the dungeon.
     */
    private void addExit() {
        int x;
        int y;

        // keep generating coords for an exit until we get a room that is empty
        do {
            x = rand.nextInt(myMazeWidth);
            y = rand.nextInt(myMazeHeight);

        } while (!myMaze[y][x].isEmpty());

        myMaze[y][x].placeExit();
    }


//    /**
//     * Checks if the dungeon is traversable.
//     *
//     * @return true if the dungeon is traversable, false otherwise.
//     */
//    private boolean isTraversable() {
//        //before this method, make sure there are all 4 pillars present within the dungeon
//
//        boolean isTraversable = traverse(myHeroY, myHeroX);
//        return isTraversable;
//    }

//    /**
//     * Recursive method to check if the dungeon is traversable.
//     *
//     * @param theY the y coordinate of the room to check
//     * @param theX the x coordinate of the room to check
//     * @return true if the dungeon is traversable, false otherwise.
//     */
//    private boolean traverse(final int theY, final int theX) {
//
//        Room room = myMaze[theY][theX];
//
//        boolean success = false;
//
////        System.out.println("DEBUG_ISTRAVERSABLE - tried to move to " + theX + ", " + theY);
//        if (validMove(theY, theX)) {
//
//            // base case
//            if (room.hasExit())
//                return true;
//
//            // not at exit so need to try other directions
//            success = traverse(theY+1, theX); //down
//            if (!success)
//                success = traverse(theY, theX+1); //right
//            if (!success)
//                success = traverse(theY-1, theX); //up
//            if (!success)
//                success = traverse(theY, theX-1); //left
//        }
//
//        return success;
//    }

//    private boolean validMove(final int theY, final int theX) {
//        Room room = myMaze[theY][theX];
//
//        //inside maze and non visited room
//        return theY >= 1 && theY < myMazeHeight -1
//                && theX >= 1 && theX < myMazeWidth -1
//                && !room.hasWall();
//    }


//    /**
//     * Determines the number of empty rooms adjacent (left/right/up/down) to the given room.
//     *
//     * @param theX the x coordinate of the room
//     * @param theY the y coordinate of the room
//     * @return the number of empty rooms adjacent to the given room
//     */
//    private int numberOfEmptyRooms(final int theX, final int theY) {
//
//        int count = 0;
//
//        // check top
//        if (myMaze[theY - 1][theX].isEmpty()) {
//            count++;
//        }
//
//        // check left
//        if (myMaze[theY][theX - 1].isEmpty()) {
//            count++;
//        }
//
//        // check right
//        if (myMaze[theY][theX + 1].isEmpty()) {
//            count++;
//        }
//
//        // check bottom
//        if (myMaze[theY + 1][theX].isEmpty()) {
//            count++;
//        }
//
//        return count;
//    }

}