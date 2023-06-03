package view;
import controller.*;
import model.*;

import java.util.Scanner;

public class TextModeInterface {

    public int menu() {
        System.out.println("Welcome to Fallen Champions, TUI edition");
        System.out.println("1 to start game, 2 to exit game");
        System.out.println("Now make your selection: ");
        return THE_SCANNER.nextInt();
    }

    public int chooseHero() {
        System.out.println("Choose your hero!");
        Scanner sc = new Scanner(System.in);
        int hero_choice = sc.nextInt();
        switch(hero_choice) {
            case 1:
                HeroFactory.buildHero(HeroTypes.ENFORCER);
            case 2:
                HeroFactory.buildHero(HeroTypes.ROBOT);
            case 3:
                HeroFactory.buildHero(HeroTypes.SUPPORT);
            case 4:
                HeroFactory.buildHero(HeroTypes.SCIENTIST);
            case 5:
                HeroFactory.buildHero(HeroTypes.WARRIOR);
            default:
                System.out.println("Please choose a hero!");
        }
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




}
