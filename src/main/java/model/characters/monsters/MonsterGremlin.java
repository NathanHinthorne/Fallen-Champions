package model.characters.monsters;

import model.characters.Debuff;
import model.characters.DungeonCharacter;

public class MonsterGremlin extends Monster {

    public static final int HEALTH = 70;
    public static final int SPEED = 4;
    public static final double BASIC_CHANCE = 0.8;
    public static final double SPECIAL_CHANCE = 0.7;
    public static final int MIN_DMG = 30;
    public static final int MAX_DMG = 40;
    public static final int COOLDOWN = 2;
    public static final int MAX_COOLDOWN = 3;
    public static final int INITIAL_COOLDOWN = 0;

    public static final int MIN_HEAL = 20;
    public static final int MAX_HEAL = 40;
    public static final double HEAL_CHANCE = 0.5;
    public static final int XP_WORTH = 80;

    private static final String[] BASIC_MISS_MSGS = {
            "The slap veers off course, hitting another nearby gremlin instead! You hear a yelp of pain and smile to yourself",
            "The slap whiffs through the air, failing to find its mark!",
            "The slap goes wide, hitting thin air!"
    };

    private static final String[] SPECIAL_MISS_MSGS = {
            "You brush off the gremlin's feeble attempt at insults.",
            "The gremlin's insults are so bad, you can't help but laugh!",
            "The gremlin's words fall on deaf ears as you remain composed."
    };

    private static final String[] BASIC_HIT_MSGS = {
            "The slap lands with a resounding smack on the hero's face!",
            "The slap connects with the hero's face!",
            "The gremlin's precise slap lands on the hero's chest!"
    };

    private static final String[] SPECIAL_HIT_MSGS = {
            "The gremlin's biting words strike a nerve, dampening your spirit.",
            "The gremlin's hurtful words strike deep, lowering your morale.",
            "The insults hit their mark, fueling a sense of anger deep within you."
    };

    private static final String[] DEATH_MSGS = {
            "The gremlin falls to the ground, defeated.",
            "The gremlins gives a shrill cry as it falls to the ground.",
            "The gremlin falls to the ground, as dead as a doornail."
    };


    public MonsterGremlin() {
        super(MonsterTypes.GREMLIN, HEALTH, SPEED, BASIC_CHANCE, SPECIAL_CHANCE, MIN_DMG, MAX_DMG, COOLDOWN,
                MAX_COOLDOWN, MIN_HEAL, MAX_HEAL, HEAL_CHANCE, XP_WORTH, INITIAL_COOLDOWN);
    }

    @Override
    public int basicAtk(final DungeonCharacter theOther) {

        if(myHealth <= 0) {
            throw new IllegalStateException("Cannot attack when dead.");
        }

        int rawDamage = 0;

        double hitChance = Math.random();

        if (hitChance <= myBasicAccuracy) {
            myAttackWasSuccess = true;
            rawDamage = RANDOM.nextInt(myMaxDmg - myMinDmg) + myMinDmg; // Random number between min and max damage
            theOther.inflictDebuff(Debuff.WEAKEN, 1);
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
            rawDamage = RANDOM.nextInt(myMaxDmg - myMinDmg) + 5; // Random number between min and max damage
            theOther.inflictDebuff(Debuff.BLIND, 1);
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
                " Slap - ",
                ""
        };
    }

    @Override
    public String[] getSpecialName() {
        return new String[] {
                "Insult -",
                ""
        };
    }

    @Override
    public String[] getPassiveName() {
        return new String[] {
                "Dodge - Decreases the hero's hit chance", // "gremlin laughs as it nimbly dodges your attack"
                ""
        };
    }

    @Override
    public String getBasicSelectMsg(final DungeonCharacter theOther) {
        return this.getName() + " slaps " + theOther.getName();
    }


    @Override
    public String getSpecialSelectMsg(final DungeonCharacter theOther) {
        return this.getName() + " makes fun of " + theOther.getName() + " with lots of \"your mom\" jokes";
    }


    @Override
    public String[] getDescription() {
        return new String[] {
                "Gremlins are small, mischievous creatures that love to cause trouble.",
                "Watch out for their painful slaps and hurtful insults."
        };
    }

    @Override
    public String getDeathMsg() {
        return DEATH_MSGS[RANDOM.nextInt(DEATH_MSGS.length)];
    }
}
