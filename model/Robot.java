package model;

public class Robot extends Hero {

    private int special2Cooldown;

    private int special3Cooldown;

    private int special4Cooldown;

    private int special5Cooldown;


    protected Robot(int theHitPoints, int theAtkSpd, float theHitChance, int theMinDmg, int theMaxDmg, int theCooldown) {
        super(500, 2, 35, 170, 230, 25);
        setSpecial2Cooldown(35);
        setSpecial3Cooldown(40);
        setSpecial4Cooldown(55);
        setSpecial5Cooldown(100);
    }

    public void setSpecial2Cooldown(int thespecial2Cooldown) {
        thespecial2Cooldown = special2Cooldown;
    }

    public void setSpecial3Cooldown(int thespecial3Cooldown) {
        thespecial3Cooldown = special3Cooldown;
    }

    public void setSpecial4Cooldown(int thespecial4Cooldown) {
        thespecial4Cooldown = special4Cooldown;
    }

    public void setSpecial5Cooldown(int thespecial5Cooldown) {
        thespecial5Cooldown = special5Cooldown;
    }


    /* Will be worked on later */
    public void specialAtk2() {

    }

    /* Will be worked on later */
    public void specialAtk3() {

    }

    /* Will be worked on later */
    public void specialAtk4() {

    }

    /* Will be worked on later */
    public void specialAtk5() {

    }

}
