package model;

public class MonsterSkeleton extends Monster {

    public static final int HEALTH = 140;
    public static final int SPEED = 3;
    public static final double BASIC_CHANCE = 0.6;
    public static final double SPECIAL_CHANCE = 0.8;
    public static final int MIN_DMG = 35;
    public static final int MAX_DMG = 45;
    public static final int COOLDOWN = 2;
    public static final int MAX_COOLDOWN = 3;
    public static final int MIN_HEAL = 20;
    public static final int MAX_HEAL = 40;
    public static final double HEAL_CHANCE = 0.5;
    public static final int XP_WORTH = 120;

    private int bonesThrown;


    public MonsterSkeleton() {
        super(MonsterTypes.SKELETON, HEALTH, SPEED, BASIC_CHANCE, SPECIAL_CHANCE, MIN_DMG, MAX_DMG, COOLDOWN,
                MAX_COOLDOWN, MIN_HEAL, MAX_HEAL, HEAL_CHANCE, XP_WORTH);
        bonesThrown = 0;
    }

    @Override
    public int specialAtk(DungeonCharacter theOther) {
        bonesThrown++;
        return 0;
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
    public String getBasicSelectMsg() {
        return " flings a sharp bone at ";
    } //TODO make this attack damage itself (because it's throwing its own bone)

    @Override
    public String getExtendedBasicSelectMsg() {
        return "";
    }

    @Override
    public String getSpecialSelectMsg() {
        return " shoots an arrow at ";
    }

    @Override
    public String getExtendedSpecialSelectMsg() {
        return "";
    }

    @Override
    public String[] getBasicMissMsg() {
        return new String[] {
                "The bone flies past, leaving you unscathed.",
                "The skeleton's aim is off, and the bone flies harmlessly passes by.",
                "You sidestep the projectile"
        };
    }

    @Override
    public String[] getSpecialMissMsg() {
        return new String[] {
                "You narrowly dodge the skeleton's arrow.",
                "You swiftly duck, barely avoiding the skeleton's arrow.",
                "The skeleton's arrow whizzes past you, missing its target."
        };
    }

    @Override
    public String[] getBasicHitMsg() {
        return new String[] {
                "It strikes you with force!",
                "You wince as the bone strikes you.",
                "The bone hits you, causing you to stumble back."
        };
    }

    @Override
    public String[] getSpecialHitMsg() {
        return new String[] {
                "You gasp in pain as the arrow hits your shoulder.",
                "You feel the sting of the skeleton's arrow as it finds its target.",
                "You feel a searing pain as the arrow finds its mark, weakening you."
        };
    }

    @Override
    public int initialCooldown() {
        return 0;
    }


    @Override
    public String[] getDescription() {
        return new String[] {
                "Skeletons are dangerous residents of the dungeon.",
                "For some odd reason, they like to throw their own bones at you.",
        };
    }

    @Override
    public String[] getDeathMsg() {
        return new String[] {
                "The skeleton collapses into a pile of bones.",
                "The skeleton's bones clatter to the ground.",
                "The skeleton's bones fall to the ground, lifeless."
        };
    }
}
