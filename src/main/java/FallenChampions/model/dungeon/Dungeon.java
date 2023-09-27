package FallenChampions.model.dungeon;

import FallenChampions.controller.DatabaseInitializer;
import FallenChampions.model.potions.Potion;
import FallenChampions.model.characters.monsters.Monster;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * A dungeon is a collection of rooms that the player can navigate through.
 *
 * @author Nathan Hinthorne
 * @version 1.0
 */
public class Dungeon implements java.io.Serializable {

    /**
     * The random number generator
     */
    private final Random RANDOM = new Random();

    /**
     * Spacing between rooms in the dungeon
     */
    private static final String ROOM_SPACING = "  ";

    /**
     * Padding for the dungeon
     */
    private static final String PADDING = " ";

    /**
     * the dungeon's rooms
     */
    private Room[][] myMaze;

    /**
     * the dungeon's width
     */
    private int myMazeWidth;

    /**
     * the dungeon's height
     */
    private int myMazeHeight;

    private int myHeroX;
    private int myHeroY;

    /**
     * the dungeon's difficulty
     */
    private Difficulty myDifficulty;

    /**
     * The list of monsters that have not been placed in the dungeon.
     */
    private List<Monster> myUnplacedMonsters;


    // enforce the usage of the builder for object construction and discourage direct instantiation
    protected Dungeon() { }


    /**
     * Builds a small dungeon with the given parameters.
     */
    public static class SmallDungeonBuilder extends DungeonBuilder {

        /**
         * the dungeon's width
         */
        private static final int MAZE_WIDTH = 7;

        /**
         * the dungeon's height
         */
        private static final int MAZE_HEIGHT = 7;

        /**
         * the dungeon's branch off chance
         */
        private static final double MAX_BRANCH_OFF_CHANCE = 0.53; // with decreasing branch chance: 0.53

        /**
         * the dungeon's chance to place a pillar in a room
         */
        private static final double PILLAR_CHANCE = 0.20;

        /**
         * the dungeon's chance to place an enemy in a room
         */
        public static final double ENEMY_CHANCE = 0.35;

        /**
         * the dungeon's chance to place a potion in a room
         */
        public static final double POTION_CHANCE = 0.25;

        /**
         * the dungeon's chance to place a pit in a room
         */
        public static final double PIT_CHANCE = 0.00;


        /**
         * helper method for building a small dungeon
         * @return a small dungeon
         */
        public Dungeon buildDungeon() {

            return super.buildDungeon(Difficulty.EASY, MAZE_WIDTH, MAZE_HEIGHT,
                                    MAX_BRANCH_OFF_CHANCE, PILLAR_CHANCE, ENEMY_CHANCE,
                                    POTION_CHANCE, PIT_CHANCE);
        }
    }

    /**
     * Builds a medium dungeon with the given parameters
     */
    public static class MediumDungeonBuilder extends DungeonBuilder {

        /**
         * the dungeon's width
         */
        private static final int MAZE_WIDTH = 10;

        /**
         * the dungeon's height
         */
        private static final int MAZE_HEIGHT = 10;

        /**
         * the dungeon's branch off chance
         */
        private static final double MAX_BRANCH_OFF_CHANCE = 0.55; // with decreasing branch chance: 0.55

        /**
         * the dungeon's chance to place a pillar in a room
         */
        private static final double PILLAR_CHANCE = 0.12;

        /**
         * the dungeon's chance to place an enemy in a room
         */
        public static final double ENEMY_CHANCE = 0.40;

        /**
         * the dungeon's chance to place a potion in a room
         */
        public static final double POTION_CHANCE = 0.30;

        /**
         * the dungeon's chance to place a pit in a room
         */
        public static final double PIT_CHANCE = 0.20;

        /**
         * helper method for building a medium dungeon
         * @return a medium dungeon
         */
        public Dungeon buildDungeon() {

            return super.buildDungeon(Difficulty.MEDIUM, MAZE_WIDTH, MAZE_HEIGHT,
                    MAX_BRANCH_OFF_CHANCE, PILLAR_CHANCE, ENEMY_CHANCE, POTION_CHANCE, PIT_CHANCE);
        }
    }
    public static class LargeDungeonBuilder extends DungeonBuilder {
        /**
         * the dungeon's difficulty
         */
        private static final String DIFFICULTY = "Hard";

        /**
         * the dungeon's width
         */
        private static final int MAZE_WIDTH = 15;

        /**
         * the dungeon's height
         */
        private static final int MAZE_HEIGHT = 15;

        /**
         * the dungeon's branch off chance
         */
        private static final double MAX_BRANCH_OFF_CHANCE = 0.58; // with decreasing branch chance: 0.58

        /**
         * the dungeon's chance to place a pillar in a room
         */
        private static final double PILLAR_CHANCE = 0.035;

