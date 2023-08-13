package controller;

import model.characters.heroes.Inventory;
import model.dungeon.*;
import model.characters.heroes.Hero;
import model.characters.heroes.HeroFactory;
import model.characters.heroes.HeroTypes;
import model.characters.monsters.Monster;
import model.potions.HealthPotion;
import model.potions.Potion;
import model.potions.PotionDefensive;
import model.potions.VisionPotion;
import org.sqlite.SQLiteDataSource;
import view.Audio;
import view.TUI;

import java.io.*;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


/**
 * This class is the main controller for the game.
 *
 * @author Nathan Hinthorne
 * @author Brendan Smith
 */
public class DungeonGame {

    /**
     * The code to type in to activate funny mode.
     */
    private final static String FUNNY_MODE_CODE = "idk yet"; //TODO ??

    /**
     * The code to type in to activate debug mode.
     */
    private final static String DEBUG_MODE_CODE = "Ethan is awesome"; // temp for version 1

    /**
     * The code to type in to activate cheat mode.
     */
    private final static String CHEAT_MODE_CODE = "idk yet"; //TODO ??


    /**
     * The game's cheat mode. If true, the player will be given extra items and abilities.
     */
    private static boolean cheatMode = false;

    /**
     * The game's debug mode. If true, the player will be able to skip past cutscenes.
     */
    private static boolean debugMode = true;

    /**
     * The game's pass-thru monster mode. If true, the player will be able to walk through monsters.
     */
    private static boolean passThruMonsterMode = false;

    /**
     * The game's funny dialogue mode. If true, the player will be given funny dialogue throughout the game.
     */
    private static boolean funnyMode = false;

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
     * the hero's name (including suffix)
     */
    private static String heroFullName;

    /**
     * the hero's first name the user inputs
     */
    private static String heroFirstName;

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
    private static boolean glitchUnlocked = false;
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
        heroes.add(HeroFactory.buildHero(HeroTypes.BEASTMASTER));

