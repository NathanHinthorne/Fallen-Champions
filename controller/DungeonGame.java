package controller;
import view.TextModeInterface;

import model.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DungeonGame {

    private final static HeroFactory HERO_FACTORY = new HeroFactory();
    private final static MonsterFactory MONSTER_FACTORY = new MonsterFactory();

    // remove "my" prefix?
    private static Dungeon myDungeon; // from model
    private static TextModeInterface myGame; // from view

    private static Hero myHero; // move to main?
    private static Monster myMonster; // move to main?

    private static boolean gameOver = false; // set to true when player dies or exits dungeon

    public Hero myHero;
    public Monster myMonster;

    public DungeonGame() {

        /* These Hero and Monster factory uses are temporary,
         * to see if they work properly.
         */
        myHero = HERO_FACTORY.buildHero(HeroTypes.WARRIOR);
        myMonster = MONSTER_FACTORY.buildMonster(MonsterTypes.SKELETON);
        myGame = new TextModeInterface();
    }



    public static void main(String[] theArgs) {

        // get user input to start game (1=start, 2=exit)
        int menuSelection = myGame.menu();
        switch(menuSelection) {
            case 1:
                // load the save files if they exist
                loadGame();

                // setup dungeon
                int difficultySelection = myGame.chooseDifficulty();
                setupDungeon(difficultySelection);

                myGame.Introduction(); // prints introduction
                int heroChoice = myGame.chooseHero();
                myHero = setupHero(heroChoice);

                myGame.gameplayMenu();
                gameLoop();
            case 2:
                System.exit(0);
            default:
                System.out.println("Please make a proper selection:");
                menuSelection = myGame.menu();
        }


        updateMap();


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

                Dungeon.getView(); // display the player's view immediately after each move

                DelayMachine.delay(2); // delay for 1 second


                // determine if the player is on the same tile as a monster
                    // play monster encounter sound
                    // play monster encounter cutscene?

                    // if player wins, continue game, earn rewards
                    // if player loses, gameOver = true

                

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
                        System.out.println("Now pick which direction you want to move");
                        System.out.println("8 for up, 4 for left, 2 for down, 6 for right");
                        break;

                    case 2: // display map
                        updateMap();
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

    /**
     * Displays the dungeon map
     */
    public static void updateMap() { System.out.println(myDungeon.toString()); }


}
