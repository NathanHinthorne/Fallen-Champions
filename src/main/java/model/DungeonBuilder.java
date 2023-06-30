package model;

import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * A dungeon builder is responsible for building a dungeon.
 *
 * @author Nathan Hinthorne
 * @version 1.0
 */
public abstract class DungeonBuilder implements java.io.Serializable {

    /**
     * The chance that a room will choose the left branch off direction when generating the maze.
     */
    public static final double ROOM_LEFT_OR_RIGHT_CHANCE = 0.50;

    /**
     * a random number generator for the dungeon builder
     */
    public static final Random rand = new Random();

    /**
     * The set of pillars that have been placed in the dungeon.
     */
    private Set<Pillars> myPlacedPillars;

    /**
     * The list of monsters that have not been placed in the dungeon.
     */
    private List<Monster> myUnplacedMonsters;

    /**
     * The chance that a pillar will be placed in a room.
     */
    protected double myPillarChance;

    /**
     * The chance that a monster will be placed in a room.
     */
    protected double myMonsterChance;

    /**
     * The chance that a potion will be placed in a room.
     */
    protected double myPotionChance;

    /**
     * The chance that a pit will be placed in a room.
     */
    protected double myPitChance;

    /**
     * The rooms within the dungeon
     */
    private Room[][] myMaze;

    /**
     * the width of the dungeon
     */
    private int myMazeWidth;

    /**
     * the height of the dungeon
     */
    private int myMazeHeight;

    /**
     * the x coordinate of the hero's starting position
     */
    private int myHeroX;

    /**
     * the y coordinate of the hero's starting position
     */
    private int myHeroY;

    /**
     * the maximum chance that a room will branch off
     */
    protected double myMaxRoomBranchOffChance;

    /**
     * the number of empty rooms in the dungeon
     */
    protected int myNumberOfEmptyRooms;

    /**
     * the difficulty of the dungeon (easy, medium, or hard)
     */
    private Difficulty myDifficulty;

    // enforce the usage of the builder for object construction and discourage direct instantiation
    /**
     * Constructs a new dungeon builder.
     */
    protected DungeonBuilder() { }


    /**
     * does everything necessary to build the dungeon
     * @param theDifficulty the difficulty of the dungeon
     * @param theMazeWidth the width of the dungeon
     * @param theMazeHeight the height of the dungeon
     * @param theMaxBranchOffChance the maximum chance that a room will branch off
     * @param thePillarChance the chance that a pillar will be placed in a room
     * @param theMonsterChance the chance that a monster will be placed in a room
     * @param thePotionChance the chance that a potion will be placed in a room
     * @param thePitChance the chance that a pit will be placed in a room
     * @return the dungeon that was built
     */
    public Dungeon buildDungeon(final Difficulty theDifficulty, final int theMazeWidth, final int theMazeHeight,
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
        dungeon.setDifficulty(myDifficulty);

        return dungeon;
    }


    /**
     * restarts the dungeon room filling process
     */
    private void restartRoomFilling() {
//        System.out.println();
//        System.out.println("DEBUG: previous bad dungeon:");
//        debugPrintObjects();
//        System.out.println();

        myMaze = new Room[myMazeHeight + 2][myMazeWidth + 2];
        myPlacedPillars = new HashSet<>();
        myUnplacedMonsters = new LinkedList<>();

        readMonsters(myDifficulty);

        // step 1: fill the dungeon COMPLETELY with walls
        fillWithWalls();

        // step 2: randomly place empty rooms
        fillWithEmptyRooms();
    }

    /**
     * restarts the dungeon object placing process
     */
    private void restartObjectPlacing() {
//        System.out.println();
//        System.out.println("DEBUG: previous bad dungeon:");
//        debugPrintObjects();
//        System.out.println();

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

    /**
     * read the monsters from the database
     * @param theDifficulty the difficulty of the dungeon
     */
    private void readMonsters(final Difficulty theDifficulty) {

        SQLiteDataSource ds = null;

        try {
            ds = new SQLiteDataSource();
//            ds.setUrl("jdbc:sqlite:Monster_Database.db");
            ds.setUrl("jdbc:sqlite:" + getClass().getResource("/Monster_Database.db").getPath());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        String query = "SELECT * FROM " + theDifficulty.toString();

        try (Connection conn = ds.getConnection();
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(query);

            while ( rs.next() ) {
                String monsterType = rs.getString( "TYPE" );

                if(monsterType.equals("GREMLIN")) {
                    myUnplacedMonsters.add(new MonsterGremlin());

                } else if (monsterType.equals("OGRE")) {
                    myUnplacedMonsters.add(new MonsterOgre());

                } else if (monsterType.equals("SKELETON")) {
                    myUnplacedMonsters.add(new MonsterSkeleton());

                } else if (monsterType.equals("WARLOCK")) {
                    myUnplacedMonsters.add(new MonsterWarlock());

                } else if (monsterType.equals("SPIDER")) {
                    myUnplacedMonsters.add(new MonsterSpider());

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

    /**
     * fill the dungeon completely with walls
     */
    private void fillWithWalls() {
        myNumberOfEmptyRooms = 0;
        for (int y = 0; y < myMazeHeight; y++) {
            for (int x = 0; x < myMazeWidth; x++) {
                myMaze[y][x] = new Room(y, x);
                myMaze[y][x].placeWall();
            }
        }
    }

    /**
     * carves out a path of empty rooms from the center of the dungeon to form walkways
     */
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

    /**
     * Recursive helper method to fill the maze with empty rooms.
     *
     * @param theY the y coordinate of the current room
     * @param theX the x coordinate of the current room
     * @param roomBranchOffChance the chance for a path of empty rooms to branch off
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

    /**
     * helper method to find the direction the path of empty rooms is being generated in
     * @param theY
     * @param theX
     * @return
     */
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

    /**
     * helper method to check if the coordinates are within the maze
     * @param theY the y coordinate of the room being checked
     * @param theX the x coordinate of the room being checked
     * @return true if the coordinates are within the maze, false otherwise
     */
    private boolean withinBounds(final int theY, final int theX) {

        //inside maze and non visited room
        return theY >= 1 && theY < myMazeHeight - 1
                && theX >= 1 && theX < myMazeWidth - 1;
    }

    /**
     * fills the maze with objects
     */
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
//                        System.out.println("DEBUG: Placed a pillar at " + x + ", " + y);
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
    }


    /**
     * Adds starting coordinates for the hero
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
     * Adds an exit to the dungeon
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

}