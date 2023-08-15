package view;
import controller.DelayMachine;
import model.characters.boss.Missile;
import model.characters.Ability;
import model.characters.Debuff;
import model.characters.DungeonCharacter;
import model.characters.heroes.Inventory;
import model.dungeon.Difficulty;
import model.dungeon.Dungeon;
import model.characters.heroes.Hero;
import model.characters.monsters.Monster;
import model.dungeon.Parchment;
import model.potions.Potion;

import java.io.Console;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Queue;
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
        displayChainSpacer();
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
            if (menuOption != theHeroes.size()) { // leave out a spacer for the last hero
                System.out.println();
            }
            menuOption++;
        }

        displayLowerSpacer();
        displayChainSpacer();
        System.out.print("Make your selection: ");
        return myScanner.next().charAt(0);
    }

    /**
     * Introduces you to the game
     */
    public void introductionP1(final boolean theFunnyDialogue, final Audio theAudio) {
        System.out.println("-------------------------INTRODUCTION-------------------------");
        System.out.println();
        DelayMachine.printDelayedText(TalkingCharacters.KEEPER, "Welcome hero,");
        DelayMachine.printDelayedText(TalkingCharacters.KEEPER,"The dungeon you see looming before you is one of many perils.");
        DelayMachine.printDelayedText(TalkingCharacters.KEEPER,"In it lies hordes of monsters, deadly traps, and countless treasures.");
        DelayMachine.printDelayedText(TalkingCharacters.KEEPER,"Find the 4 ancient pillars, and you shall be granted access to the final chamber.");
        DelayMachine.printDelayedText(TalkingCharacters.KEEPER,"Not even I, the keeper of the dungeon, know what awaits you there.");
        System.out.println();
        DelayMachine.printDelayedText(TalkingCharacters.KEEPER,"Now then, what is your name?");
    }

    public String findHeroName() {
        System.out.print("Name (one word): ");
        return myScanner.next();
    }

    /**
     * Introduces you to the game
     */
    public void introductionP2(final boolean theFunnyMode, final Audio theAudio,
                               final String theFirstName, final String theFullName) {
        System.out.println();

        if (theFunnyMode) {
            DelayMachine.printDelayedTextFast(TalkingCharacters.KEEPER,"Wait, are you serious? Your name is just " + theFirstName + "?");
            DelayMachine.delay(2);
            DelayMachine.printDelayedTextFast(TalkingCharacters.KEEPER,"That's far too ordinary -_-");
            DelayMachine.delay(4);
            DelayMachine.printDelayedText(TalkingCharacters.KEEPER,"I dub thee...");
            System.out.println();
            DelayMachine.delay(5);
            DelayMachine.printDelayedText(TalkingCharacters.KEEPER,"【 Sir " + theFullName + "! 】");
        } else {
            DelayMachine.printDelayedText(TalkingCharacters.KEEPER,"Ah, " + theFirstName + ", a fine name indeed.");
            DelayMachine.printDelayedText(TalkingCharacters.KEEPER,"However, 【" + theFullName + "】 is befitting of one such as yourself.");
        }
        System.out.println();
        DelayMachine.printDelayedText(TalkingCharacters.KEEPER,"Now then, " + theFullName + ", are you ready to begin your adventure?");
        DelayMachine.printDelayedText(TalkingCharacters.KEEPER,"Don't think you're the first to explore this dungeon.");
        DelayMachine.printDelayedText(TalkingCharacters.KEEPER,"Many others have come before you... Not one has made it out alive.");
        DelayMachine.printDelayedText(TalkingCharacters.KEEPER,"Do you think you have what it takes to overcome this dungeon?");
        DelayMachine.delay(2);
        DelayMachine.printDelayedText(TalkingCharacters.KEEPER,"Or will you become yet another FALLEN CHAMPION?");
        System.out.println();

        if (theFunnyMode) {
            DelayMachine.delay(3);
            theAudio.playSFX(theAudio.rimshot, -10);
            DelayMachine.delay(8);
            DelayMachine.printDelayedTextFast(TalkingCharacters.KEEPER,"By the way, that was a rhetorical question, you don't actually get to answer.");
            DelayMachine.printDelayedTextFast(TalkingCharacters.KEEPER,"Anyway, choose one of these I guess");

        } else {
            DelayMachine.delay(3);
            theAudio.playSFX(theAudio.dunDunDun, -10);
            DelayMachine.delay(8);
            DelayMachine.printDelayedText(TalkingCharacters.KEEPER,"So " + theFullName + ", what kind of adventurer are you anyway?");
        }
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
     * @return battle menu input
     */
    public char battleMenu(final Hero theHero) { // is this parameter needed?
        System.out.println(" Make your move!");
        System.out.println();

        String specialButton = " ['2' - Special] ";
        if (theHero.onCooldown() || theHero.hasDebuff(Debuff.SILENCE)) {
            specialButton = " ║'2' - Special║ ";
        }

        System.out.println("               ┏━━━━ ( Battle Menu ) ━━━━┓");
        System.out.println(" ┏━━━━━━━━━━━━━┛                         ┗━━━━━━━━━━━━━┓");
        System.out.println(" ┃         ['1' - Basic]    " + specialButton + "          ┃");
        System.out.println(" ┃ ['e' - Bag]  ['r' - Monster Info]  ['q' - Run Away] ┃");
        System.out.println(" ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
        System.out.println();
        System.out.print(" Choose an action: ");

        return myScanner.next().charAt(0);
    }

    public void displayHeroAndMonsterInfo(final Hero theHero, final Monster theMonster) {
        // hero info
        System.out.println();
        System.out.println(" ║ Hero:");
        displayHealth(theHero);
        if (!theHero.getActiveDebuffs().isEmpty()) {
            displayDebuffs(theHero);
        } else {
            System.out.println();
        }

        // monster info
        System.out.println();
        System.out.println(" ║ Monster:");
        displayHealth(theMonster);
        if (!theMonster.getActiveDebuffs().isEmpty()) {
            displayDebuffs(theMonster);
        } else {
            System.out.println();
        }
    }

    /**
     * The character health
     * @param theCharacter the character's health to display
     */
    public void displayHealth(final DungeonCharacter theCharacter) {

        System.out.print(" ║    ❤ H̲E̲A̲L̲T̲H̲: " + theCharacter.getHealth() + "/" + theCharacter.getMaxHealth());
    }

    /**
     * The character debuffs
     * @param theCharacter the character's debuffs to display
     */
    public void displayDebuffs(final DungeonCharacter theCharacter) {
        System.out.println();
        System.out.print(" ║    ✺ S̲T̲A̲T̲U̲S̲:");
        for (Debuff debuff : theCharacter.getActiveDebuffs().keySet()) {
            System.out.print(" (" + debuff + ")");
        }
        System.out.println();
    }

    /**
     * The character health in dungeon
     * @param theCharacter the character's health to display
     */
    public void displayHealthInDungeon(final DungeonCharacter theCharacter) {

        System.out.print(" ❤ H̲E̲A̲L̲T̲H̲: " + theCharacter.getHealth() + "/" + theCharacter.getMaxHealth());
    }

    /**
     * Menu for choosing the difficulty
     * @return the difficulty choice
     */
    public char chooseDifficulty(final boolean theMediumUnlocked, final boolean theHardUnlocked,
                                 final boolean theGlitchUnlocked) {
        System.out.println();
        System.out.println();
        System.out.println("Choose your difficulty!");
        displayChainSpacer();

        if (theGlitchUnlocked) {
            System.out.println("           ╔═══════════════════════════════════════ʘ═---══▒═-╗     ");
            System.out.println("           ║ 1 for ▒▓▒ | 2 for ▒▒▓▒ | 3 for ▓▓█ | 4 for ???  |     ");
            System.out.println("           ╚══════════════════════════════════════█-══--══ʘ══╝     ");
            System.out.println("                  ^            ^             ^         ▲           ");
            System.out.println("               (L0CK[D)     (L0CK[D)      (L0CK[D)              ");

        } else {
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
        }

        displayChainSpacer();
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
            System.out.println(theBag.getItemView());
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
        System.out.println(" ║ 『" + theHero.getName() + "』");
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

    public void monsterHeal(final int theHealAmt) {
        System.out.println(" -The enemy heals themselves for " + theHealAmt + " Health Points!");
        DelayMachine.delay(2);
    }

    public void characterSelectAbility(final DungeonCharacter theAttacker, final DungeonCharacter theDefender,
                                       final Ability theAbility) {

        if (theAbility == Ability.BASIC) {
            System.out.println(" " + theAttacker.getBasicSelectMsg(theDefender) + "...");

        } else if (theAbility == Ability.SPECIAL){
            System.out.println(" " + theAttacker.getSpecialSelectMsg(theDefender) + "...");
        }
        DelayMachine.delay(3);
    }

    public void monsterAttackResult(final Monster theMonster, final Hero theHero, final int theDamageDealt) {

        System.out.println(" " + theMonster.getAttackResult()); // attackResult contains msg for hits, misses, basics, and specials
        if (theDamageDealt != 0) {
            System.out.println("   -" + theHero.getName() + " took " + theDamageDealt + " damage!");
        }
        DelayMachine.delay(2);
    }

    public void heroAttackResult(final Hero theHero, final int theDamageDealt) {

        System.out.println(" " + theHero.getAttackResult());
        if (theDamageDealt != 0) { // if (theMonster.doesDamageOnSpecial() || theAttackHit)
            System.out.println("   -" + "The enemy took " + theDamageDealt + " damage!");
        }
        DelayMachine.delay(2);
    }
    public void playerCritMsg() {
        System.out.println("   -It was a critical hit!");
    }

    public void inflictedDebuffMsg(final Debuff theDebuff) {
        System.out.println("   -Inflicted " + theDebuff);
    }



    public void displayCooldown(final int theCooldown) {
        if (theCooldown == 1) {
            System.out.println(" -You can't use that ability for " + theCooldown + " more turn!");
        } else {
            System.out.println(" -You can't use that ability for " + theCooldown + " more turns!");
        }
    }

    /**
     * The game victory message
     */
    public void displayVictoryMsg(final int theHeroSteps, final int theMonstersDefeated, final int theLevel,
                                  final Difficulty theDifficulty, boolean theFunnyMode, final boolean theDebugMode) {

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

        if (!theDebugMode) {
            DelayMachine.delay(2);
            DelayMachine.printDelayedText(TalkingCharacters.NONE,"  RESULTS:");
            DelayMachine.printDelayedText(TalkingCharacters.NONE,"  You beat the game on " + theDifficulty + " difficulty!");
            DelayMachine.printDelayedText(TalkingCharacters.NONE,"  You took " + theHeroSteps + " steps to escape the dungeon.");
            DelayMachine.printDelayedText(TalkingCharacters.NONE,"  You reached level " + theLevel + ".");
            DelayMachine.printDelayedText(TalkingCharacters.NONE,"  You defeated " + theMonstersDefeated + " monsters.");
        }
    }

    /**
     * The battle win message
     */
    public void displayBattleWinMsg(final Monster theMonster) {
        System.out.println();
        DelayMachine.delay(1);
        System.out.println(" -" + theMonster.getDeathMsg());
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
    public void displayDeathMsg(final boolean theFunnyMode) {
        System.out.println(" -You have been defeated.");
        DelayMachine.delay(4);
        if (theFunnyMode) {
            System.out.println("" +
                    " $$$$$$\\  $$$$$$\\ $$$$$$$$\\        $$$$$$\\  $$\\   $$\\ $$$$$$$\\         $$$$$$\\   $$$$$$\\  $$$$$$$\\  $$\\   $$\\ $$$$$$$\\  \n" +
                    "$$  __$$\\ \\_$$  _|\\__$$  __|      $$  __$$\\ $$ |  $$ |$$  __$$\\       $$  __$$\\ $$  __$$\\ $$  __$$\\ $$ |  $$ |$$  __$$\\ \n" +
                    "$$ /  \\__|  $$ |     $$ |         $$ /  \\__|$$ |  $$ |$$ |  $$ |      $$ /  \\__|$$ /  \\__|$$ |  $$ |$$ |  $$ |$$ |  $$ |\n" +
                    "$$ |$$$$\\   $$ |     $$ |         $$ |$$$$\\ $$ |  $$ |$$ |  $$ |      \\$$$$$$\\  $$ |      $$$$$$$  |$$ |  $$ |$$$$$$$\\ |\n" +
                    "$$ |\\_$$ |  $$ |     $$ |         $$ |\\_$$ |$$ |  $$ |$$ |  $$ |       \\____$$\\ $$ |      $$  __$$< $$ |  $$ |$$  __$$\\ \n" +
                    "$$ |  $$ |  $$ |     $$ |         $$ |  $$ |$$ |  $$ |$$ |  $$ |      $$\\   $$ |$$ |  $$\\ $$ |  $$ |$$ |  $$ |$$ |  $$ |\n" +
                    "\\$$$$$$  |$$$$$$\\    $$ |         \\$$$$$$  |\\$$$$$$  |$$$$$$$  |      \\$$$$$$  |\\$$$$$$  |$$ |  $$ |\\$$$$$$  |$$$$$$$  |\n" +
                    " \\______/ \\______|   \\__|          \\______/  \\______/ \\_______/        \\______/  \\______/ \\__|  \\__| \\______/ \\_______/ !");
        } else {
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
        DelayMachine.printDelayedText(TalkingCharacters.NONE,"Uhhhhhh");
        DelayMachine.printDelayedTextFast(TalkingCharacters.NONE,"that's not a valid code. Please try again.");
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
        DelayMachine.printDelayedText(TalkingCharacters.KEEPER,"Ah, a " + hero.getType() + "!");
        DelayMachine.printDelayedText(TalkingCharacters.KEEPER,"I'm sure that skill will serve you well.");
        DelayMachine.printDelayedText(TalkingCharacters.KEEPER,"Good luck!");
        DelayMachine.printDelayedText(TalkingCharacters.KEEPER,"*cranks gate open*");
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
        System.out.println("Beware! Monsters will spawn in a random location every 4 steps you take.");
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
        System.out.println(" On your turn, you have 3 main choices:");
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
        System.out.println("  ║ Poisoned: Lose 5 health every turn");
        System.out.println("  ║ Blinded: Miss your next attack");
        System.out.println("  ║ Stuck: Skip your next turn");
        System.out.println("  ║ Weakened: Deal less damage");
        System.out.println("  ║ Vulnerable: Next hit on you will deal 3x the damage");
        System.out.println("  ║ Silenced: Can't use special ability");
        System.out.println(" You will see these under the \"✺ S̲T̲A̲T̲U̲S̲\" section during a battle.");

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
        System.out.println();
    }

    public void monsterFasterThanHeroMsg(final Monster theMonster) {
        System.out.println(" -The " + theMonster.getName() + " has a higher speed than you, so it attacks first!");
        System.out.println();
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

    public void displayUnlockedMedium() {
        System.out.println();
        System.out.println("╔════════════════════ ◈ INFO ◈ ════════════════════╗");
        System.out.println("║        You've unlocked MEDIUM difficulty!         ║");
        System.out.println("║   This mode contains Pits and tougher Monsters    ║");
        System.out.println("╚═══════════════════════════════════════════════════╝");
        System.out.println();
    }

    public void displayUnlockedHard() {
        System.out.println();
        System.out.println("╔════════════════════ ◈ INFO ◈ ════════════════════╗");
        System.out.println("║         You've unlocked HARD difficulty!          ║");
        System.out.println("║   This mode has something waiting at the end...   ║");
        System.out.println("╚═══════════════════════════════════════════════════╝");
        System.out.println();
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
        System.out.println();
        System.out.println("╔════════════════════ ◈ INFO ◈ ════════════════════╗");
        System.out.println("║        You've unlocked the BEASTMASTER hero       ║");
        System.out.println("║  He will now appear in the hero selection menu.   ║");
        System.out.println("╚═══════════════════════════════════════════════════╝");
        System.out.println();
    }

    public void displayUnlockedAll() {
        System.out.println();
        System.out.println("╔════════════════════ ◈ INFO ◈ ════════════════════╗");
        System.out.println("║           You've unlocked ALL the heroes          ║");
        System.out.println("║                 Congratulations!!!                ║");
        System.out.println("╚═══════════════════════════════════════════════════╝");
        System.out.println();
    }

    public void displayHintStillMoreHeroes() {
        System.out.println();
        System.out.println("╔════════════════════ ? HINT ? ═════════════════════╗");
        System.out.println("║       There are still more heroes to find.        ║");
        System.out.println("║    Keep exploring the dungeon to unlock them...   ║");
        System.out.println("╚═══════════════════════════════════════════════════╝");
        System.out.println();
    }

    public void displayHintUnlocking() {
        System.out.println();
        System.out.println("╔════════════════════ ? HINT ? ═════════════════════╗"); // either the message for the dragon after beating medium
        System.out.println("║    You hear a bone-chilling roar. Something is    ║"); // or a funny message for the super gremlin after beating hard
        System.out.println("║    still lurking in the depths of the dungeon...  ║");
        System.out.println("╚═══════════════════════════════════════════════════╝");
        System.out.println();
    }

    public void displayGlitchHintParchment() {
        System.out.println();
        System.out.println("   ╔═--══ʘ═══---═▒═══ʘ══ ? H!NT ? ═════ʘ════----═══▒═══╗");
        System.out.println("   ║ {}    USE PAR<HMENT INSCR!PTI0N IN GAM3 C0DE []   |");
        System.out.println(" ║   () ..UN..L0CK..... .....M0DE....()......[]....  ║");
        System.out.println(" ╚═ʘ════--═▒═══ʘ════-----════▒══════ʘ═══--═══ʘ═════--╝");
        System.out.println();
    }

    public void beatEasyDungeonKeeperMsg() {
        // make dungeon keeper congratulate player
        System.out.println("Dungeon Keeper: Congratulations! You've beaten the easy dungeon!");
    }

    public void findParchmentDungeonKeeperMsg(final List<Parchment> theParchments) { // only 1 parchment scrap per dungeon
        if (theParchments.size() == 1) {
            DelayMachine.printDelayedTextFast(TalkingCharacters.KEEPER,"What's that? You found a scrap of parchment?! Let me see it...");
            DelayMachine.printDelayedText(TalkingCharacters.KEEPER,"Ah... it's nothing but a bunch of scribbles. Pay it no mind.");
            DelayMachine.printDelayedText(TalkingCharacters.KEEPER,"It's probably dangerous if anything.");

        } else if (theParchments.size() == 2) {
            DelayMachine.printDelayedText(TalkingCharacters.KEEPER,"Another parchment scrap?");
            DelayMachine.printDelayedText(TalkingCharacters.KEEPER,"I've already told you, it's dangerous...");
            DelayMachine.printDelayedTextSlow(TalkingCharacters.KEEPER,"Leave it alone and stop collecting these!");
        }
    }

    public void beatMediumDungeonKeeperMsg() {
        // make dungeon keeper congratulate player
        System.out.println("");
    }

    public void beatHardDungeonKeeperMsg() {
        // make dungeon keeper congratulate player
        System.out.println("");
    }

    public void displayScrapOfParchment(final Parchment theParchment) {
        System.out.println("You found a scrap of parchment!");
        System.out.println("You see something scribbled on it from a previous adventurer: ");
        System.out.println(theParchment.toString());
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

    public void displayCyaNerd(final boolean theFunnyMode) {
        System.out.println();
        if (theFunnyMode) {
            DelayMachine.printDelayedText(TalkingCharacters.NATHAN,"Nah, you're trapped here forever now.");
            DelayMachine.delay(8);
            System.out.println();
            DelayMachine.printDelayedText(TalkingCharacters.NATHAN,"Don't you dare hit that X button.");
            System.out.println();
            DelayMachine.delay(2);
            System.out.print(". ");
            DelayMachine.delay(2);
            System.out.print(". ");
            DelayMachine.delay(2);
            System.out.print(". ");
            DelayMachine.delay(8);
            System.out.println();
            System.out.println();
            DelayMachine.printDelayedText(TalkingCharacters.NATHAN,"FINE!");
            DelayMachine.delay(2);
            DelayMachine.printDelayedText(TalkingCharacters.NATHAN,"I'll let you leave.");
            DelayMachine.delay(4);
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
        } else {
            System.out.println("" +
                    " ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄   ▄▄▄▄▄▄▄▄▄▄   ▄         ▄  ▄▄▄▄▄▄▄▄▄▄▄   ▄ \n" +
                    "▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░▌ ▐░░░░░░░░░░▌ ▐░▌       ▐░▌▐░░░░░░░░░░░▌ ▐░▌\n" +
                    "▐░█▀▀▀▀▀▀▀▀▀ ▐░█▀▀▀▀▀▀▀█░▌▐░█▀▀▀▀▀▀▀█░▌▐░█▀▀▀▀▀▀▀█░▌▐░█▀▀▀▀▀▀▀█░▌▐░▌       ▐░▌▐░█▀▀▀▀▀▀▀▀▀  ▐░▌\n" +
                    "▐░▌          ▐░▌       ▐░▌▐░▌       ▐░▌▐░▌       ▐░▌▐░▌       ▐░▌▐░▌       ▐░▌▐░▌           ▐░▌\n" +
                    "▐░▌ ▄▄▄▄▄▄▄▄ ▐░▌       ▐░▌▐░▌       ▐░▌▐░▌       ▐░▌▐░█▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄▄▄  ▐░▌\n" +
                    "▐░▌▐░░░░░░░░▌▐░▌       ▐░▌▐░▌       ▐░▌▐░▌       ▐░▌▐░░░░░░░░░░▌ ▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌ ▐░▌\n" +
                    "▐░▌ ▀▀▀▀▀▀█░▌▐░▌       ▐░▌▐░▌       ▐░▌▐░▌       ▐░▌▐░█▀▀▀▀▀▀▀█░▌ ▀▀▀▀█░█▀▀▀▀ ▐░█▀▀▀▀▀▀▀▀▀  ▐░▌\n" +
                    "▐░▌       ▐░▌▐░▌       ▐░▌▐░▌       ▐░▌▐░▌       ▐░▌▐░▌       ▐░▌     ▐░▌     ▐░▌            ▀ \n" +
                    "▐░█▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄█░▌     ▐░▌     ▐░█▄▄▄▄▄▄▄▄▄   ▄ \n" +
                    "▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░▌ ▐░░░░░░░░░░▌      ▐░▌     ▐░░░░░░░░░░░▌ ▐░▌\n" +
                    " ▀▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀▀   ▀▀▀▀▀▀▀▀▀▀        ▀       ▀▀▀▀▀▀▀▀▀▀▀   ▀ ");
        }
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


    public void mazeAbiltyText(final Hero theHero) {
        System.out.println(theHero.getPassiveMsgs().remove(0));
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
//        System.out.println("╔═════════════════════╗");
//        System.out.println("║ 1. Continue         ║    ╔═════════════════════╗");
//        System.out.println("║ 2. Change Hero      ║    ║ 6. Save             ║");
//        System.out.println("║ 3. Change Name      ║    ║ 7. Quit             ║");
//        System.out.println("║ 4. Cheat Code Menu  ║    ║                     ║");
//        System.out.println("║                     ║    ╚═════════════════════╝");
//        System.out.println("╚═════════════════════╝");
        System.out.println("╔═════════════════════╗                           ");
        System.out.println("║ 1. Continue         ║    ╔═════════════════════╗");
        System.out.println("║ 2. Change Hero      ║    ║ 6. Save             ║");
        System.out.println("║ 3. Change Name      ║    ║ 7. Quit             ║");
        System.out.println("╚═════════════════════╝    ╚═════════════════════╝");

        System.out.print("Make your selection: ");
        return myScanner.next().charAt(0);
    }

    public void codeSuccessMsg() {
        System.out.println();
        DelayMachine.printDelayedText(TalkingCharacters.NONE," -CODE ACCEPTED");
        System.out.println();
    }


    public void codeFailMsg() {
        System.out.println();
        DelayMachine.printDelayedText(TalkingCharacters.NONE," -CODE DENIED");
        System.out.println();
    }

    public void displayHeroSelected(final Hero theHero) {
        System.out.println();
        System.out.println(" -You are now the " + theHero.getType() + "!");
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

    public void displayDifficultyLocked() {
        System.out.println();
        System.out.println(" -This difficulty is locked!");
        System.out.println();
    }

    public void displayPlayerTurn() {
        System.out.println("--------------------------- PLAYER'S TURN ---------------------------");
    }

    public void displayEnemyTurn() {
        System.out.println("--------------------------- ENEMY'S TURN ----------------------------");
    }

    public void bossAttack1() {

        // need to control missile process somehow
        // should it be controlled via a method in a boss class?

    }
    private void drawGround() {
        System.out.println("    ┕━━━━━ 1 ━━━━━ 2 ━━━━━ 3 ━━━━━ 4 ━━━━━ 5 ━━━━━ 6 ━━━━━ 7 ━━━━━ 8 ━━━━━ 9 ━━━━━┙");
    }
    private void missileProcess(final Missile theMissile) {
        System.out.println(theMissile);
        drawGround();
        theMissile.closerToGround(); // this line is logical, should it be in the view?
//        DelayMachine.delay(0.01);

        System.out.println(theMissile);
        drawGround();
        theMissile.closerToGround();
//        DelayMachine.delay(0.01);
    }

    public void bossAttack2() {
        // giant laser that fires down from the sky
        System.out.println("   *   ");
        //        DelayMachine.delay(0.01);
        System.out.println("  ***  ");
        //        DelayMachine.delay(0.01);
        System.out.println(" ***** ");
        //        DelayMachine.delay(0.01);
        System.out.println("*******");
        //        DelayMachine.delay(0.01);
        System.out.println("*******");

        // like above, but the logic is complex for winning or losing, so it should be contained as a class in the model
    }

    public void bossAttack3() { // for this attack, like the missile, print the screen every time to appear as if it's moving
        // horizontal flappy bird where you need to press the right key to dodge
        System.out.println("    ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓       ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓");
        System.out.println("    ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓       ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓");
        System.out.println("    ━━━━━━ 1 ━━━━━ 2 ━━━━━ 3 ━━━━━ 4 ━━━━━ 5 ━━━━━ 6 ━━━━━ 7 ━━━━━ 8 ━━━━━ 9 ━━━━━━ ");

        System.out.println("    ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓       ▓▓▓▓▓▓▓▓▓▓▓▓▓");
        System.out.println("    ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓       ▓▓▓▓▓▓▓▓▓▓▓▓▓");
        System.out.println("    ━━━━━━ 1 ━━━━━ 2 ━━━━━ 3 ━━━━━ 4 ━━━━━ 5 ━━━━━ 6 ━━━━━ 7 ━━━━━ 8 ━━━━━ 9 ━━━━━━ ");
    }

    public void bossAttack4() {
        // need to defuse bomb
        // ??? does fit with a boss fight? need to decide

        // step 1: a bunch of random text is printed out, within that text, a random line is generated saying "CODE: 123" or something
        // step 2: a bomb is printed out
        // step 3: the user is prompted to enter the code
        // step 4: if wrong code is entered, or time is up, the bomb explodes

        /*
             . . .
              \|/
            `--+--'
              /|\
             ' | '
               |
               |
           ,--'#`--.
           |#######|
        _.-'#######`-._
     ,-'###############`-.
   ,'#####################`,
  /#########################\
 |###########################|
|#############################|
|#############################|
|#############################|
|#############################|
 |###########################|
  \#########################/
   `.#####################,'
     `._###############_,'
        `--..#####..--'
         */

        /*
                                   ________________
                              ____/ (  (    )   )  \___
                             /( (  (  )   _    ))  )   )\
                           ((     (   )(    )  )   (   )  )
                         ((/  ( _(   )   (   _) ) (  () )  )
                        ( (  ( (_)   ((    (   )  .((_ ) .  )_
                       ( (  )    (      (  )    )   ) . ) (   )
                      (  (   (  (   ) (  _  ( _) ).  ) . ) ) ( )
                      ( (  (   ) (  )   (  ))     ) _)(   )  )  )
                     ( (  ( \ ) (    (_  ( ) ( )  )   ) )  )) ( )
                      (  (   (  (   (_ ( ) ( _    )  ) (  )  )   )
                     ( (  ( (  (  )     (_  )  ) )  _)   ) _( ( )
                      ((  (   )(    (     _    )   _) _(_ (  (_ )
                       (_((__(_(__(( ( ( |  ) ) ) )_))__))_)___)
                       ((__)        \\||lll|l||///          \_))
                                (   /(/ (  )  ) )\   )
                              (    ( ( ( | | ) ) )\   )
                               (   /(| / ( )) ) ) )) )
                             (     ( ((((_(|)_)))))     )
                              (      ||\(|(|)|/||     )
                            (        |(||(||)||||        )
                              (     //|/l|||)|\\ \     )
                            (/ / //  /|//||||\\  \ \  \ _)
    -------------------------------------------------------------------------------

         */
    }

    public void displayPassiveStatus(final Queue<String> theMsgs) {
        while (!theMsgs.isEmpty()) {
            System.out.println(theMsgs.remove());
        }
    }

    public void displayStuckifyMsg(final DungeonCharacter theCharacter) {
        System.out.println(" " + theCharacter.getName() + " is stuck and cannot move this turn!");
    }

    public void displayBlindedMsg(final DungeonCharacter theCharacter) {
        System.out.println(" " + theCharacter.getName() + " is blinded and has a high chance of missing this turn!");
    }

    public void displayPoisonedMsg(final DungeonCharacter theCharacter) {
        System.out.println(" " + theCharacter.getName() + " is poisoned and will take 10 damage at the end of its turn!");
    }

    public void displaySilencedMsg(final DungeonCharacter theCharacter) {
        System.out.println(" " + theCharacter.getName() + " is silenced and cannot use their special this turn!");
    }

    public void displayVulnerableMsg(final DungeonCharacter theCharacter) {
        System.out.println(" " + theCharacter.getName() + " is vulnerable and will take 2x the damage this turn!");
    }

    public void displayWeakenedMsg(final DungeonCharacter theCharacter) {
        System.out.println(" " + theCharacter.getName() + " is weakened and will deal half the damage this turn!");
    }

    public void displaySilenced() {
        System.out.println(" You are silenced and cannot use your special this turn!");
    }
}
