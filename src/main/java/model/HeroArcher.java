package model;

/**
 * The swordsman hero.
 *
 * @author Nathan Hinthorne
 * @version 6/23/2023
 */
public class HeroArcher extends Hero {

    public static final int HEALTH = 200;
    public static final int SPEED = 3;
    public static final double BASIC_CHANCE = 0.7;
    public static final double SPECIAL_CHANCE = 0.9;
    public static final int MIN_DMG = 20;
    public static final int MAX_DMG = 40;
    public static final int COOLDOWN = 0;
    public static final int MAX_COOLDOWN = 2;
    public static final int INITIAL_COOLDOWN = 0;

    private static final double CRIT_CHANCE = 0.5;

    private static final String[] BASIC_MISS_MSGS = { //TODO let the getMsg methods handle the choosing of the msg
            "The arrow misses the target",
            "The arrow misses the monster by a hair",
            "The veers off-course, flying past the monster"
    };

    private static final String SPECIAL_MISS_MSG = "The arrows all miss";

    private static final String[] BASIC_HIT_MSGS = {
            "The arrow pierces through the monster's defenses",
            "The arrow strikes the target",
            "The arrow finds its mark, hitting the monster's chest"
    };

    private static final String[] SPECIAL_HIT_MSGS = {
            "1 of the 3 arrows lands a hit!",
            "2 of the 3 arrows score hits!",
            "3 of the 3 arrows strike the monster!"
    };



    public HeroArcher() {
        super(HeroTypes.ARCHER, HEALTH, SPEED, BASIC_CHANCE, SPECIAL_CHANCE, MIN_DMG, MAX_DMG,
                COOLDOWN, MAX_COOLDOWN, INITIAL_COOLDOWN);

        setBasicSFX("hero_archer_basic.wav");
        setSpecialSFX("hero_archer_special.wav");
    }

    @Override
    public int basicAtk(DungeonCharacter theOther) {
        int dmg = 0;

        if (Math.random() <= myBasicChance) {
            myAttackWasSuccess = true;
            dmg = myMinDmg + RANDOM.nextInt(myMaxDmg - myMinDmg + 1);
        } else {
            myAttackWasSuccess = false;
        }

        // set the monster's health
        theOther.hurt(dmg);

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
                "\"Swift Draw\" - Deftly draw back and fire",
                "upon the target, inflicting [negative affect]"
        };
    }
    @Override
    public String[] getSpecialName() {
        return new String[] {
                "\"Triple Shot\" - Rapidly fire a barrage of",
                "3 arrows, turning the target into a pincushion."
        };
    }
    @Override
    public String[] getPassiveName() {
        return new String[] {
                "\"Sniper\" - ",
                ""
        };
    }

    @Override
    public String getBasicSelectMsg(final DungeonCharacter theOther) {
        return this.getName() + " nocks an arrow and takes aim at the " + theOther.getName() + ".";
    }
    @Override
    public String getSpecialSelectMsg(final DungeonCharacter theOther) {
        return this.getName() + " pulls the bowstring taut and releases a hail of arrows towards the " + theOther.getName() + ".";
    }

    @Override
    public boolean isUnlocked() {
        return true; // Always unlocked
    }

    @Override
    public String[] getDescription() {
        return new String[] {
                "Nothing escapes the watchful eye or the swift bow of the archer.",
                "With lightning reflexes, he can fire volleys of arrows in seconds.",
        };
    }



}
