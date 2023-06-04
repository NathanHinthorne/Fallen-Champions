package controller;

import view.TextModeInterface;
import model.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DungeonGame {
    private final static boolean CHEAT_MODE = false;


    private final static HeroFactory HERO_FACTORY = new HeroFactory();

    // remove "my" prefix?
    private static Dungeon myDungeon; // from model
    private static TextModeInterface myGame; // from view

    private static Hero myHero; // move to main?
    private static Monster myMonster; // move to main?

    private static boolean gameOver = false; // set to true when player dies or exits dungeon

    public DungeonGame() {
        // does anything need to be here?
    }



    public static void main(String[] theArgs) {

        myGame = new TextModeInterface();

        // get user input to start game (1=start, 2=exit)
        int menuSelection = myGame.menu();
        switch(menuSelection) {
            case 1:

                // continue or new game (1 for new game, 2 for continue game)
                int loadSelection = myGame.continueOrNewGameMenu();
                if (loadSelection == 2) {
                    loadGame();
                }

                // setup dungeon
                int difficultySelection = myGame.chooseDifficulty();
                setupDungeon(difficultySelection);

                // print introduction
                myGame.Introduction();

                // choose hero
                int heroSelection = myGame.chooseHero();
                myHero = setupHero(heroSelection);

                // enter the main game loop
                gameLoop();

            case 2:
                System.exit(0);

            default:
                System.out.println("Please make a proper selection:");
                menuSelection = myGame.menu();
        }
    }

    private static Hero setupHero(int theChoice) {
        switch(theChoice) {
            case 1:
                return HERO_FACTORY.buildHero(HeroTypes.ENFORCER);
            case 2:
                return HERO_FACTORY.buildHero(HeroTypes.ROBOT);
            case 3:
                return HERO_FACTORY.buildHero(HeroTypes.SUPPORT);
            case 4:
                return HERO_FACTORY.buildHero(HeroTypes.SCIENTIST);
            case 5:
                return HERO_FACTORY.buildHero(HeroTypes.WARRIOR);
            default:
                System.out.println("Please choose a hero!");
                return null;
        }
    }


    public static void setupDungeon(final int theDifficulty) {
        // small dungeon = easy difficulty
        // medium dungeon = medium difficulty
        // large dungeon = hard difficulty

        switch(theDifficulty) {
            case 1:
                // Easy
                DungeonBuilder theSmallDungeonBuilder = new Dungeon.SmallDungeonBuilder(); // declare the builder
                myDungeon = theSmallDungeonBuilder.buildDungeon(); // use it to build the dungeon
                break;
            case 2:
                // Medium
                DungeonBuilder theMediumDungeonBuilder = new Dungeon.MediumDungeonBuilder(); // declare the builder
                myDungeon = theMediumDungeonBuilder.buildDungeon(); // use it to build the dungeon
                break;
            case 3:
                // Hard
                DungeonBuilder theLargeDungeonBuilder = new Dungeon.LargeDungeonBuilder(); // declare the builder
                myDungeon = theLargeDungeonBuilder.buildDungeon(); // use it to build the dungeon
                break;
            default:
                System.out.println("Please make a proper selection:");
        }
    }

    private static void gameLoop() {
            while (!gameOver) { // while the hero is still alive

                // display a view of the dungeon immediately
                if (CHEAT_MODE) {
                    myDungeon.toString(); // display the entire dungeon
                } else {
                    myDungeon.getView(); // display the 3x3 player's view
                }




                if (myDungeon.heroIsTouchingMonster()) {
                    // play monster encounter sound
                    DelayMachine.delay(2); // delay for 1 second
                    // play monster encounter cutscene? (screen closes in with a circle around the player and the monster, then the battle begins (FORGET THIS FOR TUI))

                    // if player wins, continue game, earn rewards
                    // if player loses, gameOver = true
                }


                DelayMachine.delay(1); // delay for 1 second

                //' w' to move up, 'a' to move left, 's' to move down, 'd' to move right
                // '1' to display hero info, '2' to display map, '3' open bag, '4' to quit, '5' to save game
                int gameMenuSelection = myGame.gameplayMenu();
                switch(gameMenuSelection) {
                    case 's':
                        Dungeon.playerMove(Direction.SOUTH);
                        break;

                    case 'a':
                        Dungeon.playerMove(Direction.WEST);
                        break;

                    case 'd':
                        Dungeon.playerMove(Direction.EAST);
                        break;

                    case 'w':
                        Dungeon.playerMove(Direction.NORTH);
                        break;

                    case 1: // hero info
                        myGame.displayHeroInfo(myHero);
                        break;

                    case 2: // display map
                        myDungeon.toString(); // display the entire dungeon
                        break;

                    case 3: // open bag
                        boolean choosing = true;
                        int itemSlot = myGame.openBag(myHero.getMyInventory());
                        myHero.getMyInventory().consumeItem(myHero, itemSlot);
                        break;

                    case 4: // quit
                        int quit = myGame.quitProcess();
                        if (quit == 0) {
                            gameOver = true;
                        }
                        break;

                    case 5:
                        // save game (with serialization)
                        saveGame();
                        break;

                    default:
                        System.out.println("Incorrect key");
                }
        }
    }
    // Code from https://www.youtube.com/watch?v=xudKOLX_DAk
    private static void saveGame() {
        try {
            FileOutputStream fos = new FileOutputStream("jvs.sav");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(myDungeon);
            oos.writeObject(myHero);
            oos.flush();
            oos.close();
            System.out.print("Game saved!\n");
        } catch (Exception e) {
            System.out.println("Couldn't save game!");
        }
    }
    // Code from https://www.youtube.com/watch?v=xudKOLX_DAk
    private static void loadGame() {
        try {
            FileInputStream fos = new FileInputStream("jvs.sav");
            ObjectInputStream ois = new ObjectInputStream(fos);
            myDungeon = (Dungeon) ois.readObject();
            myHero = (Hero) ois.readObject();
            ois.close();
            System.out.print("Game loaded!\n");
        } catch (Exception e) {
            System.out.println("Couldn't load game!");
        }

    }



}
