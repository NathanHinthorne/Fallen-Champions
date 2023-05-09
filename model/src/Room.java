public class Room {

    private boolean hasMonster;
    private boolean hasPotion;
    private boolean hasPillar;
    private boolean hasPit;


    public enum RoomType {
        ROOM,
        WALL
    }

//    public Room(final String theType) {
//        myType = theType;
//
//    }

    public Room() {
        myMonster = null;
        myPotion = null;
        myPillar = null;
        myIsExit = false;
    }



}
