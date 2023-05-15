package model;

public interface Healable {

    int myMinHeal = 5;
    int myMaxHeal = 10;
    double healChance = 50.0;

    public void heal();
}
