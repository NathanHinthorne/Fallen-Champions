package controller;

import view.Audio;
import view.TUI;
import model.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;


/**
 * This class is the main controller for the game.
 *
 * @author Nathan Hinthorne
 * @author Brendan Smith
 */
public class DungeonGame {

    /**
     * The game's cheat mode. If true, the player will be given extra items and abilities.
     */
    private final static boolean CHEAT_MODE = true;

    /**
     * The game's debug mode. If true, the player will be able to skip past enemies and see the whole map.
     */
    private final static boolean DEBUG_MODE = false;

    /**
     * The game's funny dialogue mode. If true, the player will be given funny dialogue throughout the game.
     */
    private final static boolean FUNNY_DIALOGUE = false;

    /**
     * shows the play is not in battle
     */
    private final static boolean IN_BATTLE = false;


    /**
     * the dungeon to be used in the game
     */
    private static Dungeon dungeon; // from model

    /**
     * the view to be used in the game. Swap with GUI later.
     */
    private static TUI game; // from view

    /**
     * the hero to be used in the game
     */
    private static Hero hero; // move to main and make non static?

    /**
     * whether the game is over
     */
    private static boolean gameOver = false; // set to true when player dies or exits dungeon

    /**
     * whether the exit is open
     */
    private static boolean exitIsOpen = false; // set to true when player collects all pillars

    /**
     * The audio object
     */
    private static Audio audio;

    /**
     * empty constructor
     */
    private DungeonGame() {
    }


    /**
     * The main method
     * @param theArgs Command line arguments
     */
    public static void main(String[] theArgs) {

        try {
            audio = Audio.getInstance();
        } catch(URISyntaxException e) {
            System.out.println("Error: Audio source could not be parsed." + e);
        }

        game = new TUI();

        // get user input to start game (1 for start, 2 for exit)
        int menuSelection = game.menu();
        audio.playSFX(audio.menuOne);
        setupMenu(menuSelection);
    }

    /**
     * Set up the menu based on the user's choice
     */
    private static void setupMenu(int theMenuSelection) {
        switch(theMenuSelection) {
            case 1:
                System.out.println();

                // continue or new game (1 for new game, 2 for continue game)
                int loadSelection = game.continueOrNewGameMenu();

                if (loadSelection == 1) { // new game
                    System.out.println();

                    audio.playSFX(audio.menuOne);

                    // setup dungeon (1 for easy, 2 for medium, 3 for hard)
                    int difficultySelection = game.chooseDifficulty();
                    audio.playSFX(audio.menuOne);
                    setupDungeon(difficultySelection);
                    System.out.println();

                    // load a random name
                    String name = giveRandomName();

                    // print introduction
                    int introSelection = game.playIntroOrNot();
                    if (introSelection == 2) {
                        // play intro music

                        game.Introduction(FUNNY_DIALOGUE, audio, name);
                        DelayMachine.delay(4);
                    }
                    System.out.println();

                    // choose hero (1 for Enforcer, 2 for Robot, 3 for Support, 4 for Scientist, 5 for Warrior)
                    audio.playMusic(audio.startingAnewSong, false);
                    int heroSelection = game.chooseHero();
                    hero = chooseHero(heroSelection);
                    audio.playSFX(audio.menuTwo);
                    hero.setName(name);
                    System.out.println();

                    if (introSelection == 2) {
                        game.heroIntroduction(hero);
                    }

                    DelayMachine.delay(4);


//                    audio.playSFX(audio.startingAnewSong); play somewhere else

                    // are you cheating...?
                    if (CHEAT_MODE) {
                        game.displayCheatModeMsg();
                        cheatModeStuff();
                    }
                    if (DEBUG_MODE) {
                        game.displayDebugModeMsg();
                    }
                    if (FUNNY_DIALOGUE) {
                        game.displayFunnyDialogueModeMsg();
                    }

                    // start msg
                    game.displayStartMsg();

                    // enter the main game loop
                    gameLoop();

                } else if (loadSelection == 2) { // continue game
                    loadGame();
                    gameLoop();
                } else {
                    System.out.println("Please make a proper selection!");
                }

            case 2:
                System.exit(0);

            default:
                game.displayWrongInput();
                int newMenuSelection = game.menu();
                setupMenu(newMenuSelection);
        }
    }

