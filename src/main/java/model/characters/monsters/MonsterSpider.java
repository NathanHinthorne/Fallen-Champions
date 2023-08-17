package model.characters.monsters;

import model.characters.Debuff;
import model.characters.DungeonCharacter;

public class MonsterSpider extends Monster {

    public static final int HEALTH = 80;
    public static final int SPEED = 1;
    public static final double BASIC_CHANCE = 0.8;
    public static final double SPECIAL_CHANCE = 0.6;
    public static final int MIN_DMG = 20;
    public static final int MAX_DMG = 40;
    public static final int COOLDOWN = 2;
    public static final int MAX_COOLDOWN = 3;
    public static final int INITIAL_COOLDOWN = 0;
    public static final int MIN_HEAL = 20;
    public static final int MAX_HEAL = 40;
    public static final double HEAL_CHANCE = 0.5;
    public static final int XP_WORTH = 110;

    private static final String[] BASIC_MISS_MSGS = {
            "The spider misses its bite!",
            "The spider's bite is too slow, and the hero dodges with ease!",
            "The spider's bite is too weak to pierce the hero's armor!"
    };

    private static final String[] SPECIAL_MISS_MSGS = {
            "The web misses!",
            "The web passes over the hero's head!",
            "The web snaps after wrapping around the hero!"
    };

    private static final String[] BASIC_HIT_MSGS = {
            "The spider's bite pierces the hero's armor!",
            "The spider's bite hits the hero!",
            "The spider's bite hits the hero's weak spot!"
    };

    private static final String[] SPECIAL_HIT_MSGS = {
            "The web wraps around the hero, immobilizing them for 1 turn!",
            "The web hits the hero, getting them stuck for 1 turn!",
            "The web traps the hero's legs, making them unable to move for 1 turn!"
    };

    private static final String[] DEATH_MSGS = {
            "The spider hisses in anger as it collapses.",
            "The spider's legs curl up as it dies.",
            "The spider falls to the ground, dead."
    };
    public static final double POISON_CHANCE = 0.5;

    public MonsterSpider() {
        super(MonsterTypes.SPIDER, HEALTH, SPEED, BASIC_CHANCE, SPECIAL_CHANCE, MIN_DMG, MAX_DMG, COOLDOWN,
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
            theOther.inflictDebuff(Debuff.POISON, 1);
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
            rawDamage = RANDOM.nextInt(myMaxDmg - myMinDmg) + 3; // Random number between min and max damage
            theOther.inflictDebuff(Debuff.STUCKIFY, 1);
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
                "Bite -",
                ""
        };
    }

    @Override
    public String[] getSpecialName() {
        return new String[] {
                "Web -",
                ""
        };
        //TODO skip hero's turn
    }

    @Override
    public String[] getPassiveName() {
        return new String[] {
                "Poison -",
                ""
        };
        //TODO every basic hit has a chance to poison the hero
    }

    @Override
    public String getBasicSelectMsg(final DungeonCharacter theOther) {
        return this.getName() + " bites " + theOther.getName();
    }


    @Override
    public String getSpecialSelectMsg(final DungeonCharacter theOther) {
        return this.getName() + " shoots a web at " + theOther.getName();
    }

    @Override
    public String[] getDescription() {
        return new String[] {
            "Spiders might have low health, but they are fast and deadly.",
            "With venomous bites and immobilizing webs, it's best to kill them quickly."
        };
    }

    @Override
    public String getDeathMsg() {
        return DEATH_MSGS[RANDOM.nextInt(DEATH_MSGS.length)];
    }
}