        startMenu();
    }

    private static void startMenu() {
        game.menu(debugMode, audio);

        // continue or new game (1 for new game, 2 for continue game)
        char loadSelection = game.continueOrNewGameMenu();
        switch(loadSelection) {

            case '1': // new game
                audio.playSFX(audio.menuOne, -10);
                newGameSetup();

            case '2': // continue game
                audio.playSFX(audio.menuOne, -10);
                loadGameSetup();
                break;

            default:
                audio.playSFX(audio.error, -10);
                game.displayWrongInput();
                startMenu();
        }
    }

    /**
     * Setup the game based on the user's choice
     */
    private static void newGameSetup() {

        // print introduction
        System.out.println();
        char introSelection = game.playIntroOrNot();
        if (introSelection != '1' && introSelection != '2') {
            audio.playSFX(audio.error, -10);
            game.displayWrongInput();
            newGameSetup();
        }

        audio.playSFX(audio.menuOne, -10);
        if (introSelection == '2') {
            game.introductionP1(funnyMode, audio);
        }

        newHeroName();

        if (introSelection == '2') {
            game.introductionP2(funnyMode, audio, heroFirstName, heroFullName);
            DelayMachine.delay(4);
        } else {
            System.out.println("【 " + heroFullName + " 】");
            game.pressAnyKeyContinue();
            audio.playSFX(audio.menuTwo, -5);
        }

        // choose hero
        newHero();

        if (introSelection == '2') {
            game.heroIntroduction(hero);
        }

        if (cheatMode) {
            cheatModeStuff();
        }

        // setup dungeon (1 for easy, 2 for medium, 3 for hard)
        selectDifficulty();

        DelayMachine.delay(1);

        if (!debugMode) {
            // start msg
            game.displayStartMsg();

            // start music
            audio.playMusic(audio.startingAnewSong, false, -12);
            DelayMachine.delay(15);
        }

        // enter the main game loop
        gameLoop();
    }

    private static void loadGameSetup() {
        loadGame();

        // start music
        audio.playMusic(audio.ambientSong, true, -10);

        gameLoop();
    }

    private static void mainMenu() {
        char choice = game.mainMenu();
        switch (choice) {

            case '1': // continue
                audio.playSFX(audio.menuOne, -10);
                hero.resetStats();
                selectDifficulty();
                gameOver = false;
                gameLoop();
                break;

            case '2': // change hero
                audio.playSFX(audio.menuOne, -10);
                changeHero();
                game.displayHeroSelected(hero);
                DelayMachine.delay(4);
                mainMenu();
                break;

            case '3': // change name
                audio.playSFX(audio.menuOne, -10);
                newHeroName();
                hero.setName(heroFullName);
                game.displayHeroNameChanged(hero);
                DelayMachine.delay(4);
                mainMenu();
                break;

            case '4': // cheat code menu
                audio.playSFX(audio.menuOne, -10);
                cheatCodeMenu();
                DelayMachine.delay(4);
                mainMenu();
                break;

            case '6':   // save
                audio.playSFX(audio.menuOne, -10);
                saveGame();
                DelayMachine.delay(4);
                mainMenu();
                break;

            case '7': // exit
                audio.playSFX(audio.menuOne, -10);
                char uSure = game.quitProcess();
                if (uSure == '1') exitGame();
                else mainMenu();
                break;

            default:
                audio.playSFX(audio.error, -10);
                game.displayWrongInput();
                mainMenu();
        }
    }

    private static String capitalize(final String firstName) {
        String firstLetter = firstName.substring(0, 1).toUpperCase();
        String restOfName = firstName.substring(1);
        return firstLetter + restOfName;
    }

    private static String giveRandomSuffix(final String theFirstName) {
        List<String> names = makeNamesList(theFirstName);

        // randomize the suffixes
        Collections.shuffle(names);
        int randomIndex = (int) (Math.random() * names.size());
        String suffix = names.get(randomIndex);

        // make the full name
        return theFirstName + " the " + suffix;

    }

    private static List<String> makeNamesList(final String theFirstName) {

        // establish connection to database
        SQLiteDataSource ds = new SQLiteDataSource();

        // try to open the database
        try {
            ds = new SQLiteDataSource();
//            ds.setUrl("jdbc:sqlite:" + getClass().getResource("/Monster_Database.db").getPath()); // can't access instance stuff here?
            ds.setUrl("jdbc:sqlite:" + DungeonGame.class.getResource("/Hero_Names.db").getPath()); // hopefully this works instead
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        String firstLetter = theFirstName.substring(0, 1).toUpperCase();
        List<String> names = new ArrayList<>();
        String query = "SELECT " + firstLetter + " FROM Names";

        try (Connection conn = ds.getConnection();
            Statement statement = conn.createStatement()) {
            ResultSet rs = statement.executeQuery(query);

            while ( rs.next() ) {
                String word = rs.getString( firstLetter );
                names.add(word);
            }
        } catch ( SQLException e ) {
            System.out.println("Could not access database");
            e.printStackTrace();
            System.exit( 0 );
        }

        return names;
    }


    /**
     * Set up the hero so the player can make a choice
     *
     * @param theChoice the hero to choose
     * @return the hero choice
     */
    private static Hero chooseHero(char theChoice) {

        switch(theChoice) {
            case '1':
                return HeroFactory.buildHero(HeroTypes.SWORDSMAN);
            case '2':
                return HeroFactory.buildHero(HeroTypes.ARCHER);
            case '3':
                return HeroFactory.buildHero(HeroTypes.JUGGERNAUT);
            case '4':
                return HeroFactory.buildHero(HeroTypes.THIEF);
            case '5':
                return HeroFactory.buildHero(HeroTypes.DOCTOR);
            case '6':
                return HeroFactory.buildHero(HeroTypes.NINJA);
            case '7':
                return HeroFactory.buildHero(HeroTypes.SCIENTIST);
            case '8':
                return HeroFactory.buildHero(HeroTypes.MAGE);
            default:
                audio.playSFX(audio.error, -10);
                game.displayWrongInput();
                char heroSelection = game.chooseHero(heroes);
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
            case '1':
                // Easy
                Dungeon.SmallDungeonBuilder theSmallDungeonBuilder = new Dungeon.SmallDungeonBuilder(); //original: DungeonBuilder theSmallDungeonBuilder...
                dungeon = theSmallDungeonBuilder.buildDungeon();
                difficulty = Difficulty.EASY;
                break;
            case '2':
                // Medium
                Dungeon.MediumDungeonBuilder  theMediumDungeonBuilder = new Dungeon.MediumDungeonBuilder();
                dungeon = theMediumDungeonBuilder.buildDungeon();
                difficulty = Difficulty.MEDIUM;
                break;
            case '3':
                // Hard
                Dungeon.LargeDungeonBuilder  theLargeDungeonBuilder = new Dungeon.LargeDungeonBuilder();
                dungeon = theLargeDungeonBuilder.buildDungeon();
                difficulty = Difficulty.HARD;
                break;
            default:
                audio.playSFX(audio.error, -10);
                game.displayWrongInput();
                selectDifficulty();
        }
    }


    /**
     * The main game loop
     */
    private static void gameLoop() {

        // setup work
        hero.setDirection(Direction.NORTH);
        heroSteps = 0;
        stepsWithActiveVisionPotion = 0;
        audio.playMusic(audio.ambientSong, true, -10);


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

            displayDungeonScreen();

            System.out.println();

            if (dungeon.heroIsTouchingWall()) {
                game.displayWallMsg();
            }

            if (dungeon.heroIsTouchingPillar() && !cheatMode) {
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
                    hero.getInventory().addToItems(potion);
                    audio.playSFX(audio.menuTwo, -5);
                    dungeon.removePotion();
                }
            }

            if (dungeon.heroIsTouchingPit()) {
                // play pit sound

                Pit pit = dungeon.getPit();

                int fallDamage = pit.fall(hero);
                game.displayPitMsg(fallDamage);

                if (hero.getHealth() <= 0) {
                    // play death sound
                    if (funnyMode) {
                        audio.playSFX(audio.heroOof, -10);
                    } else {
                        audio.playSFX(audio.heroDefeat, -10);
                    }

                    game.displayDeathMsg(funnyMode);
                    gameOver = true;
                }
            }

            if (dungeon.heroIsTouchingMonster() && !passThruMonsterMode) {
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

                MonsterBattle battle = new MonsterBattle(hero, monster, game, cheatMode, audio, funnyMode);
                boolean winnerWinnerChickenDinner = battle.newBattle();

                if (winnerWinnerChickenDinner) {
                    // play victory sound
                    audio.playSFX(audio.heroWinBattle, -10);

                    // earn rewards
                    hero.gainXP(monster.getXPWorth());

                    // win cutscene
                    game.displayBattleWinMsg(monster);
                    checkIfLevelUp(); // display level-up message if applicable
                    game.displayBattleEnd();

                    dungeon.removeMonster();
                    monstersDefeated++;

                } else {
                    // play defeat sound
                    audio.playSFX(audio.heroDefeat, -10);

                    // defeat cutscene
                    game.displayDeathMsg(funnyMode);

                    gameOver = true;
                    break;
                }
                DelayMachine.delay(8); // delay for 4 seconds
                displayDungeonScreen(); // only redisplay the dungeon screen, don't go back to the start of the loop
                audio.playMusic(audio.ambientSong, true, -10);

            }

            if (dungeon.heroIsTouchingExit()) {
                if (!exitIsOpen) {
                    audio.playSFX(audio.lockedDoor, -10);
                    game.exitLocked();

                } else {
                    victory();
                    break;
                }
            }

            // spawn monster every 4 steps
            if (heroSteps > 1 && heroSteps % 4 == 0) {
                dungeon.spawnMonster();
            }

            //' w' to move up, 'a' to move left, 's' to move down, 'd' to move right
            // '1' to display hero info, 'e' open bag, '4' to main menu, '5' to save game
            int gameMenuSelection = game.gameplayMenu();
            boolean hasMoved = false;
            switch(gameMenuSelection) {
                case 's':
                    hasMoved = dungeon.playerMove(Direction.SOUTH);
                    if (hasMoved) {
                        heroSteps++;
                        audio.playSFX(audio.step4, -10);
                    }
                    else { game.displayHitWallMsg(); }
                    hero.setDirection(Direction.SOUTH);
                    break;

                case 'a':
                    hasMoved = dungeon.playerMove(Direction.WEST);
                    if (hasMoved) {
                        heroSteps++;
                        audio.playSFX(audio.step4, -10);
                    }
                    else { game.displayHitWallMsg(); }
                    hero.setDirection(Direction.WEST);
                    break;

                case 'd':
                    hasMoved = dungeon.playerMove(Direction.EAST);
                    if (hasMoved) {
                        heroSteps++;
                        audio.playSFX(audio.step4, -10);
                    }
                    else { game.displayHitWallMsg(); }
                    hero.setDirection(Direction.EAST);
                    break;

                case 'w':
                    hasMoved = dungeon.playerMove(Direction.NORTH);
                    if (hasMoved) {
                        heroSteps++;
                        audio.playSFX(audio.step4, -10);
                    }
                    else { game.displayHitWallMsg(); }
                    hero.setDirection(Direction.NORTH);
                    break;

                case '1': // hero info
                    audio.playSFX(audio.swishOn, -10);
                    game.displayHeroStats(hero);
                    game.pressAnyKeyGoBack();
                    audio.playSFX(audio.swishOff, -13);
                    break;

                case '2':
                    audio.playSFX(audio.swishOn, -10);
                    game.displayInstructions(audio);
                    game.pressAnyKeyGoBack();
                    audio.playSFX(audio.swishOff, -13);
                    break;

                case 'e': // open bag
                    inventoryMenu();

                    break;

                case '4': // main menu
                    char quit = game.goToMainMenu();
                    if (quit == '1') {
                        audio.playSFX(audio.menuTwo, -5);
                        audio.stopMusic();
                        gameOver = true; //? needed?
                        mainMenu();
                    }
                    break;

                case '5': // save
                    saveGame();
                    break;

                case 'z': // ranged ability
                    if (hero.hasMazeAbility()) {
                        hero.activateMazeAbility(dungeon);
                    }
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

                case 'p':
                    hero.setHealth(hero.getHealth()/2);
                    System.out.println("DEBUG: Decreased Health..." + hero.getHealth());
                    break;

                default:
                    audio.playSFX(audio.error, -10);
                    game.displayWrongInput();
            }
        }
    }





































    // Code partially from https://www.youtube.com/watch?v=xudKOLX_DAk
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
            System.out.println();
            System.out.print("[ [ [ [ [ [ [ [ [ [       ---Game saved!---       ] ] ] ] ] ] ] ] ] ]\n");
            System.out.println();
        } catch (Exception e) {
            System.out.println("Couldn't save game!");
            System.out.println(e.getClass() + ": " + e.getMessage());
        }
    }


    // Code partially from https://www.youtube.com/watch?v=xudKOLX_DAk
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
            System.out.println();
            System.out.print("[ [ [ [ [ [ [ [ [ [       ---Game loaded!---        ] ] ] ] ] ] ] ] ] ]\n");
            System.out.println();
        } catch (Exception e) {
            System.out.println("Couldn't find any saved games! Try starting a new game instead.");
            newGameSetup();
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

        hero.getInventory().addToItems(new HealthPotion());
        hero.getInventory().addToItems(new VisionPotion());
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
        game.displayHealthInDungeon(hero);
        game.displayHeroDirection(hero);

        if (hero.usingVisionPotion()) {
            game.displayStepsWithVisionPotion(stepsWithActiveVisionPotion);
        }
        if (hero.hasMazeAbility() && hero.mazeAbilityActivated()) { // for archer's ranged attack, etc.
            game.displayMazeAbility(hero);
        }
    }

    private static void exitGame() {
        audio.playSFX(audio.menuTwo, -5);
        DelayMachine.delay(2);
        game.displayCyaNerd(funnyMode);
        audio.playSFX(audio.heroOof, -5);
        DelayMachine.delay(2);
        System.exit(0);
    }

    private static void displayPopups() {

        if (difficulty == Difficulty.EASY && easyGamesWon == 0) {
            easyGamesWon++;
            mediumUnlocked = true;

            audio.playSFX(audio.infoPopup, -10);
            game.displayUnlockedMedium();
            DelayMachine.delay(4);
            game.pressAnyKeyContinue();

            audio.playSFX(audio.infoPopup, -10);
            game.displayUnlockedJuggernautAndThief();
            DelayMachine.delay(4);
            game.pressAnyKeyContinue();
        }

        if (difficulty == Difficulty.MEDIUM && mediumGamesWon == 0) {
            mediumGamesWon++;
            hardUnlocked = true;

            audio.playSFX(audio.infoPopup, -10);
            game.displayUnlockedHard();
            DelayMachine.delay(4);
            game.pressAnyKeyContinue();

            audio.playSFX(audio.infoPopup, -10);
            game.displayUnlockedDoctorAndNinja();
            DelayMachine.delay(4);
            game.pressAnyKeyContinue();
        }

        if (difficulty == Difficulty.HARD && hardGamesWon == 0) {
            hardGamesWon++;

            audio.playSFX(audio.infoPopup, -10);
            game.displayUnlockedMage();
            DelayMachine.delay(4);
            game.pressAnyKeyContinue();

            game.displayHintStillMoreHeroes();
            DelayMachine.delay(4);
            game.pressAnyKeyContinue();
        }
        if (totalHeroSteps > 100) {
            audio.playSFX(audio.infoPopup, -10);
            game.displayUnlockedScientist();
            DelayMachine.delay(4);
            game.pressAnyKeyContinue();
        }
        if (totalMonstersDefeated > 100) {
            audio.playSFX(audio.infoPopup, -10);
            game.displayUnlockedBeastmaster();
            DelayMachine.delay(4);
            game.pressAnyKeyContinue();
        }
        if (allHeroesUnlocked()) {
            audio.playSFX(audio.infoPopup, -10);
            game.displayUnlockedAll();
            DelayMachine.delay(4);
            game.pressAnyKeyContinue();
        }
    }

    private static boolean allHeroesUnlocked() {
        for (Hero hero : heroes) {
            if (!hero.isUnlocked()) {
                return false;
            }
        }
        return true;
    }

    private static void changeHero() {
        // copy hero's inventory for the new hero
        ArrayList<Potion> currentBag = hero.getInventory().getItems();

        char heroSelection = game.chooseHero(heroes);
        hero = chooseHero(heroSelection);
        audio.playSFX(audio.menuOne, -10);

        // paste hero's details
        hero.getInventory().setItems(currentBag);
        hero.setName(heroFullName);
    }

    private static void newHero() {
        char heroSelection = game.chooseHero(heroes);
        hero = chooseHero(heroSelection);
        audio.playSFX(audio.menuOne, -10);
        hero.setName(heroFullName);
    }
    private static void selectDifficulty() {
        char difficultySelection = game.chooseDifficulty(mediumUnlocked, hardUnlocked, glitchUnlocked);

        if ((!mediumUnlocked && (difficultySelection == '2' || difficultySelection == '3'))
            || (!hardUnlocked && difficultySelection == '3')) {

            audio.playSFX(audio.error, -10);
            game.displayDifficultyLocked();
            selectDifficulty();
            return;
        }

        audio.playSFX(audio.menuTwo, 5);
        setupDungeon(difficultySelection);
        game.displayDifficultySelected(DungeonGame.difficulty);
    }
    private static void newHeroName() {
        System.out.println();
        heroFirstName = game.findHeroName();
        if (!startsWithLetter(heroFirstName) && !debugMode) {
            audio.playSFX(audio.error, -10);
            game.displayWrongInput();
            newHeroName();
            return;
        }
        audio.playSFX(audio.menuOne, -10);

        heroFirstName = capitalize(heroFirstName);
        heroFullName = giveRandomSuffix(heroFirstName); //TODO easter egg name like "Hiccup"
    }

    private static boolean startsWithLetter(final String heroFirstName) {
        boolean startsWithLetter = false;
        char firstLetter = heroFirstName.charAt(0);

        if ((firstLetter >= 'A' && firstLetter <= 'Z')
            || (firstLetter >= 'a' && firstLetter <= 'z')) {
            return true;
        }
        return startsWithLetter;
    }

    private static void cheatCodeMenu() { //TODO take out cheat code menu. make it hidden
        DelayMachine.delay(1);
        audio.playMusic(audio.hackerSong, true, -10);
        String code = game.cheatCodeMenu();
        if (code.equals(FUNNY_MODE_CODE)) {
            game.codeSuccessMsg();
            game.displayFunnyDialogueModeMsg();
            funnyMode = true;

        } else if (code.equals(DEBUG_MODE_CODE)) {
            game.codeSuccessMsg();
            game.displayDebugModeMsg();
            debugMode = true;

        } else if (code.equals(CHEAT_MODE_CODE)) {
            game.codeSuccessMsg();
            game.displayCheatModeMsg();
            cheatMode = true;

        } else {
            game.codeFailMsg();
        }
        DelayMachine.delay(4);
        audio.stopMusic();
    }

    private static void playAgain() {
        char choice = game.displayPlayAgainMenu();
        switch (choice) {
            case '1': // play again
                audio.playSFX(audio.menuOne, -10);
                DelayMachine.delay(2);
                audio.stopAll();
                game.sentToMainMenuMsg();
                DelayMachine.delay(6);
                mainMenu();
                break;
            case '2': // exit
                audio.playSFX(audio.menuTwo, -5);
                exitGame();
                DelayMachine.delay(2);
                System.exit(0);
                break;
            default:
                audio.playSFX(audio.error, -10);
                game.displayWrongInput();
                playAgain();
                break;
        }
    }

    private static void victory() {
        audio.stopAll();
        DelayMachine.delay(1);

        // play victory sound
        audio.playMusic(audio.triumpantFinishSong, true, -10);

        // play cutscene
        game.displayVictoryMsg(heroSteps, monstersDefeated, hero.getLevel(), difficulty, funnyMode, debugMode);

        if (!debugMode) {
            DelayMachine.delay(10);
        }

        game.pressAnyKeyContinue();
        audio.playSFX(audio.menuTwo, -5);
        audio.stopAll();

        // play credits if on hard mode
        if (difficulty == Difficulty.HARD || debugMode) {
            game.displayCredits(audio);
        }

        // update stats
        totalHeroSteps += heroSteps;

        DelayMachine.delay(2);

        // info & hint popups
        displayPopups();
        saveGame();
        gameOver = true;

        // main menu
        playAgain();
    }

}
