package view;
import controller.DelayMachine;
import model.*;

import java.io.Console;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * TUI mode of the game
 *
 * @author Austin Roaf
 * @author Brendan Smith
 * @author Nathan Hinthorne
 * @version 1.0
 */
public class TUI {

    /**
     * The scanner object that will be used to read user input.
     */
    private final Scanner myScanner;

    /**
     * The console object that will pop up for the user.
     */
//    private final Consol;


    public TUI(final Console theConsole) {
//       = theConsole;
//        myScanner = new Scanne.reader());
        myScanner = new Scanner(System.in);
    }


    /**
     * Main menu of the game
     * @return main menu input
     */
    public int menu() {
        System.out.println("Welcome to Fallen Champions, TUI edition");
        System.out.println("1 to start game, 2 to exit game");
        System.out.print("Make your selection: ");
        return myScanner.nextInt();
    }

    /**
     * Chooses the hero
     * @return the hero choice
     */
    public int chooseHero(final List<Hero> theHeroes) {

        System.out.println("Choose your hero!");
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        int menuOption = 1;
        for (Hero hero : theHeroes) {
            if (hero.isUnlocked()) {
                System.out.println(menuOption + " for " + hero.getType());
            } else {
                System.out.println(menuOption + "  --(LOCKED)--  ");
            }
            menuOption++;
        }

        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Make your selection: ");
        return myScanner.nextInt();
    }

    /**
     * Introduces you to the game
     */
    public void Introduction(final boolean theFunnyDialogue, final Audio theAudio, final String theRealName) {
        System.out.println("-------------------------INTRODUCTION-------------------------");
        System.out.println();
        DelayMachine.printDelayedText("Welcome hero,");
        DelayMachine.printDelayedText("The dungeon you see looming before you is one of many perils.");
        DelayMachine.printDelayedText("In it lies hordes of monsters, deadly traps, and countless treasures.");
        DelayMachine.printDelayedText("Find the 4 pillars of OO, and you shall be granted access to the final chamber.");
        DelayMachine.printDelayedText("Not even I, the keeper of the dungeon, know what awaits you there.");
        System.out.println();
        DelayMachine.printDelayedText("Now then, what is your name?");
        System.out.println();
        System.out.print("Name: ");
        String fakeName = myScanner.next();
        System.out.println();
        DelayMachine.printDelayedTextFast("Wait, are you serious? Your name is actually " + fakeName + "?");
        DelayMachine.delay(2);
        DelayMachine.printDelayedTextFast("There's no way I'm calling you that -_-");
        DelayMachine.delay(2);
        DelayMachine.printDelayedText("I'm going to call you " + theRealName + " instead.");

        if (theFunnyDialogue) {
            DelayMachine.printDelayedText("hehehe bet you didn't expect that one.");
        } else {
            DelayMachine.printDelayedText("It suits you much better.");
        }
        System.out.println();
        DelayMachine.printDelayedText("Now then, " + theRealName + ", are you ready to begin your adventure?");
        DelayMachine.printDelayedText("Don't think you're the first to explore this dungeon.");
        DelayMachine.printDelayedText("Many others have come before you... Not one has made it out alive.");
        DelayMachine.printDelayedText("Do you think you have what it takes to overcome this dungeon?");
        DelayMachine.delay(2);
        DelayMachine.printDelayedText("Or will you become yet another FALLEN CHAMPION?");
        System.out.println();

        if (theFunnyDialogue) {
            DelayMachine.delay(2);
            theAudio.playSFX(theAudio.rimshot, -10);
            DelayMachine.delay(4);
            DelayMachine.printDelayedTextFast("By the way, that was a rhetorical question, you don't actually get to answer.");
        } else {
            DelayMachine.delay(2);
            theAudio.playSFX(theAudio.dunDunDun, -10);
            DelayMachine.delay(4);
        }

        DelayMachine.printDelayedText("So " + theRealName + ", what kind of adventurer are you anyway?");


/*        DelayMachine.delay(2);
//        System.out.print(". ");
//        DelayMachine.delay(2);
//        System.out.print(". ");
//        DelayMachine.delay(2);
//        System.out.print(". ");
//        DelayMachine.delay(4);
//        System.out.println();
//
//        System.out.println("Well, do you have what it takes??");
//        if (theFunnyDialogue) {
//        } else {
//            DelayMachine.printDelayedText("A quiet one ay?");
//        }

//        DelayMachine.delay(2);
//        System.out.print(". ");
//        DelayMachine.delay(2);
//        System.out.print(". ");
//        DelayMachine.delay(2);
//        System.out.print(". ");
//        DelayMachine.delay(4);
//        System.out.println();
//
//        if (theFunnyDialogue) {
//            System.out.println("Bruh");
//            DelayMachine.delay(2);
//            System.out.println("*boots you into the dungeon without a second thought*");
//        } else {
//            DelayMachine.printDelayedText("A quiet one ay?");
//            DelayMachine.printDelayedText("I guess we'll find out soon enough.");
        }
        */
    }

