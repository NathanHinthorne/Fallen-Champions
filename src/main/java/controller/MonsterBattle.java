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
     * The cheat mode flag
     */
    private boolean myCheatMode;

    /**
     * The audio object
     */
    private static Audio audio;


    public MonsterBattle(final Hero theHero, final Monster theMonster, final TUI theView,
                         final boolean theCheatMode, final Audio theAudio, boolean theFunnyMode) {
        myHero = theHero;
        myMonster = theMonster;

        myGameOver = false;
        myGame = theView;
        myVictory = false;
        myCheatMode = theCheatMode;
        myFunnyMode = theFunnyMode;
        audio = theAudio;

        myHero.initializeCharacterPerBattle();
        myMonster.initializeCharacterPerBattle();
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
                    heroTurn();
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
                    heroTurn();
                    DelayMachine.delay(2);
                }
            }
        }

        myHero.uninitializeCharacterPerBattle();

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
    private void heroTurn() {

        myGame.displayChainSpacer();
        myGame.displayPlayerTurn();

        myGame.displayHeroAndMonsterInfo(myHero, myMonster);
        System.out.println();

        myHero.initializeCharacterPerTurn();

        Queue<String> passiveMsgs = myHero.getPassiveMsgs();
        if (!passiveMsgs.isEmpty()) {
            myGame.displayPassiveStatus(passiveMsgs); // ex: show swordsman defense stance msg
        }
        System.out.println();

        if (myHero.hasDebuff(Debuff.STUCKIFY)) {
            stuckProcess(myHero);
            return; // skip turn
        }
        if (myHero.hasDebuff(Debuff.BLIND)) {
            startBlindedProcess(myHero);
        }
        if (myHero.hasDebuff(Debuff.POISON)) {
            poisonedProcess(myHero);
        }
        if (myHero.hasDebuff(Debuff.SILENCE)) {
            silencedProcess(myHero);
        }
        if (myHero.hasDebuff(Debuff.VULNERATE)) {
            vulnerableProcess(myHero);
        }
        if (myHero.hasDebuff(Debuff.WEAKEN)) {
            startWeakenedProcess(myHero);
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
            audio.playSFX(audio.heroBagOpen, -10);
            inventoryMenu();

            // check monster stats
        } else if (choice == 'r') {
            checkMonsterStats();

            // run away
        } else if (choice == 'q') {
            //TODO add run away functionality. default 50% chance of success. ninja gets %80

            // cheat mode instakill
        } else if (choice == '6' && myCheatMode) { //TODO change to huge laser firing with asterisks like boss
            myGame.displayInstaKill();
            myMonster.setHealth(0);

            // wrong input must have been given
        } else {
            audio.playSFX(audio.error, -10, true);
            myGame.displayWrongInput();
            heroTurn();
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
        heroTurn();
    }

    private void heroBasic() {
        audio.playSFX(myHero.getBasicSFX(), -10, true);
        myGame.characterSelectAbility(myHero, myMonster, Ability.BASIC);
//        Map<Debuff, Integer> oldDebuffs = myHero.getActiveDebuffs(); // snapshot of debuffs before more are applied
        int damage = myHero.basicAtk(myMonster);
        int trueDamage = (int) (damage * (1 - myMonster.getDefense()));
        DelayMachine.delay(2);
        myGame.heroAttackResult(myHero, trueDamage);
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
        myHero.decreaseDebuffDuration();
    }

    private void heroSpecial() {
        // check silence
        if (myHero.hasDebuff(Debuff.SILENCE)) {
            audio.playSFX(audio.error, -10, true);
            myGame.displaySilenced();
            heroTurn();
            return;
        }
        // check cooldown
        if (myHero.getCooldown() > 0) {
            audio.playSFX(audio.error, -10, true);
            myGame.displayCooldown(myHero.getCooldown());
            heroTurn();
            return; // change this return statement for the burst attack?
        }

        // proceed with attack
        audio.playSFX(myHero.getSpecialSFX(), -10, true);
        myGame.characterSelectAbility(myHero, myMonster, Ability.SPECIAL);
//        Map<Debuff, Integer> oldDebuffs = myHero.getActiveDebuffs(); // snapshot of debuffs before more are applied
        int damage = myHero.specialAtk(myMonster);
        int trueDamage = (int) (damage * (1 - myMonster.getDefense()));
        DelayMachine.delay(2);
        myGame.heroAttackResult(myHero, trueDamage);
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

                if (myHero.hasDebuff(Debuff.BLIND)) {
                    undoBlindedProcess(myHero);
                }
                if (myHero.hasDebuff(Debuff.WEAKEN)) {
                    undoWeakenedProcess(myHero);
                }
                myHero.decreaseCooldown();
                myHero.decreaseDebuffDuration();

            } else {
                audio.playSFX(audio.error, -10, true);
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
            myGame.displayPassiveStatus(passiveMsgs); // ex: show swordsman defense stance msg
        }
        System.out.println();

        if (myMonster.hasDebuff(Debuff.STUCKIFY)) {
            stuckProcess(myMonster);
            return; // skip turn
        }
        if (myMonster.hasDebuff(Debuff.BLIND)) {
            startBlindedProcess(myMonster);
        }
        if (myMonster.hasDebuff(Debuff.POISON)) {
            poisonedProcess(myMonster);
        }
        if (myMonster.hasDebuff(Debuff.SILENCE)) {
            silencedProcess(myMonster);
        }
        if (myMonster.hasDebuff(Debuff.VULNERATE)) {
            vulnerableProcess(myMonster);
        }
        if (myMonster.hasDebuff(Debuff.WEAKEN)) {
            startWeakenedProcess(myMonster);
        }

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
        int trueDamage = (int) (damage * (1 - myHero.getDefense()));
        myGame.monsterAttackResult(myMonster, myHero, trueDamage);
        inflictDebuffMsg(myHero); // show what debuffs were inflicted on hero

        Queue<String> passiveMsgs = myMonster.getPassiveMsgs();
        if (!passiveMsgs.isEmpty()) {
            myGame.displayPassiveStatus(passiveMsgs); // ex: show swordsman defense stance msg
        }
//        myGame.displayHealthMeters(myHero, myMonster);
        myMonster.decreaseDebuffDuration();
    }

    private void monsterSpecial() {
        if (myMonster.getCooldown() > 0) {
            System.out.println("DEBUG: Monster is on cooldown, but tried to use special attack!");
        }
        myGame.characterSelectAbility(myMonster, myHero, Ability.SPECIAL);
//        Map<Debuff, Integer> oldDebuffs = myHero.getActiveDebuffs(); // snapshot of debuffs before more are applied
        int damage = myMonster.specialAtk(myHero);
        int trueDamage = (int) (damage * (1 - myHero.getDefense()));
        myGame.monsterAttackResult(myMonster, myHero, trueDamage);
        inflictDebuffMsg(myHero); // show what debuffs were inflicted on hero

        Queue<String> passiveMsgs = myMonster.getPassiveMsgs();
        if (!passiveMsgs.isEmpty()) {
            myGame.displayPassiveStatus(passiveMsgs); // ex: show swordsman defense stance msg
        }

//        myGame.displayHealthMeters(myHero, myMonster);
        myMonster.decreaseDebuffDuration();
    }

    private void monsterHeal() {
        myMonster.heal(myMonster);
        int healAmount = myMonster.getHealAmount();
        myGame.monsterHeal(healAmount);
        myMonster.decreaseDebuffDuration();
    }

    // another solution is to have another map field in DungeonCharacter that stores newly applied debuffs
    // every time you add to the old map, you also add to the new map
    // every turn, you clear the new map
