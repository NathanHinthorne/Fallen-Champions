public class Room {

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
    private AbstractPotion myPotion;
    private AbstractPillar myPillar;
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


    publuc boolean hasWall() {
        return myWall != null;
    }
    public boolean hasEntrance() {
        return myEntrance != null;
    }
    public boolean hasExit() {
        return myExit != null;
    }
    public boolean hasMonster() {
        return myMonster != null;
    }
    public boolean hasPotion() {
        return myPotion != null;
    }
    public boolean hasPillar() {
        return myPillar != null;
    }
    public boolean hasPit() {
        return myPit != null;
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
        myPillar = new AbstractPillar();
    }
    public void placePit() {
        myPit = new Pit();
    }


}
