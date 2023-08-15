package model.characters.heroes;

import model.characters.Debuff;
import model.characters.DungeonCharacter;

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
    public static final int MIN_DMG = 15;
    public static final int MAX_DMG = 25;
    public static final int COOLDOWN = 0;
    public static final int MAX_COOLDOWN = 2;
    public static final int INITIAL_COOLDOWN = 0;

    private static final double CRIT_CHANCE = 0.5;

    private static final String[] BASIC_MISS_MSGS = {
            "The sword misses the monster",
            "The sword barely misses the monster",
            "The sword flies out of your hand, completely missing the monster"
    };

    private static final String[] SPECIAL_MISS_MSGS = {
            "The sword misses the monster",
            "The sword barely misses the monster",
            "The sword flies out of your hand, completely missing the monster"
    };

    private static final String[] BASIC_HIT_MSGS = {
            "The sword slices the monster",
            "The sword cuts the monster's leg",
            "The sword slashes the monster's arm"
    };

    private static final String[] SPECIAL_HIT_MSGS = {
            "The sword slices through the monster",
            "The sword cuts the monster",
            "The sword slashes the monster"
    };

    private boolean offensiveStance;


    public HeroSwordsman() {
        super(HeroTypes.SWORDSMAN, HEALTH, SPEED, BASIC_CHANCE, SPECIAL_CHANCE, MIN_DMG, MAX_DMG,
                COOLDOWN, MAX_COOLDOWN, INITIAL_COOLDOWN);

        setBasicSFX("hero_swordsman_basic.wav");
        setSpecialSFX("hero_swordsman_special.wav");
    }

    @Override
    public int basicAtk(final DungeonCharacter theOther) {
        critHit = false;

        int damage;

        if (Math.random() <= myBasicAccuracy) {
            myAttackWasSuccess = true;
            damage = myMinDmg + RANDOM.nextInt(myMaxDmg - myMinDmg + 1);
            myAttackResult = BASIC_HIT_MSGS[RANDOM.nextInt(BASIC_MISS_MSGS.length)];
        } else {
            myAttackWasSuccess = false;
            damage = 0;
            myAttackResult = BASIC_MISS_MSGS[RANDOM.nextInt(BASIC_MISS_MSGS.length)];
        }

        theOther.hurt(damage);

        return damage;
    }

    @Override
    public int specialAtk(final DungeonCharacter theOther) {
        critHit = false;

        int damage;

        if (Math.random() <= mySpecialAccuracy) {
            myAttackWasSuccess = true;
            damage = (int) ((myMinDmg + RANDOM.nextInt(myMaxDmg - myMinDmg + 1)) * 1.8f);
            theOther.inflictDebuff(Debuff.SILENCE, 1);
            myAttackResult = SPECIAL_HIT_MSGS[RANDOM.nextInt(SPECIAL_MISS_MSGS.length)];

            if (Math.random() <= CRIT_CHANCE) {
                critHit = true;
                damage = (int) (damage * 1.5f);
            }

        } else {
            myAttackWasSuccess = false;
            damage = 0;
            myAttackResult = SPECIAL_MISS_MSGS[RANDOM.nextInt(SPECIAL_MISS_MSGS.length)];
        }

        theOther.hurt(damage);

        return damage;
    }

    @Override
    public void initializeCharacterPerBattle() {
        // default setup
        myCooldown = myInitialCooldown;
        myActiveDebuffs.clear();

        // custom setup
        offensiveStance = true;
        startOffensiveStance();
    }

    @Override
    public void uninitializeCharacterPerBattle() {
        // default setup


        // custom setup
        if (offensiveStance) {
            undoOffensiveStance();
        } else {
            undoDefensiveStance();
        }
    }

    @Override
    public void initializeCharacterPerTurn() {
        float healthPercent = (float) myHealth / (float) myMaxHealth;

        if (healthPercent > 0.5 && !offensiveStance) {
            undoDefensiveStance();
            startOffensiveStance();

        } else if (healthPercent <= 0.5 && offensiveStance) {
            undoOffensiveStance();
            startDefensiveStance();
        }
    }


    private void startOffensiveStance() {
        myPassiveMsgs.add(" " + myName + " activates their passive ability, switching to an OFFENSIVE stance.");
        myMinDmg += 10;
        myMaxDmg += 10;
    }

    private void undoOffensiveStance() {
        offensiveStance = false;
        myMinDmg -= 10;
        myMaxDmg -= 10;
    }

    private void startDefensiveStance() {
        myPassiveMsgs.add(" " + myName + " activates their passive ability, switching to a DEFENSIVE stance.");
        myDefense = 0.5f;
    }

    private void undoDefensiveStance() {
        offensiveStance = true;
        myDefense = 0;
    }


    @Override
    public String[] getBasicName() {
        return new String[] {
                "\"Sword Strike\" - Perform a basic sword strike,",
                "skillfully slicing through the enemy's defenses."
        };
    }
    @Override
    public String[] getSpecialName() {
        return new String[] {
                "\"Mighty Swing\" - Unleash a powerful, overhead",
                "slash with pinpoint accuracy (can crit hit)."
        };
    }
    @Override
    public String[] getPassiveName() {
        return new String[] {
                "\"Battle Stance\" - On high health, deal more",
                "damage. On low health, decrease damage taken."
        };
    }

    @Override
    public String getBasicSelectMsg(final DungeonCharacter theOther) {
        return this.getName() + " swings their sword at the " + theOther.getName();
    }
    @Override
    public String getSpecialSelectMsg(final DungeonCharacter theOther) {
        return this.getName() + " grips their sword and arcs a deadly slice towards the " + theOther.getName();
    }

    @Override
    public boolean isUnlocked() {
        return true; // Always unlocked
    }

    @Override
    public String[] getDescription() {
        return new String[] {
                "A master duelist, this hero wields his blade with confidence.",
                "The ability to swap battle stances makes him a versatile fighter."
        };
    }

}
