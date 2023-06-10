package view;
import controller.DelayMachine;
import model.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TUI {

    private static final Scanner THE_SCANNER = new Scanner(System.in);

    public int menu() {
        System.out.println("Welcome to Fallen Champions, TUI edition");
        System.out.println("1 to start game, 2 to exit game");
        System.out.println("Now make your selection: ");
        return THE_SCANNER.nextInt();
    }

    public int chooseHero() {
        System.out.println("Choose your hero!");
        System.out.println("1 for Enforcer, 2 for Robot, 3 for Support, 4 for Scientist, 5 for Warrior");
        return THE_SCANNER.nextInt();
    }

    public void Introduction() {
        System.out.println("It's a bright, sunny day out. You enjoy the weather, have fun, do whatever, until one day...");
        System.out.println("You suddenly wake up in a deep dark dungeon with no memory, stuck in an ancient dungeon");
        System.out.println("You then realize that you are yourself a Fallen Champion! Can you escape the dungeon?");
    }

    public int gameplayMenu() {
        System.out.println();
        System.out.println("-----------------------------------------------------");
        System.out.println("You are currently in the dungeon");
        System.out.println("Please make a selection");
        System.out.println("'w' to move up, 'a' to move left, 's' to move down, 'd' to move right\n" +
                            "'1' to display hero info, '2' to display map, 'e' open bag, '4' to quit, '5' to save game");
        System.out.println("-----------------------------------------------------");
        return THE_SCANNER.next().charAt(0);
    }

    public int battleMenu(Hero theHero, Monster theMonster) { // is this parameter needed?
        double playerPerc = (theHero.getHitPoints() / theHero.getMaxHitPoints()) * 20.0;
        System.out.println("-------------------");
        System.out.println("\nPlayer HP:  " + theHero.getHitPoints());

        double monsterPerc = (theMonster.getHitPoints() / theMonster.getMaxHitPoints()) * 20.0;
        System.out.println("Monster HP: " + theMonster.getHitPoints());

        System.out.println("\nMake your move!");
        System.out.print("[ 1 - Attack ] [ 2 - Special ] [ 3 - Bag ] --> ");
        String ret = THE_SCANNER.next();
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

    public int chooseDifficulty() {
        System.out.println("Choose your difficulty!");
        System.out.println("1 for easy, 2 for medium, 3 for hard");
        return THE_SCANNER.nextInt();
    }
    public int openBag(final Inventory myBag) {
        System.out.println("Opening bag...");
        DelayMachine.delay(3);
        System.out.println(myBag.toString());
        System.out.println("Items:   " + myBag.getSize() + "/" + myBag.getMaxSize());
        System.out.println("Pillars: " + myBag.getMyPillarCount() + "/4");
        System.out.print("Choose an item (1-4) (5 - Back) --> ");
        int index = THE_SCANNER.nextInt();
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

    public void usePotion(int theVal, Hero thePlayer) {
        Potion potion = thePlayer.getMyInventory().getPotionInventory().get(theVal);

        if (potion.type().equals("Health Potion")) {
            System.out.println("Used a Health Potion and restored " + potion.getDetail(thePlayer) + " HP!");

        } else if (potion.type().equals("Vision Potion")) {
            System.out.println("Used a vision potion!");
        }
    }

    public int quitProcess() {
        System.out.println("Are you sure you want to quit?");
        System.out.println("1 for yes, 2 for no");
        return THE_SCANNER.nextInt();

    }

    public void displayDungeonMap(Dungeon theDungeon) {
        System.out.println(theDungeon.toString());
        System.out.println();
    }

    public void displayHeroInfo(final Hero theHero) {

        System.out.println("Stats");
        System.out.println("-----------------------------------------------------");
        System.out.println(theHero.getType());
        System.out.println("Health: " + theHero.getHitPoints() + "/" + theHero.getMaxHitPoints());
//        System.out.println("Attack: " + theHero.);  //TODO what get method to call for attack power?
        System.out.println("Speed: " + theHero.getSpd());
//        System.out.println("Level: " + theHero.getLevel());  //TODO add level-up functionality to hero (maybe)
        System.out.println("-----------------------------------------------------");

    }

    public void displayPotionInfo(final Potion thePotion) {
        System.out.println("You collected a " + thePotion.type() + "!");
    }
    public void displayAbstractionPillarMsg() {
        System.out.println("You collected an Abstraction Pillar!");
    }

    public void displayEncapsulationPillarMsg() {
        System.out.println("You collected an Encapsulation Pillar!");
    }

    public void displayInheritancePillarMsg() {
        System.out.println("You collected an Inheritance Pillar!");
    }

    public void displayPolymorphismPillarMsg() {
        System.out.println("You collected a Polymorphism Pillar!");
    }



    public int continueOrNewGameMenu() {
        System.out.println("Would you like to start a new game or continue a previous game?");
        System.out.println("1 for new game, 2 for continue game");
        return THE_SCANNER.nextInt();
    }

    public void monsterTurnText() {
        System.out.println("-------------------");
        System.out.println("The Enemy is making their move...");
    }

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
                        + thePlayer.getSpecialCooldown() + " moves!");
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

    public void displayVictoryMsg() {
        System.out.println("You have escaped the dungeon!. Congratulations!!");
    }

    public void displayBattleWinMsg() {
        System.out.print("ENEMY DEFEATED! GET REKT!!");
        System.out.println();
        System.out.println("-----------------------------------------------------");
    }

    public void displayDeathMsg() {
        System.out.println("You have been defeated. GIT GUD SCRUB!");
    }

    public void displayCheatModeMsg() {
        System.out.println("*****************************************************");
        System.out.println("YOU CHEATER! I SEE WHAT YOU'RE DOING THERE! ಠ_ಠ");
        System.out.println("*****************************************************");
    }

    public void displayHeroHealth(final Hero theHero) {
        System.out.println("Health: " + theHero.getHitPoints() + "/" + theHero.getMaxHitPoints());
    }

    public void displayStartMsg() {
        System.out.println("Welcome to the Dungeon!");
    }

    public void displayInstaKill() {
        System.out.println("Woooow, you must be so powerful... cheater");
    }

    public void displayMonsterEncounterMsg(final Monster theMonster) {
        System.out.println("You have encountered a " + theMonster.getType().toString());
    }

    public void exitLocked() {
        System.out.println("The exit is locked! You need to collect all 4 pillars to open it!");
    }

    public void displayPitMsg(final int theFallDamage) {
        System.out.println("You fell into a pit! You took " + theFallDamage + " damage!");
    }

    public void displayBattleLoseMsg() {
        System.out.println("You lost the battle! As the monster defeats you, you are trapped in the dungeon");
        System.out.println("FOREVER EVER EVER EVER EVER EVER");
        System.out.println("You have fallen into the abyss of becoming a Fallen Champion, becoming your own worst enemy!");
        System.out.println("GAME OVER!");
    }

    public void displayUsePotionMsg(final Potion thePotion, int theIndex) {
//        System.out.println("You used a " + thePotion.type() + "!");
        System.out.println("You used a " + thePotion.type() + " from the slot #" + theIndex+1);

        if (thePotion.type().equals("Health Potion")) {
            System.out.println("You gained some health points!");
        } else if (thePotion.type().equals("Vision Potion")) {
            System.out.println("You can now see the entire dungeon for 3 turns! I hope you have a good memory ;)");
        }
    }

    public void displayWrongInput() {
        System.out.println("Invalid input. Please try again.");
    }
}