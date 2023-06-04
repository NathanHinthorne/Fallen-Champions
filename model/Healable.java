package model;

public interface Healable {

    int myMinHeal = 5;
    int myMaxHeal = 10;
    double healChance = 50.0;

    public int heal(final DungeonCharacter theChar);
}