        /**
         * the dungeon's chance to place an enemy in a room
         */
        public static final double ENEMY_CHANCE = 0.3;

        /**
         * the dungeon's chance to place a potion in a room
         */
        public static final double POTION_CHANCE = 0.25;

        /**
         * the dungeon's chance to place a pit in a room
         */
        public static final double PIT_CHANCE = 0.15;


        /**
         * helper method for building a large dungeon
         * @return a large dungeon
         */
        public Dungeon buildDungeon() {

            return super.buildDungeon(Difficulty.HARD, MAZE_WIDTH, MAZE_HEIGHT,
                    MAX_BRANCH_OFF_CHANCE, PILLAR_CHANCE, ENEMY_CHANCE, POTION_CHANCE, PIT_CHANCE);
        }
    }

    /**
     * checks if the hero is touching a monster
     * @return true if the hero is touching a monster, false otherwise
     */
    public boolean heroIsTouchingMonster() {
        return myMaze[myHeroY][myHeroX].hasMonster();
    }

    /**
     * checks if the hero is touching an exit
     * @return true if the hero is touching an exit, false otherwise
     */
    public boolean heroIsTouchingExit() {
        return myMaze[myHeroY][myHeroX].hasExit();
    }

    /**
     * checks if the hero is touching a wall
     * @return true if the hero is touching a wall, false otherwise
     */
    public boolean heroIsTouchingWall() {
        return myMaze[myHeroY][myHeroX].hasWall();
    }

    /**
     * checks if the hero is touching a pit
     * @return true if the hero is touching a pit, false otherwise
     */
    public boolean heroIsTouchingPit() {
        return myMaze[myHeroY][myHeroX].hasPit();
    }

    /**
     * checks if the hero is touching a potion
     * @return true if the hero is touching a potion, false otherwise
     */
    public boolean heroIsTouchingPotion() {
        return myMaze[myHeroY][myHeroX].hasPotion();
    }

    /**
     * checks if the hero is touching a pillar
     * @return true if the hero is touching a pillar, false otherwise
     */
    public boolean heroIsTouchingPillar() {
        return myMaze[myHeroY][myHeroX].hasPillar();
    }

    /**
     * gets the potion the hero is touching (if any)
     * @return the potion the hero is touching (if any)
     */
    public Potion getPotion() {
        return myMaze[myHeroY][myHeroX].getPotion();
    }

    /**
     * gets the pillar the hero is touching (if any)
     * @return the pillar the hero is touching (if any)
     */
    public Pillars getPillar() {
        return myMaze[myHeroY][myHeroX].getPillar();
    }

    /**
     * gets the pit the hero is touching (if any)
     * @return the pit the hero is touching (if any)
     */
    public Pit getPit() {
        return myMaze[myHeroY][myHeroX].getPit();
    }

    /**
     * gets the monster the hero is touching (if any)
     * @return the monster the hero is touching (if any)
     */
    public Monster getMonster() {
        return myMaze[myHeroY][myHeroX].getMonster();
    }

    /**
     * removes the monster the hero is touching (if any)
     */
    public void removeMonster() {
        myMaze[myHeroY][myHeroX].removeMonster();
    }

    /**
     * removes the potion the hero is touching (if any)
     */
    public void removePotion() {
        myMaze[myHeroY][myHeroX].removePotion();
    }

    /**
     * removes the pillar the hero is touching (if any)
     */
    public void removePillar() {
        myMaze[myHeroY][myHeroX].removePillar();
    }

    /**
     * gets the current difficulty of the dungeon
     * @return the current difficulty of the dungeon
     */
    public Difficulty getDifficulty() {
        return myDifficulty;
    }

    /**
     * reveals the contents of every room in the dungeon
     */
    public void makeRoomsVisible() {
        for (int i = 0; i < myMazeHeight; i++) {
            for (int j = 0; j < myMazeWidth; j++) {
                myMaze[i][j].setVisible(true);
            }
        }
    }

    /**
     * hides the contents of every room in the dungeon (except the ones the hero has visited)
     */
    public void makeRoomsInvisible() {
        for (int y = 0; y < myMazeHeight; y++) {
            for (int x = 0; x < myMazeWidth; x++) {
                if (!myMaze[y][x].wasVisited()) {
                    myMaze[y][x].setVisible(false);
                }
            }
        }
    }


