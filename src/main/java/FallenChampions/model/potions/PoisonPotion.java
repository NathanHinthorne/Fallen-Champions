package FallenChampions.model.potions;

import FallenChampions.model.characters.monsters.Monster;

import java.util.Random;

/**
 * A poison potion that does damage to monsters over time
 * @author Nathan Hinthorne
 */
public class PoisonPotion extends PotionOffensive {
    /**
     * Randomizes the health potion amt
     */
    private static Random random = new Random();


    /**
     * constructor for health potion
     */
    public PoisonPotion() {

    }

    @Override
    public void effect(Monster theMonster) {

    }

    @Override
    public boolean canUseDuringBattle() {
        return true;
    }

    @Override
    public boolean canUseOutsideBattle() {
        return false;
    }


    /**
     * Gets the details of the potion and player
     * @return the details of the potion and player
     */
    @Override
    public String useMsg() {
        return "You threw a poison potion at the monster! It will now take damage over time.";
    }

    @Override
    public String toString() {
        return "p";
    }

    /**
     * The potion type
     * @return the potion type
     */
    @Override
    public String type() {
        return "Poison Potion";
    }

}
