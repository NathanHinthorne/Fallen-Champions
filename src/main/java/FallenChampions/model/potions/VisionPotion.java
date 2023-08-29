package FallenChampions.model.potions;


import FallenChampions.model.characters.heroes.Hero;

/**
 * A potion that gives the player the ability to see the entire map.
 *
 * @author Nathan Hinthorne
 * @author Brendan Smith
 * @version 1.0
 */
public class VisionPotion extends PotionDefensive implements java.io.Serializable {


    public VisionPotion() { }

    /**
     * The vision potion effect
     * @param thePlayer the potion effect for the Hero to consume
     */
    @Override
    public void effect(final Hero thePlayer) {
        thePlayer.setUsingVisionPotion(true);
    }

    @Override
    public boolean canUseDuringBattle() {
        return false;
    }

    @Override
    public boolean canUseOutsideBattle() {
        return true;
    }

    @Override
    public String useMsg() {
        return " The entire dungeon is now visible for 3 turns. I hope you have a good memory ;)";
    }

    /**
     * The vision potion type
     * @return the vision potion type
     */
    @Override
    public String type() {
        return "Vision Potion";
    }

    @Override
    public String toString() {
        return "p";
    }
}
