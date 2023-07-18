package model;

public class MonsterSpider extends Monster {

    public static final int HEALTH = 80;
    public static final int SPEED = 1;
    public static final double BASIC_CHANCE = 0.8;
    public static final double SPECIAL_CHANCE = 0.6;
    public static final int MIN_DMG = 20;
    public static final int MAX_DMG = 40;
    public static final int COOLDOWN = 2;
    public static final int MAX_COOLDOWN = 3;
    public static final int MIN_HEAL = 20;
    public static final int MAX_HEAL = 40;
    public static final double HEAL_CHANCE = 0.5;
    public static final int XP_WORTH = 110;

    public static final double POISON_CHANCE = 0.5;

    public MonsterSpider() {
        super(MonsterTypes.SPIDER, HEALTH, SPEED, BASIC_CHANCE, SPECIAL_CHANCE, MIN_DMG, MAX_DMG, COOLDOWN,
                MAX_COOLDOWN, MIN_HEAL, MAX_HEAL, HEAL_CHANCE, XP_WORTH);
    }

    @Override
    public int specialAtk(DungeonCharacter theOther) {

//        theOther.setTurnIsSkipped(true);
        return 0;
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
    public String getBasicSelectMsg() {
        return " bites ";
    }

    @Override
    public String getExtendedBasicSelectMsg() {
        return "";
    }

    @Override
    public String getSpecialSelectMsg() {
        return " shoots a web at ";
    }

    @Override
    public String getExtendedSpecialSelectMsg() {
        return "";
    }

    @Override
    public String[] getBasicMissMsg() {
        return new String[] {
            "The spider misses its bite!",
            "The spider's bite is too slow, and the hero dodges with ease!",
            "The spider's bite is too weak to pierce the hero's armor!"
        };
    }

    @Override
    public String[] getSpecialMissMsg() {
        return new String[] {
            "The web misses!",
            "The web passes over the hero's head!",
            "The web snaps after wrapping around the hero!"
        };
    }

    @Override
    public String[] getBasicHitMsg() {
        return new String[] {
            "The spider's bite pierces the hero's armor!",
            "The spider's bite hits the hero!",
            "The spider's bite hits the hero's weak spot!"
        };
    }

    @Override
    public String[] getSpecialHitMsg() {
        return new String[] {
            "The web wraps around the hero, immobilizing them for 1 turn!",
            "The web hits the hero, getting them stuck for 1 turn!",
            "The web traps the hero's legs, making them unable to move for 1 turn!"
        };
    }

    @Override
    public int initialCooldown() {
        return 0;
    }


    @Override
    public String[] getDescription() {
        return new String[] {
            "Spiders might have low health, but they are fast and deadly.",
            "With venomous bites and immobilizing webs, it's best to kill them quickly."
        };
    }

    @Override
    public String[] getDeathMsg() {
        return new String[] {
            "The spider's hisses in anger as it collapses.",
            "The spider's legs curl up as it dies.",
            "The spider falls to the ground, dead."
        };
    }
}