//    private void inflictDebuffMsg(final DungeonCharacter theDebuffedCharacter, final Map<Debuff, Integer> theOldDebuffs) {
//        Map<Debuff, Integer> newDebuffs = theDebuffedCharacter.getActiveDebuffs();
//        for (final Debuff debuff : newDebuffs.keySet()) {
//
//            if (!theOldDebuffs.containsKey(debuff)) { // if the debuff in question is NEW, display the message
//                myGame.inflictedDebuffMsg(debuff);
//            }
//            else { // if the debuff in question is OLD, but the duration has been updated, display the message
//                int newDuration = newDebuffs.get(debuff);
//                int oldDuration = theOldDebuffs.get(debuff);
//                if (newDuration != oldDuration) {
//                    myGame.inflictedDebuffMsg(debuff);
//                }
//            }
//        }
//    }

    private void inflictDebuffMsg(final DungeonCharacter theCharacter) {
        Queue<Debuff> newDebuffs = theCharacter.getNewDebuffs();
        while (!newDebuffs.isEmpty()) {
            Debuff debuff = newDebuffs.remove();
            myGame.inflictedDebuffMsg(debuff);
        }
    }



    private void stuckProcess(final DungeonCharacter theCharacter) {
        myGame.displayStuckifyMsg(theCharacter);
        theCharacter.decreaseCooldown();
        theCharacter.decreaseDebuffDuration();
        DelayMachine.delay(2);
    }

    private void startBlindedProcess(final DungeonCharacter theCharacter) {
        myGame.displayBlindedMsg(theCharacter);
        theCharacter.setBasicAccuracy(theCharacter.getBasicAccuracy() - 0.5);
        theCharacter.setSpecialAccuracy(theCharacter.getSpecialAccuracy() - 0.5);
    }

    private void undoBlindedProcess(final DungeonCharacter theCharacter) {
        theCharacter.setBasicAccuracy(theCharacter.getBasicAccuracy() + 0.5);
        theCharacter.setSpecialAccuracy(theCharacter.getSpecialAccuracy() + 0.5);
    }

    private void startWeakenedProcess(final DungeonCharacter theCharacter) {
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
