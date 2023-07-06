package model;

/**
 * The swordsman hero.
 *
 * @author Nathan Hinthorne
 * @version 6/23/2023
 */
public class HeroSwordsman extends Hero {

    public static final int HEALTH = 200;
    public static final int SPEED = 3;
    public static final double BASIC_CHANCE = 0.8;
    public static final double SPECIAL_CHANCE = 0.8;
    public static final int MIN_DMG = 20;
    public static final int MAX_DMG = 40;
    public static final int COOLDOWN = 0;
    public static final int MAX_COOLDOWN = 2;
    public static final String BASIC_MSG = " swings their sword at ";
    public static final String SPECIAL_MSG = " grips their sword and arcs a deadly slice towards the ";
    private static final double CRIT_CHANCE = 0.4;

    public HeroSwordsman() {
        super(HeroTypes.SWORDSMAN, HEALTH, SPEED, BASIC_CHANCE, SPECIAL_CHANCE, MIN_DMG, MAX_DMG, COOLDOWN,
                MAX_COOLDOWN, BASIC_MSG, SPECIAL_MSG);

        setBasicSFX("hero_swordsman_basic.wav");
        setSpecialSFX("hero_swordsman_special.wav");
    }

    @Override
    public int basicAtk(DungeonCharacter theOther) {
        int dmg;

        if (Math.random() <= myBasicChance) {
            myAttackWasSuccess = true;
            dmg = myMinDmg + RANDOM.nextInt(myMaxDmg - myMinDmg + 1);
        } else {
            myAttackWasSuccess = false;
            dmg = 0;
        }

        // set the monster's health
        if (theOther.getHealth() - dmg < 0) {
            theOther.setHealth(0);
        } else {
            theOther.setHealth(theOther.getHealth() - dmg);
        }

        return dmg;
    }

    @Override
    public int specialAtk(DungeonCharacter theOther) {
        critHit = false;

        int dmg;

        if (Math.random() <= mySpecialChance) {
            myAttackWasSuccess = true;
            dmg = myMinDmg + RANDOM.nextInt(myMaxDmg - myMinDmg + 1);

            if (Math.random() <= CRIT_CHANCE) {
                dmg = dmg * 2;
            }

        } else {
            myAttackWasSuccess = false;
            dmg = 0;
        }

        // set the monster's health
        if (theOther.getHealth() - dmg < 0) {
            theOther.setHealth(0);
        } else {
            theOther.setHealth(theOther.getHealth() - dmg);
        }

        return dmg;
    }
}
