package model;

public class Room {

    // static fields for toString

    public static final String EMPTY = " ";
    public static final String MULTIPLE = "M";


    // make non-static if we add difficulty levels
    public static final double HEALTH_POTION_CHANCE = 0.65;
    // vision potion chance is 0.35
    public static final double A_PILLAR_CHANCE = 0.25;
    public static final double I_PILLAR_CHANCE = 0.25;
    public static final double P_PILLAR_CHANCE = 0.25;
    // E pillar chance is 0.25

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


    public boolean hasWall() {
        return myWall != null;
    }
    public boolean hasEntrance() {
        return myEntrance != null;
    }
    public boolean hasExit() {
        return myExit != null;
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
    private boolean hasMonster() {
        return myMonster != null;
    }

    public boolean isEmpty() {
        return !hasWall() && !hasEntrance() && !hasExit() &&
                !hasMonster() && !hasPotion() && !hasPillar() && !hasPit();
    }


    // most 'place' methods have factors determining which type of object to place
    public void placeWall() {
        myWall = new Wall();
    }
    public void placeEntrance() {
        myEntrance = new Entrance();
    } //TODO make entrance a singleton
    public void placeExit() {
        myExit = new Exit();
    } //TODO make exit a singleton
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

        if (Math.random() < A_PILLAR_CHANCE)
            myPillar = new AbstractionPillar(); // TODO something is wrong with pillar singletons.
        else if (Math.random() < I_PILLAR_CHANCE)
            myPillar = new InheritancePillar();
        else if (Math.random() < P_PILLAR_CHANCE)
            myPillar = new PolymorphismPillar();
        else {
            myPillar = new EncapsulationPillar();
        }
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
