package model;

public class MonsterOgre extends Monster {

    public static final int HEALTH = 200;
    public static final int SPEED = 1;
    public static final double BASIC_CHANCE = 0.8;
    public static final double SPECIAL_CHANCE = 0.6;
    public static final int MIN_DMG = 30;
    public static final int MAX_DMG = 40;
    public static final int COOLDOWN = 2;
    public static final int MAX_COOLDOWN = 3;
    public static final int MIN_HEAL = 15;
    public static final int MAX_HEAL = 30;
    public static final double HEAL_CHANCE = 0.3;
    public static final int XP_WORTH = 200;


    public MonsterOgre() {
        super(MonsterTypes.OGRE, HEALTH, SPEED, BASIC_CHANCE, SPECIAL_CHANCE, MIN_DMG, MAX_DMG, COOLDOWN,
                MAX_COOLDOWN, MIN_HEAL, MAX_HEAL, HEAL_CHANCE, XP_WORTH);
    }

    @Override
    public int specialAtk(DungeonCharacter theOther) {

        return 0;
    }

    @Override
    public String getBasicName() {
        return " Punch - ";
    }

    @Override
    public String[] getSpecialName() {
        return new String[] {
                "Throw - ",
                ""
        };
    }

    @Override
    public String[] getPassiveName() {
        return new String[] {
                "Fury - Starts off with high damage, but gets ",
                "weaker as it tires out."
        };
    }

    @Override
    public String getBasicSelectMsg() {
        return " hurls a ferocious punch towards ";
    }

    @Override
    public String getExtendedBasicSelectMsg() {
        return "";
    }

    @Override
    public String getSpecialSelectMsg() {
        return " grabs ";
    }

    @Override
    public String getExtendedSpecialSelectMsg() {
        return " and throws them against the wall";
    }

    @Override
    public String[] getBasicMissMsg() {
        return new String[]{
//                "The punch goes straight through a nearby wall!",
                "The punch smacks a nearby gremlin. You hear a small cry of pain and smile to yourself.",
                "The punch misses!",
                "The punch barely misses!"
        };
    }

    @Override
    public String[] getSpecialMissMsg() {
        return new String[] {
                "Luckily the wall was made of foam.",
                "It turns out the wall was made of cardboard, leaving the hero unharmed.",
                "The hero manages to land on their feet."
        };
    }

    @Override
    public String[] getBasicHitMsg() {
        return new String[] {
                "The punch lands with a hard slam",
                "The punch delivers a crushing blow to the hero!",
                "The punch connects with the brave young adventurer!"
        };
    }

    @Override
    public String[] getSpecialHitMsg() {
        return new String[] {
                "The hero suffers major damage after the brutal attack",
                "The hero struggles to get up after the damage",
                "The hero is left with a nasty bruise after the attack"
        };
    }

    @Override
    public int initialCooldown() {
        return 0;
    }

    @Override
    public String[] getDescription() {
        return new String[] {
                "Ogres are large, brutish creatures that are known for their incredible strength.",
                "Due to their immense health, it's best to wear them down after their initial outburst.",
        };
    }

    @Override
    public String[] getDeathMsg() {
        return new String[] {
                "The ogre falls to the ground with a loud thud.",
                "The ogre's body slams to the ground, shaking the entire room.",
                "The ogre's body falls to the ground, leaving a large dent in the floor."
        };
    }

}
