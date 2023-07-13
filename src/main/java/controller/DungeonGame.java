package controller;

import view.Audio;
import view.TUI;
import model.*;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


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
    private final static boolean DEBUG_MODE = true;

    /**
     * The game's funny dialogue mode. If true, the player will be given funny dialogue throughout the game.
     */
    private final static boolean FUNNY_MODE = false;



    /**
     * The console object that will pop up for the user to interact with.
     */
    private final static Console console = System.console();

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
     * the amount of steps the player has taken since starting the current dungeon
     */
    private static int heroSteps = 0;

    /**
     * the total amount of steps the player has taken since starting the game
     */
    private static int totalHeroSteps = 0;

    /**
     * the amount of steps remaining until the player's vision potion wears off
     */
    private static int stepsWithActiveVisionPotion = 0;

    /**
     * the selected difficulty
     */
    private static Difficulty difficulty;

    /**
     * the amount of monsters beaten in the current dungeon
     */
    private static int monstersDefeated = 0;

    /**
     * the total amount of monsters beaten in the game
     */
    private static int totalMonstersDefeated = 0;

    private static boolean mediumUnlocked = false;
    private static boolean hardUnlocked = false;
    private static int gamesWon = 0;
    private static int easyGamesWon = 0;
    private static int mediumGamesWon = 0;
    private static int hardGamesWon = 0;
    private static List<Hero> heroes;

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