    /**
     * Gameplay menu
     * @return gameplay menu choice
     */
    public int gameplayMenu() {
        System.out.println();
        System.out.println("-----------------------------------------------------");
        System.out.println("Please make a selection:");
        System.out.println("╔══════════════════════════════════════════════════════════════════════════╗");
        System.out.println("╠ 'w' to move North, 'd' to move East, 's' to move South, 'a' to move West ╣");
        System.out.println("╠ '1' to display hero info, 'e' open bag, '4' to quit, '5' to save game    ╣");
        System.out.println("╚══════════════════════════════════════════════════════════════════════════╝");
        System.out.println("-----------------------------------------------------");

        return myScanner.next().charAt(0);
    }

    public void displayNormalSpacer() {
        System.out.println("-----------------------------------------------------"); //TODO replace all spacers with this method in the controller
    }

    /**
     * Battle menu for battling monsters
     * @param theHero you
     * @param theMonster the monster you're battling
     * @return battle menu input
     */
    public char battleMenu(Hero theHero, Monster theMonster) { // is this parameter needed?
        System.out.println("\nPlayer HP:  " + theHero.getHealth());
        System.out.println("Monster HP: " + theMonster.getHealth());

        System.out.println("\nMake your move!");

        String specialButton = "[2 - Special]";
        if (theHero.onCooldown()) {
            specialButton = "║2 - Special║";
        }

        System.out.print("[1 - Basic] " + specialButton + " [e - Bag] --> ");

        char choice = myScanner.next().charAt(0);
        return choice;
    }

    /**
     * Menu for choosing the difficulty
     * @return the difficulty choice
     */
    public int chooseDifficulty(final boolean theMediumUnlocked, final boolean theHardUnlocked) {
        System.out.println("Choose your difficulty!");
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║ 1 for easy | 2 for medium | 3 for hard ║");
        System.out.println("╚════════════════════════════════════════╝");

        if (theHardUnlocked) {
            System.out.println("     ▲              ▲             ▲       ");
            System.out.println();
        } else if (theMediumUnlocked) {
            System.out.println("     ▲              ▲             ^       ");
            System.out.println("                               (Locked)   ");
            System.out.println();
        } else {
            System.out.println("     ▲              ^             ^       ");
            System.out.println("                 (Locked)      (Locked)   ");
            System.out.println();
        }

        System.out.print("Make your selection: ");
        return myScanner.nextInt();
    }

    /**
     * Menu for inventory choice
     * @param theBag your inventory bag
     * @param inBattle whether you're in battle
     * @return the inventory choice
     */
    public char openBag(final Inventory theBag, final boolean inBattle, final Audio theAudio) {
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Opening bag...");
        DelayMachine.delay(2);

        if (inBattle) {
            System.out.println(theBag.getItemInventory());
        } else {
            System.out.println(theBag.toString());
        }

        System.out.print("Choose an item (1-4) (e - Back) --> ");
        char input = myScanner.next().charAt(0);
        System.out.println();

        if (input == 'e') { // back
            return 'e';
        }

        int slotIndex = Character.getNumericValue(input)-1; // convert input to int (with -1 due to array index)

        if (slotIndex < 0 || slotIndex > 3) { // out of bounds
            theAudio.playSFX(theAudio.error, -10);
            displayWrongInput();
            return openBag(theBag, inBattle, theAudio);

        } else if (slotIndex > theBag.getCurrentSize()-1 && slotIndex <= theBag.getMaxSize()) { // empty slot
            theAudio.playSFX(theAudio.error, -10);
            System.out.println("That slot is empty!");
            return openBag(theBag, inBattle, theAudio);
        }

        return input;
    }

