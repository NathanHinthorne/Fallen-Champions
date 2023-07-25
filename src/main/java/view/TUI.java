package view;
import controller.DelayMachine;
import model.*;

import java.io.Console;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Scanner;

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
    public void menu(final boolean theDebugMode, final Audio theAudio) {
        if (!theDebugMode) {
            System.out.println("Welcome to...");
            System.out.println();
            DelayMachine.delay(4);
//            theAudio.playSFX(theAudio.titleScreen, -8);
            System.out.println("" +
                    "                    ▄████████    ▄████████  ▄█        ▄█          ▄████████ ███▄▄▄▄   \n" +
                    "                   ███    ███   ███    ███ ███       ███         ███    ███ ███▀▀▀██▄ \n" +
                    "                   ███    █▀    ███    ███ ███       ███         ███    █▀  ███   ███ \n" +
                    "                  ▄███▄▄▄       ███    ███ ███       ███        ▄███▄▄▄     ███   ███ \n" +
                    "                 ▀▀███▀▀▀     ▀███████████ ███       ███       ▀▀███▀▀▀     ███   ███ \n" +
                    "                   ███          ███    ███ ███       ███         ███    █▄  ███   ███ \n" +
                    "                   ███          ███    ███ ███▌    ▄ ███▌    ▄   ███    ███ ███   ███ \n" +
                    "                   ███          ███    █▀  █████▄▄██ █████▄▄██   ██████████  ▀█   █▀  \n" +
                    "                                           ▀         ▀                                ");
            System.out.println();
            DelayMachine.delay(1);
            System.out.println("" +
                    " ▄████████    ▄█    █▄       ▄████████   ▄▄▄▄███▄▄▄▄      ▄███████▄  ▄█   ▄██████▄  ███▄▄▄▄      ▄████████ \n" +
                    "███    ███   ███    ███     ███    ███ ▄██▀▀▀███▀▀▀██▄   ███    ███ ███  ███    ███ ███▀▀▀██▄   ███    ███ \n" +
                    "███    █▀    ███    ███     ███    ███ ███   ███   ███   ███    ███ ███▌ ███    ███ ███   ███   ███    █▀  \n" +
                    "███         ▄███▄▄▄▄███▄▄   ███    ███ ███   ███   ███   ███    ███ ███▌ ███    ███ ███   ███   ███        \n" +
                    "███        ▀▀███▀▀▀▀███▀  ▀███████████ ███   ███   ███ ▀█████████▀  ███▌ ███    ███ ███   ███ ▀███████████ \n" +
                    "███    █▄    ███    ███     ███    ███ ███   ███   ███   ███        ███  ███    ███ ███   ███          ███ \n" +
                    "███    ███   ███    ███     ███    ███ ███   ███   ███   ███        ███  ███    ███ ███   ███    ▄█    ███ \n" +
                    "████████▀    ███    █▀      ███    █▀   ▀█   ███   █▀   ▄████▀      █▀    ▀██████▀   ▀█   █▀   ▄████████▀  ");
            System.out.println();
            System.out.println();
            DelayMachine.delay(3);
            System.out.println("I̲M̲P̲O̲R̲T̲A̲N̲T̲ ̲I̲N̲F̲O̲R̲M̲A̲T̲I̲O̲N̲");
            System.out.println("--Since this game is console-based, you will be using the keyboard to play.");
            System.out.println("--You'll notice that the screen \"updates\" by printing new information on top of old information.");
            System.out.println("--Any input you give must be followed by the ENTER key.");
            System.out.println();
            System.out.println();
        }

//        System.out.println("1 to start game, 2 to exit game");
//        System.out.print("Make your selection: ");
//        return myScanner.next().charAt(0);
    }

    /**
     * Chooses the hero
     * @return the hero choice
     */
    public char chooseHero(final List<Hero> theHeroes) {
        System.out.println();
        System.out.println("Choose your hero!");
        displayUpperSpacer();

        int menuOption = 1;
        for (Hero hero : theHeroes) {
            if (hero.isUnlocked()) {
                System.out.println("  " + menuOption + " for " + hero.getType() + ":");
                System.out.println("  " + hero.getDescription()[0]);
                System.out.println("  " + hero.getDescription()[1]);
            } else {
                System.out.println("  " + menuOption + "  --(LOCKED)--  ");
            }
            System.out.println();
            menuOption++;
        }

        displayLowerSpacer();
        System.out.print("Make your selection: ");
        return myScanner.next().charAt(0);
    }

    /**
     * Introduces you to the game
     */
    public void introductionP1(final boolean theFunnyDialogue, final Audio theAudio) {
        System.out.println("-------------------------INTRODUCTION-------------------------");
        System.out.println();
        DelayMachine.printDelayedText("Welcome hero,");
        DelayMachine.printDelayedText("The dungeon you see looming before you is one of many perils.");
        DelayMachine.printDelayedText("In it lies hordes of monsters, deadly traps, and countless treasures.");
        DelayMachine.printDelayedText("Find the 4 ancient pillars, and you shall be granted access to the final chamber.");
        DelayMachine.printDelayedText("Not even I, the keeper of the dungeon, know what awaits you there.");
        System.out.println();
        DelayMachine.printDelayedText("Now then, what is your name?");
    }

    public String findHeroName() {
        System.out.print("Name (one word): ");
        return myScanner.next();
    }

    /**
     * Introduces you to the game
     */
    public void introductionP2(final boolean theFunnyDialogue, final Audio theAudio,
                               final String theFirstName, final String theFullName) {
        System.out.println();
        DelayMachine.printDelayedTextFast("Wait, are you serious? Your name is just " + theFirstName + "?");
        DelayMachine.delay(2);
        DelayMachine.printDelayedTextFast("That's far too ordinary -_-");
        DelayMachine.delay(4);
        DelayMachine.printDelayedText("I dub thee...");
        System.out.println();
        DelayMachine.delay(5);
        DelayMachine.printDelayedText("【 Sir " + theFullName + "! 】");
        System.out.println();
        DelayMachine.printDelayedText("Now then, " + theFullName + ", are you ready to begin your adventure?");
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
            DelayMachine.printDelayedTextFast("Anyway, choose one of these I guess");

        } else {
            DelayMachine.delay(2);
            theAudio.playSFX(theAudio.dunDunDun, -10);
            DelayMachine.delay(4);
        }

        DelayMachine.printDelayedText("So " + theFullName + ", what kind of adventurer are you anyway?");
    }


    /**
     * Gameplay menu
     * @return gameplay menu choice
     */
    public int gameplayMenu() {
        System.out.println();
        displayChainSpacer();
        System.out.println("╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("╠ 'w' move North, 'd' move East, 's' move South, 'a' move West     ╣");
        System.out.println("╠ 'e' bag, '1' hero info, '2' HELP, '4' main menu, '5' save game   ╣");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝");
        displayChainSpacer();
        System.out.print("Make your selection: ");
        return myScanner.next().charAt(0);
    }

    public void displayNormalSpacer() {
        System.out.println("---------------------------------------------------------------------"); //TODO replace all spacers with this method in the controller
    }

    public void displayChainSpacer() {
        System.out.println("<=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=>");
    }

    public void displayUpperSpacer() {
        System.out.println("╔═══════════════════════════════════════════════════════════════════╗");
    }
    public void displayLowerSpacer() {
        System.out.println("╚═══════════════════════════════════════════════════════════════════╝");
    }



    /**
     * Battle menu for battling monsters
     * @param theHero you
     * @param theMonster the monster you're battling
     * @return battle menu input
     */
    public char battleMenu(Hero theHero, Monster theMonster) { // is this parameter needed?
        System.out.println("\nPlayer Health:  " + theHero.getHealth());
        System.out.println("Monster Health: " + theMonster.getHealth());

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
    public char chooseDifficulty(final boolean theMediumUnlocked, final boolean theHardUnlocked) {
        System.out.println("Choose your difficulty!");
        displayUpperSpacer();
        System.out.println("              ╔════════════════════════════════════════╗             ");
        System.out.println("              ║ 1 for easy | 2 for medium | 3 for hard ║             ");
        System.out.println("              ╚════════════════════════════════════════╝             ");

        if (theHardUnlocked) {
            System.out.println("                     ▲            ▲             ▲                   ");
            System.out.println();
        } else if (theMediumUnlocked) {
            System.out.println("                     ▲            ▲             ^                   ");
            System.out.println("                                             (Locked)               ");
        } else {
            System.out.println("                     ▲            ^             ^                   ");
            System.out.println("                               (Locked)      (Locked)               ");
        }
        displayLowerSpacer();
        System.out.print("Make your selection: ");
        return myScanner.next().charAt(0);
    }

    public void displayDifficultySelected(final Difficulty difficulty) {
        System.out.println("You have selected " + difficulty.toString() + " difficulty.");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
    }

    /**
     * Menu for inventory choice
     * @param theBag your inventory bag
     * @param inBattle whether you're in battle
     * @return the inventory choice
     */
    public char openBag(final Inventory theBag, final boolean inBattle, final Audio theAudio) {
        displayChainSpacer();
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
        displayChainSpacer();
    }


    /**
     * Brings you a menu for whether you want to quit the game or not
     * @return the menu selection
     */
    public char quitProcess() {
        System.out.println("Are you sure you want to quit?");
        System.out.println("1 for yes, 2 for no");
        System.out.print("Make your selection: ");
        return myScanner.next().charAt(0);
    }

    /**
     * Brings you a menu for whether you want to quit the game or not
     * @return the menu selection
     */
    public char goToMainMenu() {
        System.out.println("Are you sure you want to go to the main menu?");
        System.out.println("1 for yes, 2 for no");
        System.out.print("Make your selection: ");
        return myScanner.next().charAt(0);
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
        System.out.println(" ║ " + theHero.getBasicName()[0]);
        System.out.println(" ║ " + theHero.getBasicName()[1]);
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
        System.out.println(" ║ D̲E̲S̲C̲R̲I̲P̲T̲I̲O̲N̲: ");
        System.out.println(" ║ " + theMonster.getDescription()[0]);
        System.out.println(" ║ " + theMonster.getDescription()[1]);
        System.out.println(" ║ ");
        System.out.println(" ║ A̲B̲I̲L̲I̲T̲I̲E̲S̲");
        System.out.println(" ╠ Basic: ");
        System.out.println(" ║ " + theMonster.getBasicName()[0]);
        System.out.println(" ║ " + theMonster.getBasicName()[1]);
        System.out.println(" ║ ");
        System.out.println(" ╠ Special: ");
        System.out.println(" ║ " + theMonster.getSpecialName()[0]);
        System.out.println(" ║ " + theMonster.getSpecialName()[1]);
        System.out.println(" ║ ");
        System.out.println(" ╠ Passive: ");
        System.out.println(" ║ " + theMonster.getPassiveName()[0]);
        System.out.println(" ║ " + theMonster.getPassiveName()[1]);
        System.out.println(" ║ ");
        System.out.println(" ║ S̲T̲A̲T̲S̲:");
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
        System.out.println(" -You collected a PILLAR!");
    }
    /**
     * Displays Encapsulation Pillar Message
     */
    public void displayEncapsulationPillarMsg() {
        System.out.println(" -You collected a PILLAR!");
    }
    /**
     * Displays Inheritance Pillar Message
     */
    public void displayInheritancePillarMsg() {
        System.out.println(" -You collected a PILLAR!");
    }
    /**
     * Displays Polymorphism Pillar Message
     */
    public void displayPolymorphismPillarMsg() {
        System.out.println(" -You collected a PILLAR!");
    }


    /**
     * Asks whether you want to start a new game or continue on a prior save
     * @return the game choice
     */
    public char continueOrNewGameMenu() {
        System.out.println("Would you like to start a new game or continue a previous game?");
        System.out.println("1 for new game, 2 for continue game");
        System.out.print("Make your selection: ");
        return myScanner.next().charAt(0);
    }

    /**
     * The monster turn text
     */
    public void monsterTurnText() {
        displayChainSpacer();
        System.out.println(" -The Enemy is making their move...");
    }

    public void monsterHealMsg(final int theHealAmt) {
        System.out.println(" -The enemy heals themselves for " + theHealAmt + " Health Points!");
        DelayMachine.delay(1);
    }

    public void monsterSelectsBasicMsg(final Monster theMonster, final Hero thePlayer) {
        System.out.println(" -" + theMonster.getName() + theMonster.getBasicSelectMsg() +
                thePlayer.getName() + theMonster.getExtendedBasicSelectMsg());
        DelayMachine.delay(3);
    }
    public void monsterSelectsSpecialMsg(final Monster theMonster, final Hero thePlayer) {
        System.out.println(" -" + theMonster.getName() + theMonster.getSpecialSelectMsg() +
                thePlayer.getName() + theMonster.getExtendedSpecialSelectMsg());
        DelayMachine.delay(3);
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

        DelayMachine.delay(4);
    }
    public void playerSelectsSpecialMsg(final Hero thePlayer, final Monster theMonster) {

        System.out.println(" -" + thePlayer.getName() + thePlayer.getSpecialSelectMsg() +
                theMonster.getName() + thePlayer.getExtendedSpecialSelectMsg() + "...");

        DelayMachine.delay(4);
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
                                  final Difficulty theDifficulty, boolean funnyMode, final boolean debugMode) {

        displayChainSpacer();
        System.out.println(" -You escaped the dungeon!");
        System.out.println();
        System.out.println("██╗   ██╗ ██████╗ ██╗   ██╗    ██╗    ██╗██╗███╗   ██╗██╗\n" +
                           "╚██╗ ██╔╝██╔═══██╗██║   ██║    ██║    ██║██║████╗  ██║██║\n" +
                           " ╚████╔╝ ██║   ██║██║   ██║    ██║ █╗ ██║██║██╔██╗ ██║██║\n" +
                           "  ╚██╔╝  ██║   ██║██║   ██║    ██║███╗██║██║██║╚██╗██║╚═╝\n" +
                           "   ██║   ╚██████╔╝╚██████╔╝    ╚███╔███╔╝██║██║ ╚████║██╗\n" +
                           "   ╚═╝    ╚═════╝  ╚═════╝      ╚══╝╚══╝ ╚═╝╚═╝  ╚═══╝╚═╝");
        System.out.println();

        if (!debugMode) {
            DelayMachine.delay(2);
            DelayMachine.printDelayedText("  RESULTS:");
            DelayMachine.printDelayedText("  You beat the game on " + theDifficulty + " difficulty!");
            DelayMachine.printDelayedText("  You took " + theHeroSteps + " steps to escape the dungeon.");
            DelayMachine.printDelayedText("  You reached level " + theLevel + ".");
            DelayMachine.printDelayedText("  You defeated " + theMonstersDefeated + " monsters.");
        }
    }

    /**
     * The battle win message
     */
    public void displayBattleWinMsg(final Monster theMonster) {
        System.out.println();
        DelayMachine.delay(1);
        System.out.println(" -" + theMonster.getDeathMsg()[0]);
        System.out.println();
        DelayMachine.delay(4);
        System.out.println("╒≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡╕");
        System.out.println("│ ENEMY DEFEATED │");
        System.out.println("╘≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡╛");
        System.out.println();
        DelayMachine.delay(4);
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
        System.out.println("***********************************");
        System.out.println(" You have entered cheat mode ಠ_ಠ ");
        System.out.println("***********************************");
    }

    /**
     * The funny mode message
     */
    public void displayFunnyDialogueModeMsg() {
        System.out.println("****************************************");
        System.out.println(" You have entered funny mode ( ͡° ͜ʖ ͡°) ");
        System.out.println("****************************************");
    }

    /**
     * The debug mode message
     */
    public void displayDebugModeMsg() {
        System.out.println("************************************");
        System.out.println(" You have entered debug mode ❐‿❑ ");
        System.out.println("************************************");
    }

    public void displayWrongCode() {
        DelayMachine.printDelayedText("Uhhhhhh");
        DelayMachine.printDelayedTextFast("that's not a valid code. Please try again.");
    }


    /**
     * The hero health
     * @param theHero you
     */
    public void displayHeroHealth(final Hero theHero) {
        System.out.print("Health: " + theHero.getHealth() + "/" + theHero.getMaxHealth());
    }

    public void displayHeroDirection(final Hero theHero) {
        System.out.print("    Facing: " + theHero.getDirection());
    }

    /**
     * Display the steps left with the vision potion
     * @param theSteps the steps the hero has taken while the vision potion is active
     */
    public void displayStepsWithVisionPotion(final int theSteps) {
        System.out.print("    Steps left with vision potion: " + (4-theSteps));
    }

    /**
     * The game start message
     */
    public void displayStartMsg() {
        System.out.println("                     ╔═════════════════════════╗                     ");
        System.out.println("---------------------║ Welcome to the Dungeon! ║---------------------");
        System.out.println("                     ╚═════════════════════════╝                     ");
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
        System.out.println("████████████████████████████████████████████████████████████████████");
        System.out.println("█                       --- BATTLE START ---                       █");
        System.out.println("████████████████████████████████████████████████████████████████████");
        System.out.println();
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

    public char playIntroOrNot() {
        System.out.println("Would you like to play the intro? (please say yes)");
        System.out.println("1 for no, 2 for yes");
        System.out.print("Make your selection: ");
        return myScanner.next().charAt(0);
    }

    public void displayWallMsg() {
        System.out.println("WHAT ARE YOU DOING IN A WALL?! GET OUT OF THERE YOU FOOL");
    }

    public void displayHitWallMsg() {
        System.out.println(" -You ran into a wall you oaf");
    }

    public void heroIntroduction(Hero hero) {
        System.out.println();
        DelayMachine.printDelayedText("Ah, a " + hero.getType() + "!");
        DelayMachine.printDelayedText("I'm sure that skill will serve you well.");
        DelayMachine.printDelayedText("Good luck!");
        DelayMachine.printDelayedText("*cranks gate open*");
        System.out.println();

    }

    public void displayInstructions(final Audio theAudio) {
        System.out.println();
        System.out.println();
        System.out.println("" +
                "88 88b 88 .dP\"Y8 888888 88\"\"Yb 88   88  dP\"\"b8 888888 88  dP\"Yb  88b 88 .dP\"Y8 \n" +
                "88 88Yb88 `Ybo.\"   88   88__dP 88   88 dP   `\"   88   88 dP   Yb 88Yb88 `Ybo.\" \n" +
                "88 88 Y88 o.`Y8b   88   88\"Yb  Y8   8P Yb        88   88 Yb   dP 88 Y88 o.`Y8b \n" +
                "88 88  Y8 8bodP'   88   88  Yb `YbodP'  YboodP   88   88  YbodP  88  Y8 8bodP' ");
        System.out.println();
        System.out.println("▂ ▃ ▄ ▅ ▆ ▇ █  What Am I Looking at?  █ ▇ ▆ ▅ ▄ ▃ ▂");
        System.out.println();
        System.out.println(" The giant square you see is a top-down view of the dungeon,");
        System.out.println(" which is a 2D grid of rooms/walls.");
        System.out.println(" The empty spaces are rooms, and the '*' symbols are walls.");
        System.out.println(" The '□' symbol is you, the hero.");
        System.out.println(" The contents of the rooms will remain hidden until you enter them.");
        System.out.println();
        System.out.println(" Each room may contain one or more of the following:");
        System.out.println("" +
                " ║ '▮' = exit\n" +
                " ║ 'M' = monster\n" +
                " ║ 'X' = pit\n" +
                " ║ 'p' = potion\n" +
                " ║ 'I' = pillar\n" +
                " ║ '&' = multiple items in the same room");
        System.out.println();
        System.out.println(" You may find a vision potion on your journey.");
        System.out.println(" Using this potion will reveal the contents of EVERY room");

        pressAnyKeyNextPage();
        theAudio.playSFX(theAudio.swishOn, -10);

        System.out.println();
        System.out.println();
        System.out.println("" +
                "88 88b 88 .dP\"Y8 888888 88\"\"Yb 88   88  dP\"\"b8 888888 88  dP\"Yb  88b 88 .dP\"Y8 \n" +
                "88 88Yb88 `Ybo.\"   88   88__dP 88   88 dP   `\"   88   88 dP   Yb 88Yb88 `Ybo.\" \n" +
                "88 88 Y88 o.`Y8b   88   88\"Yb  Y8   8P Yb        88   88 Yb   dP 88 Y88 o.`Y8b \n" +
                "88 88  Y8 8bodP'   88   88  Yb `YbodP'  YboodP   88   88  YbodP  88  Y8 8bodP' ");
        System.out.println();
        System.out.println("▂ ▃ ▄ ▅ ▆ ▇ █  How Does the Dungeon Work?  █ ▇ ▆ ▅ ▄ ▃ ▂");
        System.out.println();
        System.out.println("Beware! Monsters will spawn in a random location every 3 steps you take.");
//         talk about what different potions do

        pressAnyKeyNextPage();
        theAudio.playSFX(theAudio.swishOn, -10);

        System.out.println();
        System.out.println();
        System.out.println("" +
                "88 88b 88 .dP\"Y8 888888 88\"\"Yb 88   88  dP\"\"b8 888888 88  dP\"Yb  88b 88 .dP\"Y8 \n" +
                "88 88Yb88 `Ybo.\"   88   88__dP 88   88 dP   `\"   88   88 dP   Yb 88Yb88 `Ybo.\" \n" +
                "88 88 Y88 o.`Y8b   88   88\"Yb  Y8   8P Yb        88   88 Yb   dP 88 Y88 o.`Y8b \n" +
                "88 88  Y8 8bodP'   88   88  Yb `YbodP'  YboodP   88   88  YbodP  88  Y8 8bodP' ");
        System.out.println();
        System.out.println("▂ ▃ ▄ ▅ ▆ ▇ █  How do I battle?  █ ▇ ▆ ▅ ▄ ▃ ▂");
        System.out.println();
        System.out.println(" You will eventually encounter monsters in the dungeon.");
        System.out.println(" After encountering one, a battle will commence.");
        System.out.println(" Whoever has a higher speed will attack first.");
        System.out.println(" You will take turns attacking the monster until one of you dies.");
        System.out.println();
        System.out.println(" On your turn, you have 3 choices:");
        System.out.println("  ║ 1. Use your basic attack");
        System.out.println("  ║ 2. Use your special attack");
        System.out.println("  ║ 3. Use a potion from your inventory");
        System.out.println();
        System.out.println(" Strategy is key!");
        System.out.println(" Use all 3 options to gain the upper hand and win the battle.");
        System.out.println();
        System.out.println(" There are negative effects characters can inflict on each other.");
        System.out.println(" These effects will last for a certain number of turns.");
        System.out.println(" The effects are:");
        System.out.println("  ║ Poison: lose some health every turn");
        System.out.println("  ║ Blinded: miss your next attack");
        System.out.println("  ║ Stuck: skip your next turn");
        System.out.println("  ║ Weakened: deal less damage");
        System.out.println("  ║ Vulnerable: next hit on you will deal 3x the damage");
        System.out.println("  ║ Silenced: can't use special ability");

        pressAnyKeyNextPage();
        theAudio.playSFX(theAudio.swishOn, -10);

        System.out.println();
        System.out.println();
        System.out.println("" +
                "88 88b 88 .dP\"Y8 888888 88\"\"Yb 88   88  dP\"\"b8 888888 88  dP\"Yb  88b 88 .dP\"Y8 \n" +
                "88 88Yb88 `Ybo.\"   88   88__dP 88   88 dP   `\"   88   88 dP   Yb 88Yb88 `Ybo.\" \n" +
                "88 88 Y88 o.`Y8b   88   88\"Yb  Y8   8P Yb        88   88 Yb   dP 88 Y88 o.`Y8b \n" +
                "88 88  Y8 8bodP'   88   88  Yb `YbodP'  YboodP   88   88  YbodP  88  Y8 8bodP' ");
        System.out.println();
        System.out.println("▂ ▃ ▄ ▅ ▆ ▇ █  How Do I Win?  █ ▇ ▆ ▅ ▄ ▃ ▂");
        System.out.println();
        System.out.println(" You win by escaping through the exit door.");
        System.out.println(" However, the exit will remain locked until you possess");
        System.out.println(" all 4 pillars in your inventory.");
    }

    public void displayCantUseItemDuringBattle(Potion thePotion) {
        System.out.println(" -You can't use a " + thePotion.type() + " during a battle!");
    }

    public void displayCantUseItemOutsideBattle(Potion thePotion) {
        System.out.println(" -You can't use a " + thePotion.type() + " outside of a battle!");
    }

    public void levelUpMsg(final int theLevel) {
        System.out.println(" -You leveled up!");
        System.out.println("  You are now level " + theLevel);
        System.out.println("  Gained +5 damage, +10 max health");
        System.out.println("  Your health is fully restored!");
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
        System.out.println("████████████████████████████████████████████████████████████████████");
        System.out.println("█                        --- BATTLE END ---                        █");
        System.out.println("████████████████████████████████████████████████████████████████████");
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

    public char pressAnyKeyGoBack() {
        System.out.println();
        System.out.println("Press any key, then ENTER to return...");
        return myScanner.next().charAt(0);
    }

    public char pressAnyKeyContinue() {
        System.out.println();
        System.out.println("Press any key, then ENTER to continue...");
        return myScanner.next().charAt(0);
    }

    public char pressAnyKeyNextPage() {
        System.out.println();
        System.out.println("Press any key, then ENTER for the next page...");
        return myScanner.next().charAt(0);
    }


    public void displayUnlockedJuggernautAndThief() {
        System.out.println();
        System.out.println("╔════════════════════ ◈ INFO ◈ ════════════════════╗");
        System.out.println("║ You've unlocked the JUGGERNAUT and THIEF heroes!  ║");
        System.out.println("║ They will now appear in the hero selection menu.  ║");
        System.out.println("╚═══════════════════════════════════════════════════╝");
        System.out.println();
    }
    public void displayUnlockedDoctorAndNinja() {
        System.out.println();
        System.out.println("╔════════════════════ ◈ INFO ◈ ════════════════════╗");
        System.out.println("║   You've unlocked the DOCTOR and NINJA heroes!    ║");
        System.out.println("║ They will now appear in the hero selection menu.  ║");
        System.out.println("╚═══════════════════════════════════════════════════╝");
        System.out.println();
    }
    public void displayUnlockedScientist() {
        System.out.println();
        System.out.println("╔════════════════════ ◈ INFO ◈ ════════════════════╗");
        System.out.println("║         You've unlocked the SCIENTIST hero        ║");
        System.out.println("║  He will now appear in the hero selection menu.   ║");
        System.out.println("╚═══════════════════════════════════════════════════╝");
        System.out.println();
    }
    public void displayUnlockedMage() {
        System.out.println();
        System.out.println("╔════════════════════ ◈ INFO ◈ ════════════════════╗");
        System.out.println("║           You've unlocked the MAGE hero!          ║");
        System.out.println("║  He will now appear in the hero selection menu.   ║");
        System.out.println("╚═══════════════════════════════════════════════════╝");
        System.out.println();
    }

    public void displayUnlockedBeastmaster() {
        System.out.println("╔════════════════════ ◈ INFO ◈ ════════════════════╗");
        System.out.println("║        You've unlocked the BEASTMASTER hero       ║");
        System.out.println("║  He will now appear in the hero selection menu.   ║");
        System.out.println("╚═══════════════════════════════════════════════════╝");
        System.out.println();
    }

    public void displayUnlockedAll() {
        System.out.println("╔════════════════════ ◈ INFO ◈ ════════════════════╗");
        System.out.println("║           You've unlocked ALL the heroes          ║");
        System.out.println("║                 Congratulations!!!                ║");
        System.out.println("╚═══════════════════════════════════════════════════╝");
        System.out.println();
    }

    public void displayHintUnlocking() {
        System.out.println("╔════════════════════ ? HINT ? ═════════════════════╗");
        System.out.println("║       There are still more heroes to find.        ║");
        System.out.println("║    Keep exploring the dungeon to unlock them...   ║");
        System.out.println("╚═══════════════════════════════════════════════════╝");
        System.out.println();
    }



    public void displayCredits(final Audio theAudio) { // CREDITS
        DelayMachine.delay(4);
        theAudio.playMusic(theAudio.rickRollSong, false, -10);
        DelayMachine.delay(2);
        System.out.println();
        System.out.println();
        System.out.println("" +
                "    ,o888888o.    8 888888888o.   8 8888888888   8 888888888o.       8 8888 8888888 8888888888    d888888o.   \n" +
                "   8888     `88.  8 8888    `88.  8 8888         8 8888    `^888.    8 8888       8 8888        .`8888:' `88. \n" +
                ",8 8888       `8. 8 8888     `88  8 8888         8 8888        `88.  8 8888       8 8888        8.`8888.   Y8 \n" +
                "88 8888           8 8888     ,88  8 8888         8 8888         `88  8 8888       8 8888        `8.`8888.     \n" +
                "88 8888           8 8888.   ,88'  8 888888888888 8 8888          88  8 8888       8 8888         `8.`8888.    \n" +
                "88 8888           8 888888888P'   8 8888         8 8888          88  8 8888       8 8888          `8.`8888.   \n" +
                "88 8888           8 8888`8b       8 8888         8 8888         ,88  8 8888       8 8888           `8.`8888.  \n" +
                "`8 8888       .8' 8 8888 `8b.     8 8888         8 8888        ,88'  8 8888       8 8888       8b   `8.`8888. \n" +
                "   8888     ,88'  8 8888   `8b.   8 8888         8 8888    ,o88P'    8 8888       8 8888       `8b.  ;8.`8888 \n" +
                "    `8888888P'    8 8888     `88. 8 888888888888 8 888888888P'       8 8888       8 8888        `Y8888P ,88P' \n" +
                "▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄  ");


        DelayMachine.delay(2);
        System.out.println();
        DelayMachine.delay(2);
        System.out.println();
        DelayMachine.delay(2);
        System.out.println();
        DelayMachine.delay(2);
        System.out.println();
        DelayMachine.delay(2);
        System.out.println("" +
                "  _____                                               _             \n" +
                " |  __ \\                                             (_)            \n" +
                " | |__) | __ ___   __ _ _ __ __ _ _ __ ___  _ __ ___  _ _ __   __ _ \n" +
                " |  ___/ '__/ _ \\ / _` | '__/ _` | '_ ` _ \\| '_ ` _ \\| | '_ \\ / _` |\n" +
                " | |   | | | (_) | (_| | | | (_| | | | | | | | | | | | | | | | (_| |\n" +
                " |_|   |_|  \\___/ \\__, |_|  \\__,_|_| |_| |_|_| |_| |_|_|_| |_|\\__, |\n" +
                "                   __/ |                                       __/ |\n" +
                "                  |___/                                       |___/ ");

        DelayMachine.delay(2);
        System.out.println();
        DelayMachine.delay(2);
        System.out.println("  Nathan Hinthorne");
        DelayMachine.delay(2);
        System.out.println();
        DelayMachine.delay(2);
        System.out.println("  (with a little help from Brendan Smith and Austin Roaf)");
        DelayMachine.delay(2);
        System.out.println();
        DelayMachine.delay(2);
        System.out.println();
        DelayMachine.delay(2);
        System.out.println();
        DelayMachine.delay(2);
        System.out.println();

        System.out.println("" +
                "  __  __           _      \n" +
                " |  \\/  |         (_)     \n" +
                " | \\  / |_   _ ___ _  ___ \n" +
                " | |\\/| | | | / __| |/ __|\n" +
                " | |  | | |_| \\__ \\ | (__ \n" +
                " |_|  |_|\\__,_|___/_|\\___|");

        DelayMachine.delay(2);
        System.out.println();
        DelayMachine.delay(2);
        System.out.println(" Starting Off Theme by: Nathan Hinthorne");
        DelayMachine.delay(2);
        System.out.println();
        DelayMachine.delay(2);
        System.out.println(" Dungeon Theme by: Nathan Hinthorne");
        DelayMachine.delay(2);
        System.out.println();
        DelayMachine.delay(2);
        System.out.println(" Battle Theme by: Nathan Hinthorne");
        DelayMachine.delay(2);
        System.out.println();
        DelayMachine.delay(2);
        System.out.println(" Victory Theme by: Jon Presstone");
        DelayMachine.delay(2);
        System.out.println();
        DelayMachine.delay(2);
        System.out.println(" Credits Theme by: 8 Bit Universe");
        DelayMachine.delay(2);
        System.out.println();
        DelayMachine.delay(2);
        System.out.println();
        DelayMachine.delay(2);
        System.out.println();
        DelayMachine.delay(2);
        System.out.println("" +
                "   _____                 _       _   _______ _                 _        \n" +
                "  / ____|               (_)     | | |__   __| |               | |       \n" +
                " | (___  _ __   ___  ___ _  __ _| |    | |  | |__   __ _ _ __ | | _____ \n" +
                "  \\___ \\| '_ \\ / _ \\/ __| |/ _` | |    | |  | '_ \\ / _` | '_ \\| |/ / __|\n" +
                "  ____) | |_) |  __/ (__| | (_| | |    | |  | | | | (_| | | | |   <\\__ \\\n" +
                " |_____/| .__/ \\___|\\___|_|\\__,_|_|    |_|  |_| |_|\\__,_|_| |_|_|\\_\\___/\n" +
                "        | |                                                             \n" +
                "        |_|                                                             ");

        DelayMachine.delay(2);
        System.out.println();
        DelayMachine.delay(2);
        System.out.println("  My friends and family, for all their amazing ideas for characters and abilities.");
        DelayMachine.delay(2);
        System.out.println();
        DelayMachine.delay(2);
        System.out.println("  My professor, Tom Capaul, for giving me the assignment which started this whole game off.");

        // make the rest of the text run off the screen with a bunch of blank lines
        for (int i = 0; i < 30; i++) {
            DelayMachine.delay(2);
            System.out.println();
        }

        DelayMachine.delay(4);
        displayRecommendListening();
        DelayMachine.delay(4);
        pressAnyKeyContinue();
        theAudio.stopAll();
    }

    public char displayPlayAgainMenu() {
        System.out.println("Would you like to play again? (1 for yes, 2 for no)");
        System.out.print("Make your selection: ");
        return myScanner.next().charAt(0);
    }

    public void displayCyaNerd() {
//        System.out.println();
//        DelayMachine.printDelayedText("Nah, you're trapped here forever now.");
//        DelayMachine.delay(2);
//        System.out.println();
//        DelayMachine.printDelayedText("Don't you dare hit that X button...");
//        System.out.println();
//        DelayMachine.delay(3);
//        System.out.println();
//        DelayMachine.printDelayedTextFast("Alright, whatever. I'll let you leave.");
        System.out.println();
        System.out.println("" +
                " ▄▄▄▄▄▄▄▄▄▄▄  ▄         ▄  ▄▄▄▄▄▄▄▄▄▄▄            ▄▄        ▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄  \n" +
                "▐░░░░░░░░░░░▌▐░▌       ▐░▌▐░░░░░░░░░░░▌          ▐░░▌      ▐░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░▌ \n" +
                "▐░█▀▀▀▀▀▀▀▀▀ ▐░▌       ▐░▌▐░█▀▀▀▀▀▀▀█░▌          ▐░▌░▌     ▐░▌▐░█▀▀▀▀▀▀▀▀▀ ▐░█▀▀▀▀▀▀▀█░▌▐░█▀▀▀▀▀▀▀█░▌\n" +
                "▐░▌          ▐░▌       ▐░▌▐░▌       ▐░▌          ▐░▌▐░▌    ▐░▌▐░▌          ▐░▌       ▐░▌▐░▌       ▐░▌\n" +
                "▐░▌          ▐░█▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄█░▌          ▐░▌ ▐░▌   ▐░▌▐░█▄▄▄▄▄▄▄▄▄ ▐░█▄▄▄▄▄▄▄█░▌▐░▌       ▐░▌\n" +
                "▐░▌          ▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌          ▐░▌  ▐░▌  ▐░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░▌       ▐░▌\n" +
                "▐░▌           ▀▀▀▀█░█▀▀▀▀ ▐░█▀▀▀▀▀▀▀█░▌          ▐░▌   ▐░▌ ▐░▌▐░█▀▀▀▀▀▀▀▀▀ ▐░█▀▀▀▀█░█▀▀ ▐░▌       ▐░▌\n" +
                "▐░▌               ▐░▌     ▐░▌       ▐░▌          ▐░▌    ▐░▌▐░▌▐░▌          ▐░▌     ▐░▌  ▐░▌       ▐░▌\n" +
                "▐░█▄▄▄▄▄▄▄▄▄      ▐░▌     ▐░▌       ▐░▌          ▐░▌     ▐░▐░▌▐░█▄▄▄▄▄▄▄▄▄ ▐░▌      ▐░▌ ▐░█▄▄▄▄▄▄▄█░▌\n" +
                "▐░░░░░░░░░░░▌     ▐░▌     ▐░▌       ▐░▌          ▐░▌      ▐░░▌▐░░░░░░░░░░░▌▐░▌       ▐░▌▐░░░░░░░░░░▌ \n" +
                " ▀▀▀▀▀▀▀▀▀▀▀       ▀       ▀         ▀            ▀        ▀▀  ▀▀▀▀▀▀▀▀▀▀▀  ▀         ▀  ▀▀▀▀▀▀▀▀▀▀  ");
        System.out.println();
    }

    public void displayRecommendListening() {
        System.out.println("Personally, I'd recommend listening to the rest of the song. It's pretty good.");
        System.out.println("But when you're ready.");
    }

    public String cheatCodeMenu() {
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("╔██♥☺▓▒▓▒♦▒██▒(ʘ ͟ʖ ʘ)══▓▒█♠♣▒█♥▒▓█▒♣▓█══♣▒▓▒☻█▒═▒♥═╗");
        System.out.println("▒                                                  ▓");
        System.out.println("█          ╔═╗╦ ╦╔═╗╔═╗╔╦╗  ╔╦╗╔═╗╔╗╔╦ ╦           ▒");
        System.out.println("║          ║  ╠═╣║╣ ╠═╣ ║   ║║║║╣ ║║║║ ║           ♦");
        System.out.println("☺          ╚═╝╩ ╩╚═╝╩ ╩ ╩   ╩ ╩╚═╝╝╚╝╚═╝           █");
        System.out.println("▓                                                  ║");
        System.out.println("╚═▓♣▒¯\\_(ツ)_/¯══▓▓█♥▓☺▒▒═══▒▒▒▓▓♣▓(͡° ͜ʖ ͡°)█════▓▒▓▓╝");

        System.out.println();
        System.out.print("Enter a cheat code: ");
        String input = myScanner.nextLine(); // Read the entire line
        myScanner.nextLine(); // Consume the leftover newline character
        return input;
    }


    public void displayMazeAbility(final Hero theHero) {
//        System.out.println(theHero.activateMazeAbility());
    }

    public char quitOrContinueMenu() {
        System.out.println("Would you like to quit or continue? (1 for continue, 2 for quit)");
        System.out.print("Make your selection: ");
        return myScanner.next().charAt(0);
    }

    public char mainMenu() {
        for (int i = 0; i < 15; i++) {
            System.out.println();
        }
        System.out.println("" +
                "8b    d8    db    88 88b 88     8b    d8 888888 88b 88 88   88 \n" +
                "88b  d88   dPYb   88 88Yb88     88b  d88 88__   88Yb88 88   88 \n" +
                "88YbdP88  dP__Yb  88 88 Y88     88YbdP88 88\"\"   88 Y88 Y8   8P \n" +
                "88 YY 88 dP\"\"\"\"Yb 88 88  Y8     88 YY 88 888888 88  Y8 `YbodP' ");
        System.out.println("╔═════════════════════╗");
        System.out.println("║ 1. Continue         ║");
        System.out.println("║ 2. Change Hero      ║");
        System.out.println("║ 3. Change Name      ║");
        System.out.println("║ 4. Cheat Code Menu  ║");
        System.out.println("║ 5. Quit             ║");
        System.out.println("╚═════════════════════╝");

        System.out.print("Make your selection: ");
        return myScanner.next().charAt(0);
    }

    public void codeSuccessMsg() {
        System.out.println();
        DelayMachine.printDelayedText(" -CODE ACCEPTED");
        System.out.println();
    }


    public void codeFailMsg() {
        System.out.println();
        DelayMachine.printDelayedText(" -CODE DENIED");
        System.out.println();
    }

    public void displayHeroSelected(final Hero theHero) {
        System.out.println();
        System.out.println(" -You are now the " + theHero.getName() + "!");
        System.out.println();
    }

    public void sentToMainMenuMsg() {
        System.out.println();
        displayChainSpacer();
        System.out.println(" You will now be sent to the main menu, where you can select");
        System.out.println(" a different hero and continue your adventure...");
        displayChainSpacer();
    }

    public void displayHeroNameChanged(final Hero theHero) {
        System.out.println();
        System.out.println(" -You are now 【 Sir " + theHero.getName() + "! 】");
        System.out.println();
    }
}
