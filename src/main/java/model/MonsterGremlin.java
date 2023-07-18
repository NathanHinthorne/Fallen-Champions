package model;

public class MonsterGremlin extends Monster {

    public static final int HEALTH = 70;
    public static final int SPEED = 4;
    public static final double BASIC_CHANCE = 0.8;
    public static final double SPECIAL_CHANCE = 0.6;
    public static final int MIN_DMG = 20;
    public static final int MAX_DMG = 40;
    public static final int COOLDOWN = 2;
    public static final int MAX_COOLDOWN = 3;

    public static final int MIN_HEAL = 20;
    public static final int MAX_HEAL = 40;
    public static final double HEAL_CHANCE = 0.5;
    public static final int XP_WORTH = 100;


    public MonsterGremlin() {
        super(MonsterTypes.GREMLIN, HEALTH, SPEED, BASIC_CHANCE, SPECIAL_CHANCE, MIN_DMG, MAX_DMG, COOLDOWN,
                MAX_COOLDOWN, MIN_HEAL, MAX_HEAL, HEAL_CHANCE, XP_WORTH);
    }

    @Override
    public int specialAtk(DungeonCharacter theOther) {

        return 0;
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
                "",
                ""
        };
    }

    @Override
    public String getBasicSelectMsg() {
        return " slaps ";
    }

    @Override
    public String getExtendedBasicSelectMsg() {
        return "";
    }

    @Override
    public String getSpecialSelectMsg() {
        return " makes fun of ";
    }

    @Override
    public String getExtendedSpecialSelectMsg() {
        return " with lots of \"your mom\" jokes!";
    }

    @Override
    public String[] getBasicMissMsg() {
        return new String[]{
                "The slap veers off course, hitting another nearby gremlin instead! You hear a yelp of pain and smile to yourself",
                "The slap whiffs through the air, failing to find its mark!",
                "The slap goes wide, hitting thin air!"};
    }

    @Override
    public String[] getSpecialMissMsg() {
        return new String[]{
                "You brush off the gremlin's feeble attempt at insults.",
                "The gremlin's insults are so bad, you can't help but laugh!",
                "The gremlin's words fall on deaf ears as you remain composed."};
    }

    @Override
    public String[] getBasicHitMsg() {
        return new String[]{
                "The slap lands with a resounding smack on the hero's face!",
                "The slap connects with the hero's face!",
                "The gremlin's precise slap lands on the hero's chest!"};
    }

    @Override
    public String[] getSpecialHitMsg() {
        return new String[] {
                "The gremlin's biting words strike a nerve, dampening your spirit.",
                "The gremlin's hurtful words strike deep, lowering your morale.",
                "The insults hit their mark, fueling a sense of anger deep within you."};
    }

    @Override
    public int initialCooldown() {
        return 0;
    }


    @Override
    public String[] getDescription() {
        return new String[] {
                "Gremlins are small, mischievous creatures that love to cause trouble.",
                "Watch out for their painful slaps and hurtful insults."
        };
    }

    @Override
    public String[] getDeathMsg() {
        return new String[] {
                "The gremlin falls to the ground, defeated.",
                "The gremlins gives a shrill cry as it falls to the ground.",
                "The gremlin falls to the ground, as dead as a doornail."
        };
    }
}