    public void closeBag(final Audio theAudio) {
        System.out.println("Closing bag...");
        theAudio.playSFX(theAudio.heroBagClose, -10);
        DelayMachine.delay(1);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }


    /**
     * Brings you a menu for whether you want to quit the game or not
     * @return the menu selection
     */
    public int quitProcess() {
        System.out.println("Are you sure you want to quit?");
        System.out.println("1 for yes, 2 for no");
        System.out.print("Make your selection: ");
        return myScanner.nextInt();
    }

    /**
     * Displays the dungeon map
     * @param theDungeon the map
     */
    public void displayDungeonMap(Dungeon theDungeon) {
        System.out.println(theDungeon.toString());
        System.out.println();
    }

    /**
     * Displays the hero info
     * @param theHero you
     */
    public void displayHeroStats(final Hero theHero) {

        System.out.println(" ╔════════════════════════════════════════════════");
        System.out.println(" ║ " + theHero.getName());
        System.out.println(" ║ " + theHero.getType());
        System.out.println(" ║ ");
        System.out.println(" ║ A̲B̲I̲L̲I̲T̲I̲E̲S̲");
        System.out.println(" ╠ Basic: ");
        System.out.println(" ║ " + theHero.getBasicName());
        System.out.println(" ║ ");
        System.out.println(" ╠ Special: ");
        System.out.println(" ║ " + theHero.getSpecialName()[0]);
        System.out.println(" ║ " + theHero.getSpecialName()[1]);
        System.out.println(" ║ ");
        System.out.println(" ╠ Passive: ");
        System.out.println(" ║ " + theHero.getPassiveName()[0]);
        System.out.println(" ║ " + theHero.getPassiveName()[1]);
        System.out.println(" ║ ");
        System.out.println(" ║ S̲T̲A̲T̲S̲");
        System.out.println(" ╠ Health: " + theHero.getHealth() + "/" + theHero.getMaxHealth());
        System.out.println(" ╠ Attack: " + (theHero.getMinDmg() + theHero.getMaxDmg())/2);
        System.out.println(" ╠ Speed: " + theHero.getSpeed());
        System.out.println(" ╠ Level: " + theHero.getLevel());
        System.out.println(" ╠ Experience: " + theHero.getXP() + "/" + theHero.getXPToLevel());
        System.out.println(" ╚════════════════════════════════════════════════");
    }

    /**
     * Displays the monster info
     * @param theMonster the monster
     */
    public void displayMonsterStats(final Monster theMonster) {
        System.out.println(" ╔════════════════════════════════════════════════");
        System.out.println(" ║ " + theMonster.getName());
        System.out.println(" ║ ");
        System.out.println(" ║ DESCRIPTION: ");
        System.out.println(" ║ " + theMonster.getDescription()[0]);
        System.out.println(" ║ " + theMonster.getDescription()[1]);
        System.out.println(" ║ ");
        System.out.println(" ║ ABILITIES:");
        System.out.println(" ╠ Basic: " + theMonster.getBasicName());
        System.out.println(" ║ ");
        System.out.println(" ╠ Special: " + theMonster.getSpecialName()[0]);
        System.out.println(" ║    "       + theMonster.getSpecialName()[1]);
        System.out.println(" ║ ");
        System.out.println(" ╠ Passive: " + theMonster.getPassiveName()[0]);
        System.out.println(" ║    "       + theMonster.getPassiveName()[1]);
        System.out.println(" ║ ");
        System.out.println(" ║ STATS:");
        System.out.println(" ╠ Health: " + theMonster.getHealth() + "/" + theMonster.getMaxHealth());
        System.out.println(" ╠ Attack: " + (theMonster.getMinDmg() + theMonster.getMaxDmg())/2);
        System.out.println(" ╠ Speed: " + theMonster.getSpeed());
        System.out.println(" ╚════════════════════════════════════════════════");
    }

    /**
     * Displays the potion info
     * @param thePotion the potion info
     */
    public void collectPotionMsg(final Potion thePotion) {
        System.out.println(" -You collected a " + thePotion.type() + "!");
    }