    private static String giveRandomName() {
        String[] names;

        if (FUNNY_DIALOGUE) {
            names = new String[]{"Jimothy", "Hot Rod Todd", "Big Chungus", "Spooderman", "Chunky" };
        } else {
            names = new String[]{"Gideon the Greedy", "Cedric the Sensible", "Arthur the Aggravating",
                    "Lancelot the Lethargic", "Patrick the Peculiar", "Galahad the Gullible" };
        }

        int randomIndex = (int) (Math.random() * names.length);
        return names[randomIndex];
    }

    /**
     * Set up the hero so the player can make a choice
     *
     * @param theChoice the hero to choose
     * @return the hero choice
     */
    private static Hero chooseHero(int theChoice) {

        switch(theChoice) {
            case 1:
                return HeroFactory.buildHero(HeroTypes.SWORDSMAN);
            case 2:
                return HeroFactory.buildHero(HeroTypes.THIEF);
            case 3:
                return HeroFactory.buildHero(HeroTypes.JUGGERNAUT);
            case 4:
                return HeroFactory.buildHero(HeroTypes.ARCHER);
            case 5:
                return HeroFactory.buildHero(HeroTypes.SCIENTIST);
            case 6:
                return HeroFactory.buildHero(HeroTypes.DOCTOR);
            case 7:
                return HeroFactory.buildHero(HeroTypes.MAGE);
            default:
                game.displayWrongInput();
                int heroSelection = game.chooseHero();
                return chooseHero(heroSelection);
        }
    }

