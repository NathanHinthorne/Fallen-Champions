package model;

public class Monster_Warlock extends Monster {


    public Monster_Warlock() {

        super(300,
                3,
                55,
                96,
                56,
                60,
                125,
                15,
                65,
                200,
                20,
                MonsterTypes.WARLOCK);
    }

    /**
     * Second special Attack for Monster Boss
     */
    @Override
    public void specialAtk(DungeonCharacter theOther) {
        if(getHitPoints() <= 0)
        {
            throw new IllegalArgumentException("Hit Points cannot be less than zero");
        }

        if(getHitChance() > getLowHitChance() && getHitChance() < getHighHitChance()) {
            // Attack successful
            theOther.setHitPoints(theOther.getHitPoints() - getMaxDamage());
        } else { // Will be worked on later, planned to be an error message
            // Attack failed
            System.out.println("Attack Failed!");

        }

        //getSpecialCooldown() = MAX_SPECIAL_COOLDOWN; // reset the cooldown

    }
}