    /**
     * Displays Abstraction Pillar Message
     */
    public void displayAbstractionPillarMsg() {
        System.out.println(" -You collected an ABSTRACTION PILLAR!");
    }
    /**
     * Displays Encapsulation Pillar Message
     */
    public void displayEncapsulationPillarMsg() {
        System.out.println(" -You collected an ENCAPSULATION PILLAR!");
    }
    /**
     * Displays Inheritance Pillar Message
     */
    public void displayInheritancePillarMsg() {
        System.out.println(" -You collected an INHERITANCE PILLAR!");
    }
    /**
     * Displays Polymorphism Pillar Message
     */
    public void displayPolymorphismPillarMsg() {
        System.out.println(" -You collected a POLYMORPHISM PILLAR!");
    }


    /**
     * Asks whether you want to start a new game or continue on a prior save
     * @return the game choice
     */
    public int continueOrNewGameMenu() {
        System.out.println("Would you like to start a new game or continue a previous game?");
        System.out.println("1 for new game, 2 for continue game");
        System.out.print("Make your selection: ");
        return myScanner.nextInt();
    }

    /**
     * The monster turn text
     */
    public void monsterTurnText() {
        System.out.println("-----------------------------------------------------");
        System.out.println(" -The Enemy is making their move...");
    }

    public void monsterHealMsg(final int theHealAmt) {
        System.out.println(" -The enemy heals themselves for " + theHealAmt + " Health Points!");
        DelayMachine.delay(1);
    }

    public void monsterSelectsBasicMsg(final Monster theMonster, final Hero thePlayer) {
        System.out.println(" -" + theMonster.getName() + theMonster.getBasicSelectMsg() +
                thePlayer.getName() + theMonster.getExtendedBasicSelectMsg());
        DelayMachine.delay(2);
    }
    public void monsterSelectsSpecialMsg(final Monster theMonster, final Hero thePlayer) {
        System.out.println(" -" + theMonster.getName() + theMonster.getSpecialSelectMsg() +
                thePlayer.getName() + theMonster.getExtendedSpecialSelectMsg());
        DelayMachine.delay(2);
    }
    public void monsterHitsBasicMsg(final Monster theMonster, final Hero theHero, final int theDamageDealt) {
        System.out.println(" -" + theMonster.getBasicHitMsg()[0]);
        System.out.println("  " + theHero.getName() + " took " + theDamageDealt + " damage!");
        DelayMachine.delay(2);
    }
    public void monsterHitsSpecialMsg(final Monster theMonster, final Hero theHero, final int theDamageDealt) {
        System.out.println(" -" + theMonster.getSpecialHitMsg()[0]);
        System.out.println("  " + theHero.getName() + " took " + theDamageDealt + " damage!");
        DelayMachine.delay(2);
    }
    public void monsterBasicMiss(final Monster theMonster) {
        System.out.println("  " + theMonster.getBasicMissMsg()[0]);
        DelayMachine.delay(2);
    }

    public void monsterSpecialMiss(final Monster theMonster) {
        System.out.println("  " + theMonster.getSpecialMissMsg()[0]);
        DelayMachine.delay(2);
    }



    public void playerSelectsBasicMsg(final Hero thePlayer, final Monster theMonster) {

        System.out.println(" -" + thePlayer.getName() + thePlayer.getBasicSelectMsg() +
                theMonster.getName() + thePlayer.getExtendedBasicSelectMsg() + "...");

        DelayMachine.delay(2);
    }
    public void playerSelectsSpecialMsg(final Hero thePlayer, final Monster theMonster) {

        System.out.println(" -" + thePlayer.getName() + thePlayer.getSpecialSelectMsg() +
                theMonster.getName() + thePlayer.getExtendedSpecialSelectMsg() + "...");

        DelayMachine.delay(2);
    }
    public void playerHitsBasicMsg(final Hero thePlayer, final int theDamageDealt, final boolean funnyMode) {
        System.out.println("  " + thePlayer.getBasicHitMsg()[0]);
        System.out.println("  The enemy took " + theDamageDealt + " damage!");
        DelayMachine.delay(2);
    }
    public void playerHitsSpecialMsg(final Hero thePlayer, final int theDamageDealt) { // special doesn't always do damage. find way to deal with this
        System.out.println("  " + thePlayer.getSpecialHitMsg()[0]);
        System.out.println("  The enemy took " + theDamageDealt + " damage!");
        DelayMachine.delay(2);
    }
    public void playerBasicMissMsg(final Hero thePlayer) {
        System.out.println("  " + thePlayer.getBasicMissMsg()[0]);
    }

