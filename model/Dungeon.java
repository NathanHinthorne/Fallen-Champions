package model;

import java.awt.Point;
import java.util.*;

public class Dungeon {

    private static Dungeon myDungeon;
    private static Room[][] myMaze;
    private static int myMazeWidth;
    private static int myMazeHeight;
    private static int myHeroX;
    private static int myHeroY;
    private static int myEntranceX;
    private static int myEntranceY;
    private static int myExitX;
    private static int myExitY;
    private static Set<Pillars> myPlacedPillars;
    private static List<Monster> myUnplacedMonsters;

    private Dungeon() { } // prevent external instantiation

    public static class SmallDungeonBuilder extends DungeonBuilder { // put parameters to the Dungeon constructor inside here?
        private static final String DIFFICULTY = "Easy";
        private static final int DUNGEON_WIDTH = 5;
        private static final int DUNGEON_HEIGHT = 5;
        private static final double BRANCH_OFF_CHANCE = 0.50; // with decreasing branch chance: 0.50
        private static final double PILLAR_CHANCE = 0.20;

        @Override
        public Dungeon buildDungeon() {

            myDungeon = new Dungeon();
            myMaze = new Room[DUNGEON_HEIGHT+2][DUNGEON_WIDTH+2];

            // setup maze attributes
            myUnplacedMonsters = readMonsters(DIFFICULTY);
            myPlacedPillars = new HashSet<>();

            this.setMaze(myMaze);
            this.setMazeWidth(DUNGEON_WIDTH+2);
            this.setMazeHeight(DUNGEON_HEIGHT+2);
            this.setMaxBranchOffChance(BRANCH_OFF_CHANCE);
            this.setPillarChance(PILLAR_CHANCE);


            // step 1: fill the dungeon COMPLETELY with walls
            fillWithWalls();

            // step 2: randomly place empty rooms
            fillWithEmptyRooms();

            // step 3: fill empty rooms with objects
            fillWithObjects(myUnplacedMonsters, myPlacedPillars);

            // step 4: add entrance and exit
            Point entranceCoords = addEntrance(); //this line keeps running cuz it's not finding a room to place the entrance in
            Point exitCoords = addExit();

            entranceX = entranceCoords.x;
            entranceY = entranceCoords.y;
            exitX = exitCoords.x;
            exitY = exitCoords.y;

            // step 5: find a starting point for the hero
            Point heroCoords = findStartingPoint();

            myHeroX = heroCoords.x;
            myHeroY = heroCoords.y;

            // step 6: keep building dungeons until we find one that's traversable
//            while(!isTraversable()) {
//                buildDungeon();
//            }

            return myDungeon;
        }
    }


    public static class MediumDungeonBuilder extends DungeonBuilder {
        private static final String DIFFICULTY = "Medium";
        private static final int DUNGEON_WIDTH = 10;
        private static final int DUNGEON_HEIGHT = 10;
        private static final double BRANCH_OFF_CHANCE = 0.55; // with decreasing branch chance: 0.55
        private static final double PILLAR_CHANCE = 0.12;


        @Override
        public Dungeon buildDungeon() {

            myDungeon = new Dungeon();
            myMaze = new Room[DUNGEON_HEIGHT+2][DUNGEON_WIDTH+2];

            // setup maze attributes
            myUnplacedMonsters = readMonsters(DIFFICULTY);
            myPlacedPillars = new HashSet<>();

            this.setMaze(myMaze);
            this.setMazeWidth(DUNGEON_WIDTH+2);
            this.setMazeHeight(DUNGEON_HEIGHT+2);
            this.setMaxBranchOffChance(BRANCH_OFF_CHANCE);
            this.setPillarChance(PILLAR_CHANCE);


            // step 1: fill the dungeon COMPLETELY with walls
            fillWithWalls();

            // step 2: randomly place empty rooms
            fillWithEmptyRooms();

            // step 3: fill empty rooms with objects
            fillWithObjects(myUnplacedMonsters, myPlacedPillars);

            // step 4: add entrance and exit
            Point entranceCoords = addEntrance();
            Point exitCoords = addExit();

            entranceX = entranceCoords.x;
            entranceY = entranceCoords.y;
            exitX = exitCoords.x;
            exitY = exitCoords.y;

            // step 5: find a starting point for the hero
            Point heroCoords = findStartingPoint();

            myHeroX = heroCoords.x;
            myHeroY = heroCoords.y;

            // step 6: keep building dungeons until we find one that's traversable (NOT NEEDED?)
//            while(!isTraversable()) {
//                buildDungeon();
//            }

            return myDungeon;
        }
    }
    public static class LargeDungeonBuilder extends DungeonBuilder {
        private static final String DIFFICULTY = "Hard";
        private static final int DUNGEON_WIDTH = 15;
        private static final int DUNGEON_HEIGHT = 15;
        private static final double BRANCH_OFF_CHANCE = 0.60; // with decreasing branch chance: 0.60
        private static final double PILLAR_CHANCE = 0.10;

