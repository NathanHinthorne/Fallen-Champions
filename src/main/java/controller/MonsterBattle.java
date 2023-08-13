package controller;
import model.characters.Ability;
import model.characters.Debuff;
import model.characters.DungeonCharacter;
import model.characters.heroes.Hero;
import model.characters.heroes.Inventory;
import model.characters.monsters.Monster;
import model.potions.Potion;
import model.potions.PotionDefensive;
import model.potions.PotionOffensive;
import view.*;

import java.util.Map;
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

        myHero.startCooldownAtInitial();
        myMonster.startCooldownAtInitial();
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
                    enemyTurn();
                    DelayMachine.delay(2);
                }
            }
        } else {
            myGame.monsterFasterThanHeroMsg(myMonster);
            while (!myGameOver) {
                if (myMonster.getHealth() > 0) {
                    enemyTurn();
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

        myGame.displayChainSpacer();
        myGame.displayPlayerTurn();

        myGame.displayHeroText();
        myGame.displayHealth(myHero);
        if (myHero.getActiveDebuffs().size() > 0) {
            myGame.displayDebuffs(myHero);
        } else {
            System.out.println();
        }

        myGame.displayMonsterText();
        myGame.displayHealth(myMonster);
        if (myMonster.getActiveDebuffs().size() > 0) {
            myGame.displayDebuffs(myMonster);
        } else {
            System.out.println();
        }
        System.out.println();

        char choice = myGame.battleMenu(myHero);
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

        // run away
        } else if (choice == 'q') {
            //TODO add run away functionality. default 50% chance of success. ninja gets %80


        // cheat mode instakill
        } else if (choice == '6' && myCheatMode) { //TODO change to huge laster firing with asterisks like boss
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
        audio.playSFX(audio.swishOn, -10);
        myGame.displayMonsterStats(myMonster);
        myGame.pressAnyKeyGoBack();
        audio.playSFX(audio.swishOff, -13);
        playerTurn();
    }

    private void performBasicAttack() {
        audio.playSFX(myHero.getBasicSFX(), -10, true);
        myGame.characterSelectAbility(myHero, myMonster, Ability.BASIC);
        Map<Debuff, Integer> oldDebuffs = myHero.getActiveDebuffs(); // snapshot of debuffs before more are applied
        int damage = myHero.basicAtk(myMonster);
        DelayMachine.delay(2);
        myGame.heroAttackResult(myHero, damage);
        inflictDebuffMsg(myMonster, oldDebuffs); // show what debuffs were inflicted on monster
//        myGame.displayHealthMeters(myHero, myMonster);


//        if (myHero.attackWasSuccessful()) { // might need this to do different things if attack misses
//            myGame.heroAttackResult(myHero, damage);
//        } else {
//            myGame.heroAttackResult(myHero, damage);
//        }

        myHero.decreaseCooldown();
        myHero.decreaseDebuffDuration();
    }

    private void performSpecialAttack() {
        // check cooldown
        if (myHero.getCooldown() > 0) {
            audio.playSFX(audio.error, -10, true);
            myGame.displayCooldown(myHero.getCooldown());
            playerTurn();
            return; // change this return statement for the burst attack?
        }

        // proceed with attack
        audio.playSFX(myHero.getSpecialSFX(), -10, true);
        myGame.characterSelectAbility(myHero, myMonster, Ability.SPECIAL);
        Map<Debuff, Integer> oldDebuffs = myHero.getActiveDebuffs(); // snapshot of debuffs before more are applied
        int damage = myHero.specialAtk(myMonster);
        DelayMachine.delay(2);
        myGame.heroAttackResult(myHero, damage);
        if (myHero.wasCritHit()) {
            myGame.playerCritMsg();
        }
        inflictDebuffMsg(myMonster, oldDebuffs); // show what debuffs were inflicted on monster
//        myGame.displayHealthMeters(myHero, myMonster);

//        if (myHero.attackWasSuccessful()) {
//            myGame.heroResultSpecial(myHero, damage);
//            if (myHero.wasCritHit()) {
//                myGame.playerCritMsg();
//            }
//        } else {
//            myGame.playerSpecialMissMsg(myHero);
//        }

        myHero.resetCooldown();
        myHero.decreaseDebuffDuration();
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
                myHero.decreaseDebuffDuration();

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
    private void enemyTurn() {

        myGame.displayChainSpacer();
        myGame.displayEnemyTurn();

        myGame.displayMonsterText();
        myGame.displayHealth(myMonster);
        if (myMonster.getActiveDebuffs().size() > 0) {
            myGame.displayDebuffs(myMonster);
        } else {
            System.out.println();
        }
        System.out.println();

        DelayMachine.delay(2);

        int choice = RANDOMIZER.nextInt(3);
        myMonster.decreaseCooldown();

        //TODO make sure monster initial cooldown is set to 1 or 2?

        //TODO make monster always choose special attack first!!
        // because strategy needs to be for player to remember when monster will use their special next

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
        myGame.characterSelectAbility(myMonster, myHero, Ability.BASIC);
        Map<Debuff, Integer> oldDebuffs = myHero.getActiveDebuffs(); // snapshot of debuffs before more are applied
        int damage = myMonster.basicAtk(myHero);
        myGame.monsterAttackResult(myMonster, myHero, damage);
        inflictDebuffMsg(myHero, oldDebuffs); // show what debuffs were inflicted on hero
//        myGame.displayHealthMeters(myHero, myMonster);
        myMonster.decreaseDebuffDuration();
    }

    private void monsterSpecial() {
        if (myMonster.getCooldown() > 0) {
            System.out.println("DEBUG: Monster is on cooldown, but tried to use special attack!");
        }
        myGame.characterSelectAbility(myMonster, myHero, Ability.SPECIAL);
        Map<Debuff, Integer> oldDebuffs = myHero.getActiveDebuffs(); // snapshot of debuffs before more are applied
        int damage = myMonster.specialAtk(myHero);
        myGame.monsterAttackResult(myMonster, myHero, damage);
        inflictDebuffMsg(myHero, oldDebuffs); // show what debuffs were inflicted on hero
//        myGame.displayHealthMeters(myHero, myMonster);
        myMonster.decreaseDebuffDuration();
    }

    private void monsterHeal() {
        myMonster.heal(myMonster);
        int healAmount = myMonster.getHealAmount();
        myGame.monsterHeal(healAmount);
        myMonster.decreaseDebuffDuration();
    }

    // another solution is to have a field in DungeonCharacter that stores newly applied debuffs
    private void inflictDebuffMsg(final DungeonCharacter theDebuffedCharacter, final Map<Debuff, Integer> theOldDebuffs) {
        Map<Debuff, Integer> newDebuffs = theDebuffedCharacter.getActiveDebuffs();
        for (final Debuff debuff : newDebuffs.keySet()) {

            if (!theOldDebuffs.containsKey(debuff)) { // if the debuff in question is NEW, display the message
                myGame.inflictedDebuffMsg(debuff);
            }
            else { // if the debuff in question is OLD, but the duration has been updated, display the message
                int newDuration = newDebuffs.get(debuff);
                int oldDuration = theOldDebuffs.get(debuff);
                if (newDuration != oldDuration) {
                    myGame.inflictedDebuffMsg(debuff);
                }
            }
        }
    }
}
