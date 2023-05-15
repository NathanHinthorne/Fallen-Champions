package model;
import java.util.Random;

public class Dungeon {

    // make non-static if we add difficulty levels
    public static final double NEW_ROOM_CHANCE = 0.10;
    public static final double EXTENDED_ROOM_CHANCE = 0.70;
    public static final double ENEMY_CHANCE = 0.20;
    public static final double POTION_CHANCE = 0.10;
    public static final double PILLAR_CHANCE = 0.01;
    public static final double PIT_CHANCE = 0.10;
    public static final Random rand = new Random();

    // fields
    private Room[][] myDungeon;
    private int myDungeonWidth;
    private int myDungeonHeight;
    private int myHeroX;
    private int myHeroY;
    private Room myCurrRoom;


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

        // keep generating dungeons until we get one that is traversable
        do {
            generateDungeon();
        } while(!isTraversable());
    }

    /**
     * Fills the dungeon with rooms and walls
     */
    private Room[][] generateDungeon() {

        // step 1: fill the dungeon COMPLETELY with walls
        fillWithWalls();

        // step 2: randomly place empty rooms and rooms with items
        addEmptyRooms(); //TODO generate items in diff method?

        // step 3: add entrance and exit
        addEntranceAndExit();

        return myDungeon;
    }

    private void fillWithWalls() {
        for (int y = 0; y < myDungeonHeight; y++) {
            for (int x = 0; x < myDungeonWidth; x++) {
                myDungeon[y][x] = new Room();
                myDungeon[y][x].placeWall();
            }
        }
    }

    private void addEmptyRooms() {

        for (int y = 1; y < myDungeonHeight-1; y++) { // skip over the edges of the dungeon
            for (int x = 1; x < myDungeonWidth-1; x++) {

                Room room = myDungeon[y][y];

                //TODO Two choices to determine where to place room
                // 1. perlin noise
                // 2. simple if statements like below
                int numberOfEmptyRooms = numberOfEmptyRooms(x, y);
                if (numberOfEmptyRooms == 0) {
                    if (Math.random() < NEW_ROOM_CHANCE) {
                        room.removeWall();
                    }
                } else if (numberOfEmptyRooms == 1) {
                    if (Math.random() < EXTENDED_ROOM_CHANCE) {
                        room.removeWall();
                    }
                }

                if (Math.random() < ENEMY_CHANCE) { //TODO limit the number of enemies in the dungeon
                    room.placeMonster();             //TODO use a list to accomplish this?
                }
                if (Math.random() < POTION_CHANCE) { //TODO limit the number of potions in the dungeon, or not?
                    room.placePotion();
                }
                if (Math.random() < PILLAR_CHANCE) { //TODO make each specific pillar class a singleton
                    room.placePillar();
                }
                if (Math.random() < PIT_CHANCE) {  //TODO limit the number of pits in the dungeon, or not?
                    room.placePit();
                }
            }
        }
    }

    private void addEntranceAndExit() {

        int x;
        int y;

        // keep generating coords for entrance until we get a room that is empty
        do {
            x = rand.nextInt(myDungeonWidth);
            y = rand.nextInt(myDungeonHeight);

        } while (!myDungeon[y][x].isEmpty());
        myDungeon[y][x].placeEntrance();

        // keep generating coords for an exit until we get a room that is empty
        do {
            x = rand.nextInt(myDungeonWidth);
            y = rand.nextInt(myDungeonHeight);

        } while (!myDungeon[y][x].isEmpty());
        myDungeon[y][x].placeExit();
    }

    public int numberOfEmptyRooms(final int theX, final int theY) {

        int count = 0;

        // check top
        if (myDungeon[theY-1][theX].isEmpty()) {
            count++;
        }

        // check left
        if (myDungeon[theY][theX-1].isEmpty()) {
            count++;
        }

        // check right
        if (myDungeon[theY][theX+1].isEmpty()) {
            count++;
        }

        // check bottom
        if (myDungeon[theY+1][theX].isEmpty()) {
            count++;
        }

        return count;
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

    private boolean isTraversable() {
        //TODO check if the room is traversable with a complicated algorithm

        return true;
    }

    @Override
    public String toString() {
        //TODO iterate over the dungeon 2D array.
        // for each room, determine what char to print based on what objects are in the room

        for (int i = 0; i < myDungeonHeight; i++) {
            for (int j = 0; j < myDungeonWidth; j++) {
                System.out.print(myDungeon[i][j].toString());
            }
        }

        return null;
    }

    public Room getMyCurrRoom() {
        return myCurrRoom;
    }

}
