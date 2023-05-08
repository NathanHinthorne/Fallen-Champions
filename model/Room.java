package model;

public class Room {

    // should these characters be in the view?
    public static final char ABSTRACTION_PILLAR = 'A';
    public static final char ENCAPSULATION_PILLAR = 'E';
    public static final char INHERITANCE_PILLAR = 'I';
    public static final char POLYMORPHISM_PILLAR = 'P';
    public static final char EXIT = 'E';
    public static final char HERO = 'H';
    public static final char potion = 'p';
    public static final char monster = 'M';


    private Monster myMonster;
    private Potion myPotion;
    private Pillar myPillar;
    private boolean myIsExit;


    public enum RoomType {
        ROOM,
        WALL
    }

    public Room(final String theType) {
        myType = theType;

    }


}
