package FallenChampions.controller;

import FallenChampions.model.characters.Ability;
import FallenChampions.model.characters.Debuff;
import FallenChampions.model.characters.DungeonCharacter;
import FallenChampions.model.dungeon.BattleResult;
import FallenChampions.model.potions.PotionDefensive;
import FallenChampions.model.potions.PotionOffensive;
import FallenChampions.view.AudioManager;
import FallenChampions.model.characters.heroes.Hero;
import FallenChampions.model.characters.heroes.Inventory;
import FallenChampions.model.characters.monsters.Monster;
import FallenChampions.model.potions.Potion;
import FallenChampions.view.TUI2;

import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.CompletableFuture;


/**
 * The Monster Battle function in the game.
 *
 * @author Brendan Smith
 * @author Nathan Hinthorne
 * @version 1.0 - 5/20/23
 */
public class MonsterBattle2 {

    /**
     * The randomizer object
     */
    private static final Random RANDOMIZER = new Random();

    /**
     * Funny mode
     */
    private final boolean myFunnyMode;

    /**
     * Debug mode
     */
    private final boolean myDebugMode;

    /**
     * The cheat mode flag
     */
    private final boolean myCheatMode;

    /**
     * The text user interface
     */
    private static TUI2 tui;

    /**
     * The hero
     */
    private final Hero myHero;

    /**
     * The monster
     */
    private final Monster myMonster;

    /**
     * 1 if the current character's minDmg was originally an odd number, 0 otherwise
     */
    int minWeakenOffset;

    /**
     * 1 if the current character's maxDmg was originally an odd number, 0 otherwise
     */
    int maxWeakenOffset;

    /**
     * The game over flag
     */
    private boolean myGameOver;

    /**
     * The victory flag
     */
    private boolean myVictory;

    /**
     * Whether the hero ran away
     */
    private boolean myHeroRanAway;

    /**
     * The audio object
     */
    private static AudioManager audio;


    public MonsterBattle2(final Hero theHero, final Monster theMonster,
                         final boolean theCheatMode, final boolean theDebugMode, final boolean theFunnyMode) {

        tui = TUI2.getInstance();
        audio = AudioManager.getInstance();

        myGameOver = false;
        myVictory = false;
        myCheatMode = theCheatMode;
        myFunnyMode = theFunnyMode;
        myDebugMode = theDebugMode;

        myHero = theHero;
        myMonster = theMonster;
        myHero.initializeCharacterPerBattle();
        myMonster.initializeCharacterPerBattle();
    }

    /**
     * Starts a new battle between the player and a random
     * monster.
     * @return the result of the battle, win, lose, or run
     */
    public BattleResult newBattle() {

        /* The battle gameplay loop will end as soon as either the
         * player's or the monster's HP hits 0.
         */

        audio.playMusic(audio.battleSong, true, -5);

        if (myHero.getSpeed() > myMonster.getSpeed()) {
            tui.heroFasterThanMonsterMsg(myMonster);
            while (!myGameOver) {
                if (myHero.getHealth() > 0) {
                    audio.playSFX(audio.menu1, 100);
                    heroTurn();
                    SleepDelay.delay(2);
                }
                if (myMonster.getHealth() > 0 && !myGameOver) { // prevents monster from taking a 2nd turn after hero runs
                    monsterTurn();
                    SleepDelay.delay(2);
                }
            }
        } else {
            tui.monsterFasterThanHeroMsg(myMonster);
            while (!myGameOver) {
                if (myMonster.getHealth() > 0) {
                    monsterTurn();
                    SleepDelay.delay(2);
                }
                if (myHero.getHealth() > 0) {
                    audio.playSFX(audio.menu1, 100);
                    heroTurn();
                    SleepDelay.delay(2);
                }
            }
        }

        myHero.uninitializeCharacterPerBattle();
        myMonster.uninitializeCharacterPerBattle();

        audio.stopAll();

        if (myHeroRanAway) {
            return BattleResult.RUN;
        } else if (myVictory) {
            return BattleResult.WIN;
        } else {
            return BattleResult.LOSE;
        }
    }

