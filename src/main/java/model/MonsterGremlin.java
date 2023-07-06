package model;

public class MonsterGremlin extends Monster {

    public static final int HEALTH = 100;
    public static final int SPEED = 1;
    public static final double BASIC_CHANCE = 0.8;
    public static final double SPECIAL_CHANCE = 0.6;
    public static final int MIN_DMG = 20;
    public static final int MAX_DMG = 40;
    public static final int COOLDOWN = 2;
    public static final int MAX_COOLDOWN = 3;
    public static final String BASIC_MSG = " bites ";
    public static final String SPECIAL_MSG = " shoots web at ";
    public static final int MIN_HEAL = 20;
    public static final int MAX_HEAL = 40;
    public static final double HEAL_CHANCE = 0.5;
    public static final int XP_WORTH = 100;


    public MonsterGremlin() {
        super(MonsterTypes.GREMLIN, HEALTH, SPEED, BASIC_CHANCE, SPECIAL_CHANCE, MIN_DMG, MAX_DMG, COOLDOWN,
                MAX_COOLDOWN, BASIC_MSG, SPECIAL_MSG, MIN_HEAL, MAX_HEAL, HEAL_CHANCE, XP_WORTH);
    }

    @Override
    public int specialAtk(DungeonCharacter theOther) {

        return 0;
    }


}
