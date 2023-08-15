package model.characters.monsters;

import model.characters.Debuff;
import model.characters.DungeonCharacter;

public class MonsterSkeleton extends Monster {

    public static final int HEALTH = 140;
    public static final int SPEED = 3;
    public static final double BASIC_CHANCE = 0.6;
    public static final double SPECIAL_CHANCE = 0.8;
    public static final int MIN_DMG = 35;
    public static final int MAX_DMG = 45;
    public static final int COOLDOWN = 2;
    public static final int MAX_COOLDOWN = 3;
    public static final int INITIAL_COOLDOWN = 0;
    public static final int MIN_HEAL = 20;
    public static final int MAX_HEAL = 40;
    public static final double HEAL_CHANCE = 0.5;
    public static final int XP_WORTH = 90;

    private static final String[] BASIC_MISS_MSGS = {
            "The bone flies past, leaving you unscathed.",
            "The skeleton's aim is off, and the bone clatters harmlessly to the ground.",
            "You sidestep the projectile."
    };

    private static final String[] SPECIAL_MISS_MSGS = {
            "You narrowly dodge the skeleton's arrow.",
            "You swiftly duck, barely avoiding the skeleton's arrow.",
            "The skeleton's arrow whizzes past you, missing its target."
    };

    private static final String[] BASIC_HIT_MSGS = {
            "It strikes your shoulder, sending a sharp pain through your body.",
            "You wince as the bone pierces your arm.",
            "The bone hits you, causing you to stumble back."
    };

    private static final String[] SPECIAL_HIT_MSGS = {
            "You gasp in pain as the arrow hits your shoulder.",
            "You feel the sting of the skeleton's arrow as it finds its target.",
            "You feel a searing pain as the arrow finds its mark."
    };

    private static final String[] DEATH_MSGS = {
            "The skeleton collapses into a pile of bones.",
            "The skeleton's bones clatter to the ground.",
            "The skeleton's bones fall to the ground, lifeless."
    };

    private int bonesThrown;


    public MonsterSkeleton() {
        super(MonsterTypes.SKELETON, HEALTH, SPEED, BASIC_CHANCE, SPECIAL_CHANCE, MIN_DMG, MAX_DMG, COOLDOWN,
                MAX_COOLDOWN, MIN_HEAL, MAX_HEAL, HEAL_CHANCE, XP_WORTH, INITIAL_COOLDOWN);
        bonesThrown = 0;
    }

    @Override
    public int basicAtk(DungeonCharacter theOther) {

        if(myHealth <= 0) {
            throw new IllegalStateException("Cannot attack when dead.");
        }

        int dmg = 0;
        System.out.println("                                                            [DEBUG: Temporary attack! Override this in the subclass!]");

        double hitChance = Math.random();

        if (hitChance <= myBasicAccuracy) {
            myAttackWasSuccess = true;
            dmg = RANDOM.nextInt(myMaxDmg - myMinDmg) + myMinDmg; // Random number between min and max damage
            theOther.hurt(dmg);
            myAttackResult = BASIC_HIT_MSGS[RANDOM.nextInt(BASIC_HIT_MSGS.length)];

            //temp
            theOther.inflictDebuff(Debuff.STUCKIFY, 1);

        } else {
            myAttackWasSuccess = false;
            myAttackResult = BASIC_MISS_MSGS[RANDOM.nextInt(BASIC_MISS_MSGS.length)];
        }

        return dmg;
    }

    @Override
    public int specialAtk(final DungeonCharacter theOther) {

        if(myHealth <= 0) {
            throw new IllegalStateException("Cannot attack when dead.");
        }

        int dmg = 0;
        System.out.println("                                                              [DEBUG: Temporary attack! Override this in the subclass!]");

        double hitChance = Math.random();

        if (hitChance <= myBasicAccuracy) {
            myAttackWasSuccess = true;
            dmg = RANDOM.nextInt(myMaxDmg - myMinDmg) + myMinDmg * 2; // Random number between min and max damage
            theOther.hurt(dmg);
            theOther.inflictDebuff(Debuff.VULNERATE, 1);
            myAttackResult = SPECIAL_HIT_MSGS[RANDOM.nextInt(SPECIAL_HIT_MSGS.length)];

        } else {
            myAttackWasSuccess = false;
            myAttackResult = SPECIAL_MISS_MSGS[RANDOM.nextInt(SPECIAL_MISS_MSGS.length)];
        }

        return dmg;
    }

    @Override
    public String[] getBasicName() {
        return new String[] {
                "Bone Throw - ",
                ""
        };
    }

    @Override
    public String[] getSpecialName() {
        return new String[] {
                "Bow Shot - ",
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
        return this.getName() + " flings a sharp bone at " + theOther.getName();
    } //TODO make this attack damage itself (because it's throwing its own bone)


    @Override
    public String getSpecialSelectMsg(final DungeonCharacter theOther) {
        return this.getName() + " shoots an arrow at " + theOther.getName();
    }

    @Override
    public String[] getDescription() {
        return new String[] {
                "Skeletons are dangerous residents of the dungeon.",
                "For some odd reason, they like to throw their own bones at you.",
        };
    }

    @Override
    public String getDeathMsg() {
        return DEATH_MSGS[RANDOM.nextInt(DEATH_MSGS.length)];
    }
}