    /**
     * Prompts the user for their choice and displays
     * current Player and Monster HP and other info
     */
    private void heroTurn() {

        tui.displayChainSpacer();
        tui.displayPlayerTurn();

        tui.displayHeroAndMonsterInfo(myHero, myMonster);

        myHero.initializeCharacterPerTurn();

        Queue<String> passiveMsgs = myHero.getPassiveMsgs();
        if (!passiveMsgs.isEmpty()) {
            tui.passiveAbilityActivated(passiveMsgs); // ex: show swordsman defense stance msg
        }
        tui.println();

        boolean debuffMsgTriggered = false;
        if (myHero.hasDebuff(Debuff.STUCKIFY)) {
            stuckProcess(myHero);
            return; // skip turn
        }
        if (myHero.hasDebuff(Debuff.BLIND)) {
            blindedProcess(myHero);
            debuffMsgTriggered = true;
        }
        if (myHero.hasDebuff(Debuff.POISON)) {
            poisonedProcess(myHero);
            debuffMsgTriggered = true;
        }
        if (myHero.hasDebuff(Debuff.SILENCE)) {
            silencedProcess(myHero);
            debuffMsgTriggered = true;
        }
        if (myHero.hasDebuff(Debuff.VULNERATE)) {
            vulnerableProcess(myHero);
            debuffMsgTriggered = true;
        }
        if (myHero.hasDebuff(Debuff.WEAKEN)) {
            weakenedProcess(myHero);
            debuffMsgTriggered = true;
        }

        if (debuffMsgTriggered) {
            tui.println();
        }

        // menu
        CompletableFuture<Character> userInputFuture = tui.battleMenu(myHero);
        char userInput = userInputFuture.join();

        tui.println();
        // Basic Attack
        if (userInput == '1') {
            heroBasic();

            // Special Attack
        } else if (userInput == '2') {
            heroSpecial();

            // Open inventory
        } else if (userInput == 'e') {
            audio.playSFX(audio.heroBagOpen, 100);
            inventoryMenu();

            // check monster stats
        } else if (userInput == 'r') {
            checkMonsterStats();

            // run away
        } else if (userInput == 'q') {
            runAwayProcess(myDebugMode);

        } else if (userInput == 'f') {
            audio.playSFX(audio.swishOn, 100);
            tui.debugHeroStats(myHero);
            tui.pressAnyKeyGoBack();
            audio.playSFX(audio.swishOff, 80);
            heroTurn();

            // cheat mode instakill
        } else if (userInput == '6' && myCheatMode) { //TODO change to huge laser firing with asterisks like boss
            tui.displayInstaKill();
            myMonster.setHealth(0);

            // wrong input must have been given
        } else {
            audio.playSFX(audio.error, 100);
            tui.displayWrongInput();
            heroTurn();
        }


        //! need to put inside thenApplySync() to prevent from running before it's supposed to?
        // Checks if the attack killed the enemy
        if (myMonster.getHealth() <= 0) {
            myGameOver = true;
            myVictory = true;
        }
    }

    private void runAwayProcess(final boolean debugMode) {
        tui.displayRunAway(myHero);
        double runAwayChance = Math.random();
        if (runAwayChance < myHero.getRunSuccessChance() || debugMode) {
//            myAudio.playSFX(myAudio.runAwaySuccess, 100);
            tui.displayRunAwaySuccess();
            myHeroRanAway = true;
            myGameOver = true;
        } else {
//            myAudio.playSFX(myAudio.runAwaySuccess, 100);
            tui.displayRunAwayFail();
            myHeroRanAway = false;
        }
    }


    private void checkMonsterStats() {
        audio.playSFX(audio.swishOn, 100);
        tui.displayMonsterStats(myMonster);
        tui.pressAnyKeyGoBack();
        audio.playSFX(audio.swishOff, 80);
        heroTurn();
    }