    public void playerSpecialMissMsg(final Hero thePlayer) {
        System.out.println("  " + thePlayer.getSpecialMissMsg()[0]);
    }


    public void displayCooldown(final int theCooldown) {
        if (theCooldown == 1) {
            System.out.println(" -You can't use that ability for " + theCooldown + " more turn!");
        } else {
            System.out.println(" -You can't use that ability for " + theCooldown + " more turns!");
        }
    }

// YOU WIN

    /**
     * The game victory message
     */
    public void displayVictoryMsg(final int theHeroSteps, final int theMonstersDefeated, final int theLevel,
                                  final Difficulty theDifficulty, boolean funnyMode) {

        System.out.println("-----------------------------------------------------");
        System.out.println(" -You escaped the dungeon!");
        System.out.println();
        System.out.println("██╗   ██╗ ██████╗ ██╗   ██╗    ██╗    ██╗██╗███╗   ██╗██╗\n" +
                           "╚██╗ ██╔╝██╔═══██╗██║   ██║    ██║    ██║██║████╗  ██║██║\n" +
                           " ╚████╔╝ ██║   ██║██║   ██║    ██║ █╗ ██║██║██╔██╗ ██║██║\n" +
                           "  ╚██╔╝  ██║   ██║██║   ██║    ██║███╗██║██║██║╚██╗██║╚═╝\n" +
                           "   ██║   ╚██████╔╝╚██████╔╝    ╚███╔███╔╝██║██║ ╚████║██╗\n" +
                           "   ╚═╝    ╚═════╝  ╚═════╝      ╚══╝╚══╝ ╚═╝╚═╝  ╚═══╝╚═╝");
        System.out.println();
        DelayMachine.delay(2);
        DelayMachine.printDelayedText("  RESULTS:");
        DelayMachine.printDelayedText("  You beat the game on " + theDifficulty + " difficulty!");
        DelayMachine.printDelayedText("  You took " + theHeroSteps + " steps to escape the dungeon.");
        DelayMachine.printDelayedText("  You reached level " + theLevel + ".");
        DelayMachine.printDelayedText("  You defeated " + theMonstersDefeated + " monsters.");
    }

    /**
     * The battle win message
     */
    public void displayBattleWinMsg(final Monster theMonster) {
        System.out.println();
        System.out.println(" -" + theMonster.getDeathMsg()[0]);
        System.out.println();
        DelayMachine.delay(2);
        System.out.println("  [][][][][][][][][][]");
        System.out.println("  [] ENEMY DEFEATED []");
        System.out.println("  [][][][][][][][][][]");
        System.out.println();
        DelayMachine.delay(1);
        System.out.println("RESULTS:");
        System.out.println(" -You gained " + theMonster.getXPWorth() + " experience points!");
        System.out.println();
    }

    /**
     * The death message
     */
    public void displayDeathMsg(final boolean theFunnyDialogue) {
        System.out.println(" -You have been defeated.");
        if (theFunnyDialogue) {
            System.out.println("  GIT GUD SCRUB!");
        }
        DelayMachine.delay(4);
        System.out.println("\n" +
                " $$$$$$\\                                           $$$$$$\\                                 \n" +
                "$$  __$$\\                                         $$  __$$\\                                \n" +
                "$$ /  \\__| $$$$$$\\  $$$$$$\\$$$$\\   $$$$$$\\        $$ /  $$ |$$\\    $$\\  $$$$$$\\   $$$$$$\\  \n" +
                "$$ |$$$$\\  \\____$$\\ $$  _$$  _$$\\ $$  __$$\\       $$ |  $$ |\\$$\\  $$  |$$  __$$\\ $$  __$$\\ \n" +
                "$$ |\\_$$ | $$$$$$$ |$$ / $$ / $$ |$$$$$$$$ |      $$ |  $$ | \\$$\\$$  / $$$$$$$$ |$$ |  \\__|\n" +
                "$$ |  $$ |$$  __$$ |$$ | $$ | $$ |$$   ____|      $$ |  $$ |  \\$$$  /  $$   ____|$$ |      \n" +
                "\\$$$$$$  |\\$$$$$$$ |$$ | $$ | $$ |\\$$$$$$$\\        $$$$$$  |   \\$  /   \\$$$$$$$\\ $$ |      \n" +
                " \\______/  \\_______|\\__| \\__| \\__| \\_______|       \\______/     \\_/     \\_______|\\__|      \n" +
                "                                                                                           \n" +
                "                                                                                           \n" +
                "                                                                                           \n");
    }