        @Override
        public Dungeon buildDungeon() {

            myDungeon = new Dungeon();
            myMaze = new Room[DUNGEON_HEIGHT+2][DUNGEON_WIDTH+2];

            // setup maze attributes
            myUnplacedMonsters = readMonsters(DIFFICULTY);
            myPlacedPillars = new HashSet<>();

            this.setMaze(myMaze);
            this.setMazeWidth(DUNGEON_WIDTH+2);
            this.setMazeHeight(DUNGEON_HEIGHT+2);
            this.setMaxBranchOffChance(BRANCH_OFF_CHANCE);
            this.setPillarChance(PILLAR_CHANCE);

            // step 1: fill the dungeon COMPLETELY with walls
            fillWithWalls();

            // step 2: randomly place empty rooms
            fillWithEmptyRooms();

            // step 3: fill empty rooms with objects
            fillWithObjects(myUnplacedMonsters, myPlacedPillars);

            // step 4: add entrance and exit
            Point entranceCoords = addEntrance();
            Point exitCoords = addExit();

            entranceX = entranceCoords.x;
            entranceY = entranceCoords.y;
            exitX = exitCoords.x;
            exitY = exitCoords.y;

            // step 5: find a starting point for the hero
            Point heroCoords = findStartingPoint();

            myHeroX = heroCoords.x;
            myHeroY = heroCoords.y;

            // step 6: keep building dungeons until we find one that's traversable
//            while(!isTraversable()) {
//                buildDungeon();
//            }

            return myDungeon;
        }
    }



    // for inner classes to use (if needed)
    private Room[][] getMaze() {
        return myMaze;
    }
    private Dungeon getDungeon() { return myDungeon; }

    public boolean heroIsTouchingMonster() {
        return myMaze[myHeroY][myHeroX].hasMonster();
    }


    // a method in the view will check for keyboard inputs
    // once triggered, it will tell the controller to call this method
    /**
     * Moves the player in the given direction.
     *
     * @param dir the direction to move the player
     */
    public static void playerMove(final Direction dir) {
        myMaze[myHeroX][myHeroY].removeHero();

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
        myMaze[myHeroX][myHeroY].placeHero();
    }

    /**
     * Returns a 3x3 grid of rooms centered on the hero.
     *
     * @return a 3x3 grid of rooms centered on the hero
     */
    public static String getView() { //! return a string instead?

        StringBuilder sb = new StringBuilder();

        for (int x = myHeroX - 1; x <= myHeroX + 1; x++) {
            for (int y = myHeroY - 1; y <= myHeroY + 1; y++) {
                sb.append(myMaze[x][y].toString());
            }
        }
        return sb.toString();
    }

    //only call when using vision potion
    /**
     * Returns a 7x7 grid of rooms centered on the hero.
     *
     * @return a 7x7 grid of rooms centered on the hero
     */
    public static String getExpandedView() {

        StringBuilder sb = new StringBuilder();

        for (int x = myHeroX - 3; x <= myHeroX + 3; x++) {
            for (int y = myHeroY - 3; y <= myHeroY + 3; y++) {
                sb.append(myMaze[x][y].toString());
            }
        }
        return sb.toString();
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

        for (int i = 0; i < myMazeHeight; i++) {
            sb.append("\n");
            for (int j = 0; j < myMazeWidth; j++) {
                sb.append(myMaze[i][j].toString());
                sb.append("  ");
            }
        }
        return sb.toString();
    }
}