    /**
     * Moves the player in the given direction.
     *
     * @param dir the direction to move the player
     * @return true if the player moved, false otherwise
     */
    public boolean playerMove(final Direction dir) {
        boolean hasMoved;

        Room oldRoom = myMaze[myHeroY][myHeroX];
        int oldHeroX = myHeroX;
        int oldHeroY = myHeroY;

        if (dir == Direction.NORTH && !myMaze[myHeroY-1][myHeroX].hasWall()) {
            myHeroY--;
        } else if (dir == Direction.EAST && !myMaze[myHeroY][myHeroX+1].hasWall()) {
            myHeroX++;
        } else if (dir == Direction.SOUTH && !myMaze[myHeroY+1][myHeroX].hasWall()) {
            myHeroY++;
        } else if (dir == Direction.WEST && !myMaze[myHeroY][myHeroX-1].hasWall()) {
            myHeroX--;
        }

        if (oldHeroX == myHeroX && oldHeroY == myHeroY) {
            hasMoved = false;
        } else {
            Room newRoom = myMaze[myHeroY][myHeroX];

            oldRoom.removeHero();
            oldRoom.setVisited(true);
            newRoom.placeHero();
            newRoom.setVisible(true);
            hasMoved = true;
        }

        return hasMoved;
    }

    /**
     * spawns a monster somewhere in the dungeon
     */
    public void spawnMonster() {
        Random rand = new Random();
        int x = rand.nextInt(myMazeWidth);
        int y = rand.nextInt(myMazeHeight);

        while (myMaze[y][x].hasWall() || myMaze[y][x].hasPit() || myMaze[y][x].hasExit()
                || myMaze[y][x].hasPotion() || myMaze[y][x].hasPillar()) {

            x = rand.nextInt(myMazeWidth);
            y = rand.nextInt(myMazeHeight);
        }

        myMaze[y][x].placeMonster(myDifficulty);
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

        for (int y = 0; y < myMazeHeight; y++) {
            sb.append("\n" + PADDING);
            for (int x = 0; x < myMazeWidth; x++) {
                sb.append(myMaze[y][x].toString());
                sb.append(ROOM_SPACING);
            }
        }

        return sb.toString();
    }

    /**
     * sets the current rooms in the dungeon to the given 2D array
     * @param theMaze the 2D array of rooms to set the dungeon to
     */
    public void setMaze(Room[][] theMaze) {
        myMaze = theMaze;
    }

    /**
     * sets the width of the dungeon
     * @param theWidth the width of the dungeon
     */
    public void setMazeWidth(int theWidth) {
        myMazeWidth = theWidth;
    }

    /**
     * gets the width of the dungeon
     * @return the width of the dungeon
     */
    public int getMazeWidth() {
        return myMazeWidth;
    }

    /**
     * sets the height of the dungeon
     * @param theHeight the height of the dungeon
     */
    public void setMazeHeight(int theHeight) {
        myMazeHeight = theHeight;
    }

    /**
     * gets the height of the dungeon
     * @return the height of the dungeon
     */
    public int getMazeHeight() {
        return myMazeHeight;
    }

    /**
     * sets the x coordinate of the hero
     * @param theX the x coordinate of the hero
     */
    public void setHeroX(int theX) {
        myHeroX = theX;
    }

    /**
     * gets the x coordinate of the hero
     * @return the x coordinate of the hero
     */
    public int getHeroX() {
        return myHeroX;
    }

    /**
     * sets the y coordinate of the hero
     * @param theY the y coordinate of the hero
     */
    public void setHeroY(int theY) {
        myHeroY = theY;
    }

    /**
     * gets the y coordinate of the hero
     * @return the y coordinate of the hero
     */
    public int getHeroY() {
        return myHeroY;
    }

    /**
     * sets the difficulty of the dungeon (easy, medium, or hard)
     * @param theDifficulty the difficulty of the dungeon (easy, medium, or hard)
     */
    public void setDifficulty(final Difficulty theDifficulty) {
        myDifficulty = theDifficulty;
    }

    /**
     * gets the 2D array of rooms in the dungeon
     * @return the 2D array of rooms in the dungeon
     */
    public Room[][] getMaze() {
        return myMaze;
    }

    /**
     * gets the contents of the room above the hero
     */
    public void roomAbove() {
        myMaze[myHeroY-1][myHeroX].getContents();
    }

    /**
     * gets the contents of the room below the hero
     */
    public void roomBelow() {
        myMaze[myHeroY+1][myHeroX].getContents();
    }

    /**
     * gets the contents of the room to the left of the hero
     */
    public void roomLeft() {
        myMaze[myHeroY][myHeroX-1].getContents();
    }

    /**
     * gets the contents of the room to the right of the hero
     */
    public void roomRight() {
        myMaze[myHeroY][myHeroX+1].getContents();
    }


    public void moveHeroToRandomRoom() {
        boolean hasMoved;
        int randomDirIndex;
        Direction randomDir;

        do {
            randomDirIndex = RANDOM.nextInt(4);
            randomDir = Direction.values()[randomDirIndex];
            hasMoved = playerMove(randomDir);
            
        } while (!hasMoved);
    }
}
