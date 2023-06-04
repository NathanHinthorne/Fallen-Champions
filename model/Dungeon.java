package model;

import java.awt.Point;
import java.util.*;

public class Dungeon {

    private Room[][] myMaze;
    private int myMazeWidth;
    private int myMazeHeight;
    private int myHeroX;
    private int myHeroY;


    // enforce the usage of the builder for object construction and discourage direct instantiation
    protected Dungeon() { }


    public static class SmallDungeonBuilder extends DungeonBuilder {
        private static final String DIFFICULTY = "Easy";
        private static final int MAZE_WIDTH = 5;
        private static final int MAZE_HEIGHT = 5;
        private static final double MAX_BRANCH_OFF_CHANCE = 0.50; // with decreasing branch chance: 0.50
        private static final double PILLAR_CHANCE = 0.20;


        public Dungeon buildDungeon() {

            return super.buildDungeon(DIFFICULTY, MAZE_WIDTH, MAZE_HEIGHT,
                                            MAX_BRANCH_OFF_CHANCE, PILLAR_CHANCE);
        }
    }


    public static class MediumDungeonBuilder extends DungeonBuilder {
        private static final String DIFFICULTY = "Medium";
        private static final int MAZE_WIDTH = 10;
        private static final int MAZE_HEIGHT = 10;
        private static final double MAX_BRANCH_OFF_CHANCE = 0.55; // with decreasing branch chance: 0.55
        private static final double PILLAR_CHANCE = 0.12;

        public Dungeon buildDungeon() {

            return super.buildDungeon(DIFFICULTY, MAZE_WIDTH, MAZE_HEIGHT,
                    MAX_BRANCH_OFF_CHANCE, PILLAR_CHANCE);
        }
    }
    public static class LargeDungeonBuilder extends DungeonBuilder { // no static fields here. instance fields in DungeonBuilder and set them up in the constructor
        private static final String DIFFICULTY = "Hard";
        private static final int MAZE_WIDTH = 15;
        private static final int MAZE_HEIGHT = 15;
        private static final double MAX_BRANCH_OFF_CHANCE = 0.60; // with decreasing branch chance: 0.60
        private static final double PILLAR_CHANCE = 0.10;

        public Dungeon buildDungeon() {

            return super.buildDungeon(DIFFICULTY, MAZE_WIDTH, MAZE_HEIGHT,
                    MAX_BRANCH_OFF_CHANCE, PILLAR_CHANCE);
        }
    }



    // for inner classes to use (if needed)
    private Room[][] getMaze() {
        return myMaze;
    }


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
    public void playerMove(final Direction dir) {
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
    public String getView() { //! return a string instead?

        StringBuilder sb = new StringBuilder();

        for (int x = myHeroX - 1; x <= myHeroX + 1; x++) {
            sb.append("\n");
            for (int y = myHeroY - 1; y <= myHeroY + 1; y++) {
                sb.append(myMaze[x][y].toString());
                sb.append("  ");
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
    public String getExpandedView() {

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


    public void setMaze(Room[][] theMaze) {
        myMaze = theMaze;
    }
    public void setMazeWidth(int theWidth) {
        myMazeWidth = theWidth;
    }
    public void setMazeHeight(int theHeight) {
        myMazeHeight = theHeight;
    }
    public void setHeroX(int theX) {
        myHeroX = theX;
    }
    public void setHeroY(int theY) {
        myHeroY = theY;
    }
}
