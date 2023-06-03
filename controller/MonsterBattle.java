package controller;
import model.*;
import view.*;
import java.util.Random;

public class MonsterBattle {

    private static final Random RANDOMIZER = new Random();

    private final Hero myHero;
    private final Monster myMonster;
    private boolean myGameOver;
    private boolean myVictory;

    public MonsterBattle(Hero theHero, Monster theMonster) {
        myHero = theHero;
        myMonster = theMonster;
        myGameOver = false;
        myVictory = false;
        newBattle(myHero, myMonster);
    }

    /**
     * Starts a new battle between the player and a random
     * monster.
     */
    public void newBattle(Hero thePlayer, Monster theEnemy) {

        /* The battle gameplay loop will end as soon as either the
         * player's or the monster's HP hits 0.
         */
        if (thePlayer.getSpd() > theEnemy.getSpd()) {
            while (!myGameOver) {
                playerTurn(thePlayer, theEnemy);
                monsterTurn(theEnemy, thePlayer);
            }
        } else {
            while (!myGameOver) {
                monsterTurn(theEnemy, thePlayer);
                playerTurn(thePlayer, theEnemy);
            }
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

        } else if (theOption == 1) {
            // TODO special attack

        } else if (theOption == 2) {
            //TODO use item

        }

    }

    private void playerTurn(Hero thePlayer, Monster theEnemy) {
        /**
         * Read input from user to determine what to do
         */

        if (theEnemy.getHitPoints() <= 0) {
            myGameOver = true;
            myVictory = false;
        }

    }

    private void monsterTurn(Monster theEnemy, Hero thePlayer) {
        // Bound to how many things the monster can do
        int choice = RANDOMIZER.nextInt(3);

        if (choice == 0) {
            theEnemy.basicAtk(thePlayer);
        } else if (choice == 1) {
            theEnemy.specialAtk(thePlayer);
        } else if (choice == 2) {
            theEnemy.heal();
        }

        if (thePlayer.getHitPoints() <= 0) {
            myGameOver = true;
            myVictory = false;
        }

    }






//    public static boolean has_won(int win) {
//        if(win == 1) {
//            return true;
//        }
//        return false;
//    }
//
//    public static boolean has_lost(int lose) {
//        if(lose == 1) {
//            return true;
//        }
//        return false;
//    }
//
//    public static boolean is_ongoing(int ongoing) {
//        if(ongoing == 0) {
//            return false;
//        } else if(has_won(1) || has_lost(1)) {
//            return false;
//        }
//
//        return true;
//    }
//    public static boolean is__monsterbattle_ongoing(int ongoing) {
//        if(ongoing == 0) {
//            return false;
//        } else if(has_won(1) || has_lost(1) ) {
//            return false;
//        }
//
//        return true;
//    }

}
