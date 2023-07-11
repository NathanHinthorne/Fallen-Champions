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
     * Funny mode
     */
    private final boolean myFunnyMode;

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


    public MonsterBattle(Hero theHero, Monster theMonster, final TUI theView, final boolean theCheatMode, Audio theAudio, boolean theFunnyMode) {
        myHero = theHero;
        myMonster = theMonster;

        myGameOver = false;
        myGame = theView;
        myVictory = false;
        myCheatMode = theCheatMode;
        myFunnyMode = theFunnyMode;
        audio = theAudio;

        myHero.setCooldown(myHero.initialCooldown());
        myMonster.setCooldown(myMonster.initialCooldown());
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
            myGame.heroFasterThanMonsterMsg(myMonster);
            while (!myGameOver) {
                if (myHero.getHealth() > 0) {
                    audio.playSFX(audio.menuOne, -10, true);
                    playerTurn();
                    DelayMachine.delay(2);
                }
                if (myMonster.getHealth() > 0) {
                    monsterTurn();
                    DelayMachine.delay(2);
                }
            }
        } else {
            myGame.monsterFasterThanHeroMsg(myMonster);
            while (!myGameOver) {
                if (myMonster.getHealth() > 0) {
                    monsterTurn();
                    DelayMachine.delay(2);
                }
                if (myHero.getHealth() > 0) {
                    audio.playSFX(audio.menuOne, -10, true);
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

        char choice = myGame.battleMenu(myHero, myMonster);
        myGame.displayNormalSpacer();
        System.out.println();

        // Basic Attack
        if (choice == '1') {
            performBasicAttack();

        // Special Attack
        } else if (choice == '2') {
            performSpecialAttack();

        // Open inventory
        } else if (choice == 'e') {
            audio.playSFX(audio.heroBagOpen, -10);
            inventoryMenu();

        // check monster stats
        } else if (choice == 'r') {
            checkMonsterStats();

        // cheat mode instakill
        } else if (choice == '6' && myCheatMode) {
            myGame.displayInstaKill();
            myMonster.setHealth(0);

        // wrong input must have been given
        } else {
            audio.playSFX(audio.error, -10, true);
            myGame.displayWrongInput();
            playerTurn();
        }

        // Checks if the attack killed the enemy
        if (myMonster.getHealth() <= 0) {
            myGameOver = true;
            myVictory = true;
        }

    }

    private void checkMonsterStats() {
        myGame.displayMonsterStats(myMonster);
        playerTurn();
    }

    private void performBasicAttack() {
        audio.playSFX(myHero.getBasicSFX(), -10, true);
        myGame.playerSelectsBasicMsg(myHero, myMonster);
        int damage = myHero.basicAtk(myMonster);

        if (myHero.attackWasSuccessful()) {
            myGame.playerHitsBasicMsg(myHero, damage, myFunnyMode);
        } else {
            myGame.playerBasicMissMsg(myHero);
        }

        myHero.decreaseCooldown();
    }

    private void performSpecialAttack() {
        // check cooldown
        if (myHero.getCooldown() > 0) {
            audio.playSFX(audio.error, -10, true);
            myGame.displayCooldown(myHero.getCooldown());
            playerTurn();
            return;
        }

        // proceed with attack
        audio.playSFX(myHero.getSpecialSFX(), -10, true);
        myGame.playerSelectsSpecialMsg(myHero, myMonster);
        int damage = myHero.specialAtk(myMonster);
        DelayMachine.delay(2);

        if (myHero.attackWasSuccessful()) {
            myGame.playerHitsSpecialMsg(myHero, damage);
            if (myHero.wasCritHit()) {
                myGame.playerCritMsg();
            }
        } else {
            myGame.playerSpecialMissMsg(myHero);
        }

        myHero.resetCooldown();
    }

    public void inventoryMenu() {
        Inventory bag = myHero.getInventory();
        char input = myGame.openBag(bag, true, audio); // input is guaranteed to be 1-4 or e

        if (input != 'e') { // back button was pressed
            int slotIndex = Character.getNumericValue(input)-1; // convert input to int (with -1 due to array indexing)

            Potion potion = bag.getItem(slotIndex);
            if (potion.canUseDuringBattle()) {
                bag.removeItem(slotIndex);

                if (potion instanceof PotionDefensive) {
                    PotionDefensive defPotion = (PotionDefensive) potion;
                    defPotion.effect(myHero);

                } else if (potion instanceof PotionOffensive) {
                    PotionOffensive offPotion = (PotionOffensive) potion;
                    offPotion.effect(myMonster);
                }

                audio.playSFX(audio.heroDrinkPotion, -10);
                myGame.usePotionMsg(potion, slotIndex);

                myHero.decreaseCooldown();

            } else {
                audio.playSFX(audio.error, -10, true);
                myGame.displayCantUseItemDuringBattle(potion);
                inventoryMenu();
            }

        } else { // back button was pressed
            myGame.closeBag(audio);
            playerTurn();
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



        //TODO make sure monster initial cooldown is set to 1 or 2?

        //TODO make monster always choose special attack first.

        // Basic Attack
        if (choice == 0) {
            monsterBasic();

        // Special Attack
        } else if (choice == 1 && myMonster.getCooldown() <= 0) {
            monsterSpecial();

        // Heal
        } else if (choice == 2 && myMonster.getHealth() < (myMonster.getMaxHealth() - myMonster.getMaxHeal())) {
            monsterHeal();

            // Basic attack failsafe
        } else {
            monsterBasic();
        }

        // checks if the attack killed the player
        if (myHero.getHealth() <= 0) {
            myGameOver = true;
            myVictory = false;
        }
    }


    private void monsterBasic() {
        myGame.monsterSelectsBasicMsg(myMonster, myHero);

        int damage = myMonster.basicAtk(myHero);
        if (myMonster.attackWasSuccessful()) {
            myGame.monsterHitsBasicMsg(myMonster, myHero, damage);
        } else {
            myGame.monsterBasicMiss(myMonster);
        }
    }

    private void monsterSpecial() {
        myGame.monsterSelectsSpecialMsg(myMonster, myHero);

        int damage = myMonster.basicAtk(myHero);
        if (myMonster.attackWasSuccessful()) {
            myGame.monsterHitsSpecialMsg(myMonster, myHero, damage);
        } else {
            myGame.monsterSpecialMiss(myMonster);
        }
    }

    private void monsterHeal() {
        myMonster.heal(myMonster);
        int healAmount = myMonster.getHealAmount();
        myGame.monsterHealMsg(healAmount);
        DelayMachine.delay(2);
    }


}
