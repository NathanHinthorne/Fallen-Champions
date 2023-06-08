package controller;

import view.TextModeInterface;
import model.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DungeonGame {
    private final static boolean CHEAT_MODE = true;


    private final static HeroFactory HERO_FACTORY = new HeroFactory();

    private final static MonsterFactory MONSTER_FACTORY = new MonsterFactory();

    // remove "my" prefix?
    private static Dungeon dungeon; // from model
    private static TextModeInterface game; // from view

    private static Hero hero; // move to main and make non static?

    private static boolean gameOver = false; // set to true when player dies or exits dungeon
    private static boolean exitIsOpen = false; // set to true when player collects all pillars

    public DungeonGame() {
        // does anything need to be here?
    }



    public static void main(String[] theArgs) {

        game = new TextModeInterface();

        // get user input to start game (1 for start, 2 for exit)
        int menuSelection = game.menu();
        switch(menuSelection) {
            case 1:

                System.out.println(); // empty line

                // continue or new game (1 for new game, 2 for continue game)
                int loadSelection = game.continueOrNewGameMenu();
                if (loadSelection == 2) {
                    loadGame();
                    gameLoop();
                } else if (loadSelection == 1) {
                    System.out.println(); // empty line

                    // setup dungeon (1 for easy, 2 for medium, 3 for hard)
                    int difficultySelection = game.chooseDifficulty();
                    setupDungeon(difficultySelection);
                    System.out.println(); // empty line

                    // print introduction
                    game.Introduction();
                    System.out.println(); // empty line

                    // choose hero (1 for Enforcer, 2 for Robot, 3 for Support, 4 for Scientist, 5 for Warrior)
                    int heroSelection = game.chooseHero();
                    hero = setupHero(heroSelection);
                    System.out.println(); // empty line

                    // enter the main game loop ('w' to move up, 'a' to move left, 's' to move down, 'd' to move right
                    //                           '1' to display hero info, '2' to display map, 'e' open bag, '4' to quit, '5' to save game)
                    gameLoop();
                } else {
                    System.out.println("Please make a proper selection!");
                }


            case 2:
                System.exit(0);

            default:
                System.out.println("Please make a proper selection:");
                main(theArgs); // original code: "menuSelection = game.menu();"
        }
    }

    /**
     * Set up the hero so the player can make a choice
     * @param theChoice the hero to choose
     * @return the hero choice
     */
    private static Hero setupHero(int theChoice) {
        switch(theChoice) {
            case 1:
                return HERO_FACTORY.buildHero(HeroTypes.ENFORCER); // use this instead? HeroFactory.buildHero(HeroTypes.ENFORCER);
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
                Dungeon.SmallDungeonBuilder theSmallDungeonBuilder = new Dungeon.SmallDungeonBuilder(); //original: DungeonBuilder theSmallDungeonBuilder...
                dungeon = theSmallDungeonBuilder.buildDungeon();
                break;
            case 2:
                // Medium
                Dungeon.MediumDungeonBuilder  theMediumDungeonBuilder = new Dungeon.MediumDungeonBuilder();
                dungeon = theMediumDungeonBuilder.buildDungeon();
                break;
            case 3:
                // Hard
                Dungeon.LargeDungeonBuilder  theLargeDungeonBuilder = new Dungeon.LargeDungeonBuilder();
                dungeon = theLargeDungeonBuilder.buildDungeon();
                break;
            default:
                System.out.println("Please make a proper selection:");
        }
    }

    private static void gameLoop() {
            while (!gameOver) { // while the hero is still alive

                // display a view of the dungeon immediately
                if (CHEAT_MODE) {
                    game.printDungeonMap(dungeon);
                } else {
                    game.printPlayerView(dungeon); // display the 3x3 player's view
                }

                if (CHEAT_MODE) {
                    hero.getMyInventory().addPillar(Pillars.ABSTRACTION);
                    hero.getMyInventory().addPillar(Pillars.ENCAPSULATION);
                    hero.getMyInventory().addPillar(Pillars.INHERITANCE);
                    hero.getMyInventory().addPillar(Pillars.POLYMORPHISM);
                }

                if (dungeon.heroIsTouchingPillar()) {
                    // play ding sound

                    Pillars pillar = dungeon.getPillar();
                    hero.getMyInventory().addPillar(pillar);
                    switch (pillar) {
                        case ABSTRACTION:
                            game.displayAbstractionPillarMsg();
                            break;
                        case ENCAPSULATION:
                            game.displayEncapsulationPillarMsg();
                            break;
                        case INHERITANCE:
                            game.displayInheritancePillarMsg();
                            break;
                        case POLYMORPHISM:
                            game.displayPolymorphismPillarMsg();
                            break;
                    }

                    if (hero.getMyInventory().hasAllPillars()) {
                        exitIsOpen = true;
                    }
                }

                if (dungeon.heroIsTouchingPotion()) {
                    // play ding sound
                    Potion potion = dungeon.getPotion();
                    hero.getMyInventory().addToInventory(potion);
                    game.displayPotionInfo(potion);
                }

                if (dungeon.heroIsTouchingPit()) {
                    // play pit sound

                    dungeon.getPit().fall(hero);

                    if (hero.getHitPoints() <= 0) {
                        gameOver = true;
                    }
                }

                if (dungeon.heroIsTouchingMonster()) {
                    Monster monster = dungeon.getMonster();

                    // play monster encounter sound
                    System.out.println("You have encountered a monster!");

                    DelayMachine.delay(2); // delay for 1 second
                    // play monster encounter cutscene? (screen closes in with a circle around the player and the monster, then the battle begins (FORGET THIS FOR TUI))
                    MonsterBattle battle = new MonsterBattle(hero,monster,game);
                    boolean winnerWinnerChickenDinner = battle.newBattle();

                    if (winnerWinnerChickenDinner) {
                        // play victory sound
                        // win cutscene
                        // earn rewards
                    } else {
                        // play defeat sound
                        // defeat cutscene
                        gameOver = true;
                        break;
                    }
                }

                if (dungeon.heroIsTouchingExit()) {
                    if (exitIsOpen) {
                        // play victory sound
                        // play cutscene?
                        game.displayVictoryMsg();
                        gameOver = true;
                        break;
                    } else {
                        System.out.println("The exit is locked! You need to collect all 4 pillars to open it!");
                    }
                }

                if (dungeon.heroIsTouchingWall()) {
                    System.out.println("WHAT ARE YOU DOING IN A WALL?! GET OUT OF THERE YOU FOOL");
                }

//                DelayMachine.delay(1); // delay for 1 second

                //' w' to move up, 'a' to move left, 's' to move down, 'd' to move right
                // '1' to display hero info, '2' to display map, 'e' open bag, '4' to quit, '5' to save game
                int gameMenuSelection = game.gameplayMenu();
                switch(gameMenuSelection) {
                    case 's':
                        dungeon.playerMove(Direction.SOUTH);
                        break;

                    case 'a':
                        dungeon.playerMove(Direction.WEST);
                        break;

                    case 'd':
                        dungeon.playerMove(Direction.EAST);
                        break;

                    case 'w':
                        dungeon.playerMove(Direction.NORTH);
                        break;

                    case '1': // hero info
                        game.displayHeroInfo(hero);
                        break;

                    case '2': // display map
                        game.printDungeonMap(dungeon);
                        break;

                    case 'e': // open bag
                        boolean choosing = true;
                        int itemSlot = game.openBag(hero.getMyInventory());
                        hero.getMyInventory().consumeItem(hero, itemSlot);
                        break;

                    case '4': // quit
                        int quit = game.quitProcess();
                        if (quit == 1) {
                            gameOver = true;
                        }
                        break;

                    case '5': // save
                        saveGame();
                        break;

                    default:
                        System.out.println("Please make a proper selection:");
                }
        }
    }
    // Code from https://www.youtube.com/watch?v=xudKOLX_DAk
    private static void saveGame() {
        try {
            FileOutputStream fos = new FileOutputStream("jvs.sav");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(dungeon);
            oos.writeObject(hero);
            oos.flush();
            oos.close();
            System.out.print("Game saved!\n");
        } catch (Exception e) {
            System.out.println("Couldn't save game!");
            System.out.println(e.getClass() + ": " + e.getMessage());
        }
    }
    // Code from https://www.youtube.com/watch?v=xudKOLX_DAk
    private static void loadGame() {
        try {
            FileInputStream fos = new FileInputStream("jvs.sav");
            ObjectInputStream ois = new ObjectInputStream(fos);
            dungeon = (Dungeon) ois.readObject();
            hero = (Hero) ois.readObject();
            ois.close();
            System.out.print("Game loaded!\n");
        } catch (Exception e) {
            System.out.println("Couldn't load game! Starting a new game...");
        }

    }




    // CHEAT SHEET for dungeon symbols:
        //   = empty room
        // * = wall
        // H = hero
        // M = monster
        // X = pit
        // o = entrance
        // O = exit
        // p = potion
        // A = abstraction pillar
        // I = inheritance pillar
        // P = polymorphism pillar
        // E = encapsulation pillar
        // & = multiple items in the same room
}
