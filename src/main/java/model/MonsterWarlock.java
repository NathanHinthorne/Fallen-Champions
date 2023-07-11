package model;

public class MonsterWarlock extends Monster {

    public static final int HEALTH = 150;
    public static final int SPEED = 1;
    public static final double BASIC_CHANCE = 0.8;
    public static final double SPECIAL_CHANCE = 0.6;
    public static final int MIN_DMG = 20;
    public static final int MAX_DMG = 40;
    public static final int COOLDOWN = 2;
    public static final int MAX_COOLDOWN = 3;
    public static final String BASIC_MSG = " casts a ball of lightning at ";
    public static final String BASIC_MSG2 = "!";
    public static final String[] BASIC_MISS_MSG =
            {"The punch goes straight through a nearby brick wall!",
                    "The punch misses!",
                    "The punch barely misses!"};

    public static final String[] BASIC_HIT_MSG =
            {"The punch lands with a sickening thud!",
                    "The punch delivers a crushing blow to the hero!",
                    "The punch connects with the brave young adventurer!"};

    public static final String SPECIAL_MSG = " unleashes a fiery inferno upon "; // change to something else that doesn't do straight damage
    public static final String SPECIAL_MSG2 = " and throws them against the wall!";
    public static final String[] SPECIAL_MISS_MSG =
            {"The sword misses the "};
    public static final String[] SPECIAL_HIT_MSG =
            {"The sword slices through the "};
    public static final int MIN_HEAL = 20;
    public static final int MAX_HEAL = 40;
    public static final double HEAL_CHANCE = 0.5;
    public static final int XP_WORTH = 250;


    public MonsterWarlock() {
        super(MonsterTypes.WARLOCK, HEALTH, SPEED, BASIC_CHANCE, SPECIAL_CHANCE, MIN_DMG, MAX_DMG, COOLDOWN,
                MAX_COOLDOWN, MIN_HEAL, MAX_HEAL, HEAL_CHANCE, XP_WORTH);
    }

    @Override
    public int specialAtk(DungeonCharacter theOther) {

        return 0;
    }

    @Override
    public String getBasicName() {
        return " Lightning Bolt - ";
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
    public String getBasicSelectMsg() {
        return "";
    }

    @Override
    public String getExtendedBasicSelectMsg() {
        return "";
    }

    @Override
    public String getSpecialSelectMsg() {
        return "";
    }

    @Override
    public String getExtendedSpecialSelectMsg() {
        return "";
    }

    @Override
    public String[] getBasicMissMsg() {
        return new String[] {
                ""
        };
    }

    @Override
    public String[] getSpecialMissMsg() {
        return new String[] {
                ""
        };
    }

    @Override
    public String[] getBasicHitMsg() {
        return new String[] {
                ""
        };
    }

    @Override
    public String[] getSpecialHitMsg() {
        return new String[] {
                ""
        };
    }

    @Override
    public int initialCooldown() {
        return 2;
    }


    @Override
    public String[] getDescription() {
        return new String[] {
            "Warlocks are powerful magic users that unleash devastating spells upon their foes.",
            "Their damage fluctuates as they charge up and release energy, so defeat them in their weak moments.",
        }; //"making them a formidable foe"
    }

    @Override
    public String[] getDeathMsg() {
        return new String[] {
                "The warlock gives one final breath before collapsing to the ground.",
                "The warlock falls to the ground, defeated.",
                "The warlock howls with rage and crumples to the floor."
        };
    }
}
