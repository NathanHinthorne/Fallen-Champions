package model;

public class Robot extends Hero {

    private int special2Cooldown;

    private int special3Cooldown;

    private int special4Cooldown;

    private int special5Cooldown;


    protected Robot(int theHitPoints, int theAtkSpd, float theHitChance, int theMinDmg, int theMaxDmg, int theCooldown) {
        super(theHitPoints, theAtkSpd, theHitChance, theMinDmg, theMaxDmg, theCooldown);
    }


}
