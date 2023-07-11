//package model;
//
//import java.io.File;
//
///**
// * The swordsman hero.
// *
// * @author Nathan Hinthorne
// * @version 6/23/2023
// */
//public class HeroArcher extends Hero {
//
//    public static final int HEALTH = 200;
//    public static final int SPEED = 3;
//    public static final double BASIC_CHANCE = 0.8;
//    public static final double SPECIAL_CHANCE = 0.8;
//    public static final int MIN_DMG = 20;
//    public static final int MAX_DMG = 40;
//    public static final int COOLDOWN = 0;
//    public static final int MAX_COOLDOWN = 2;
//    public static final String BASIC_MSG = " swings a sword at ";
//    public static final String SPECIAL_MSG = " grips their sword and arcs a deadly slice towards the ";
//    public static final String MISS_MSG = " misses";
//    public static final String SPECIAL_MISS_MSG = " misses"; // needed for thief, ninja, etc.
//    public static final String BASIC_HIT_MSG = " scores a hit!";
//    public static final String SPECIAL_HIT_MSG = " dealt a devastating blow!";
//    private static final double CRIT_CHANCE = 0.4;
//
//    public HeroArcher() {
//        super(HeroTypes.SWORDSMAN, HEALTH, SPEED, BASIC_CHANCE, SPECIAL_CHANCE, MIN_DMG, MAX_DMG, COOLDOWN,
//                MAX_COOLDOWN, BASIC_MSG, SPECIAL_MSG);
//
//        setBasicSFX("hero_swordsman_basic.wav");
//        setSpecialSFX("hero_swordsman_special.wav");
//    }
//
//    @Override
//    public int specialAtk(DungeonCharacter theOther) {
//        return 0;
//    }
//}
