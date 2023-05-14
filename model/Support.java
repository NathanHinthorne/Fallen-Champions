package model;

public class Support extends Hero implements Healable {

    private int minHeal;

    private int maxHeal;

    private float healChance;

    private int special2Cooldown;



    protected Support() {
        super(85,4,75,35,80,15);
        setSpecial2Cooldown(15);

    }

    public void setSpecial2Cooldown(int thespecial2Cooldown) {
        thespecial2Cooldown = special2Cooldown;
    }


    /* Will be worked on later */
    public void specialAtk2() {

    }

    @Override
    public void heal() {
        //TODO heal the support hero
    }
}
