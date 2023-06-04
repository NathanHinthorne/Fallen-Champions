package controller;
import model.*;
import org.w3c.dom.Text;
import view.*;
import java.util.Random;

public class MonsterBattle {

    private static final Random RANDOMIZER = new Random();
    private final TextModeInterface myGame;

    private final Hero myHero;
    private final Monster myMonster;
    private boolean myGameOver;
    private boolean myVictory;

    public MonsterBattle(Hero theHero, Monster theMonster, final TextModeInterface theView) {
        myHero = theHero;
        myMonster = theMonster;
        myGameOver = false;
        myVictory = false;
        myGame = theView;
    }

    /**
     * Starts a new battle between the player and a random
     * monster.
     * @param thePlayer The player
     * @param theEnemy The monster
     * @return True if the player loses, false if they win.
     */
    public boolean newBattle(Hero thePlayer, Monster theEnemy) {

        /* The battle gameplay loop will end as soon as either the
         * player's or the monster's HP hits 0.
         */
        if (thePlayer.getSpd() > theEnemy.getSpd()) {
            while (!myGameOver) {
                playerTurn(thePlayer, theEnemy);
                DelayMachine.delay(3);
                monsterTurn(theEnemy, thePlayer);
                DelayMachine.delay(3);
            }
        } else {
            while (!myGameOver) {
                monsterTurn(theEnemy, thePlayer);
                DelayMachine.delay(3);
                playerTurn(thePlayer, theEnemy);
                DelayMachine.delay(3);
            }
        }

        if (myVictory) {
            return false;
        }
        return true;

    }

    /**
     * Prompts the user for their choice and displays
     * current Player and Monster HP and other info
     * @param thePlayer
     * @param theEnemy
     */
    private void playerTurn(Hero thePlayer, Monster theEnemy) {
        /**
         * Read input from user to determine what to do
         */
        thePlayer.takeTurn();

        int choice = myGame.battleMenu(thePlayer, theEnemy);
        System.out.println();

        if (choice == 1) {
            thePlayer.basicAtk(theEnemy);

        } else if (choice == 2) {
            thePlayer.specialAtk(theEnemy);

        } else if (choice == 3) {
            myGame.openBag(thePlayer.getMyInventory());

        }

        if (theEnemy.getHitPoints() <= 0) {
            myGameOver = true;
            myVictory = true;
        }

    }

    private void monsterTurn(Monster theEnemy, Hero thePlayer) {
        // Bound to how many things the monster can do
        int choice = RANDOMIZER.nextInt(3);
        theEnemy.takeTurn();

        if (choice == 0) {
            theEnemy.basicAtk(thePlayer);
        } else if (choice == 1) {
            theEnemy.specialAtk(thePlayer);
        } else if (choice == 2 && theEnemy.getHitPoints() < theEnemy.getMaxHitPoints()) {
            theEnemy.heal(theEnemy);
        } else { // Failsafe
            theEnemy.basicAtk(thePlayer);
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
