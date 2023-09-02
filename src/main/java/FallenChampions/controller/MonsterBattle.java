package FallenChampions.controller;

import FallenChampions.model.characters.Ability;
import FallenChampions.model.characters.Debuff;
import FallenChampions.model.characters.DungeonCharacter;
import FallenChampions.model.dungeon.BattleResult;
import FallenChampions.model.potions.PotionDefensive;
import FallenChampions.model.potions.PotionOffensive;
import FallenChampions.view.TUI;
import FallenChampions.view.AudioManager;
import FallenChampions.model.characters.heroes.Hero;
import FallenChampions.model.characters.heroes.Inventory;
import FallenChampions.model.characters.monsters.Monster;
import FallenChampions.model.potions.Potion;

import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Random;


/**
 * The Monster Battle function in the game.
 *
 * @author Brendan Smith
 * @author Nathan Hinthorne
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
     * Debug mode
     */
    private final boolean myDebugMode;

    /**
     * The cheat mode flag
     */
    private boolean myCheatMode;

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


    public MonsterBattle(final Hero theHero, final Monster theMonster, final TUI theView,
                         final boolean theCheatMode, final AudioManager theAudio, final boolean theFunnyMode,
                         final boolean theDebugMode) {

        myHero = theHero;
        myMonster = theMonster;

        myGameOver = false;
        myGame = theView;
        myVictory = false;
        myCheatMode = theCheatMode;
        myFunnyMode = theFunnyMode;
        myDebugMode = theDebugMode;
        audio = theAudio;

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
            myGame.heroFasterThanMonsterMsg(myMonster);
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
            myGame.monsterFasterThanHeroMsg(myMonster);
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

        myGame.displayChainSpacer();
        myGame.displayPlayerTurn();

        myGame.displayHeroAndMonsterInfo(myHero, myMonster);
        System.out.println();

        myHero.initializeCharacterPerTurn();

        Queue<String> passiveMsgs = myHero.getPassiveMsgs();
        if (!passiveMsgs.isEmpty()) {
            myGame.passiveAbilityActivated(passiveMsgs); // ex: show swordsman defense stance msg
        }
        System.out.println();

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
            System.out.println();
        }

        // menu
        char choice = myGame.battleMenu(myHero);
        System.out.println();

            // Basic Attack
        if (choice == '1') {
            heroBasic();

            // Special Attack
        } else if (choice == '2') {
            heroSpecial();

            // Open inventory
        } else if (choice == 'e') {
            audio.playSFX(audio.heroBagOpen, 100);
            inventoryMenu();

            // check monster stats
        } else if (choice == 'r') {
            checkMonsterStats();

            // run away
        } else if (choice == 'q') {
            runAwayProcess(myDebugMode);

        } else if (choice == 'f') {
            audio.playSFX(audio.swishOn, 100);
            myGame.debugHeroStats(myHero);
            myGame.pressAnyKeyGoBack();
            audio.playSFX(audio.swishOff, 80);
            heroTurn();

            // cheat mode instakill
        } else if (choice == '6' && myCheatMode) { //TODO change to huge laser firing with asterisks like boss
            myGame.displayInstaKill();
            myMonster.setHealth(0);

            // wrong input must have been given
        } else {
            audio.playSFX(audio.error, 100);
            myGame.displayWrongInput();
            heroTurn();
        }


        // Checks if the attack killed the enemy
        if (myMonster.getHealth() <= 0) {
            myGameOver = true;
            myVictory = true;
        }
    }

    private void runAwayProcess(final boolean debugMode) {
        myGame.displayRunAway(myHero);
        double runAwayChance = Math.random();
        if (runAwayChance < myHero.getRunSuccessChance() || debugMode) {
//            myAudio.playSFX(myAudio.runAwaySuccess, 100);
            myGame.displayRunAwaySuccess();
            myHeroRanAway = true;
            myGameOver = true;
        } else {
//            myAudio.playSFX(myAudio.runAwaySuccess, 100);
            myGame.displayRunAwayFail();
            myHeroRanAway = false;
        }
    }


    private void checkMonsterStats() {
        audio.playSFX(audio.swishOn, 100);
        myGame.displayMonsterStats(myMonster);
        myGame.pressAnyKeyGoBack();
        audio.playSFX(audio.swishOff, 80);
        heroTurn();
    }

    private void heroBasic() {
        audio.playSFX(myHero.getBasicSFX(), 100);
        myGame.characterSelectAbility(myHero, myMonster, Ability.BASIC);
//        Map<Debuff, Integer> oldDebuffs = myHero.getActiveDebuffs(); // snapshot of debuffs before more are applied
        int damage = myHero.basicAtk(myMonster);
        SleepDelay.delay(2);
        myGame.heroAttackResult(myHero, damage);
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
            myGame.displaySilenced();
            heroTurn();
            return;
        }
        // check cooldown
        if (myHero.getCooldown() > 0) {
            audio.playSFX(audio.error, 100);
            myGame.displayCooldown(myHero.getCooldown());
            heroTurn();
            return; // change this return statement for the burst attack?
        }

        // proceed with attack
        audio.playSFX(myHero.getSpecialSFX(), 100);
        myGame.characterSelectAbility(myHero, myMonster, Ability.SPECIAL);
