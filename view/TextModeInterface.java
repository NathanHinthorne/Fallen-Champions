package view;
import model.*;

import java.util.Scanner;

public class TextModeInterface {

    private static final Scanner THE_SCANNER = new Scanner(System.in);

    public int menu() {
        System.out.println("Welcome to Fallen Champions, TUI edition");
        System.out.println("1 to start game, 2 to exit game");
        System.out.println("Now make your selection: ");
        return THE_SCANNER.nextInt();
    }

    public int chooseHero() {
        System.out.println("Choose your hero!");
        return THE_SCANNER.nextInt();
    }

    public void Introduction() {
        System.out.println("It's a bright, sunny day out. You enjoy the weather, have fun, do whatever, until one day...");
        System.out.println("You suddenly wake up in a deep dark dungeon with no memory, stuck in an ancient dungeon");
        System.out.println("You then realize that you are yourself a Fallen Champion! Can you escape the dungeon?");
    }

    public int gameplayMenu() {
        System.out.println("You are currently in the dungeon");
        System.out.println("Now make a selection");
        System.out.println("1 move hero, 2 to display hero info, 3 to locate hero, 4 open bag, 5 to quit, 6 to save game");
        return THE_SCANNER.nextInt();
    }

    public int battleMenu(final int numberOfSpecials) { // is this parameter needed?
        System.out.println("You are currently in battle");
        System.out.println("Now make a selection");
        System.out.println("1 to attack, 2 to use special attack, 3 to use potion");
        return THE_SCANNER.nextInt();
    }

    public int chooseDifficulty() {
        System.out.println("Choose your difficulty!");
        System.out.println("1 for easy, 2 for medium, 3 for hard");
        return THE_SCANNER.nextInt();
    }
    public int openBag(final Inventory myBag) {
        System.out.println("Make your Selection 1-4 (press 5 to exit) :");
        System.out.println(myBag.toString());
        int ret = THE_SCANNER.nextInt();
        if (ret < 1 && ret > 5) {
            ret = openBag(myBag);
        }
        return ret;
    }

    public int quitProcess() {
        System.out.println("Are you sure you want to quit?");
        System.out.println("1 for yes, 2 for no");
        return THE_SCANNER.nextInt();

    }

    public void displayHeroInfo(final Hero theHero) {

        System.out.println("Stats");
        System.out.println("-----------------------------------------------------");
        System.out.println(theHero.getType());
        System.out.println("Health: " + theHero.getHitPoints() + "/" + theHero.getMaxHitPoints());
//        System.out.println("Attack: " + theHero.);  //TODO what get method to call for attack power?
        System.out.println("Speed: " + theHero.getSpd());
//        System.out.println("Level: " + theHero.getLevel());  //TODO add level-up functionality to hero (maybe)

    }

    public int continueOrNewGameMenu() {
        System.out.println("Would you like to start a new game or continue a previous game?");
        System.out.println("1 for new game, 2 for continue game");
        return THE_SCANNER.nextInt();
    }


}
