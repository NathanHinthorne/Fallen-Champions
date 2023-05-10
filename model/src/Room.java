public class Room {

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
        myMonster = new Monster(); //TODO access the monster factory instead of creating a new monster
    }
    public void placePotion() {
        myPotion = new Potion();
    }
    public void placePillar() {
        myPillar = new Pillar();
    }
    public void placePit() {
        myPit = new Pit();
    }


}
