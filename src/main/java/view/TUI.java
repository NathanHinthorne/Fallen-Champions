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
        System.out.println("5 for Scientist, 6 for Doctor, 7 for Mage.");
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
        DelayMachine.printDelayedText("Find the 4 pillars of OO, and you will be granted access to the final chamber.");
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
            DelayMachine.printDelayedText("hehehe");
            DelayMachine.printDelayedText("Bet you didn't expect that one.");
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

        if (theFunnyDialogue) {

            theAudio.playSFX(theAudio.rimshot);
            DelayMachine.delay(2);
        }

        DelayMachine.delay(2);
        System.out.print(". ");
        DelayMachine.delay(2);
        System.out.print(". ");
        DelayMachine.delay(2);
        System.out.print(". ");
        DelayMachine.delay(4);
        System.out.println();

        System.out.println("Well, do you have what it takes??");
//        if (theFunnyDialogue) {
//        } else {
//            DelayMachine.printDelayedText("A quiet one ay?");
//        }

        DelayMachine.delay(2);
        System.out.print(". ");
        DelayMachine.delay(2);
        System.out.print(". ");
        DelayMachine.delay(2);
        System.out.print(". ");
        DelayMachine.delay(4);
        System.out.println();

        if (theFunnyDialogue) {
            System.out.println("Bruh");
            DelayMachine.delay(2);
            System.out.println("*boots you into the dungeon without a second thought*");
        } else {
            DelayMachine.printDelayedText("A quiet one ay?");
            DelayMachine.printDelayedText("I guess we'll find out soon enough.");
        }
    }

    /**
     * Gameplay menu
     * @return gameplay menu choice
     */
    public int gameplayMenu() {
        System.out.println();
        System.out.println("-----------------------------------------------------");
        System.out.println("You are currently in the dungeon");
        System.out.println("Please make a selection");
        System.out.println("'w' to move up, 'a' to move left, 's' to move down, 'd' to move right\n" +
                            "'1' to display hero info, '2' to display map, 'e' open bag, '4' to quit, '5' to save game");
        System.out.println("-----------------------------------------------------");
        return SCANNER.next().charAt(0);
    }

    /**
     * Battle menu for battling monsters
     * @param theHero you
     * @param theMonster the monster you're battling
     * @return battle menu input
     */
    public int battleMenu(Hero theHero, Monster theMonster) { // is this parameter needed?
        double playerPerc = (theHero.getHealth() / theHero.getMaxHealth()) * 20.0;
        System.out.println("-------------------");
        System.out.println("\nPlayer HP:  " + theHero.getHealth());

        double monsterPerc = (theMonster.getHealth() / theMonster.getMaxHealth()) * 20.0;
        System.out.println("Monster HP: " + theMonster.getHealth());

        System.out.println("\nMake your move!");
        System.out.print("[ 1 - Attack ] [ 2 - Special ] [ 3 - Bag ] --> ");
        String ret = SCANNER.next();
        int intret = 0;
        try {
            intret = Integer.parseInt(ret);
        } catch(NumberFormatException e) {
            System.out.println("Invalid input! Please try again.");
            DelayMachine.delay(2);
            intret = battleMenu(theHero, theMonster);
        }
        return intret;
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
     * @param myBag your inventory bag
     * @return the inventory choice
     */
    public int openBag(final Inventory myBag) {
        System.out.println("Opening bag...");
        DelayMachine.delay(3);
        System.out.println(myBag.toString());
        System.out.println("Items:   " + myBag.getSize() + "/" + myBag.getMaxSize());
        System.out.println("Pillars: " + myBag.getMyPillarCount() + "/4");
        System.out.print("Choose an item (1-4) (5 - Back) --> ");
        int index = SCANNER.nextInt();
        System.out.println("-------------------");
        if (index < 1 && index > 5) {
            index = openBag(myBag);
        }
        else if (index == 5) {
            return index;
        } else if (index > myBag.getSize() && index < 5) {
            System.out.println("That Slot is empty!");
            index = openBag(myBag);
        } else {
            System.out.println("Invalid input! please try again.");
            index = openBag(myBag);
        }
        return index;
    }

    /**
     * Potion selection
     * @param theVal the potion you selected
     * @param thePlayer you
     */
    public void usePotion(int theVal, Hero thePlayer) {
        Potion potion = thePlayer.getMyInventory().getPotionInventory().get(theVal);

        if (potion.type().equals("Health Potion")) {
            System.out.println("Used a Health Potion and restored " + potion.getDetail(thePlayer) + " HP!");

        } else if (potion.type().equals("Vision Potion")) {
            System.out.println("Used a vision potion!");
        }
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

        System.out.println("Stats");
        System.out.println("-----------------------------------------------------");
        System.out.println(theHero.getType());
        System.out.println("Health: " + theHero.getHealth() + "/" + theHero.getMaxHealth());
//        System.out.println("Attack: " + theHero.);  //TODO what get method to call for attack power?
        System.out.println("Speed: " + theHero.getSpeed());
//        System.out.println("Level: " + theHero.getLevel());  //TODO add level-up functionality to hero (maybe)
        System.out.println("-----------------------------------------------------");

    }

    /**
     * Displays the potion info
     * @param thePotion the potion info
     */
    public void displayPotionInfo(final Potion thePotion) {
        System.out.println("You collected a " + thePotion.type() + "!");
    }

    /**
     * Displays Abstraction Pillar Message
     */
    public void displayAbstractionPillarMsg() {
        System.out.println("You collected an Abstraction Pillar!");
    }
    /**
     * Displays Encapsulation Pillar Message
     */
    public void displayEncapsulationPillarMsg() {
        System.out.println("You collected an Encapsulation Pillar!");
    }
    /**
     * Displays Inheritance Pillar Message
     */
    public void displayInheritancePillarMsg() {
        System.out.println("You collected an Inheritance Pillar!");
    }
    /**
     * Displays Polymorphism Pillar Message
     */
    public void displayPolymorphismPillarMsg() {
        System.out.println("You collected a Polymorphism Pillar!");
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
        System.out.println("-------------------");
        System.out.println("The Enemy is making their move...");
    }

    /**
     * The monster move text
     * @param theVal the value
     * @param theHpAmt the monster hp amt
     */
    public void monsterMoves(int theVal, int theHpAmt) {
        if (theVal == 0) {
            if (theHpAmt == 0) {
                System.out.println("Their attack failed!");
            } else {
                System.out.println("The enemy performs an attack for " + theHpAmt + " damage!");
            }
        } else if (theVal == 1) {
            if (theHpAmt == 0) {
                System.out.println("Their special attack failed!");
            } else {
                System.out.println("The enemy performs a special attack for " + theHpAmt + " damage!");
            }
        } else if (theVal == 2) {

            System.out.println("The enemy heals themselves for " + theHpAmt + " Health Points!");
        }
    }

    /**
     * The player move
     * @param theVal the player choice value
     * @param theHpAmt the hp amt
     * @param thePlayer you
     */
    public void playerMoves(int theVal, int theHpAmt, Hero thePlayer) {
        System.out.println("-------------------");
        if (theVal == 1) {
            if (theHpAmt == 0) {
                System.out.println("Your attack failed!");
            } else {
                System.out.println("You perform an attack for " + theHpAmt + " damage!");
            }
        } else if (theVal == 2) {
            if (theHpAmt == -1) {
                System.out.println("You cannot use your special for another "
                        + thePlayer.getCooldown() + " moves!");
            } else if (theHpAmt == 0) {
                System.out.println("Your special attack failed!");
            } else {
                System.out.println("You perform a special attack for " + theHpAmt + " damage!");
            }
        } else if (theVal == 3) {
            System.out.println("Opening bag...");
        } else {
            playerMoves(theVal, theHpAmt, thePlayer);
        }
    }

    /**
     * The game victory message
     */
    public void displayVictoryMsg(final int theHeroSteps) {
        System.out.println("You have escaped the dungeon!. Congratulations!!");
        System.out.println("You took " + theHeroSteps + " steps to escape the dungeon.");
    }

    /**
     * The battle win message
     */
    public void displayBattleWinMsg() {
        System.out.print("ENEMY DEFEATED! GET REKT!!");
        System.out.println();
        System.out.println("-----------------------------------------------------");
    }

    /**
     * The death message
     */
    public void displayDeathMsg(final boolean theFunnyDialogue) {
        System.out.println("You have been defeated.");
        if (theFunnyDialogue) {
            System.out.println("GIT GUD SCRUB!");
        }
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
        System.out.println("YOU CHEATER! I SEE WHAT YOU'RE DOING THERE! ಠ_ಠ");
        System.out.println("*****************************************************");
    }

    /**
     * The hero health
     * @param theHero you
     */
    public void displayHeroHealth(final Hero theHero) {
        System.out.println("Health: " + theHero.getHealth() + "/" + theHero.getMaxHealth());
    }

    /**
     * The game start message
     */
    public void displayStartMsg() {
        System.out.println("Welcome to the Dungeon!");
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
        System.out.println("You have encountered a " + theMonster.getType().toString());
    }

    /**
     * Locked Exit message
     */
    public void exitLocked() {
        System.out.println("The exit is locked! You need to collect all 4 pillars to open it!");
    }

    /**
     * Fell into pit message
     * @param theFallDamage the pit you fell in
     */
    public void displayPitMsg(final int theFallDamage) {
        System.out.println("You fell into a pit! You took " + theFallDamage + " damage!");
    }

    /**
     * The use potion message
     * @param thePotion the potion you used
     * @param theIndex the slot of the potion
     */
    public void displayUsePotionMsg(final Potion thePotion, int theIndex) {
//        System.out.println("You used a " + thePotion.type() + "!");
        System.out.println("You used a " + thePotion.type() + " from the slot #" + theIndex+1);

        if (thePotion.type().equals("Health Potion")) {
            System.out.println("You gained some health points!");
        } else if (thePotion.type().equals("Vision Potion")) {
            System.out.println("You can now see the entire dungeon for 3 turns! I hope you have a good memory ;)");
        }
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

    public void displayDebugModeMsg() {
        System.out.println("*****************************************************");
        System.out.println("You have entered debug mode");
        System.out.println("*****************************************************");
    }

    public void displayWallMsg() {
        System.out.println("WHAT ARE YOU DOING IN A WALL?! GET OUT OF THERE YOU FOOL");
    }
}