    /**
     * The cheat mode message
     */
    public void displayCheatModeMsg() {
        System.out.println("*****************************************************");
        System.out.println("You have entered cheat mode ಠ_ಠ");
        System.out.println("*****************************************************");
    }

    /**
     * The funny mode message
     */
    public void displayFunnyDialogueModeMsg() {
        System.out.println("*****************************************************");
        System.out.println("You have entered funny dialogue mode ( ͡° ͜ʖ ͡°)");
        System.out.println("*****************************************************");
    }

    /**
     * The debug mode message
     */
    public void displayDebugModeMsg() {
        System.out.println("*****************************************************");
        System.out.println("You have entered debug mode ❐‿❑");
        System.out.println("*****************************************************");
    }


    /**
     * The hero health
     * @param theHero you
     */
    public void displayHeroHealth(final Hero theHero) {
        System.out.print("Health: " + theHero.getHealth() + "/" + theHero.getMaxHealth());
    }

    /**
     * Display the steps left with the vision potion
     * @param theSteps the steps the hero has taken while the vision potion is active
     */
    public void displayStepsWithVisionPotion(final int theSteps) {
        System.out.print("         Steps left with vision potion: " + (4-theSteps));
    }

    /**
     * The game start message
     */
    public void displayStartMsg() {
        System.out.println("                ╔═════════════════════════╗                ");
        System.out.println("----------------║ Welcome to the Dungeon! ║----------------");
        System.out.println("                ╚═════════════════════════╝                ");
    }

    /**
     * The instant kill message
     */
    public void displayInstaKill() {
        System.out.println("Woooow, you must be so powerful... cheater");
    }

    /**
     * Monster encounter message
     * @param theMonster the monster you're encountering
     */
    public void displayMonsterEncounterMsg(final Monster theMonster) {
        String monsterName = theMonster.getType().toString();
        String article = isVowel(monsterName.charAt(0)) ? " an " : " a ";
        System.out.println(" -You have encountered" + article + monsterName);
        DelayMachine.delay(2);
        System.out.println();
        System.out.println("+---------------------------------------------------+");
        System.out.println("|                  BATTLE START                     |");
        System.out.println("+---------------------------------------------------+");
        DelayMachine.delay(1);

    }

    /**
     * Locked Exit message
     */
    public void exitLocked() {
        System.out.println(" -The exit is locked! You need to collect all 4 pillars to open it!");
    }

    /**
     * Fell into pit message
     * @param theFallDamage the pit you fell in
     */
    public void displayPitMsg(final int theFallDamage) {
        System.out.println(" -You fell into a pit! You took " + theFallDamage + " damage!");
    }

    /**
     * The use potion message
     * @param thePotion the potion you used
     * @param theSlotIndex the slot of the potion
     */
    public void usePotionMsg(final Potion thePotion, int theSlotIndex) {
        System.out.println(" -You used a " + thePotion.type() + " from the slot #" + (theSlotIndex+1));
        System.out.println(thePotion.useMsg());
    }

    /**
     * The invalid input message
     */
    public void displayWrongInput() {
        System.out.println("Invalid input. Please try again.");
    }

    public int playIntroOrNot() {
        System.out.println("Would you like to play the intro?");
        System.out.println("1 for no, 2 for yes");
        return myScanner.nextInt();
    }

    public void displayWallMsg() {
        System.out.println("WHAT ARE YOU DOING IN A WALL?! GET OUT OF THERE YOU FOOL");
    }