    private void heroBasic() {
        audio.playSFX(myHero.getBasicSFX(), 100);
        tui.characterSelectAbility(myHero, myMonster, Ability.BASIC);
//        Map<Debuff, Integer> oldDebuffs = myHero.getActiveDebuffs(); // snapshot of debuffs before more are applied
        int damage = myHero.basicAtk(myMonster);
        SleepDelay.delay(2);
        tui.heroAttackResult(myHero, damage);
        inflictDebuffMsg(myMonster); // show what debuffs were inflicted on monster

//        myGame.displayHealthMeters(myHero, myMonster);


//        if (myHero.attackWasSuccessful()) { // might need this to do different things if attack misses
//            myGame.heroAttackResult(myHero, damage);
//        } else {
//            myGame.heroAttackResult(myHero, damage);
//        }

        if (myHero.hasDebuff(Debuff.BLIND)) {
            undoBlindedProcess(myHero);
        }
        if (myHero.hasDebuff(Debuff.WEAKEN)) {
            undoWeakenedProcess(myHero);
        }
        myHero.decreaseCooldown();
        myHero.tickDebuffs();
        myMonster.tickIndividualDebuff(Debuff.VULNERATE);
    }

    private void heroSpecial() {
        // check silence
        if (myHero.hasDebuff(Debuff.SILENCE)) {
            audio.playSFX(audio.error, 100);
            tui.displaySilenced();
            heroTurn();
            return;
        }
        // check cooldown
        if (myHero.getCooldown() > 0) {
            audio.playSFX(audio.error, 100);
            tui.displayCooldown(myHero.getCooldown());
            heroTurn();
            return; // change this return statement for the burst attack?
        }

        // proceed with attack
        audio.playSFX(myHero.getSpecialSFX(), 100);
        tui.characterSelectAbility(myHero, myMonster, Ability.SPECIAL);
//        Map<Debuff, Integer> oldDebuffs = myHero.getActiveDebuffs(); // snapshot of debuffs before more are applied
        int damage = myHero.specialAtk(myMonster);
        SleepDelay.delay(2);
        tui.heroAttackResult(myHero, damage);
        if (myHero.wasCritHit()) {
            tui.playerCritMsg();
        }
        inflictDebuffMsg(myMonster); // show what debuffs were inflicted on monster

//        myGame.displayHealthMeters(myHero, myMonster);

//        if (myHero.attackWasSuccessful()) {
//            myGame.heroResultSpecial(myHero, damage);
//            if (myHero.wasCritHit()) {
//                myGame.playerCritMsg();
//            }
//        } else {
//            myGame.playerSpecialMissMsg(myHero);
//        }

        if (myHero.hasDebuff(Debuff.WEAKEN)) {
            undoWeakenedProcess(myHero);
        }
        if (myHero.hasDebuff(Debuff.BLIND)) {
            undoBlindedProcess(myHero);
        }
        myHero.resetCooldown();
        myHero.tickDebuffs();
        myMonster.tickIndividualDebuff(Debuff.VULNERATE);
    }

