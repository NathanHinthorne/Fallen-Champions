package FallenChampions.controller;

import FallenChampions.model.dungeon.*;
import FallenChampions.model.potions.PotionDefensive;
import FallenChampions.model.potions.VisionPotion;
import FallenChampions.view.AudioManager;
import FallenChampions.view.TUI;
import FallenChampions.model.characters.heroes.Inventory;
import FallenChampions.model.characters.heroes.Hero;
import FallenChampions.model.characters.heroes.HeroFactory;
import FallenChampions.model.characters.heroes.HeroTypes;
import FallenChampions.model.characters.monsters.Monster;
import FallenChampions.model.potions.HealthPotion;
import FallenChampions.model.potions.Potion;
import org.sqlite.SQLiteDataSource;

import java.io.*;
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
public class GameTest {

    /**
     * The codes to type in to activate various modes
     */
    private static Map<String, Modes> codes;

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
    private static TUI tui; // from view

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
    private static AudioManager audio;

    /**
     * empty constructor
     */
    private GameTest() {

    }


    /**
     * The main method
     * @param theArgs Command line arguments
     */
    public static void main(String[] theArgs) {

        audio = AudioManager.getInstance();
        tui = new TUI(console);

        heroes = createHeroList();
        codes = createCodesMap();

        startMenu();
    }

    private static List<Hero> createHeroList() {
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
        return heroes;
    }

    private static Map<String, Modes> createCodesMap() {
        codes = new HashMap<>();
        codes.put("(cheat code)", Modes.CHEAT);
        codes.put("(debug code)", Modes.DEBUG);
        codes.put("(funny mode)", Modes.FUNNY);
        return codes;
    }


    private static void startMenu() {
        tui.menu(debugMode, audio);

        // continue or new game (1 for new game, 2 for continue game)
        char loadSelection = tui.continueOrNewGameMenu();
        switch(loadSelection) {

            case '1': // new game
                audio.playSFX(audio.menu1, 100);
                newGameSetup();

            case '2': // continue game
                audio.playSFX(audio.menu1, 100);
                loadGameSetup();
                break;

            default:
                audio.playSFX(audio.error, 100);
                tui.displayWrongInput();
                startMenu();
        }
    }

