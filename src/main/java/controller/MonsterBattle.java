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
     * Shows the player is in battle
     */
    private static final boolean IN_BATTLE = true;

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

        theHero.setCooldown(0);
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

        audio.playMusic(audio.battleSong, true, -5);

        if (myHero.getSpeed() > myMonster.getSpeed()) {
            while (!myGameOver) {
                if (myHero.getHealth() > 0) {
                    audio.playSFX(audio.menuOne, -10);
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
                    audio.playSFX(audio.menuOne, -10);
                    playerTurn();
                    DelayMachine.delay(2);
                }
            }
        }

        audio.stopAll();
        if (myVictory) {
            checkIfLevelUp();

            return true;
        }
        return false;
    }

    private void checkIfLevelUp() {
        if (myHero.getXP() >= Hero.LEVEL_1_XP && myHero.getLevel() == 0) {
            myHero.levelUp();
            myGame.levelUpMsg(myHero.getLevel());
            audio.playSFX(audio.levelUp, -10);
        } else if (myHero.getXP() >= Hero.LEVEL_2_XP && myHero.getLevel() == 1) {
            myHero.levelUp();
            myGame.levelUpMsg(myHero.getLevel());
            audio.playSFX(audio.levelUp, -10);
        } else if (myHero.getXP() >= Hero.LEVEL_3_XP && myHero.getLevel() == 2) {
            myHero.levelUp();
            myGame.levelUpMsg(myHero.getLevel());
            audio.playSFX(audio.levelUp, -10);
        } else if (myHero.getXP() >= Hero.LEVEL_4_XP && myHero.getLevel() == 3) {
            myHero.levelUp();
            myGame.levelUpMsg(myHero.getLevel());
            audio.playSFX(audio.levelUp, -10);
        } else if (myHero.getXP() >= Hero.LEVEL_5_XP && myHero.getLevel() == 4) {
            myHero.levelUp();
            myGame.levelUpMsg(myHero.getLevel());
            audio.playSFX(audio.levelUp, -10);
        }
    }

    /**
     * Prompts the user for their choice and displays
     * current Player and Monster HP and other info
     */
    private void playerTurn() {

        int choice = myGame.battleMenu(myHero, myMonster);
        System.out.println();

        // Basic Attack
        if (choice == 1) {
            performBasicAttack();

        // Special Attack
        } else if (choice == 2) {
            performSpecialAttack();

        // Open inventory
        } else if (choice == 3) {
            inventorySelectionProcess();

        // cheat mode instakill
        } else if (choice == 6 && myCheatMode) {
            myGame.displayInstaKill();
            myMonster.setHealth(0);

        // wrong input must have been given
        } else {
            audio.playSFX(audio.error, -10);
            myGame.displayWrongInput();
            playerTurn();
        }

        myHero.decreaseCooldown();

        // Checks if the attack killed the enemy
        if (myMonster.getHealth() <= 0) {
            myGameOver = true;
            myVictory = true;
        }

    }

    private void performBasicAttack() {
        myGame.playerSelectsBasicMsg(myHero, myMonster);
        audio.playSFX(myHero.getBasicSFX(), -10);
        int damage = myHero.basicAtk(myMonster);
        DelayMachine.delay(2);

        if (myHero.attackWasSuccessful()) {
            myGame.playerHitsBasicMsg(damage);
        } else {
            myGame.playerMissesMsg();
        }
    }

    private void performSpecialAttack() {
        // check cooldown
        if (myHero.getCooldown() > 0) {
            audio.playSFX(audio.error, -10);
            myGame.displayCooldown(myHero.getCooldown());
            playerTurn();
            return;
        }

        // proceed with attack
        myGame.playerSelectsSpecialMsg(myHero, myMonster);
        audio.playSFX(myHero.getSpecialSFX(), -10);
        int damage = myHero.specialAtk(myMonster);
        DelayMachine.delay(2);

        if (myHero.attackWasSuccessful()) {
            myGame.playerHitsSpecialMsg(damage);
            if (myHero.wasCritHit()) {
                myGame.playerCritMsg();
            }
        } else {
            myGame.playerMissesMsg();
        }

        myHero.resetCooldown();
    }

    public void inventorySelectionProcess() {
        Inventory bag = myHero.getInventory();
        int slotIndex = myGame.openBag(bag, true, audio); // slotIndex is guaranteed to be 1-5

        if (slotIndex == 5) { // back button was pressed
            playerTurn();

        } else {
            Potion potion = myHero.getInventory().getItem(slotIndex);
            if (potion.canUseDuringBattle()) {
                myHero.getInventory().removeItem(slotIndex);

                if (potion instanceof PotionDefensive) {
                    PotionDefensive defPotion = (PotionDefensive) potion;
                    defPotion.effect(myHero);
                    myGame.usePotionMsg(potion);

                } else if (potion instanceof PotionOffensive) {
                    PotionOffensive offPotion = (PotionOffensive) potion;
                    offPotion.effect(myMonster);
                    myGame.usePotionMsg(potion);
                }

            } else {
                audio.playSFX(audio.error, -10);
                myGame.displayCantUseItemDuringBattle(potion);
                inventorySelectionProcess();
            }
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


        //TODO make monster always choose special attack first. make sure monster initial cooldown is set to 1 or 2


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
            myMonster.heal(myMonster);
            int amt = myMonster.getHealAmount();
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
