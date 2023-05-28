package view;
//import model.*;
import controller.*;
import model.HeroFactory;
import model.HeroTypes;

import java.util.Scanner;

public class TextModeInterface {

    public static void menu() {
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.println("Welcome to Fallen Champions, TUI edition");
            System.out.println("Now make a selection!");
            System.out.println("1 to start game, 2 to exit game");
            System.out.println("Now make your selection: ");
            int selection = sc.nextInt();
            switch(selection) {
                case 1:
                    // Start Game
                    MonsterBattle.is_ongoing(2);
                    new DungeonGame();
                    Introduction();
                    choose_hero();
                    gameplay_menu();
                case 2:
                    System.exit(0);
                default:
                    System.out.println("Please make a proper selection:");
            }
        }


    }

    private static void choose_hero() {
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

    private static void Introduction() {
        System.out.println("It's a bright, sunny day out. You enjoy the weather, have fun, do whatever, until one day...");
        System.out.println("You suddenly wake up in a deep dark dungeon with no memory, stuck in an ancient dungeon");
        System.out.println("You then realize that you are yourself a Fallen Champion! Can you escape the dungeon?");
    }

    public static void gameplay_menu() {
        Scanner sc = new Scanner(System.in);
        while(MonsterBattle.is_ongoing(2)) {

        }
    }





}
