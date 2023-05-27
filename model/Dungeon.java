package model;

import org.sqlite.SQLiteDataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.awt.Point;
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

    private Dungeon() { } // prevent external instantiation

    public static class SmallDungeonBuilder extends DungeonBuilder { // put parameters to the Dungeon constructor inside here?
        private static final Difficulty DIFFICULTY = Difficulty.EASY;
        private static final int DUNGEON_WIDTH = 10;
        private static final int DUNGEON_HEIGHT = 10;
        //TODO add more static fields if we want them to change with difficulty (like potion, monster chances, etc.)

        @Override
        public Dungeon buildDungeon() {
            if (dungeon != null) { // for singleton
                dungeon = new Dungeon();
            }
            maze = new Room[DUNGEON_HEIGHT][DUNGEON_WIDTH];

            // setup maze attributes
            myUnplacedMonsters = readMonsters();
            myPlacedPillars = new HashSet<Pillars>();

            this.setMaze(maze);
            this.setMazeWidth(DUNGEON_WIDTH);
            this.setMazeHeight(DUNGEON_HEIGHT);


            // step 1: fill the dungeon COMPLETELY with walls
            fillWithWalls();

            // step 2: randomly place empty rooms
            fillWithEmptyRooms();

            // step 3: fill empty rooms with objects
            fillWithObjects(myUnplacedMonsters, myPlacedPillars);

            // step 4: add entrance and exit
            Point entranceCoords = addEntrance();
            Point exitCoords = addExit();

            myEntranceX = entranceCoords.x;
            myEntranceY = entranceCoords.y;
            myExitX = exitCoords.x;
            myExitY = exitCoords.y;

            // step 5: find a starting point for the hero
            Point heroCoords = findStartingPoint();

            myHeroX = heroCoords.x;
            myHeroY = heroCoords.y;

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
            // make sure to access the correct table containing the monsters required for EASY difficulty

            SQLiteDataSource ds = null;

            try {
                ds = new SQLiteDataSource();
                ds.setUrl("jdbc:sqlite:monsters.db");
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }
            System.out.println("Opened database successfully");

            // create a table (not needed)
//            String query = "CREATE TABLE IF NOT EXISTS monsters ( " +
//                    "TYPE NOT NULL )";
//
//            try (Connection conn = ds.getConnection();
//                 Statement stmt = conn.createStatement();) {
//                int rv = stmt.executeUpdate(query);
//                System.out.println("database executed successfully: " + rv);
//
//            } catch (SQLException e) {
//                e.printStackTrace();
//                System.exit(0);
//            }

            // Temporary to test DB functionality
            String query1 = "INSERT INTO monsters ( TYPE ) VALUES ( myMonster )";
            String query2 = "INSERT INTO questions ( TYPE ) VALUES ( myMonster )";

            try (Connection conn = ds.getConnection();
                 Statement stmt = conn.createStatement();) {
                int rv = stmt.executeUpdate(query1);
                System.out.println("database executed successfully: " + rv);

                rv = stmt.executeUpdate(query2);
                System.out.println("database executed successfully: " + rv);
            } catch (SQLException e) {
                e.printStackTrace();
                System.exit(0);
            }

            query = "SELECT * FROM monsters";

            try ( Connection conn = ds.getConnection();
                  Statement stmt = conn.createStatement(); ) {

                ResultSet rs = stmt.executeQuery(query);

                while ( rs.next() ) {
                    String monster = rs.getString( "TYPE" );

                    System.out.println( "Result: Monster = " + monster );
                }
            } catch ( SQLException e ) {
                e.printStackTrace();
                System.exit( 0 );
            }

            return unplacedMonsters;
        }

    }
    public static class MediumDungeonBuilder extends DungeonBuilder {
        private static final int DUNGEON_WIDTH = 20;
        private static final int DUNGEON_HEIGHT = 20;

        //TODO add more static fields if we want them to change with difficulty (like potion, monster chances, etc.)

        @Override
        public Dungeon buildDungeon() {
            if (dungeon != null) { // for singleton
                dungeon = new Dungeon();
            }



            return dungeon;
        }

        @Override
        public Queue<Monster> readMonsters() {
            Queue<Monster> unplacedMonsters = new LinkedList<>();

            //TODO access SQLite and fill up the queue
            // make sure to access the correct table containing the monsters required for MEDIUM difficulty

            return unplacedMonsters;
        }
    }
    public static class LargeDungeonBuilder extends DungeonBuilder {
        private static final int DUNGEON_WIDTH = 30;
        private static final int DUNGEON_HEIGHT = 30;

        //TODO add more static fields if we want them to change with difficulty (like potion, monster chances, etc.)

        @Override
        public Dungeon buildDungeon() {
            if (dungeon != null) { // for singleton
                dungeon = new Dungeon();
            }



            return dungeon;
        }

        @Override
        public Queue<Monster> readMonsters() {
            Queue<Monster> unplacedMonsters = new LinkedList<>();

            //TODO access SQLite and fill up the queue
            // make sure to access the correct table containing the monsters required for HARD difficulty

            return unplacedMonsters;
        }
    }

    // for inner classes to use (if needed)
    private Room[][] getMaze() {
        return maze;
    }
    private Dungeon getDungeon() { return dungeon; }


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
