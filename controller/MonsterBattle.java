package controller;
import model.*;
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
        myGame = theView;
        myVictory = false;
    }

    /**
     * Starts a new battle between the player and a random
     * monster.
     * @return True if the player wins, false if they lose.
     */
    public boolean newBattle() {

        /* The battle gameplay loop will end as soon as either the
         * player's or the monster's HP hits 0.
         */
        if (myHero.getSpd() > myMonster.getSpd()) {
            while (!myGameOver) {
                if (myHero.getHitPoints() > 0) {
                    playerTurn();
                    DelayMachine.delay(2);
                }
                if (myMonster.getHitPoints() > 0) {
                    monsterTurn();
                    DelayMachine.delay(2);
                }
            }
        } else {
            while (!myGameOver) {
                if (myMonster.getHitPoints() > 0) {
                    monsterTurn();
                    DelayMachine.delay(2);
                }
                if (myHero.getHitPoints() > 0) {
                    playerTurn();
                    DelayMachine.delay(2);
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
     */
    private void playerTurn() {
        /**
         * Read input from user to determine what to do
         */
        myHero.takeTurn();

        int choice = myGame.battleMenu(myHero, myMonster);
        System.out.println();

        // Basic Attack
        if (choice == 1) {
            int amt = myHero.basicAtk(myMonster);
            myGame.playerMoves(choice, amt, myHero);
            DelayMachine.delay(2);

        // Special Attack
        } else if (choice == 2) {
            int cooldown = myHero.getSpecialCooldown();
            int amt = myHero.specialAtk(myMonster);
            myGame.playerMoves(choice, amt, myHero);
            DelayMachine.delay(2);
            if (cooldown > 0) {
                playerTurn();
            }

        // Open inventory
        } else if (choice == 3) {
            boolean itemused = false;
            int slot = myGame.openBag(myHero.getMyInventory());
            if (slot > 4 || slot < 0) {
                playerTurn();
            } else {
                myGame.usePotion(slot-1, myHero);
                myHero.getMyInventory().consumeItem(myHero, slot-1);
            }

        } else if (choice == 6) {
            myGame.displayInstaKill();
            myMonster.setHitPoints(0);
        }

        // Checks if the attack killed the enemy
        if (myMonster.getHitPoints() <= 0) {
            myGameOver = true;
            myVictory = true;
        }

    }

    private void monsterTurn() {
        // Bound to how many things the monster can do

        myGame.monsterTurnText();
        DelayMachine.delay(2);

        int choice = RANDOMIZER.nextInt(3);
        myMonster.takeTurn();

        // Basic Attack
        if (choice == 0) {
            int amt = myMonster.basicAtk(myHero);
            myGame.monsterMoves(choice, amt);
            DelayMachine.delay(2);

        // Special Attack
        } else if (choice == 1 && myMonster.getSpecialCooldown() <= 0) {
            int amt = myMonster.specialAtk(myHero);
            myGame.monsterMoves(choice, amt);
            DelayMachine.delay(2);

        // Heal
        } else if (choice == 2 && myMonster.getHitPoints() < (myMonster.getMaxHitPoints() - myMonster.getMaxHeal())) {
            int amt = myMonster.heal(myMonster);
            myGame.monsterMoves(choice, amt);
            DelayMachine.delay(2);

        // Basic attack failsafe
        } else {
            int amt = myMonster.basicAtk(myHero);
            myGame.monsterMoves(0, amt);
            DelayMachine.delay(2);
        }

        // checks if the attack killed the player
        if (myHero.getHitPoints() <= 0) {
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
