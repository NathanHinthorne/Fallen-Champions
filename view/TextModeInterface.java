package view;
//import model.*;
import controller.*;
import model.*;

import java.util.Scanner;

public class TextModeInterface {

    public void menu() {
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

    private void choose_hero() {
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

    private void Introduction() {
        System.out.println("It's a bright, sunny day out. You enjoy the weather, have fun, do whatever, until one day...");
        System.out.println("You suddenly wake up in a deep dark dungeon with no memory, stuck in an ancient dungeon");
        System.out.println("You then realize that you are yourself a Fallen Champion! Can you escape the dungeon?");
    }

    public void gameplay_menu() {
        Scanner sc = new Scanner(System.in);

        while(MonsterBattle.is_ongoing(2)) {
            System.out.println("You are currently in the dungeon");
            System.out.println("Now make a selection");
            System.out.println("1 move hero, 2 to display hero info, 3 to locate hero, 4 to use potion, 5 to quit");
            int selection = sc.nextInt();
            switch(selection) {
                case 1:
                    System.out.println("Now pick which direction you want to move");
                    System.out.println("8 for up, 4 for left, 2 for down, 6 for right");
                    selection = sc.nextInt();
                    switch (selection) {
                        case 2:
                            Dungeon.playerMove(Direction.SOUTH);
                        case 4:
                            Dungeon.playerMove(Direction.WEST);
                        case 6:
                            Dungeon.playerMove(Direction.EAST);
                        case 8:
                            Dungeon.playerMove(Direction.NORTH);
                    }
                case 2:
                    Dungeon.getView();
                case 3:
                    //
                case 4:
                    System.out.println("Now select which potion you want to use");
                    System.out.println("1 for vision, 2 for health");
                    selection = sc.nextInt();
                    switch(selection) {
                        case 1:
//                            VisionPotion.effect();
                        case 2:
//                            HealthPotion.heal();
                    }
                case 5:
                    System.out.println("Are you sure you want to quit? 1 for yes, 2 for no");
                    selection = sc.nextInt();
                    switch (selection) {
                        case 1:
                            System.exit(0);
                        case 2:
                            continue;
                        default:
                            System.out.println("Please make a selection");
                    }
                default:
                    System.out.println("Please make a selection");
            }
        }
    }





}
