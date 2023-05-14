package model;

public class Scientist extends Hero {

    private int special2Cooldown;

    private int special3Cooldown;



    protected Scientist(int theHitPoints, int theAtkSpd, float theHitChance, int theMinDmg, int theMaxDmg, int theCooldown) {
        super(100, 5, 75, 80, 140, 15);
        setSpecial2Cooldown(40);
        setSpecial3Cooldown(50);
    }

    public void setSpecial2Cooldown(int thespecial2Cooldown) {
        thespecial2Cooldown = special2Cooldown;
    }

    public void setSpecial3Cooldown(int thespecial3Cooldown) {
        thespecial3Cooldown = special3Cooldown;
    }

    /* Will be worked on later */
    public void specialAtk2() {

    }
    /* Will be worked on later */
    public void specialAtk3() {

    }
}
