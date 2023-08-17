package model.characters.monsters;

import model.characters.Debuff;
import model.characters.DungeonCharacter;

public class MonsterWarlock extends Monster {

    public static final int HEALTH = 150;
    public static final int SPEED = 1;
    public static final double BASIC_CHANCE = 0.8;
    public static final double SPECIAL_CHANCE = 0.8;
    public static final int MIN_DMG = 20;
    public static final int MAX_DMG = 40;
    public static final int COOLDOWN = 2;
    public static final int MAX_COOLDOWN = 3;
    public static final int INITIAL_COOLDOWN = 0;
    public static final int MIN_HEAL = 20;
    public static final int MAX_HEAL = 40;
    public static final double HEAL_CHANCE = 0.5;
    public static final int XP_WORTH = 250;

    private static final String[] BASIC_MISS_MSGS = {
            "[Miss]",
            "[Miss]",
            "[Miss]"
    };

    private static final String[] SPECIAL_MISS_MSGS = {
            "[Miss]",
            "[Miss]",
            "[Miss]"
    };

    private static final String[] BASIC_HIT_MSGS = {
            "[Hit]",
            "[Hit]",
            "[Hit]"
    };

    private static final String[] SPECIAL_HIT_MSGS = {
            "[Hit]",
            "[Hit]",
            "[Hit]"
    };

    private static final String[] DEATH_MSGS = {
            "The warlock gives one final breath before collapsing to the ground.",
            "The warlock falls to the ground, defeated.",
            "The warlock howls with rage and crumples to the floor."
    };


    public MonsterWarlock() {
        super(MonsterTypes.WARLOCK, HEALTH, SPEED, BASIC_CHANCE, SPECIAL_CHANCE, MIN_DMG, MAX_DMG, COOLDOWN,
                MAX_COOLDOWN, MIN_HEAL, MAX_HEAL, HEAL_CHANCE, XP_WORTH, INITIAL_COOLDOWN);
    }

    @Override
    public int basicAtk(DungeonCharacter theOther) {

        if(myHealth <= 0) {
            throw new IllegalStateException("Cannot attack when dead.");
        }

        int rawDamage = 0;

        double hitChance = Math.random();

        if (hitChance <= myBasicAccuracy) {
            myAttackWasSuccess = true;
            rawDamage = RANDOM.nextInt(myMaxDmg - myMinDmg) + myMinDmg; // Random number between min and max damage
            myAttackResult = BASIC_HIT_MSGS[RANDOM.nextInt(BASIC_HIT_MSGS.length)];

        } else {
            myAttackWasSuccess = false;
            myAttackResult = BASIC_MISS_MSGS[RANDOM.nextInt(BASIC_MISS_MSGS.length)];
        }

        int damageDealt = calculateDamageDealt(rawDamage, theOther);
        theOther.damage(rawDamage);

        return damageDealt;
    }

    @Override
    public int specialAtk(final DungeonCharacter theOther) {

        if(myHealth <= 0) {
            throw new IllegalStateException("Cannot attack when dead.");
        }

        int rawDamage = 0;

        double hitChance = Math.random();

        if (hitChance <= myBasicAccuracy) {
            myAttackWasSuccess = true;
            rawDamage = RANDOM.nextInt(myMaxDmg - myMinDmg) + myMinDmg * 2; // Random number between min and max damage
            myAttackResult = SPECIAL_HIT_MSGS[RANDOM.nextInt(SPECIAL_HIT_MSGS.length)];

        } else {
            myAttackWasSuccess = false;
            myAttackResult = SPECIAL_MISS_MSGS[RANDOM.nextInt(SPECIAL_MISS_MSGS.length)];
        }

        int damageDealt = calculateDamageDealt(rawDamage, theOther);
        theOther.damage(rawDamage);

        return damageDealt;
    }

    @Override
    public String[] getBasicName() {
        return new String[] {
                " Lightning Bolt - ",
                ""
        };
    }

    @Override
    public String[] getSpecialName() {
        return new String[] {
                "Fireball - ",
                ""
        };
    }

    @Override
    public String[] getPassiveName() {
        return new String[] {
                "",
                ""
        };
    }

    @Override
    public String getBasicSelectMsg(final DungeonCharacter theOther) {
        return this.getName() + " casts a ball of lightning at " + theOther.getName();
    }


    @Override
    public String getSpecialSelectMsg(final DungeonCharacter theOther) {  //TODO change to something else that doesn't do straight damage?
        return this.getName() + " unleashes a fiery inferno upon " + theOther.getName();
    }


    @Override
    public String[] getDescription() {
        return new String[] {
            "Warlocks are powerful magic users that unleash devastating spells upon their foes.",
            "Their damage fluctuates as they charge up and release energy, so defeat them in their weak moments.",
        }; //"making them a formidable foe"
    }

    @Override
    public String getDeathMsg() {
        return DEATH_MSGS[RANDOM.nextInt(DEATH_MSGS.length)];
    }
}
