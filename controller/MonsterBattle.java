package controller;
import model.*;
import view.*;

public class MonsterBattle {

    /**
     * Starts a new battle between the player and a random
     * monster.
     * @param theHero The player
     * @param theMonster The enemy faced
     */
    public static void newBattle(Hero theHero, Monster theMonster) {

        /* The battle gameplay loop will end as soon as either the
         * player's or the monster's HP hits 0.
         */
        while (theHero.getHitPoints() > 0  && theMonster.getHitPoints() > 0) {
            // Gameplay loop
        }

        if (theHero.getHitPoints() <= 0) {
            // End game
        } else if (theMonster.getHitPoints() <= 0) {
            // continue game
        }

    }

    private void basicAttack(DungeonCharacter theAttacker, DungeonCharacter theTarget) {
        theTarget.setHitPoints(theTarget.getHitPoints() - theAttacker.basicAtk());
    }


    public int playerOption() {
        /* TODO communicate with View to get
         * the player's turn option
         * (Attack, heal. item, etc.)
         */
        return 0;
    }

}
