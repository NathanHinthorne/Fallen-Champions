package model;

public class Warrior extends Hero{

    private int special2Cooldown;


    protected Warrior() {
        super(350, 4, 45, 120, 160, 20);
        setSpecial2Cooldown(50);
    }

    public void setSpecial2Cooldown(int thespecial2Cooldown) {
        thespecial2Cooldown = special2Cooldown;
    }


    /* Will be worked on later */
    public void specialAtk2() {

    }

}
