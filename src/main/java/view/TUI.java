package view;
import controller.DelayMachine;
import model.*;

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

    private static final Scanner SCANNER = new Scanner(System.in);

    /**
     * Main menu of the game
     * @return main menu input
     */
    public int menu() {
        System.out.println("Welcome to Fallen Champions, TUI edition");
        System.out.println("1 to start game, 2 to exit game");
        System.out.print("Make your selection: ");
        return SCANNER.nextInt();
    }

    /**
     * Chooses the hero
     * @return the hero choice
     */
    public int chooseHero() {
        System.out.println("Choose your hero!");
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("1 for Swordsman, 2 for Thief, 3 for Juggernaut, 4 for Archer,");
        System.out.println("5 for Scientist, 6 Ninja, 7 for Doctor, 8 for Mage.");
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        System.out.print("Make your selection: ");
        return SCANNER.nextInt();
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
//        DelayMachine.printDelayedText("To succeed, you must use your wits and cunning to overcome the challenges that await you.");
        DelayMachine.printDelayedText("Find the 4 pillars of OO, and you shall be granted access to the final chamber.");
        DelayMachine.printDelayedText("Not even I, the keeper of the dungeon, know what awaits you there.");
        System.out.println();
        DelayMachine.printDelayedText("Now then, what is your name?");
        System.out.println();
        System.out.print("Name: ");
        String fakeName = SCANNER.next();
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
        System.out.println("╔═══════════════════════════════════════════════════════════════════════╗");
        System.out.println("╠ 'w' to move up, 'a' to move left, 's' to move down, 'd' to move right ╣");
        System.out.println("╠ '1' to display hero info, 'e' open bag, '4' to quit, '5' to save game ╣");
        System.out.println("╚═══════════════════════════════════════════════════════════════════════╝");
        System.out.println("-----------------------------------------------------");
        return SCANNER.next().charAt(0);
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
        if (theHero.onCooldown()) {
            System.out.print("[1 - Attack] ║2 - Special║ [e - Bag] --> ");
        } else {
            System.out.print("[1 - Attack] [2 - Special] [e - Bag] --> ");
        }
        char choice = SCANNER.next().charAt(0);
//        int choiceInt;
//        try {
//            choiceInt = Integer.parseInt(choice);
//        } catch(NumberFormatException e) {
//            System.out.println("Invalid input! Please try again.");
//            DelayMachine.delay(2);
//            choiceInt = battleMenu(theHero, theMonster);
//        }
        System.out.println("-----------------------------------------------------");
        return choice;
    }

    /**
     * Menu for choosing the difficulty
     * @return the difficulty choice
     */
    public int chooseDifficulty() {
        System.out.println("Choose your difficulty!");
        System.out.println("1 for easy, 2 for medium, 3 for hard");
        System.out.print("Make your selection: ");
        return SCANNER.nextInt();
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

        DelayMachine.delay(3);
        if (inBattle) {
            System.out.println(theBag.getItemInventory());
        } else {
            System.out.println(theBag.toString());
        }

        System.out.print("Choose an item (1-4) (e - Back) --> ");
        char input = SCANNER.next().charAt(0);
        System.out.println();

        if (input == 'e') { // back
            return 'e';
        }

        int slotIndex = Character.getNumericValue(input); // convert input to int

        if (slotIndex <= 0 || slotIndex >= 5) { // out of bounds
            theAudio.playSFX(theAudio.error, -10);
            System.out.println("Invalid input! please try again.");
            return openBag(theBag, inBattle, theAudio);

        } else if (slotIndex > theBag.getSize()-1 && slotIndex <= theBag.getMaxSize()) { // empty slot
            theAudio.playSFX(theAudio.error, -10);
            System.out.println("That slot is empty!");
            return openBag(theBag, inBattle, theAudio);
        }

        return input;
    }

    public void closeBag() {
        System.out.println("Closing bag...");
        DelayMachine.delay(2);
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
        return SCANNER.nextInt();
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
    public void displayHeroInfo(final Hero theHero) {
        System.out.println(" ╔═══════════════════════");
        System.out.println(" ║ " + theHero.getName());
        System.out.println(" ║ <" + theHero.getType() + ">");
        System.out.println(" ║ ");
        System.out.println(" ╠ Health: " + theHero.getHealth() + "/" + theHero.getMaxHealth());
        System.out.println(" ╠ Attack: " + (theHero.getMinDmg() + theHero.getMaxDmg())/2);
        System.out.println(" ╠ Speed: " + theHero.getSpeed());
        System.out.println(" ╠ Level: " + theHero.getLevel());
        System.out.println(" ╠ Experience: " + theHero.getXP() + "/" + theHero.getXPToLevel());
        System.out.println(" ╚═══════════════════════");
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
        return SCANNER.nextInt();
    }

    /**
     * The monster turn text
     */
    public void monsterTurnText() {
        System.out.println("-----------------------------------------------------");
        System.out.println("The Enemy is making their move...");
    }

    public void monsterHealMsg(final int theHealAmt) {
        System.out.println(" -The enemy heals themselves for " + theHealAmt + " Health Points!");
    }

    public void monsterSelectsBasicMsg(final Monster theMonster, final Hero thePlayer) {
        System.out.print(" -" + theMonster.getName() + theMonster.getBasicMsg() + thePlayer.getName());
    }
    public void monsterSelectsSpecialMsg(final Monster theMonster, final Hero thePlayer) {
        System.out.print(" -" + theMonster.getName() + theMonster.getSpecialMsg() + thePlayer.getName());
    }
    public void monsterHitsBasicMsg(final int theDamageDealt) {
        System.out.println(" for " + theDamageDealt + " damage!");

//        System.out.println("  " + thePlayer.getName()  + " took " + theDamageDealt + " damage!");
    }
    public void monsterHitsSpecialMsg(final int theDamageDealt) {
        System.out.println(" for " + theDamageDealt + " damage!");
//        System.out.println("  " + thePlayer.getName()  + " took " + theDamageDealt + " damage!"); // special doesn't always do damage. find way to deal with this
    }
    public void monsterAttackMissMsg() {
        System.out.println(", but fails in its attempt!");
    }



    public void playerSelectsBasicMsg(final Hero thePlayer, final Monster theMonster) {
        System.out.println(" -" + thePlayer.getName() + thePlayer.getBasicMsg() + theMonster.getName() + "...");
    }
    public void playerSelectsSpecialMsg(final Hero thePlayer, final Monster theMonster) {
        System.out.println(" -" + thePlayer.getName() + thePlayer.getSpecialMsg() + theMonster.getName() + "...");
    }
    public void playerHitsBasicMsg(final int theDamageDealt, final boolean funnyMode) {
        DelayMachine.delay(1);
        System.out.println("  Success!");
        if (funnyMode) {
            System.out.println("  It really hurt");
        } else {
            System.out.println("  The enemy took " + theDamageDealt + " damage!");
        }
    }
    public void playerHitsSpecialMsg(final int theDamageDealt) {
        DelayMachine.delay(1);
        System.out.println("  Success!");
        System.out.println("  The enemy took " + theDamageDealt + " damage"); // special doesn't always do damage. find way to deal with this
    }
    public void playerAttackMissesMsg() {
        System.out.println("  The attack failed!");
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
                                  final boolean funnyMode) {

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
        DelayMachine.printDelayedText("RESULTS:");
        DelayMachine.printDelayedText("  You took " + theHeroSteps + " steps to escape the dungeon.");
        DelayMachine.printDelayedText("  You reached level " + theLevel + ".");
        DelayMachine.printDelayedText("  You defeated " + theMonstersDefeated + " monsters.");
//        DelayMachine.printDelayedText("  You beat the game in " + theTimeTaken + " seconds.");
    }

    /**
     * The battle win message
     */
    public void displayBattleWinMsg(final Monster theMonster) {
        System.out.println();
        System.out.println("  [][][][][][][][][][]");
        System.out.println("  [] ENEMY DEFEATED []");
        System.out.println("  [][][][][][][][][][]");
        System.out.println();
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
        System.out.println(" -You used a " + thePotion.type() + " from the slot #" + theSlotIndex);
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
        return SCANNER.nextInt();
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
}
