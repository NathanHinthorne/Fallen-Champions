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
    public static final double SPECIAL_CHANCE = 0.9;
    public static final int MIN_DMG = 20;
    public static final int MAX_DMG = 40;
    public static final int COOLDOWN = 0;
    public static final int MAX_COOLDOWN = 2;

    private static final double CRIT_CHANCE = 0.5;

    public HeroSwordsman() {
        super(HeroTypes.SWORDSMAN, HEALTH, SPEED, BASIC_CHANCE, SPECIAL_CHANCE, MIN_DMG, MAX_DMG, COOLDOWN, MAX_COOLDOWN);

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

        int damage;

        if (Math.random() <= mySpecialChance) {
            myAttackWasSuccess = true;
            damage = myMinDmg + RANDOM.nextInt(myMaxDmg - myMinDmg + 1) + 20;

            if (Math.random() <= CRIT_CHANCE) {
                critHit = true;
                damage = damage * 2;
            }

        } else {
            myAttackWasSuccess = false;
            damage = 0;
        }

        // set the monster's health
        if (theOther.getHealth() - damage < 0) {
            theOther.setHealth(0);
        } else {
            theOther.setHealth(theOther.getHealth() - damage);
        }

        return damage;
    }

    @Override
    public String[] getBasicName() {
        return new String[] {
                "\"Sword Strike\" - Perform a basic sword strike",
                ""
        };
    }
    @Override
    public String[] getSpecialName() {
        return new String[] {
            "\"Mighty Swing\" - Unleash a powerful, overhead",
            "slice with pinpoint accuracy (can crit hit)"
        };
    }
    @Override
    public String[] getPassiveName() {
        return new String[] {
                "\"Battle Stance\" - ",
                ""
        };
    }

    @Override
    public String getBasicSelectMsg() {
        return " swings their sword at the ";
    }
    @Override
    public String getExtendedBasicSelectMsg() {
        return "";
    }
    @Override
    public String getSpecialSelectMsg() {
        return " grips their sword and arcs a deadly slice towards the ";
    }
    @Override
    public String getExtendedSpecialSelectMsg() {
        return "";
    }

    @Override
    public String[] getBasicMissMsg() {
        return new String[]
                {"The sword misses the monster",
                "The sword barely misses the monster",
                "The sword flies out of your hand, completely missing the monster"};
    }
    @Override
    public String[] getBasicHitMsg() {
        return new String[]
                {"The sword slices the monster",
                "The sword cuts the monster's nose off",
                "The sword slashes the monster's arm"};
    }
    @Override
    public String[] getSpecialMissMsg() {
        return new String[]
                {"The sword misses the monster",
                "The sword barely misses the monster",
                "The sword flies out of your hand, completely missing the monster"};
    }
    @Override
    public String[] getSpecialHitMsg() {
        return new String[]
                {"The sword slices through the monster",
                "The sword cuts the monster",
                "The sword slashes the monster"};
    }

    @Override
    public int initialCooldown() {
        return 0;
    }

    @Override
    public boolean isUnlocked() {
        return true; // Always unlocked
    }
    


}
