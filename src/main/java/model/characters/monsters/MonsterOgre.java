package model.characters.monsters;

import model.characters.Debuff;
import model.characters.DungeonCharacter;

public class MonsterOgre extends Monster {

    public static final int HEALTH = 200;
    public static final int SPEED = 1;
    public static final double BASIC_CHANCE = 0.8;
    public static final double SPECIAL_CHANCE = 0.7;
    public static final int MIN_DMG = 40;
    public static final int MAX_DMG = 55;
    public static final int COOLDOWN = 2;
    public static final int MAX_COOLDOWN = 3;
    public static final int INITIAL_COOLDOWN = 1;
    public static final int MIN_HEAL = 15;
    public static final int MAX_HEAL = 30;
    public static final double HEAL_CHANCE = 0.3;
    public static final int XP_WORTH = 200;

    private static final String[] BASIC_MISS_MSGS = {
            "The punch smacks a nearby gremlin. You hear a small cry of pain and smile to yourself.",
            "The punch misses!",
            "The punch barely misses!"
    };

    private static final String[] SPECIAL_MISS_MSGS = {
            "Luckily the wall was made of foam.",
            "It turns out the wall was made of cardboard, leaving the hero unharmed.",
            "The hero manages to land on their feet."
    };

    private static final String[] BASIC_HIT_MSGS = {
            "The punch lands with a hard slam",
            "The punch delivers a crushing blow to the hero!",
            "The punch connects with the brave young adventurer!"
    };

    private static final String[] SPECIAL_HIT_MSGS = {
            "The hero suffers major damage after the brutal attack",
            "The hero struggles to get up after the damage",
            "The hero is left with a nasty bruise after the attack"
    };

    private static final String[] DEATH_MSGS = {
            "The ogre falls to the ground with a loud thud.",
            "The ogre's body slams to the ground, shaking the entire room.",
            "The ogre's body falls to the ground, leaving a large dent in the floor."
    };


    public MonsterOgre() {
        super(MonsterTypes.OGRE, HEALTH, SPEED, BASIC_CHANCE, SPECIAL_CHANCE, MIN_DMG, MAX_DMG, COOLDOWN,
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
            rawDamage = RANDOM.nextInt(myMaxDmg - myMinDmg) + myMinDmg;
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
            rawDamage = (RANDOM.nextInt(myMaxDmg - myMinDmg) + myMinDmg) * 2;
            theOther.inflictDebuff(Debuff.WEAKEN, 1);
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
                " Punch - ",
                ""
        };
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
    public String getBasicSelectMsg(final DungeonCharacter theOther) {
        return this.getName() + " hurls a ferocious punch towards " + theOther.getName();
    }


    @Override
    public String getSpecialSelectMsg(final DungeonCharacter theOther) {
        return this.getName() + " grabs " + theOther.getName() + " and throws them against the wall";
    }

    @Override
    public String[] getDescription() {
        return new String[] {
                "Ogres are large, brutish creatures that are known for their incredible strength.",
                "Due to their immense health, it's best to wear them down after their initial outburst.",
        };
    }

    @Override
    public String getDeathMsg() {
        return DEATH_MSGS[RANDOM.nextInt(DEATH_MSGS.length)];
    }

}