    public void heroIntroduction(Hero hero) {
        DelayMachine.printDelayedText("Ah, a " + hero.getType() + "!");
        DelayMachine.printDelayedText("I'm sure that skill will serve you well.");
        DelayMachine.printDelayedText("Good luck!");
        DelayMachine.printDelayedText("*cranks gate open*");
        System.out.println();

    }

    public void displayCantUseItemDuringBattle(Potion thePotion) {
        System.out.println(" -You can't use a " + thePotion.type() + " during a battle!");
    }

    public void displayCantUseItemOutsideBattle(Potion thePotion) {
        System.out.println(" -You can't use a " + thePotion.type() + " outside of a battle!");
    }

    public void levelUpMsg(final int theLevel) {
        System.out.println(" -You leveled up!");
        System.out.println("  Gained +5 damage, +10 max health");
        if (theLevel == 5) {
            System.out.println("  You have reached the max level");
        }
    }

    public void displayInventoryFullMsg() {
        System.out.println(" -You found a potion, but your inventory is full.");
    }

    public void playerCritMsg() {
        System.out.println("  It was a critical hit!");
    }

    public void displayBattleEnd() {
        System.out.println();
        System.out.println("+---------------------------------------------------+");
        System.out.println("|                    BATTLE END                     |");
        System.out.println("+---------------------------------------------------+");
    }

    private boolean isVowel(char ch) {
        ch = Character.toLowerCase(ch);
        return ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u';
    }

    public void exitIsOpenMsg() {
        System.out.println(" -You hear a door creak open in the distance...");
    }

    public void displayArrowSpacer() {
        System.out.println("<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>");
    }

    public void printAudioError(URISyntaxException theError) {
        System.out.println("Error: Audio source could not be parsed." + theError);
    }

    public void heroFasterThanMonsterMsg(final Monster theMonster) {
        System.out.println(" -You have a higher speed than " + theMonster.getName() + ", so you get to attack first!");
    }

    public void monsterFasterThanHeroMsg(final Monster theMonster) {
        System.out.println(" -The " + theMonster.getName() + " has a higher speed than you, so it attacks first!");
    }

    public char pressAnyKeyMsg() {
        System.out.println("Press any key to continue...");
        return myScanner.next().charAt(0);
    }

    public void displayUnlockedJuggernautAndThief() {
        System.out.println();
        System.out.println("╔══════════════════════ INFO ═══════════════════════╗");
        System.out.println("║ You've unlocked the JUGGERNAUT and THIEF heroes!  ║");
        System.out.println("║ They will now appear in the hero selection menu.  ║");
        System.out.println("╚═══════════════════════════════════════════════════╝");
        System.out.println();
    }
    public void displayUnlockedDoctorAndNinja() {
        System.out.println();
        System.out.println("╔══════════════════════ INFO ═══════════════════════╗");
        System.out.println("║   You've unlocked the DOCTOR and NINJA heroes!    ║");
        System.out.println("║ They will now appear in the hero selection menu.  ║");
        System.out.println("╚═══════════════════════════════════════════════════╝");
        System.out.println();
    }
    public void displayUnlockedScientist() {
        System.out.println();
        System.out.println("╔══════════════════════ INFO ═══════════════════════╗");
        System.out.println("║         You've unlocked the SCIENTIST hero        ║");
        System.out.println("║  He will now appear in the hero selection menu.   ║");
        System.out.println("╚═══════════════════════════════════════════════════╝");
        System.out.println();
    }
    public void displayUnlockedMage() {
        System.out.println();
        System.out.println("╔══════════════════════ INFO ═══════════════════════╗");
        System.out.println("║           You've unlocked the MAGE hero!          ║");
        System.out.println("║  He will now appear in the hero selection menu.   ║");
        System.out.println("╚═══════════════════════════════════════════════════╝");
        System.out.println();
    }

    public void displayUnlockedBeastmaster() {
        System.out.println("╔══════════════════════ INFO ═══════════════════════╗");
        System.out.println("║        You've unlocked the BEASTMASTER hero       ║");
        System.out.println("║  He will now appear in the hero selection menu.   ║");
        System.out.println("╚═══════════════════════════════════════════════════╝");
        System.out.println();
    }
}
