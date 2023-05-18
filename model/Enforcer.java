package model;

public class Enforcer extends Hero {

    private int special2Cooldown;

    private int special3Cooldown;

    public Enforcer() {
        super(350, 4, 45, 120, 160, 20);
        setSpecial2Cooldown(50);
        setSpecial3Cooldown(65);
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