    /**
     * Setup the game based on the user's choice
     */
    private static void newGameSetup() {

        // print introduction
        System.out.println();
        char introSelection = tui.playIntroOrNot();
        if (introSelection != '1' && introSelection != '2') {
            audio.playSFX(audio.error, 100);
            tui.displayWrongInput();
            newGameSetup();
        }

        audio.playSFX(audio.menu1, 100);
        if (introSelection == '2') {
            tui.introductionP1(funnyMode, audio);
        }

        newHeroName();

        if (introSelection == '2') {
            tui.introductionP2(funnyMode, audio, heroFirstName, heroFullName);
            SleepDelay.delay(4);
        } else {
            System.out.println("【 " + heroFullName + " 】");
            tui.pressAnyKeyContinue();
            audio.playSFX(audio.menu2, 120);
        }

        // choose hero
        newHero();

        if (introSelection == '2') {
            tui.heroIntroduction(hero);
        }

        if (cheatMode) {
            cheatModeStuff();
        }

        // setup dungeon (1 for easy, 2 for medium, 3 for hard)
        selectDifficulty();

        SleepDelay.delay(1);

        if (!debugMode) {
            // start msg
            tui.displayStartMsg();

            // start music
            audio.playMusic(audio.startingAnewSong, false, -12);
            SleepDelay.delay(15);
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
        char choice = tui.mainMenu();
        switch (choice) {

            case '1': // continue
                audio.playSFX(audio.menu1, 100);
                hero.resetStats();
                selectDifficulty();
                gameOver = false;
                gameLoop();
                break;

            case '2': // change hero
                audio.playSFX(audio.menu1, 100);
                changeHero();
                tui.displayHeroSelected(hero);
                SleepDelay.delay(4);
                mainMenu();
                break;

            case '3': // change name
                audio.playSFX(audio.menu1, 100);
                newHeroName();
                hero.setName(heroFullName);
                tui.displayHeroNameChanged(hero);
                SleepDelay.delay(4);
                mainMenu();
                break;

            case '4': // cheat code menu
                audio.playSFX(audio.menu1, 100);
                cheatCodeMenu();
                SleepDelay.delay(4);
                mainMenu();
                break;

            case '6':   // save
                audio.playSFX(audio.menu1, 100);
                saveGame();
                SleepDelay.delay(4);
                mainMenu();
                break;

            case '7': // exit
                audio.playSFX(audio.menu1, 100);
                char uSure = tui.quitProcess();
                if (uSure == '1') exitGame();
                else mainMenu();
                break;

            default:
                audio.playSFX(audio.error, 100);
                tui.displayWrongInput();
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
            ds.setUrl("jdbc:sqlite:" + GameTest.class.getResource("/Hero_Names.db").getPath()); // hopefully this works instead
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
                audio.playSFX(audio.error, 100);
                tui.displayWrongInput();
                char heroSelection = tui.chooseHero(heroes);
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
                audio.playSFX(audio.error, 100);
                tui.displayWrongInput();
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
                tui.displayWallMsg();
            }

            if (dungeon.heroIsTouchingPillar() && !cheatMode) {
                // play ding sound
                audio.playSFX(audio.heroCollectPillar, 100);

                Pillars pillar = dungeon.getPillar();
                hero.getInventory().addPillar(pillar);
                switch (pillar) {
                    case ABSTRACTION:
                        tui.displayAbstractionPillarMsg();
                        break;
                    case ENCAPSULATION:
                        tui.displayEncapsulationPillarMsg();
                        break;
                    case INHERITANCE:
                        tui.displayInheritancePillarMsg();
                        break;
                    case POLYMORPHISM:
                        tui.displayPolymorphismPillarMsg();
                        break;
                }

                if (hero.getInventory().hasAllPillars()) {
                    exitIsOpen = true;
                    tui.exitIsOpenMsg();
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
                    audio.playSFX(audio.heroBagFull, 100);
                    tui.displayInventoryFullMsg();
                } else {
                    audio.playSFX(audio.heroCollectPotion, 110);
                    tui.collectPotionMsg(potion);
                    hero.getInventory().addToItems(potion);
                    audio.playSFX(audio.menu2, 120);
                    dungeon.removePotion();
                }
            }

            if (dungeon.heroIsTouchingPit()) {
                // play pit sound

                Pit pit = dungeon.getPit();

                int fallDamage = pit.fall(hero);
                tui.displayPitMsg(fallDamage);

                if (hero.getHealth() <= 0) {
                    // play death sound
                    if (funnyMode) {
                        audio.playSFX(audio.heroOof, 100);
                    } else {
                        audio.playSFX(audio.heroDefeat, 100);
                    }

                    tui.displayDeathMsg(funnyMode);
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
                audio.playSFX(audio.encounter, 100);

                // play monster encounter cutscene
                tui.displayMonsterEncounterMsg(monster);

                SleepDelay.delay(1); // delay for 0.5 seconds

                MonsterBattle battle = new MonsterBattle(hero, monster, tui, cheatMode, audio, funnyMode, debugMode);
                BattleResult winnerWinnerChickenDinner = battle.newBattle();

                if (winnerWinnerChickenDinner == BattleResult.WIN) {
                    // play victory sound
                    audio.playSFX(audio.heroWinBattle, 100);

                    // earn rewards
                    hero.gainXP(monster.getXPWorth());

                    // win cutscene
                    tui.displayBattleWinMsg(monster);
                    checkIfLevelUp(); // display level-up message if applicable
                    tui.displayBattleEnd();

                    dungeon.removeMonster();
                    monstersDefeated++;

                } else if (winnerWinnerChickenDinner == BattleResult.RUN) {
                    tui.displayBattleEnd();

                    // kick hero to a nearby room
                    dungeon.moveHeroToRandomRoom();
                    
                } else {
                    // play defeat sound
                    audio.playSFX(audio.heroDefeat, 100);

                    // defeat cutscene
                    tui.displayDeathMsg(funnyMode);

                    gameOver = true;
                    break;
                }
                SleepDelay.delay(8); // delay for 4 seconds
                displayDungeonScreen(); // only redisplay the dungeon screen, don't go back to the start of the loop
                audio.playMusic(audio.ambientSong, true, -10);

            }

            if (dungeon.heroIsTouchingExit()) {
                if (!exitIsOpen) {
                    audio.playSFX(audio.lockedDoor, 100);
                    tui.exitLocked();

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
            int gameMenuSelection = tui.gameplayMenu();
            boolean hasMoved = false;
            switch(gameMenuSelection) {
                case 's':
                    hasMoved = dungeon.playerMove(Direction.SOUTH);
                    if (hasMoved) {
                        heroSteps++;
                        audio.playSFX(audio.step4, 100);
                    }
                    else { tui.displayHitWallMsg(); }
                    hero.setDirection(Direction.SOUTH);
                    break;

                case 'a':
                    hasMoved = dungeon.playerMove(Direction.WEST);
                    if (hasMoved) {
                        heroSteps++;
                        audio.playSFX(audio.step4, 100);
                    }
                    else { tui.displayHitWallMsg(); }
                    hero.setDirection(Direction.WEST);
                    break;

                case 'd':
                    hasMoved = dungeon.playerMove(Direction.EAST);
                    if (hasMoved) {
                        heroSteps++;
                        audio.playSFX(audio.step4, 100);
                    }
                    else { tui.displayHitWallMsg(); }
                    hero.setDirection(Direction.EAST);
                    break;

                case 'w':
                    hasMoved = dungeon.playerMove(Direction.NORTH);
                    if (hasMoved) {
                        heroSteps++;
                        audio.playSFX(audio.step4, 100);
                    }
                    else { tui.displayHitWallMsg(); }
                    hero.setDirection(Direction.NORTH);
                    break;

                case '1': // hero info
                    audio.playSFX(audio.swishOn, 100);
                    tui.displayHeroStats(hero);
                    tui.pressAnyKeyGoBack();
                    audio.playSFX(audio.swishOff, 80);
                    break;

                case '2':
                    audio.playSFX(audio.swishOn, 100);
                    tui.displayInstructions(audio);
                    tui.pressAnyKeyGoBack();
                    audio.playSFX(audio.swishOff, 80);
                    break;

                case 'e': // open bag
                    inventoryMenu();

                    break;

                case '4': // main menu
                    char quit = tui.goToMainMenu();
                    if (quit == '1') {
                        audio.playSFX(audio.menu2, 120);
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
                    tui.displayArrowSpacer();
                    dungeon.roomAbove();
                    tui.displayArrowSpacer();
                    break;

                case 'l':
                    tui.displayArrowSpacer();
                    dungeon.roomRight();
                    tui.displayArrowSpacer();
                    break;

                case 'k':
                    tui.displayArrowSpacer();
                    dungeon.roomBelow();
                    tui.displayArrowSpacer();
                    break;

                case 'j':
                    tui.displayArrowSpacer();
                    dungeon.roomLeft();
                    tui.displayArrowSpacer();
                    break;

                case 'p':
                    hero.setHealth(hero.getHealth()/2);
                    System.out.println("DEBUG: Decreased Health..." + hero.getHealth());
                    break;

                default:
                    audio.playSFX(audio.error, 100);
                    tui.displayWrongInput();
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
            tui.displayGameSaved();
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
            tui.displayGameLoaded();
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
            tui.levelUpMsg(hero.getLevel());
            audio.playSFX(audio.heroLevelUp, -15);
            SleepDelay.delay(2);
        }
    }

    private static void inventoryMenu() {
        Inventory bag = hero.getInventory();
        audio.playSFX(audio.heroBagOpen, -10);
        char input = tui.openBag(bag, false, audio); // input is guaranteed to be 1-4 or e

        if (input != 'e') {
            int slotIndex = Character.getNumericValue(input)-1; // convert input to int (with -1 due to array indexing)

            Potion potion = bag.getItem(slotIndex);
            if (potion.canUseOutsideBattle()) {
                PotionDefensive defPotion = (PotionDefensive) potion;
                defPotion.effect(hero);
                bag.removeItem(slotIndex);
            } else {
                audio.playSFX(audio.error, -10);
                tui.displayCantUseItemOutsideBattle(potion);
                inventoryMenu();
            }

            audio.playSFX(audio.heroDrinkPotion, -10);
            tui.usePotionMsg(potion, slotIndex);
        }
        tui.closeBag(audio);
    }

    private static void displayDungeonScreen() {
        tui.displayDungeonMap(dungeon);
        tui.displayHealthInDungeon(hero);
//        game.displayHeroHealthCool(hero); for healthbars
        tui.displayHeroDirection(hero);

        if (hero.usingVisionPotion()) {
            tui.displayStepsWithVisionPotion(stepsWithActiveVisionPotion);
        }
        if (hero.hasMazeAbility() && hero.mazeAbilityActivated()) { // for archer's ranged attack, etc.
            tui.mazeAbiltyText(hero);
        }
    }

    private static void exitGame() {
        audio.playSFX(audio.menu2, -5);
        SleepDelay.delay(2);
        tui.displayCyaNerd(funnyMode);
        audio.playSFX(audio.heroOof, -5);
        SleepDelay.delay(2);
        System.exit(0);
    }

    private static void displayPopups() {

        if (difficulty == Difficulty.EASY && easyGamesWon == 0) {
            easyGamesWon++;
            mediumUnlocked = true;

            audio.playSFX(audio.infoPopup, -10);
            tui.displayUnlockedMedium();
            SleepDelay.delay(4);
            tui.pressAnyKeyContinue();

            audio.playSFX(audio.infoPopup, -10);
            tui.displayUnlockedJuggernautAndThief();
            SleepDelay.delay(4);
            tui.pressAnyKeyContinue();
        }

        if (difficulty == Difficulty.MEDIUM && mediumGamesWon == 0) {
            mediumGamesWon++;
            hardUnlocked = true;

            audio.playSFX(audio.infoPopup, -10);
            tui.displayUnlockedHard();
            SleepDelay.delay(4);
            tui.pressAnyKeyContinue();

            audio.playSFX(audio.infoPopup, -10);
            tui.displayUnlockedDoctorAndNinja();
            SleepDelay.delay(4);
            tui.pressAnyKeyContinue();
        }

        if (difficulty == Difficulty.HARD && hardGamesWon == 0) {
            hardGamesWon++;

            audio.playSFX(audio.infoPopup, -10);
            tui.displayUnlockedMage();
            SleepDelay.delay(4);
            tui.pressAnyKeyContinue();

            tui.displayHintStillMoreHeroes();
            SleepDelay.delay(4);
            tui.pressAnyKeyContinue();
        }
        if (totalHeroSteps > 100) {
            audio.playSFX(audio.infoPopup, -10);
            tui.displayUnlockedScientist();
            SleepDelay.delay(4);
            tui.pressAnyKeyContinue();
        }
        if (totalMonstersDefeated > 100) {
            audio.playSFX(audio.infoPopup, -10);
            tui.displayUnlockedBeastmaster();
            SleepDelay.delay(4);
            tui.pressAnyKeyContinue();
        }
        if (allHeroesUnlocked()) {
            audio.playSFX(audio.infoPopup, -10);
            tui.displayUnlockedAll();
            SleepDelay.delay(4);
            tui.pressAnyKeyContinue();
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

        char heroSelection = tui.chooseHero(heroes);
        hero = chooseHero(heroSelection);
        audio.playSFX(audio.menu1, -10);

        // paste hero's details
        hero.getInventory().setItems(currentBag);
        hero.setName(heroFullName);
    }

    private static void newHero() {
        char heroSelection = tui.chooseHero(heroes);
        hero = chooseHero(heroSelection);
        audio.playSFX(audio.menu1, -10);
        hero.setName(heroFullName);
    }
    private static void selectDifficulty() {
        char difficultySelection = tui.chooseDifficulty(mediumUnlocked, hardUnlocked, glitchUnlocked);

        if ((!mediumUnlocked && (difficultySelection == '2' || difficultySelection == '3'))
            || (!hardUnlocked && difficultySelection == '3')) {

            audio.playSFX(audio.error, -10);
            tui.displayDifficultyLocked();
            selectDifficulty();
            return;
        }

        audio.playSFX(audio.menu2, 5);
        setupDungeon(difficultySelection);
        tui.displayDifficultySelected(difficulty);
    }
    private static void newHeroName() {
        System.out.println();
        heroFirstName = tui.findHeroName();
        if (!startsWithLetter(heroFirstName) && !debugMode) {
            audio.playSFX(audio.error, -10);
            tui.displayWrongInput();
            newHeroName();
            return;
        }
        audio.playSFX(audio.menu1, -10);

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
        SleepDelay.delay(1);
        audio.playMusic(audio.hackerSong, true, -10);
        String code = tui.cheatCodeMenu();

        for (String key : codes.keySet()) {
            if (code.equals(key)) {
                Modes mode = codes.get(key);
                switch (mode) {
                    case CHEAT:
                        cheatMode = true;
                        tui.codeSuccessMsg();
                        tui.displayCheatMode();
                        break;
                    case DEBUG:
                        debugMode = true;
                        tui.codeSuccessMsg();
                        tui.displayDebugMode();
                        break;
                    case FUNNY:
                        funnyMode = true;
                        tui.codeSuccessMsg();
                        tui.displayFunnyMode();
                        break;
                    default:
                        audio.playSFX(audio.error, -10);
                        tui.displayWrongInput();
                        cheatCodeMenu();
                        break;
                }
            }
        }
        SleepDelay.delay(4);
        audio.stopMusic();
    }

    private static void playAgain() {
        char choice = tui.displayPlayAgainMenu();
        switch (choice) {
            case '1': // play again
                audio.playSFX(audio.menu1, -10);
                SleepDelay.delay(2);
                audio.stopAll();
                tui.sentToMainMenuMsg();
                SleepDelay.delay(6);
                mainMenu();
                break;
            case '2': // exit
                audio.playSFX(audio.menu2, -5);
                exitGame();
                SleepDelay.delay(2);
                System.exit(0);
                break;
            default:
                audio.playSFX(audio.error, -10);
                tui.displayWrongInput();
                playAgain();
                break;
        }
    }

    private static void victory() {
        audio.stopAll();
        SleepDelay.delay(1);

        // play victory sound
        audio.playMusic(audio.triumpantFinishSong, true, -10);

        // play cutscene
        tui.displayVictoryMsg(heroSteps, monstersDefeated, hero.getLevel(), difficulty, funnyMode, debugMode);

        if (!debugMode) {
            SleepDelay.delay(10);
        }

        tui.pressAnyKeyContinue();
        audio.playSFX(audio.menu2, -5);
        audio.stopAll();

        // play credits if on hard mode
        if (difficulty == Difficulty.HARD) {
            tui.displayCredits(audio);
        }

        // update stats
        totalHeroSteps += heroSteps;

        SleepDelay.delay(2);

        // info & hint popups
        displayPopups();
        saveGame();
        gameOver = true;

        // main menu
        playAgain();
    }

}
