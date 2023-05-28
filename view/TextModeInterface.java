package view;
//import model.*;
import controller.*;
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
                case 2:
                    System.exit(0);
                default:
                    System.out.println("Please make a proper selection:");
            }
        }


    }





}
