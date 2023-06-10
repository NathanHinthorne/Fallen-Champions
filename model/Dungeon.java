package model;

/**
 * A dungeon is a collection of rooms that the player can navigate through.
 *
 * @author Nathan Hinthorne
 * @version 1.0
 */
public class Dungeon implements java.io.Serializable {

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

    /**
     * the dungeon's difficulty
     */
    private int myHeroX;
    private int myHeroY;
    private String myDifficulty;


    // enforce the usage of the builder for object construction and discourage direct instantiation
    public Dungeon() { }


    public static class SmallDungeonBuilder extends DungeonBuilder {
        private static final String DIFFICULTY = "Easy";
        private static final int MAZE_WIDTH = 7;
        private static final int MAZE_HEIGHT = 7;
        private static final double MAX_BRANCH_OFF_CHANCE = 0.53; // with decreasing branch chance: 0.50
        private static final double PILLAR_CHANCE = 0.20;
        public static final double ENEMY_CHANCE = 0.35;
        public static final double POTION_CHANCE = 0.25;
        public static final double PIT_CHANCE = 0.00;


        public Dungeon buildDungeon() {

            return super.buildDungeon(DIFFICULTY, MAZE_WIDTH, MAZE_HEIGHT,
                                    MAX_BRANCH_OFF_CHANCE, PILLAR_CHANCE, ENEMY_CHANCE, POTION_CHANCE, PIT_CHANCE);
        }
    }
    public static class MediumDungeonBuilder extends DungeonBuilder {
        private static final String DIFFICULTY = "Medium";
        private static final int MAZE_WIDTH = 10;
        private static final int MAZE_HEIGHT = 10;
        private static final double MAX_BRANCH_OFF_CHANCE = 0.55; // with decreasing branch chance: 0.55
        private static final double PILLAR_CHANCE = 0.12;
        public static final double ENEMY_CHANCE = 0.40;
        public static final double POTION_CHANCE = 0.30;
        public static final double PIT_CHANCE = 0.20;

        public Dungeon buildDungeon() {

            return super.buildDungeon(DIFFICULTY, MAZE_WIDTH, MAZE_HEIGHT,
                    MAX_BRANCH_OFF_CHANCE, PILLAR_CHANCE, ENEMY_CHANCE, POTION_CHANCE, PIT_CHANCE);
        }
    }
    public static class LargeDungeonBuilder extends DungeonBuilder { // no static fields here. instance fields in DungeonBuilder and set them up in the constructor
        private static final String DIFFICULTY = "Hard";
        private static final int MAZE_WIDTH = 15;
        private static final int MAZE_HEIGHT = 15;
        private static final double MAX_BRANCH_OFF_CHANCE = 0.58; // with decreasing branch chance: 0.60
        private static final double PILLAR_CHANCE = 0.035;
        public static final double ENEMY_CHANCE = 0.3;
        public static final double POTION_CHANCE = 0.25;
        public static final double PIT_CHANCE = 0.15;


        public Dungeon buildDungeon() {

            return super.buildDungeon(DIFFICULTY, MAZE_WIDTH, MAZE_HEIGHT,
                    MAX_BRANCH_OFF_CHANCE, PILLAR_CHANCE, ENEMY_CHANCE, POTION_CHANCE, PIT_CHANCE);
        }
    }


    public boolean heroIsTouchingMonster() {
        return myMaze[myHeroY][myHeroX].hasMonster();
    }
    public boolean heroIsTouchingExit() {
        return myMaze[myHeroY][myHeroX].hasExit();
    }
    public boolean heroIsTouchingWall() {
        return myMaze[myHeroY][myHeroX].hasWall();
    }
    public boolean heroIsTouchingPit() {
        return myMaze[myHeroY][myHeroX].hasPit();
    }
    public boolean heroIsTouchingPotion() {
        return myMaze[myHeroY][myHeroX].hasPotion();
    }
    public boolean heroIsTouchingPillar() {
        return myMaze[myHeroY][myHeroX].hasPillar();
    }