    public void inventoryMenu() {
        Inventory bag = myHero.getInventory();

        CompletableFuture<Character> userInputFuture = tui.openBag(bag, true); // input is guaranteed to be 1-4 or e
        userInputFuture.thenApplyAsync(userInput -> {

            if (userInput != 'e') { // back button was pressed
                int slotIndex = Character.getNumericValue(userInput)-1; // convert input to int (with -1 due to array indexing)

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

                    audio.playSFX(audio.heroDrinkPotion, 100);
                    tui.usePotionMsg(potion, slotIndex);

                    if (myHero.hasDebuff(Debuff.BLIND)) {
                        undoBlindedProcess(myHero);
                    }
                    if (myHero.hasDebuff(Debuff.WEAKEN)) {
                        undoWeakenedProcess(myHero);
                    }
                    myHero.decreaseCooldown();
                    myHero.tickDebuffs();
                    myMonster.tickIndividualDebuff(Debuff.VULNERATE);

                } else {
                    audio.playSFX(audio.error, 100);
                    tui.displayCantUseItemDuringBattle(potion);
                    inventoryMenu();
                }

            } else { // back button was pressed
                tui.closeBag();
                heroTurn();
            }

            return userInput; // Return the input for further processing if necessary
        });

    }

    /**
     * Prompts the monster for their choice
     * current Player and Monster HP and other info
     */
    private void monsterTurn() {

        myMonster.initializeCharacterPerTurn();

        tui.displayChainSpacer();
        tui.displayEnemyTurn();

        tui.displayHeroAndMonsterInfo(myHero, myMonster);

        Queue<String> passiveMsgs = myMonster.getPassiveMsgs();
        if (!passiveMsgs.isEmpty()) {
            tui.passiveAbilityActivated(passiveMsgs);
        }
        tui.println();

        boolean debuffMsgTriggered = false;
        if (myMonster.hasDebuff(Debuff.STUCKIFY)) {
            stuckProcess(myMonster);
            return; // skip turn
        }
        if (myMonster.hasDebuff(Debuff.BLIND)) {
            blindedProcess(myMonster);
            debuffMsgTriggered = true;
        }
        if (myMonster.hasDebuff(Debuff.POISON)) {
            poisonedProcess(myMonster);
            debuffMsgTriggered = true;
        }
        if (myMonster.hasDebuff(Debuff.SILENCE)) {
            silencedProcess(myMonster);
            debuffMsgTriggered = true;
        }
        if (myMonster.hasDebuff(Debuff.VULNERATE)) {
            vulnerableProcess(myMonster);
            debuffMsgTriggered = true;
        }
        if (myMonster.hasDebuff(Debuff.WEAKEN)) {
            weakenedProcess(myMonster);
            debuffMsgTriggered = true;
        }

        if (debuffMsgTriggered) {
            tui.println();
        }

        SleepDelay.delay(2);

        int choice = RANDOMIZER.nextInt(3);
        myMonster.decreaseCooldown();

        //TODO make sure monster initial cooldown is set to 1 or 2?

        //TODO make monster always choose special attack first!!
        // because strategy needs to be for player to remember when monster will use their special next

        // Basic Attack
        if (choice == 0) {
            monsterBasic();

            // Special Attack
        } else if (choice == 1 && myMonster.getCooldown() <= 0 && !myMonster.hasDebuff(Debuff.SILENCE)) {
            monsterSpecial();

            // Heal
        } else if (choice == 2 && myMonster.getHealth() < (myMonster.getMaxHealth() - myMonster.getMaxHeal())) {
            monsterHeal();

            // Basic attack failsafe
        } else {
            monsterBasic();
        }

        // undo any debuff actions that were applied to the monster
        if (myMonster.hasDebuff(Debuff.BLIND)) {
            undoBlindedProcess(myMonster);
        }
        if (myMonster.hasDebuff(Debuff.WEAKEN)) {
            undoWeakenedProcess(myMonster);
        }

        // checks if the attack killed the player
        if (myHero.getHealth() <= 0) {
            myGameOver = true;
            myVictory = false;
        }
    }


    private void monsterBasic() {
        tui.characterSelectAbility(myMonster, myHero, Ability.BASIC);
//        Map<Debuff, Integer> oldDebuffs = myHero.getActiveDebuffs(); // snapshot of debuffs before more are applied
        int damage = myMonster.basicAtk(myHero);
        tui.monsterAttackResult(myMonster, myHero, damage);
        inflictDebuffMsg(myHero); // show what debuffs were inflicted on hero

        Queue<String> passiveMsgs = myMonster.getPassiveMsgs();
        if (!passiveMsgs.isEmpty()) {
            tui.passiveAbilityActivated(passiveMsgs); // ex: show swordsman defense stance msg
        }
//        myGame.displayHealthMeters(myHero, myMonster);
        myMonster.tickDebuffs();
        myHero.tickIndividualDebuff(Debuff.VULNERATE);
    }

    private void monsterSpecial() {
        if (myMonster.getCooldown() > 0) {
            System.out.println("DEBUG: Monster is on cooldown, but tried to use special attack!");
        }
        tui.characterSelectAbility(myMonster, myHero, Ability.SPECIAL);
        int damage = myMonster.specialAtk(myHero);
        tui.monsterAttackResult(myMonster, myHero, damage);
        inflictDebuffMsg(myHero); // show what debuffs were inflicted on hero

        Queue<String> passiveMsgs = myMonster.getPassiveMsgs();
        if (!passiveMsgs.isEmpty()) {
            tui.passiveAbilityActivated(passiveMsgs); // ex: show swordsman defense stance msg
        }

        myMonster.tickDebuffs();
    }

    private void monsterHeal() {
        myMonster.heal(myMonster);
        int healAmount = myMonster.getHealAmount();
        tui.monsterHeal(healAmount);
        myMonster.tickDebuffs();
    }


    private void inflictDebuffMsg(final DungeonCharacter theCharacter) {

        Map<Debuff, Integer> newDebuffs = theCharacter.getNewDebuffs();
        Iterator<Debuff> iterator = newDebuffs.keySet().iterator();

        while (iterator.hasNext()) {
            Debuff debuff = iterator.next();
            int duration = newDebuffs.remove(debuff);
            tui.inflictedDebuffMsg(debuff, duration);
            // instead of myActiveDebuffs.remove(debuff);. concurrent modification exception
            // Use iterator's remove() method to safely remove the element
        }
    }


    private void stuckProcess(final DungeonCharacter theCharacter) {
        tui.displayStuckifyMsg(theCharacter);
        theCharacter.decreaseCooldown();
        theCharacter.tickDebuffs();
        SleepDelay.delay(2);
    }

    private void blindedProcess(final DungeonCharacter theCharacter) {
        tui.displayBlindedMsg(theCharacter);
        theCharacter.setBasicAccuracy(theCharacter.getBasicAccuracy() - 0.5);
        theCharacter.setSpecialAccuracy(theCharacter.getSpecialAccuracy() - 0.5);
    }

    private void undoBlindedProcess(final DungeonCharacter theCharacter) {
        theCharacter.resetBasicAccuracy();
        theCharacter.resetSpecialAccuracy();
    }

    private void weakenedProcess(final DungeonCharacter theCharacter) {
        tui.displayWeakenedMsg(theCharacter);

        int minDmg = theCharacter.getMinDmg();
        int maxDmg = theCharacter.getMaxDmg();

        minWeakenOffset = minDmg % 2;
        maxWeakenOffset = maxDmg % 2;

        theCharacter.setMinDmg(minDmg/2); //TODO problem with odd numbers, ex: 5/2 = 2
        theCharacter.setMaxDmg(maxDmg/2); //TODO when reverting the change, 2*2 = 4, not 5
        // offset should fix this!!
    }

    private void undoWeakenedProcess(final DungeonCharacter theCharacter) {
        theCharacter.setMinDmg((theCharacter.getMinDmg()*2) + minWeakenOffset);
        theCharacter.setMaxDmg((theCharacter.getMaxDmg()*2) + maxWeakenOffset);
    }

    private void poisonedProcess(final DungeonCharacter theCharacter) {
        tui.displayPoisonedMsg(theCharacter);
        theCharacter.damage(10);
    }

    private void silencedProcess(final DungeonCharacter theCharacter) {
        tui.displaySilencedMsg(theCharacter);
    }

    private void vulnerableProcess(final DungeonCharacter theCharacter) { // only debuff that takes affect on OTHER player's turn
        tui.displayVulnerableMsg(theCharacter);
    }

}
