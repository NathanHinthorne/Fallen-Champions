package model;

public class Room {

    // static fields for toString

    public static final String EMPTY = " ";
    public static final String MULTIPLE = "M";

//    public static final char MONSTER = 'm';
//    public static final char HERO = 'H';


    // make non-static if we add difficulty levels
    public static final double HEALTH_POTION_CHANCE = 0.65; // vision potion chance is 0.35
    public static final double ENEMY_CHANCE = 0.20;
    public static final double POTION_CHANCE = 0.10;
    public static final double PILLAR_CHANCE = 0.01;

    // fields
    private Wall myWall;
    private Entrance myEntrance;
    private Exit myExit;
    private Monster myMonster;
    private Potion myPotion;
    private Pillar myPillar;
    private Pit myPit;


    public Room() {
        myWall = null;
        myEntrance = null;
        myExit = null;
        myMonster = null;
        myPotion = null;
        myPillar = null;
        myPit = null;
    }



    private boolean hasWall() {
        return myWall != null;
    }
    private boolean hasEntrance() {
        return myEntrance != null;
    }
    private boolean hasExit() {
        return myExit != null;
    }
    private boolean hasPotion() {
        return myPotion != null;
    }
    private boolean hasPillar() {
        return myPillar != null;
    }
    private boolean hasPit() {
        return myPit != null;
    }
    public boolean isEmpty() {
        return !hasWall() && !hasEntrance() && !hasExit() &&
                !hasMonster() && !hasPotion() && !hasPillar() && !hasPit();
    }


    // most 'place' method have factors determining which type of object to place
    public void placeWall() {
        myWall = new Wall();
    }
    public void placeEntrance() {
        myEntrance = new Entrance();
    }
    public void placeExit() {
        myExit = new Exit();
    }
    public void placeMonster() {
        //TODO read from SQLite to determine what monster to place next
        //TODO to create a new monster, access the monster factory
    }
    public void placePotion() {

        // randomly choose between placing a health potion and a vision potion
        if (Math.random() < HEALTH_POTION_CHANCE) {
            myPotion = new HealthPotion();
        } else {
            myPotion = new VisionPotion();
        }
    }
    public void placePillar() {
        //TODO decide what type of pillar to place
//        myPillar = new Pillar();
    }
    public void placePit() {
        myPit = new Pit();
    }

    public void removeWall() {
        myWall = null;
    }

    @Override
    public String toString() {
        if (hasWall()) {
            return myWall.toString();
        } else if (hasPotion() && hasPillar()) {
            return MULTIPLE; // doesn't make sense in this context to make a class for multiple
        } else if (hasPotion()) {
            return myPotion.toString();
        } else if (hasPillar()) {
            return myPillar.toString();
        } else if (hasPit()) {
            return myPit.toString();
        } else if (hasExit()) {
            return myExit.toString();
        } else if (hasEntrance()) {
            return myEntrance.toString();
        } else {
            return EMPTY; // doesn't make sense in this context to make a class for empty
        }

    }



}
