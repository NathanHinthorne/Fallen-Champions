package model;

public class Hero_Support extends Hero implements Healable,java.io.Serializable {
    // The minimum heal
    private int minHeal;
    // The maximum heal
    private int maxHeal;
    // The heal chance
    private float healChance;
    // The special cooldown
    private int special2Cooldown;


    /**
     * Super constructor for hero support
     */
    public Hero_Support() {
        super(85,4,35,75,55,35, 85, 0, HeroTypes.SUPPORT);
//        setSpecial2Cooldown(15);

    }

//    /**
//     * Sets the special cooldown
//     * @param thespecial2Cooldown the special cooldown
//     */
//    public void setSpecial2Cooldown(int thespecial2Cooldown) {
//        thespecial2Cooldown = special2Cooldown;
//    }

    /**
     * Second special attack for hero
     */
    @Override
    public int specialAtk(DungeonCharacter theOther) {

        this.setHitChance(generateHitChance(getLowHitChance(), getHighHitChance()));
        int pointer = MY_RANDOM.nextInt(100);

        if(getHitChance() >= pointer) {
            // Attack lands
            if (getSpecialCooldown() < 1) {
                theOther.setHitPoints(theOther.getHitPoints() - getMaxDamage());
                this.setSpecialCooldown(MAX_SPECIAL_COOLDOWN); // reset the cooldown
                this.heal(this);
                return getMaxDamage();
            } else {
                return -1;
            }
        } else {
            // Attack failed
            return 0;
        }

        //getSpecialCooldown() = MAX_SPECIAL_COOLDOWN; // reset the cooldown
    }

    /**
     * Generates the heal chance
     * @param theLowChance the low chance
     * @param theHighChance the high chance
     * @return the heals chance
     */
    public static int generateHealChance(int theLowChance, int theHighChance) {
        return theLowChance + MY_RANDOM.nextInt(theHighChance - theLowChance + 1);
    }

    /**
     * Heals the hero
     */
    @Override
    public int heal(final DungeonCharacter thePlayer) {
        //TODO heal the support hero
        int healAmt = generateHealChance(minHeal, maxHeal);
        thePlayer.setHitPoints(getHitPoints() + healAmt);
        return healAmt;
    }
}
