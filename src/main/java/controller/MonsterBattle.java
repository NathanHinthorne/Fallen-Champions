package controller;
import model.*;
import view.*;

import java.util.Random;


/**
 * The Monster Battle function in the game.
 *
 * @author Brendan Smith
 * @version 1.0 - 5/20/23
 */
public class MonsterBattle {

    /**
     * The randomizer object
     */
    private static final Random RANDOMIZER = new Random();

    /**
     * The text user interface
     */
    private final TUI myGame;

    /**
     * The hero
     */
    private final Hero myHero;

    /**
     * The monster
     */
    private final Monster myMonster;

    /**
     * The game over flag
     */
    private boolean myGameOver;

    /**
     * The victory flag
     */
    private boolean myVictory;

    /**
     * The cheat mode flag
     */
    private boolean myCheatMode;

    /**
     * The audio object
     */
    private static Audio audio;


    public MonsterBattle(Hero theHero, Monster theMonster, final TUI theView, final boolean theCheatMode, Audio theAudio) {
        myHero = theHero;
        myMonster = theMonster;
        myGameOver = false;
        myGame = theView;
        myVictory = false;
        myCheatMode = theCheatMode;
        audio = theAudio;
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

        audio.playMusic(audio.battleSong, true);

        if (myHero.getSpeed() > myMonster.getSpeed()) {
            while (!myGameOver) {
                if (myHero.getHealth() > 0) {
                    audio.playSFX(audio.menuOne);
                    playerTurn();
                    DelayMachine.delay(2);
                }
                if (myMonster.getHealth() > 0) {
                    monsterTurn();
                    DelayMachine.delay(2);
                }
            }
        } else {
            while (!myGameOver) {
                if (myMonster.getHealth() > 0) {
                    monsterTurn();
                    DelayMachine.delay(2);
                }
                if (myHero.getHealth() > 0) {
                    audio.playSFX(audio.menuOne);
                    playerTurn();
                    DelayMachine.delay(2);
                }
            }
        }

        audio.stopAll();
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

        myHero.decreaseCooldown();

        int choice = myGame.battleMenu(myHero, myMonster);
        System.out.println();

        // Basic Attack
        if (choice == 1) {
            int amt = myHero.basicAtk(myMonster);
            myGame.playerMoves(choice, amt, myHero);
            audio.playSFX(audio.heroBasic);
            DelayMachine.delay(2);

        // Special Attack
        } else if (choice == 2) {
            int cooldown = myHero.getCooldown();
            int amt = myHero.specialAtk(myMonster);
            myGame.playerMoves(choice, amt, myHero);
            audio.playSFX(myHero.getSpecialSFX());
            DelayMachine.delay(2);
            if (cooldown > 0) {
                playerTurn();
            }

        // Open inventory
        } else if (choice == 3) {
            boolean itemused = false;
            int slot = myGame.openBag(myHero.getMyInventory());
            if (slot > 3 || slot < 1) {
                playerTurn();
            } else {
                myGame.usePotion(slot, myHero);
                myHero.getMyInventory().consumeItem(myHero, slot); //
            }

        } else if (choice == 6 && myCheatMode) {
            myGame.displayInstaKill();
            myMonster.setHealth(0);
        } else {
            myGame.displayWrongInput();
            playerTurn();
        }

        // Checks if the attack killed the enemy
        if (myMonster.getHealth() <= 0) {
            myGameOver = true;
            myVictory = true;
        }

    }
    /**
     * Prompts the monster for their choice
     * current Player and Monster HP and other info
     */
    private void monsterTurn() {
        // Bound to how many things the monster can do

        myGame.monsterTurnText();
        DelayMachine.delay(2);

        int choice = RANDOMIZER.nextInt(3);
        myMonster.decreaseCooldown();

        // Basic Attack
        if (choice == 0) {
            int amt = myMonster.basicAtk(myHero);
            myGame.monsterMoves(choice, amt);
            DelayMachine.delay(2);

        // Special Attack
        } else if (choice == 1 && myMonster.getCooldown() <= 0) {
            int amt = myMonster.specialAtk(myHero);
            myGame.monsterMoves(choice, amt);
            DelayMachine.delay(2);

        // Heal
        } else if (choice == 2 && myMonster.getHealth() < (myMonster.getMaxHealth() - myMonster.getMaxHeal())) {
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
        if (myHero.getHealth() <= 0) {
            myGameOver = true;
            myVictory = false;
        }
    }
}
