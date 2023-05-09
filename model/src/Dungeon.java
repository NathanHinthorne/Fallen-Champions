public class Dungeon {

    private Room[][] myDungeon;
    private int myDungeonWidth;
    private int myDungeonHeight;
    private int myHeroX;
    private int myHeroY;
    private Room myCurrRoom;

    // make non-static if we add difficulty levels
    public static final double WALL_PERCENT = 0.25;
    public static final double ENEMY_PERCENT = 0.20;
    public static final double POTION_PERCENT = 0.10;
    public static final double PILLAR_PERCENT = 0.01;


    /**
     * Creates a new dungeon with the given dimensions and starting position.
     *
     * @param theDungeonWidth the width of the dungeon in rooms
     * @param theDungeonHeight the height of the dungeon in rooms
     * @param theHeroX the starting x position of the hero
     * @param theHeroY the starting y position of the hero
     */
    public Dungeon(int theDungeonWidth, int theDungeonHeight, int theHeroX, int theHeroY) {

        // initialize fields
        myDungeonHeight = theDungeonHeight;
        myDungeonWidth = theDungeonWidth;
        myHeroX = theHeroX;
        myHeroY = theHeroY;
        myDungeon = new Room[theDungeonWidth][theDungeonHeight];
        updateCurrRoom();

        // fill the dungeon with rooms
        generateDungeon();
    }

    /**
     * Fills the dungeon with rooms and walls
     */
    private Room[][] generateDungeon() {

        for (int i = 0; i < myDungeonHeight; i++) {
            for (int j = 0; j < myDungeonWidth; j++) {

                if (Math.random() < WALL_PERCENT) {
                    myDungeon[i][j] = new Room("wall"); // place a wall here
                } else {
                    myDungeon[i][j] = new Room(""); // place an empty room here
                }
            }
        }
        return myDungeon;
    }

    // a method in the view will check for keyboard inputs
    // once triggered, it will tell the controller to call this method
    /**
     * Moves the player in the given direction.
     *
     * @param dir the direction to move the player
     */
    public void playerMove(Direction dir) {
        if (dir == Direction.NORTH) {
            myHeroY--;
        } else if (dir == Direction.EAST) {
            myHeroX++;
        } else if (dir == Direction.SOUTH) {
            myHeroY--;
        } else if (dir == Direction.WEST){
            myHeroX--;
        } else {
            System.out.println("Invalid direction was given.\n" +
                    "Make sure playerMove() receives one of these:\n" +
                    "NORTH, EAST, SOUTH, WEST");
        }

        updateCurrRoom();
    }

    /**
     * Updates the current room based on the hero's position.
     */
    private void updateCurrRoom() {
        myCurrRoom = myDungeon[myHeroX][myHeroY];
    }

}