    /**
     * Setup the dungeon based on the difficulty.
     * 1 = easy/small, 2 = medium/medium, 3 = hard/large
     *
     * @param theDifficulty the difficulty of the dungeon
     */
    public static void setupDungeon(final int theDifficulty) {

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
                game.displayWrongInput();

                int difficultySelection = game.chooseDifficulty();
                audio.playSFX(audio.menuOne);
                setupDungeon(difficultySelection);
                System.out.println();
        }
    }

    /**
     * The main game loop
     */
    private static void gameLoop() {

        if (audio.isPlayingMusic()) {
            DelayMachine.delay(8);
        }
        audio.playMusic(audio.ambientSong, true);
        int heroSteps = 0;
        int stepsWithActiveVisionPotion = 0;

        while (!gameOver) { // while the hero is still alive

            audio.playSFX(audio.menuTwo);

            if (dungeon.heroIsTouchingPillar() && !CHEAT_MODE) {
                // play ding sound

                Pillars pillar = dungeon.getPillar();
                hero.getInventory().addPillar(pillar);
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

                if (hero.getInventory().hasAllPillars()) {
                    exitIsOpen = true;
                }

                dungeon.removePillar();
            }

            if (dungeon.heroIsTouchingPotion()) {
                // play ding sound
                Potion potion = dungeon.getPotion();
                hero.getInventory().addToInventory(potion);
                game.displayPotionInfo(potion);

                audio.playSFX(audio.menuTwo);

                dungeon.removePotion();
            }

            if (dungeon.heroIsTouchingPit() && !DEBUG_MODE) {
                // play pit sound

                Pit pit = dungeon.getPit();

                int fallDamage = pit.fall(hero);
                game.displayPitMsg(fallDamage);

                if (hero.getHealth() <= 0) {
                    // play death sound
                    if (FUNNY_DIALOGUE) {
                        audio.playSFX(audio.heroOof);
                    } else {
                        audio.playSFX(audio.heroDefeat);
                    }

                    game.displayDeathMsg(FUNNY_DIALOGUE);
                    gameOver = true;
                }
            }

            if (dungeon.heroIsTouchingMonster() && !DEBUG_MODE) {
                Monster monster = dungeon.getMonster();

                // play monster encounter sound
                audio.stopMusic();
                audio.playSFX(audio.encounter);

                // play monster encounter cutscene (screen closes in with a circle around the player and the monster, then the battle begins (FORGET THIS FOR TUI))
                game.displayMonsterEncounterMsg(monster);

                DelayMachine.delay(1); // delay for 0.5 seconds

                MonsterBattle battle = new MonsterBattle(hero, monster, game, CHEAT_MODE, audio);
                boolean winnerWinnerChickenDinner = battle.newBattle();

                if (winnerWinnerChickenDinner) {
                    // play victory sound
                    // win cutscene
                    game.displayBattleWinMsg();
                    dungeon.removeMonster();
                    // earn rewards
                } else {
                    // play defeat sound
                    // defeat cutscene
                    game.displayDeathMsg(FUNNY_DIALOGUE);
                    gameOver = true;
                    break;
                }

                audio.playMusic(audio.ambientSong, true);
            }

            if (dungeon.heroIsTouchingExit()) {
                if (exitIsOpen) {
                    // play victory sound
                    audio.playSFX(audio.menuTwo);

                    // play cutscene
                    game.displayVictoryMsg(heroSteps);

                    gameOver = true;
                    break;
                } else {
                    game.exitLocked();
                }
            }

            if (dungeon.heroIsTouchingWall()) {
                game.displayWallMsg();
            }

//                DelayMachine.delay(1); // delay for 1 second

            // check if vision potion is still active
            if (stepsWithActiveVisionPotion > 3) {
                hero.setUsingVisionPotion(false);
                dungeon.makeRoomsInvisible();
                stepsWithActiveVisionPotion = 0;
            }

            if (hero.usingVisionPotion() || DEBUG_MODE) {
                dungeon.makeRoomsVisible();
            }

            game.displayDungeonMap(dungeon);

            game.displayHeroHealth(hero);

            //' w' to move up, 'a' to move left, 's' to move down, 'd' to move right
            // '1' to display hero info, '2' to display map, 'e' open bag, '4' to quit, '5' to save game
            int gameMenuSelection = game.gameplayMenu();
            switch(gameMenuSelection) {
                case 's':
                    dungeon.playerMove(Direction.SOUTH);
                    heroSteps++;
                    break;

                case 'a':
                    dungeon.playerMove(Direction.WEST);
                    heroSteps++;
                    break;

                case 'd':
                    dungeon.playerMove(Direction.EAST);
                    heroSteps++;
                    break;

                case 'w':
                    dungeon.playerMove(Direction.NORTH);
                    heroSteps++;
                    break;

                case '1': // hero info
                    game.displayHeroInfo(hero);
                    break;

                case '2': // display map
                    game.displayDungeonMap(dungeon);
                    break;

                case 'e': // open bag
                    int itemSlot = game.openBag(hero.getInventory(), false);

                    if (itemSlot < 5 && itemSlot > 0) {
                        Potion potion = hero.getInventory().getItem(itemSlot);
                        if (potion.canUseOutsideBattle()) {
                            hero.getInventory().removeItem(itemSlot);

                        }

                        audio.playSFX(audio.heroDrinkPotion);
                        if (potion.type().equals("Vision Potion"))  {
                            hero.setUsingVisionPotion(true);
                            dungeon.makeRoomsVisible();
                        }

                        game.displayUsePotionMsg(potion, itemSlot);
                    }
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

                case 'i':
                    System.out.println("Room has:");
                    dungeon.roomAbove();
                    break;

                case 'l':
                    System.out.println("Room has:");
                    dungeon.roomRight();
                    break;

                case 'k':
                    System.out.println("Room has:");
                    dungeon.roomBelow();
                    break;

                case 'j':
                    System.out.println("Room has:");
                    dungeon.roomLeft();
                    break;

                default:
                    game.displayWrongInput();
            }
        }
    }


    // Code from https://www.youtube.com/watch?v=xudKOLX_DAk
    /**
     * Saves the game to a file called jvs.sav
     */
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
    /**
     * Loads the game from a file called jvs.sav
     */
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

    /**
     * cheat mode stuff
     */
    private static void cheatModeStuff() {
        hero.getInventory().addPillar(Pillars.ABSTRACTION);
        hero.getInventory().addPillar(Pillars.ENCAPSULATION);
        hero.getInventory().addPillar(Pillars.INHERITANCE);
        hero.getInventory().addPillar(Pillars.POLYMORPHISM);

        hero.getInventory().addToInventory(new HealthPotion());
        hero.getInventory().addToInventory(new HealthPotion());
        hero.getInventory().addToInventory(new VisionPotion());
        hero.getInventory().addToInventory(new VisionPotion());
    }
}