    public Potion getPotion() {
        return myMaze[myHeroY][myHeroX].getPotion();
    }
    public Pillars getPillar() {
        return myMaze[myHeroY][myHeroX].getPillar();
    }
    public Pit getPit() {
        return myMaze[myHeroY][myHeroX].getPit();
    }
    public Monster getMonster() {
        return myMaze[myHeroY][myHeroX].getMonster();
    }

    public void removeMonster() {
        myMaze[myHeroY][myHeroX].removeMonster();
    }

    public void removePotion() {
        myMaze[myHeroY][myHeroX].removePotion();
    }
    public void removePillar() {
        myMaze[myHeroY][myHeroX].removePillar();
    }


    public String getDifficulty() {
        return myDifficulty;
    }

    public void makeRoomsVisible() {
        for (int i = 0; i < myMazeHeight; i++) {
            for (int j = 0; j < myMazeWidth; j++) {
                myMaze[i][j].setVisible(true);
            }
        }
    }

    public void makeRoomsInvisible() {
        for (int y = 0; y < myMazeHeight; y++) {
            for (int x = 0; x < myMazeWidth; x++) {
                if (!myMaze[y][x].wasVisited()) {
                    myMaze[y][x].setVisible(false);
                }
            }
        }
    }



    // a method in the view will check for keyboard inputs
    // once triggered, it will tell the controller to call this method
    /**
     * Moves the player in the given direction.
     *
     * @param dir the direction to move the player
     */
    public void playerMove(final Direction dir) {
        Room oldRoom = myMaze[myHeroY][myHeroX];

        if (dir == Direction.NORTH && !myMaze[myHeroY-1][myHeroX].hasWall()) {
            myHeroY--;
        } else if (dir == Direction.EAST && !myMaze[myHeroY][myHeroX+1].hasWall()) {
            myHeroX++;
        } else if (dir == Direction.SOUTH && !myMaze[myHeroY+1][myHeroX].hasWall()) {
            myHeroY++;
        } else if (dir == Direction.WEST && !myMaze[myHeroY][myHeroX-1].hasWall()) {
            myHeroX--;
        }
        Room newRoom = myMaze[myHeroY][myHeroX];

        oldRoom.removeHero();
        oldRoom.setVisited(true);
        newRoom.placeHero();
        newRoom.setVisible(true);
    }

    /**
     * Returns a 3x3 grid of rooms centered on the hero.
     *
     * @return a 3x3 grid of rooms centered on the hero
     */
    public String getView() {

        StringBuilder sb = new StringBuilder();

        for (int y = myHeroY - 1; y <= myHeroY + 1; y++) {
            sb.append("\n");
            for (int x = myHeroX - 1; x <= myHeroX + 1; x++) {
                sb.append(myMaze[y][x].toString());
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

        for (int y = myHeroY - 3; y <= myHeroY + 3; y++) {
            sb.append("\n");
            for (int x = myHeroX - 3; x <= myHeroX + 3; x++) {
                sb.append(myMaze[y][x].toString());
                sb.append("  ");
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

        for (int y = 0; y < myMazeHeight; y++) {
            sb.append("\n");
            for (int x = 0; x < myMazeWidth; x++) {
                sb.append(myMaze[y][x].toString());
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

    public int getMazeWidth() {
        return myMazeWidth;
    }
    public void setMazeHeight(int theHeight) {
        myMazeHeight = theHeight;
    }

    public int getMazeHeight() {
        return myMazeHeight;
    }
    public void setHeroX(int theX) {
        myHeroX = theX;
    }

    public int getHeroX() {
        return myHeroX;
    }
    public void setHeroY(int theY) {
        myHeroY = theY;
    }

    public int getMyHeroY() {
        return myHeroY;
    }

    public void setDifficulty(final String theDifficulty) {
        myDifficulty = theDifficulty;
    }

    public Room[][] getMyMaze() {
        return myMaze;
    }

    public void roomAbove() {
        myMaze[myHeroY-1][myHeroX].getContents();
    }

    public void roomBelow() {
        myMaze[myHeroY+1][myHeroX].getContents();
    }
    public void roomLeft() {
        myMaze[myHeroY][myHeroX-1].getContents();
    }
    public void roomRight() {
        myMaze[myHeroY][myHeroX+1].getContents();
    }
}
