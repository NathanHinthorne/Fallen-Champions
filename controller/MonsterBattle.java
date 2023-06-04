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
     * @return True if the player wins, false if they lose.
     */
    public boolean newBattle(Hero thePlayer, Monster theEnemy) {

        /* The battle gameplay loop will end as soon as either the
         * player's or the monster's HP hits 0.
         */
        if (thePlayer.getSpd() > theEnemy.getSpd()) {
            while (!myGameOver) {
                if (thePlayer.getHitPoints() > 0) {
                    playerTurn(thePlayer, theEnemy);
                    DelayMachine.delay(3);
                }
                if (theEnemy.getHitPoints() > 0) {
                    monsterTurn(theEnemy, thePlayer);
                    DelayMachine.delay(3);
                }
            }
        } else {
            while (!myGameOver) {
                if (theEnemy.getHitPoints() > 0) {
                    monsterTurn(theEnemy, thePlayer);
                    DelayMachine.delay(3);
                }
                if (thePlayer.getHitPoints() > 0) {
                    playerTurn(thePlayer, theEnemy);
                    DelayMachine.delay(3);
                }
            }
        }

        if (myVictory) {
            return true;
        }
        return false;

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

        // Basic Attack
        if (choice == 1) {
            int amt = thePlayer.basicAtk(theEnemy);
            myGame.playerMoves(choice, amt, thePlayer);
            DelayMachine.delay(2);

        // Special Attack
        } else if (choice == 2) {
            int temp = thePlayer.getSpecialCooldown();
            int amt = thePlayer.specialAtk(theEnemy);
            myGame.playerMoves(choice, amt, thePlayer);
            DelayMachine.delay(2);
            if (temp > 0) {
                playerTurn(thePlayer,theEnemy);
            }

        // Open inventory
        } else if (choice == 3) {
            boolean itemused = false;
            int slot = myGame.openBag(thePlayer.getMyInventory());
            if (slot > 4 || slot < 0) {
                playerTurn(thePlayer,theEnemy);
            } else {
                myGame.usePotion(slot-1, thePlayer);
                thePlayer.getMyInventory().consumeItem(thePlayer, slot-1);
            }

        }

        // Checks if the attack killed the enemy
        if (theEnemy.getHitPoints() <= 0) {
            myGameOver = true;
            myVictory = true;
        }

    }

    private void monsterTurn(Monster theEnemy, Hero thePlayer) {
        // Bound to how many things the monster can do

        myGame.monsterTurnText();
        DelayMachine.delay(2);

        int choice = RANDOMIZER.nextInt(3);
        theEnemy.takeTurn();

        // Basic Attack
        if (choice == 0) {
            int amt = theEnemy.basicAtk(thePlayer);
            myGame.monsterMoves(choice, amt);
            DelayMachine.delay(2);

        // Special Attack
        } else if (choice == 1 && theEnemy.getSpecialCooldown() <= 0) {
            int amt = theEnemy.specialAtk(thePlayer);
            myGame.monsterMoves(choice, amt);
            DelayMachine.delay(2);

        // Heal
        } else if (choice == 2 && theEnemy.getHitPoints() < (theEnemy.getMaxHitPoints() - theEnemy.getMaxHeal())) {
            int amt = theEnemy.heal(theEnemy);
            myGame.monsterMoves(choice, amt);
            DelayMachine.delay(2);

        // Basic attack failsafe
        } else {
            int amt = theEnemy.basicAtk(thePlayer);
            myGame.monsterMoves(0, amt);
            DelayMachine.delay(2);
        }

        // checks if the attack killed the player
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