//        if (console == null) {
//            System.out.println("No console.");
//            System.exit(1);
//        }

        try {
            audio = Audio.getInstance();
        } catch(URISyntaxException e) {
            game.printAudioError(e);
        }

        game = new TUI(console);

        // create list of heroes
        heroes = new ArrayList<>();
        heroes.add(HeroFactory.buildHero(HeroTypes.SWORDSMAN));
        heroes.add(HeroFactory.buildHero(HeroTypes.ARCHER));
        heroes.add(HeroFactory.buildHero(HeroTypes.JUGGERNAUT));
        heroes.add(HeroFactory.buildHero(HeroTypes.THIEF));
        heroes.add(HeroFactory.buildHero(HeroTypes.DOCTOR));
        heroes.add(HeroFactory.buildHero(HeroTypes.NINJA));
        heroes.add(HeroFactory.buildHero(HeroTypes.SCIENTIST));
        heroes.add(HeroFactory.buildHero(HeroTypes.MAGE));

        // get user input to start game (1 for start, 2 for exit)
        char menuSelection = game.menu();
        audio.playSFX(audio.menuOne, -10);
        setupMenu(menuSelection);
    }

    /**
     * Set up the menu based on the user's choice
     */
    private static void setupMenu(int theMenuSelection) {
        switch(theMenuSelection) {
            case '1':
                System.out.println();

                displayActiveModes();

                // continue or new game (1 for new game, 2 for continue game)
                int loadSelection = game.continueOrNewGameMenu();

                if (loadSelection == 1) { // new game
                    System.out.println();

                    audio.playSFX(audio.menuOne, -10);

                    // setup dungeon (1 for easy, 2 for medium, 3 for hard)
                    int difficultySelection = game.chooseDifficulty(mediumUnlocked, hardUnlocked);
                    audio.playSFX(audio.menuOne, -10);
                    setupDungeon(difficultySelection);
                    System.out.println();

                    // load a random name
                    String name = giveRandomName();
                                                    // TODO make a list adjectives in alphabetical order.
                                                    // Let the use type in the first word, then pick a random adjective
                                                    // add "the" in between them. ex "Natan the noob"
                                                    // this is funnier, and gives the chance for the easter egg name like "Hiccup"

                    // print introduction
                    int introSelection = game.playIntroOrNot();
                    audio.playSFX(audio.menuOne, -10);
                    if (introSelection == 2) {
                        // play intro music

                        game.Introduction(FUNNY_MODE, audio, name);
                        DelayMachine.delay(4);
                    }
                    System.out.println();

                    // choose hero (1 for Enforcer, 2 for Robot, 3 for Support, 4 for Scientist, 5 for Warrior)
                    int heroSelection = game.chooseHero(heroes);
                    hero = chooseHero(heroSelection);
                    audio.playSFX(audio.menuTwo, 0);
                    hero.setName(name);
                    System.out.println();

                    if (introSelection == 2) {
                        game.heroIntroduction(hero);
                    }

                    if (CHEAT_MODE) {
                        cheatModeStuff();
                    }

                    DelayMachine.delay(1);

                    if (!DEBUG_MODE) {
                        // start msg
                        game.displayStartMsg();

                        // start music
                        audio.playMusic(audio.startingAnewSong, false, -12);
                        DelayMachine.delay(15);
                    }

                    audio.playMusic(audio.ambientSong, true, -10);

                    // start ticking time (call a method for this)

                    // enter the main game loop
                    gameLoop();

                } else if (loadSelection == 2) { // continue game
                    loadGame();
                    gameLoop();
                } else {
                    game.displayWrongInput();
                }

            case '2':
                audio.playSFX(audio.menuTwo, 0);
                DelayMachine.delay(2);
                game.displayCyaNerd();
                audio.playSFX(audio.heroOof, 0);
                DelayMachine.delay(2);
                System.exit(0);

            default:
                audio.playSFX(audio.error, -10);
                game.displayWrongInput();
                int newMenuSelection = game.menu();
                setupMenu(newMenuSelection);
        }
    }

    private static String giveRandomName() {
        String[] names;

        if (FUNNY_MODE) {
            names = new String[]{"Jimothy", "Hot Rod Todd", "Big Chungus", "Spooderman", "Chunky"};
        } else {
            names = new String[]{"Gideon the Greedy", "Cedric the Sensible", "Arthur the Aggravating",
                    "Lancelot the Lethargic", "Patrick the Peculiar", "Galahad the Gullible", "Oswald the Oddball",
                    "Benedict the Boring", "Archibald the Absurd", "Wilbur the Whimsical", "Horace the Hilarious"};

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
                return HeroFactory.buildHero(HeroTypes.ARCHER);
            case 3:
                return HeroFactory.buildHero(HeroTypes.JUGGERNAUT);
            case 4:
                return HeroFactory.buildHero(HeroTypes.THIEF);
            case 5:
                return HeroFactory.buildHero(HeroTypes.DOCTOR);
            case 6:
                return HeroFactory.buildHero(HeroTypes.NINJA);
            case 7:
                return HeroFactory.buildHero(HeroTypes.SCIENTIST);
            case 8:
                return HeroFactory.buildHero(HeroTypes.MAGE);
            default:
                audio.playSFX(audio.error, -10);
                game.displayWrongInput();
                int heroSelection = game.chooseHero(heroes);
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
                difficulty = Difficulty.EASY;
                break;
            case 2:
                // Medium
                Dungeon.MediumDungeonBuilder  theMediumDungeonBuilder = new Dungeon.MediumDungeonBuilder();
                dungeon = theMediumDungeonBuilder.buildDungeon();
                difficulty = Difficulty.MEDIUM;
                break;
            case 3:
                // Hard
                Dungeon.LargeDungeonBuilder  theLargeDungeonBuilder = new Dungeon.LargeDungeonBuilder();
                dungeon = theLargeDungeonBuilder.buildDungeon();
                difficulty = Difficulty.HARD;
                break;
            default:
                audio.playSFX(audio.error, -10);
                game.displayWrongInput();

                int difficultySelection = game.chooseDifficulty(mediumUnlocked, hardUnlocked);
                audio.playSFX(audio.menuOne, -10);
                setupDungeon(difficultySelection);
                System.out.println();
        }
    }

    /**
     * The main game loop
     */
    private static void gameLoop() {

        while (!gameOver) { // while the hero is still alive

            // check if vision potion is still active
            if (stepsWithActiveVisionPotion > 3) {
                hero.setUsingVisionPotion(false);
                dungeon.makeRoomsInvisible();
                stepsWithActiveVisionPotion = 0;
            }

            // uncover rooms if vision potion is active
            if (hero.usingVisionPotion()) {
                dungeon.makeRoomsVisible();
                stepsWithActiveVisionPotion++;
            }

            game.displayDungeonMap(dungeon);

            game.displayHeroHealth(hero);

            if (hero.usingVisionPotion()) {
                game.displayStepsWithVisionPotion(stepsWithActiveVisionPotion);
            }

            System.out.println();

            if (dungeon.heroIsTouchingPillar() && !CHEAT_MODE) {
                // play ding sound
                audio.playSFX(audio.heroCollectPillar, -10);

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
                    game.exitIsOpenMsg();
                }

                dungeon.removePillar();
            }

            if (dungeon.heroIsTouchingPotion()) {

                // play ding sound
//                if (audio.isPlayingSFX()) {
//                    DelayMachine.delay(4);
//                }

                Potion potion = dungeon.getPotion();

                if (hero.getInventory().isFull()) {
                    audio.playSFX(audio.heroBagFull, -10);
                    game.displayInventoryFullMsg();
                } else {
                    audio.playSFX(audio.heroCollectPotion, -8);
                    game.collectPotionMsg(potion);
                    hero.getInventory().addToInventory(potion);
                    audio.playSFX(audio.menuTwo, -10);
                    dungeon.removePotion();
                }
            }

            if (dungeon.heroIsTouchingPit() && !DEBUG_MODE) {
                // play pit sound

                Pit pit = dungeon.getPit();

                int fallDamage = pit.fall(hero);
                game.displayPitMsg(fallDamage);

                if (hero.getHealth() <= 0) {
                    // play death sound
                    if (FUNNY_MODE) {
                        audio.playSFX(audio.heroOof, -10);
                    } else {
                        audio.playSFX(audio.heroDefeat, -10);
                    }

                    game.displayDeathMsg(FUNNY_MODE);
                    gameOver = true;
                }
            }

            if (dungeon.heroIsTouchingMonster()) {
                Monster monster = dungeon.getMonster();

                // play monster encounter sound
//                if (audio.isPlayingSFX()) {
//                    DelayMachine.delay(4);
//                }
                audio.stopMusic();
                audio.playSFX(audio.encounter, -10);

                // play monster encounter cutscene
                game.displayMonsterEncounterMsg(monster);

                DelayMachine.delay(1); // delay for 0.5 seconds

                MonsterBattle battle = new MonsterBattle(hero, monster, game, CHEAT_MODE, audio, FUNNY_MODE);
                boolean winnerWinnerChickenDinner = battle.newBattle();

                if (winnerWinnerChickenDinner) {
                    // play victory sound
//                    audio.playSFX(audio.battleVictory, -10);

                    // earn rewards
                    hero.gainXP(monster.getXPWorth());

                    // win cutscene
                    if (!DEBUG_MODE) {
                        game.displayBattleWinMsg(monster);
                        checkIfLevelUp(); // display level-up message if applicable
                        game.displayBattleEnd();
                    }

                    dungeon.removeMonster();
                    monstersDefeated++;

                } else {
                    // play defeat sound
                    audio.playSFX(audio.heroDefeat, -10);

                    // defeat cutscene
                    game.displayDeathMsg(FUNNY_MODE);

                    gameOver = true;
                    break;
                }
                DelayMachine.delay(8); // delay for 4 seconds
                displayDungeonScreen(); // only redisplay the dungeon screen, don't go back to the start of the loop
                audio.playMusic(audio.ambientSong, true, -10);

            }

            if (dungeon.heroIsTouchingExit()) {
                if (exitIsOpen) {
                    // play victory sound
                    audio.stopAll();
                    DelayMachine.delay(1);

                    audio.playMusic(audio.triumpantFinishSong, true, -10);

                    // play cutscene
                    game.displayVictoryMsg(heroSteps, monstersDefeated, hero.getLevel(), difficulty, FUNNY_MODE);

                    if (!DEBUG_MODE) {
                        DelayMachine.delay(10);
                    }

                    game.pressAnyKeyMsg();
                    audio.stopAll();

                    // play credits if on hard mode
                    if (difficulty == Difficulty.HARD || DEBUG_MODE) {
                        DelayMachine.delay(4);
                        audio.playMusic(audio.rickRollSong, false, -10);
                        DelayMachine.delay(2);
                        game.displayCredits();
                        DelayMachine.delay(4);
                        game.displayRecommendListening();
                        DelayMachine.delay(4);
                        game.pressAnyKeyMsg();
                    }

                    // turn off music
                    audio.stopAll();

                    // update stats
                    totalHeroSteps += heroSteps;
                    heroSteps = 0;

                    DelayMachine.delay(2);

                    // make a popup showing characters that were unlocked
                    if (difficulty == Difficulty.EASY && easyGamesWon == 0) {
                        audio.playSFX(audio.infoPopup, -10);
                        game.displayUnlockedJuggernautAndThief();
                        easyGamesWon++;
                        DelayMachine.delay(4);
                        game.pressAnyKeyMsg();
                    }
                    if (difficulty == Difficulty.MEDIUM && mediumGamesWon == 0) {
                        audio.playSFX(audio.infoPopup, -10);
                        game.displayUnlockedDoctorAndNinja();
                        mediumGamesWon++;
                        DelayMachine.delay(4);
                        game.pressAnyKeyMsg();
                    }
                    if (difficulty == Difficulty.HARD && mediumGamesWon == 0) {
                        audio.playSFX(audio.infoPopup, -10);
                        game.displayUnlockedMage();
                        hardGamesWon++;
                        DelayMachine.delay(4);
                        game.pressAnyKeyMsg();
                    }
                    if (totalHeroSteps > 100) {
                        audio.playSFX(audio.infoPopup, -10);
                        game.displayUnlockedScientist();
                        DelayMachine.delay(4);
                        game.pressAnyKeyMsg();
                    }
                    if (totalMonstersDefeated > 100) {
                        audio.playSFX(audio.infoPopup, -10);
                        game.displayUnlockedBeastmaster();
                        DelayMachine.delay(4);
                        game.pressAnyKeyMsg();
                    }

                    // make a menu popup for choosing to exit or play again on different difficulty
                    game.displayPlayAgainMenu();


                    DelayMachine.delay(130);

                    gameOver = true;
                    break;
                } else {
                    game.exitLocked();
                }
            }

            if (dungeon.heroIsTouchingWall()) {
                game.displayWallMsg();
            }

            // spawn monster every 3 steps
            if (heroSteps > 1 && heroSteps % 3 == 0) {
                dungeon.spawnMonster();
            }

//            if (DEBUG_MODE) { //TODO move to start of gameLoop?
//                dungeon.makeRoomsVisible();
//            }

            //' w' to move up, 'a' to move left, 's' to move down, 'd' to move right
            // '1' to display hero info, 'e' open bag, '4' to quit, '5' to save game
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
                    audio.playSFX(audio.heroStatsOn, -10);
                    game.displayHeroStats(hero);
                    game.pressAnyKeyMsg(); // we don't need user input, right?
                    audio.playSFX(audio.heroStatsOff, -13);
                    break;

                case 'e': // open bag
                    inventoryMenu();

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
                    game.displayArrowSpacer();
                    dungeon.roomAbove();
                    game.displayArrowSpacer();
                    break;

                case 'l':
                    game.displayArrowSpacer();
                    dungeon.roomRight();
                    game.displayArrowSpacer();
                    break;

                case 'k':
                    game.displayArrowSpacer();
                    dungeon.roomBelow();
                    game.displayArrowSpacer();
                    break;

                case 'j':
                    game.displayArrowSpacer();
                    dungeon.roomLeft();
                    game.displayArrowSpacer();
                    break;

                default:
                    audio.playSFX(audio.error, -10);
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
        exitIsOpen = true;

        hero.getInventory().addToInventory(new HealthPotion());
        hero.getInventory().addToInventory(new VisionPotion());
    }

    private static void displayActiveModes() {
        if (CHEAT_MODE) {
            game.displayCheatModeMsg();
        }
        if (DEBUG_MODE) {
            game.displayDebugModeMsg();
        }
        if (FUNNY_MODE) {
            game.displayFunnyDialogueModeMsg();
        }
    }

    private static void checkIfLevelUp() {
        if ((hero.getXP() >= Hero.LEVEL_1_XP && hero.getLevel() == 0)
                || (hero.getXP() >= Hero.LEVEL_2_XP && hero.getLevel() == 1)
                || (hero.getXP() >= Hero.LEVEL_3_XP && hero.getLevel() == 2)
                || (hero.getXP() >= Hero.LEVEL_4_XP && hero.getLevel() == 3)
                || (hero.getXP() >= Hero.LEVEL_5_XP && hero.getLevel() == 4)) {

            hero.levelUp();
            game.levelUpMsg(hero.getLevel());
            audio.playSFX(audio.heroLevelUp, -15);
            DelayMachine.delay(2);
        }
    }

    private static void inventoryMenu() {
        Inventory bag = hero.getInventory();
        audio.playSFX(audio.heroBagOpen, -10);
        char input = game.openBag(bag, false, audio); // input is guaranteed to be 1-4 or e

        if (input != 'e') {
            int slotIndex = Character.getNumericValue(input)-1; // convert input to int (with -1 due to array indexing)

            Potion potion = bag.getItem(slotIndex);
            if (potion.canUseOutsideBattle()) {
                PotionDefensive defPotion = (PotionDefensive) potion;
                defPotion.effect(hero);
                bag.removeItem(slotIndex);
            } else {
                audio.playSFX(audio.error, -10);
                game.displayCantUseItemOutsideBattle(potion);
                inventoryMenu();
            }

            audio.playSFX(audio.heroDrinkPotion, -10);
            game.usePotionMsg(potion, slotIndex);
        }
        game.closeBag(audio);
    }

    private static void displayDungeonScreen() {
        game.displayDungeonMap(dungeon);

        game.displayHeroHealth(hero);

        if (hero.usingVisionPotion()) {
            game.displayStepsWithVisionPotion(stepsWithActiveVisionPotion);
        }
    }

}
