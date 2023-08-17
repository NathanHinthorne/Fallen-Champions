package model.potions;

import model.characters.monsters.Monster;

public abstract class PotionOffensive implements Potion {

    /**
     * The potion effect
     * @param theMonster the potion effect for the monster to get hit by
     */
    public abstract void effect(final Monster theMonster);

}