//        Map<Debuff, Integer> oldDebuffs = myHero.getActiveDebuffs(); // snapshot of debuffs before more are applied
        int damage = myHero.specialAtk(myMonster);
        SleepDelay.delay(2);
        myGame.heroAttackResult(myHero, damage);
        if (myHero.wasCritHit()) {
            myGame.playerCritMsg();
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

                audio.playSFX(audio.heroDrinkPotion, 100);
                myGame.usePotionMsg(potion, slotIndex);

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
                myGame.displayCantUseItemDuringBattle(potion);
                inventoryMenu();
            }

        } else { // back button was pressed
            myGame.closeBag(audio);
            heroTurn();
        }
    }

    /**
     * Prompts the monster for their choice
     * current Player and Monster HP and other info
     */
    private void monsterTurn() {

        myMonster.initializeCharacterPerTurn();

        myGame.displayChainSpacer();
        myGame.displayEnemyTurn();

        myGame.displayHeroAndMonsterInfo(myHero, myMonster);
        System.out.println();

        Queue<String> passiveMsgs = myMonster.getPassiveMsgs();
        if (!passiveMsgs.isEmpty()) {
            myGame.passiveAbilityActivated(passiveMsgs);
        }
        System.out.println();

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
            System.out.println();
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
        myGame.characterSelectAbility(myMonster, myHero, Ability.BASIC);
//        Map<Debuff, Integer> oldDebuffs = myHero.getActiveDebuffs(); // snapshot of debuffs before more are applied
        int damage = myMonster.basicAtk(myHero);
        myGame.monsterAttackResult(myMonster, myHero, damage);
        inflictDebuffMsg(myHero); // show what debuffs were inflicted on hero

        Queue<String> passiveMsgs = myMonster.getPassiveMsgs();
        if (!passiveMsgs.isEmpty()) {
            myGame.passiveAbilityActivated(passiveMsgs); // ex: show swordsman defense stance msg
        }
//        myGame.displayHealthMeters(myHero, myMonster);
        myMonster.tickDebuffs();
        myHero.tickIndividualDebuff(Debuff.VULNERATE);
    }

    private void monsterSpecial() {
        if (myMonster.getCooldown() > 0) {
            System.out.println("DEBUG: Monster is on cooldown, but tried to use special attack!");
        }
        myGame.characterSelectAbility(myMonster, myHero, Ability.SPECIAL);
        int damage = myMonster.specialAtk(myHero);
        myGame.monsterAttackResult(myMonster, myHero, damage);
        inflictDebuffMsg(myHero); // show what debuffs were inflicted on hero

        Queue<String> passiveMsgs = myMonster.getPassiveMsgs();
        if (!passiveMsgs.isEmpty()) {
            myGame.passiveAbilityActivated(passiveMsgs); // ex: show swordsman defense stance msg
        }

        myMonster.tickDebuffs();
    }

    private void monsterHeal() {
        myMonster.heal(myMonster);
        int healAmount = myMonster.getHealAmount();
        myGame.monsterHeal(healAmount);
        myMonster.tickDebuffs();
    }


    private void inflictDebuffMsg(final DungeonCharacter theCharacter) {

        Map<Debuff, Integer> newDebuffs = theCharacter.getNewDebuffs();
        Iterator<Debuff> iterator = newDebuffs.keySet().iterator();

        while (iterator.hasNext()) {
            Debuff debuff = iterator.next();
            int duration = newDebuffs.remove(debuff);
            myGame.inflictedDebuffMsg(debuff, duration);
            // instead of myActiveDebuffs.remove(debuff);. concurrent modification exception
            // Use iterator's remove() method to safely remove the element
        }
    }


    private void stuckProcess(final DungeonCharacter theCharacter) {
        myGame.displayStuckifyMsg(theCharacter);
        theCharacter.decreaseCooldown();
        theCharacter.tickDebuffs();
        SleepDelay.delay(2);
    }

    private void blindedProcess(final DungeonCharacter theCharacter) {
        myGame.displayBlindedMsg(theCharacter);
        theCharacter.setBasicAccuracy(theCharacter.getBasicAccuracy() - 0.5);
        theCharacter.setSpecialAccuracy(theCharacter.getSpecialAccuracy() - 0.5);
    }

    private void undoBlindedProcess(final DungeonCharacter theCharacter) {
        theCharacter.resetBasicAccuracy();
        theCharacter.resetSpecialAccuracy();
    }

    private void weakenedProcess(final DungeonCharacter theCharacter) {
        myGame.displayWeakenedMsg(theCharacter);

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
        myGame.displayPoisonedMsg(theCharacter);
        theCharacter.damage(10);
    }

    private void silencedProcess(final DungeonCharacter theCharacter) {
        myGame.displaySilencedMsg(theCharacter);
    }

    private void vulnerableProcess(final DungeonCharacter theCharacter) { // only debuff that takes affect on OTHER player's turn
        myGame.displayVulnerableMsg(theCharacter);
    }

}
