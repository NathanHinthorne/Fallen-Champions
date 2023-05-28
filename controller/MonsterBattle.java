package controller;
import model.*;
import view.*;

public class MonsterBattle {

    private Hero myHero;
    private Monster myMonster;

    public MonsterBattle(Hero theHero, Monster theMonster) {
        myHero = theHero;
        myMonster = theMonster;
    }

    /**
     * Starts a new battle between the player and a random
     * monster.
     */
    public void newBattle() {

        /* The battle gameplay loop will end as soon as either the
         * player's or the monster's HP hits 0.
         */

        is__monsterbattle_ongoing(2);

        while (myHero.getHitPoints() > 0  && myMonster.getHitPoints() > 0) {
            // Gameplay loop
            // Get player input, then cast that input to playerOption(int x)
        }

        if (myHero.getHitPoints() <= 0) {
            // End game
            has_lost(1);
        } else if (myMonster.getHitPoints() <= 0) {
            // continue game
        }

    }

    /**
     * Used to apply the choice the player made
     * in a battle from view, attack, special,
     * or item use represented as an int.
     * @param theOption What the player chose to
     *                  do.
     */
    public void playerOption(int theOption) {
        /* TODO communicate with View to get
         * the player's turn option
         * (Attack, heal. item, etc.)
         */


        if (theOption == 0) {
            // TODO basic attack
            basicAttack(myHero, myMonster);
        } else if (theOption == 1) {
            // TODO special attack

        } else if (theOption == 2) {
            //TODO use item

        }

    }

    private void basicAttack(DungeonCharacter theAttacker, DungeonCharacter theTarget) {
        theTarget.setHitPoints(theTarget.getHitPoints() - theAttacker.basicAtk());
    }

    private void specialAttack(DungeonCharacter theAttacker, DungeonCharacter theTarget) {
        theTarget.setHitPoints(theTarget.getHitPoints() - theAttacker.specialAtk());
    }

    public static boolean has_won(int win) {
        if(win == 1) {
            return true;
        }
        return false;
    }

    public static boolean has_lost(int lose) {
        if(lose == 1) {
            return true;
        }
        return false;
    }

    public static boolean is_ongoing(int ongoing) {
        if(ongoing == 0) {
            return false;
        } else if(has_won(1) || has_lost(1)) {
            return false;
        }

        return true;
    }
    public static boolean is__monsterbattle_ongoing(int ongoing) {
        if(ongoing == 0) {
            return false;
        } else if(has_won(1) || has_lost(1) ) {
            return false;
        }

        return true;
    }

}
