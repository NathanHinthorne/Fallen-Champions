package FallenChampions.view;
import FallenChampions.controller.Delay;
import FallenChampions.controller.TextSpeed;
import FallenChampions.model.characters.Ability;
import FallenChampions.model.characters.Debuff;
import FallenChampions.model.characters.DungeonCharacter;
import FallenChampions.model.characters.boss.Missile;
import FallenChampions.model.dungeon.Difficulty;
import FallenChampions.model.characters.heroes.Inventory;
import FallenChampions.model.dungeon.Dungeon;
import FallenChampions.model.characters.heroes.Hero;
import FallenChampions.model.characters.monsters.Monster;
import FallenChampions.model.dungeon.Parchment;
import FallenChampions.model.potions.Potion;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.util.Duration;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;

/**
 * TUI mode of the game
 *
 * @author Austin Roaf
 * @author Brendan Smith
 * @author Nathan Hinthorne
 * @version 1.0
 */
public class TUI {

    /**
     * The TUI object that sends and receives information from the user.
     */
    private static TUI instance;

    /**
     * The console object that the user will interact with.
     */
    private static Console console;

    /**
     * The audio manager object that will play sfx and music
     */
    private static AudioManager audio;

    private State currentState; // Variable to track the current state

    private TUI() {
        audio = AudioManager.getInstance();
        console = Console.getInstance();
    }

    public static TUI getInstance() {
        if (instance == null) {
            instance = new TUI();
        }
        return instance;
    }

    /**
     * Utilizes async operations to get user input
     * @return The user's char input
     */
    private CompletableFuture<Character> futureCharInput() { // review these concepts later
//        console.disableInput(false); // re-enable input box after output is finished displaying

        CompletableFuture<Character> userInputFuture = new CompletableFuture<>();

        // Set the onMessageReceivedHandler to capture user input
        console.setOnMessageReceivedHandler(userInput -> {
            userInputFuture.complete(userInput.charAt(0));
        });

        // Return the CompletableFuture to await user input asynchronously
        return userInputFuture;
    }

    private CompletableFuture<Character> futureCharInputWithinTimeLimit(final int theTimeLimit) { // used autocomplete to make this. review it.

        CompletableFuture<Character> userInputFuture = new CompletableFuture<>();

        // Set the onMessageReceivedHandler to capture user input
        console.setOnMessageReceivedHandler(userInput -> {
            userInputFuture.complete(userInput.charAt(0));
        });

        // Create a CompletableFuture to signal completion
        CompletableFuture<Void> completionIndicator = new CompletableFuture<>();

        // Complete the CompletableFuture
        Delay.pause(theTimeLimit).join();
        completionIndicator.complete(null);

        // Return the CompletableFuture to await user input asynchronously
        return userInputFuture;
    }

    /**
     * Utilizes async operations to get user input
     * @return The user's string input
     */
    private CompletableFuture<String> futureStringInput() { // review these concepts later
//        console.disableInput(false); // re-enable input box after output is finished displaying

        CompletableFuture<String> userInputFuture = new CompletableFuture<>();

        // Set the onMessageReceivedHandler to capture user input
        console.setOnMessageReceivedHandler(userInput -> {
            // seems like userInput should be a consumer, not a string itself
            // seems like lambda is creating that string
            // when are we checking if a string has been accepted to the consumer (thus knowing if text has been entered by user)?
            // why does userInput represent the text the user entered?

            userInputFuture.complete(userInput);
        });

        // Return the CompletableFuture to await user input asynchronously
        return userInputFuture;
    }

//    public void onUserInputReceived(String userInput) { // won't use this approach, but should keep track of state for other methods
//        switch (currentState) {
//            case START_MENU:
//                handleStartMenuInput(userInput);
//                break;
//            case MAIN_MENU:
//                handleMainMenuInput(userInput);
//                break;
//            case DUNGEON_MENU:
//                handleGamePlayInput(userInput);
//                break;
//        }
//    }

    /**
     * Asks whether you want to start a new game or continue on a prior save
     * @return the game choice
     */
    public CompletableFuture<Character> continueOrNewGameMenu() {

        console.println("Would you like to start a new game or continue a previous game?");
        console.println("1 for new game, 2 for continue game");
        console.print("Make your selection: ");

        console.setHintText("Type here...");

        return futureCharInput();
    }


    /**
     * Displays the title
     */
    public CompletableFuture<Void> title() {

        console.disableInput(true); // disable input box while output is currently being displayed

        SequentialTransition sequentialTransition = new SequentialTransition();

        console.println("Welcome to...");
        console.println();

//        Delay.delayAndExecute(2);
        PauseTransition frame1 = new PauseTransition(Duration.seconds(2));
        frame1.setOnFinished(event -> {
            //TODO play sfx...
            console.println("" +
                    "                     ▄████████    ▄████████  ▄█        ▄█          ▄████████ ███▄▄▄▄   \n" +
                    "                    ███    ███   ███    ███ ███       ███         ███    ███ ███▀▀▀██▄ \n" +
                    "                    ███    █▀    ███    ███ ███       ███         ███    █▀  ███   ███ \n" +
                    "                   ▄███▄▄▄       ███    ███ ███       ███        ▄███▄▄▄     ███   ███ \n" +
                    "                  ▀▀███▀▀▀     ▀███████████ ███       ███       ▀▀███▀▀▀     ███   ███ \n" +
                    "                    ███          ███    ███ ███       ███         ███    █▄  ███   ███ \n" +
                    "                    ███          ███    ███ ███▌    ▄ ███▌    ▄   ███    ███ ███   ███ \n" +
                    "                    ███          ███    █▀  █████▄▄██ █████▄▄██   ██████████  ▀█   █▀  \n" +
                    "                                             ▀         ▀                                \n");
        });

//        Delay.delayAndExecute(0.5);
        PauseTransition frame2 = new PauseTransition(Duration.seconds(0.5));
        frame2.setOnFinished(event -> {
            console.println("" +
                    "  ▄████████    ▄█    █▄       ▄████████   ▄▄▄▄███▄▄▄▄      ▄███████▄  ▄█   ▄██████▄  ███▄▄▄▄      ▄████████ \n" +
                    " ███    ███   ███    ███     ███    ███ ▄██▀▀▀███▀▀▀██▄   ███    ███ ███  ███    ███ ███▀▀▀██▄   ███    ███ \n" +
                    " ███    █▀    ███    ███     ███    ███ ███   ███   ███   ███    ███ ███▌ ███    ███ ███   ███   ███    █▀  \n" +
                    " ███         ▄███▄▄▄▄███▄▄   ███    ███ ███   ███   ███   ███    ███ ███▌ ███    ███ ███   ███   ███        \n" +
                    " ███        ▀▀███▀▀▀▀███▀  ▀███████████ ███   ███   ███ ▀█████████▀  ███▌ ███    ███ ███   ███ ▀███████████ \n" +
                    " ███    █▄    ███    ███     ███    ███ ███   ███   ███   ███        ███  ███    ███ ███   ███          ███ \n" +
                    " ███    ███   ███    ███     ███    ███ ███   ███   ███   ███        ███  ███    ███ ███   ███    ▄█    ███ \n" +
                    " ████████▀    ███    █▀      ███    █▀   ▀█   ███   █▀   ▄████▀      █▀    ▀██████▀   ▀█   █▀   ▄████████▀  \n\n");
        });

//        Delay.delayAndExecute(1.5);
        PauseTransition frame3 = new PauseTransition(Duration.seconds(1.5));
        frame3.setOnFinished(event -> {
            console.println("IMPORTANT INFORMATION! \n" +
                            " --Since this game is console-based, you will be using the keyboard to play. \n" +
                            " --You'll notice that the screen \"updates\" by printing new information on top of old information. \n" +
                            " --Any input you give must be followed by the ENTER key. \n\n");
        });

        // Add the transitions to the sequential transition in the desired order
        sequentialTransition.getChildren().addAll(frame1, frame2, frame3);

        // Create a CompletableFuture to signal completion
        CompletableFuture<Void> completionIndicator = new CompletableFuture<>();

        sequentialTransition.setOnFinished(event -> {
            // Complete the CompletableFuture when the sequentialTransition finishes
            completionIndicator.complete(null);

            // Re-enable input box after output is finished displaying
            console.disableInput(false);
        });

        // Start playing the sequentialTransition
        sequentialTransition.play();

        // Return the CompletableFuture to the caller
        return completionIndicator;
    }

    /**
     * The game start message
     */
    public CompletableFuture<Void> displayStartMsg() {
        console.disableInput(true);

        console.println("                     ╔═════════════════════════╗                     ");
        console.println("---------------------║ Welcome to the Dungeon! ║---------------------");
        console.println("                     ╚═════════════════════════╝                     ");

        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(8));

        // Create a CompletableFuture to signal completion
        CompletableFuture<Void> completionIndicator = new CompletableFuture<>();

        pauseTransition.setOnFinished(event -> {
            // Complete the CompletableFuture when the sequentialTransition finishes
            completionIndicator.complete(null);

            // Re-enable input box after output is finished displaying
            console.disableInput(false);
        });

        // Start playing the sequentialTransition
        pauseTransition.play();

        // Return the CompletableFuture to the caller
        return completionIndicator;
    }

    /**
     * Chooses the hero
     * @return the hero choice
     */
    public CompletableFuture<Character> chooseHeroMsg(final List<Hero> theHeroes, final boolean theDebugMode) {
        console.disableInput(true); // disable input box while output is currently being displayed

        console.println("Choose your hero!");
        if (!theDebugMode) {
            Delay.pause(2).join();
        }
        displayUpperSpacer();

        int menuOption = 1;
        for (Hero hero : theHeroes) {
            if (!theDebugMode) {
                Delay.pause(0.25).join();
            }
            if (hero.isUnlocked()) {
                console.println("  " + menuOption + " for " + hero.getType() + ":");
                console.println("  " + hero.getDescription()[0]);
                console.println("  " + hero.getDescription()[1]);
            } else {
                console.println("  " + menuOption + "  --(LOCKED)--  ");
            }
            if (menuOption != theHeroes.size()) { // leave out a spacer for the last hero
                console.println();
            }
            menuOption++;
        }

        displayLowerSpacer();
        console.print("Make your selection: ");

        console.disableInput(false); // re-enable input box after output is finished displaying

        return futureCharInput();
    }

    private void displayCastle() {
        List<String> castle = new ArrayList<>();

        castle.add("                                                                    !_");
        castle.add("                                                                    |*~=-.,");
        castle.add("                                                                    |_,-'`");
        castle.add("                                                                    |");
        castle.add("                                                                    |");
        castle.add("                                                                   /^\\");
        castle.add("                                     !_                           /   \\");
        castle.add("                                     |*`~-.,                     /,    \\");
        castle.add("                                     |.-~^`                     /#\"     \\");
        castle.add("                                     |                        _/##_   _  \\_");
        castle.add("                                _   _|  _   _   _            [ ]_[ ]_[ ]_[ ]");
        castle.add("                               [ ]_[ ]_[ ]_[ ]_[ ]            |_=_-=_ - =_|");
        castle.add("                             !_ |_=_ =-_-_  = =_|           !_ |=_= -    |");
        castle.add("                             |*`--,_- _        |            |*`~-.,= []  |");
        castle.add("                             |.-'|=     []     |   !_       |_.-\"`_-     |");
        castle.add("                             |   |_=- -        |   |*`~-.,  |  |=_-      |");
        castle.add("                            /^\\  |=_= -        |   |_,-~`  /^\\ |_ - =[]  |");
        castle.add("                        _  /   \\_|_=- _   _   _|  _|  _   /   \\|=_-      |");
        castle.add("                       [ ]/,    \\[ ]_[ ]_[ ]_[ ]_[ ]_[ ]_/,    \\[ ]=-    |");
        castle.add("                        |/#\"     \\_=-___=__=__- =-_ -=_ /#\"     \\| _ []  |");
        castle.add("                       _/##_   _  \\_-_ =  _____       _/##_   _  \\_ -    |\\");
        castle.add("                      [ ]_[ ]_[ ]_[ ]=_0~{_ _ _}~0   [ ]_[ ]_[ ]_[ ]=-   | \\");
        castle.add("                      |_=__-_=-_  =_|-=_ |  ,  |     |_=-___-_ =-__|_    |  \\");
        castle.add("                       | _- =-     |-_   | ((* |      |= _=       | -    |___\\");
        castle.add("                       |= -_=      |=  _ |  `  |      |_-=_       |=_    |/+\\|");
        castle.add("                       | =_  -     |_ = _ `-.-`       | =_ = =    |=_-   ||+||");
        castle.add("                       |-_=- _     |=_   =            |=_= -_     |  =   ||+||");
        castle.add("                       |=_- /+\\    | -=               |_=- /+\\    |=_    |^^^|");
        castle.add("                       |=_ |+|+|   |= -  -_,--,_      |_= |+|+|   |  -_  |=  |");
        castle.add("                       |  -|+|+|   |-_=  / |  | \\     |=_ |+|+|   |-=_   |_-/");
        castle.add("                       |=_=|+|+|   | =_= | |  | |     |_- |+|+|   |_ =   |=/");
        castle.add("                       | _ ^^^^^   |= -  | |  <&>     |=_=^^^^^   |_=-   |/");
        castle.add("                       |=_ =       | =_-_| |  | |     |   =_      | -_   |");
        castle.add("                       |_=-_       |=_=  | |  | |     |=_=        |=-    |");
        castle.add("                  ^^^^^^^^^^`^`^^`^`^`^^^\"\"\"\"\"\"\"\"^`^^``^^`^^`^^`^`^``^`^``^``^^");

        console.lineAnimation(castle, 0.1f).join();
        console.println();
    }

    /**
     * Introduces user to the game
     */
    public CompletableFuture<Void> introductionP1(final boolean theFunnyMode, final boolean theDebugMode) {
        console.disableInput(true); // disable input box while output is currently being displayed

        SequentialTransition sequentialTransition = new SequentialTransition();

        console.println("------------------------------------------------INTRODUCTION------------------------------------------------\n");
        displayCastle();
        Delay.pause(1).join();
        console.print("Dungeon Keeper: ");
        console.typeAnimation("Welcome hero,\n", TextSpeed.MEDIUM).join();
        console.print("                ");
        console.typeAnimation("The dungeon you see looming before you is one of many perils.\n", TextSpeed.MEDIUM).join();
        console.print("                ");
        console.typeAnimation("In it lies hordes of monsters, deadly traps, and countless treasures.\n", TextSpeed.MEDIUM).join();
        console.print("                ");
        console.typeAnimation("Find the 4 ancient pillars, and you shall be granted access to the final chamber.\n", TextSpeed.MEDIUM).join();
        console.print("                ");
        console.typeAnimation("Not even I, the master of this dungeon, know what awaits you there.\n", TextSpeed.MEDIUM).join();

        Delay.pause(1).join();
        console.println();
        console.print("Dungeon Keeper: ");
        console.typeAnimation("Now then, what is your name?\n", TextSpeed.MEDIUM).join();

        Delay.pause(0.5).join(); // buffer for next message

        // Re-enable input box after output is finished displaying
        console.disableInput(false);

        // Create a CompletableFuture to signal completion
        CompletableFuture<Void> completionIndicator = new CompletableFuture<>();

        // Complete the CompletableFuture
        completionIndicator.complete(null);
        return completionIndicator;
    }


    /**
     * Gets the user's name
     * @return the user's name
     */
    public CompletableFuture<String> findHeroName() {
        console.print("Name (one word): ");

        return futureStringInput();
    }

    /**
     * Introduces user to the game
     * @param theFunnyMode whether the funny mode is on
     * @param theFirstName the user's first name
     * @param theFullName the user's first name and extension
     */
    public CompletableFuture<Void> introductionP2(final boolean theFunnyMode, final String theFirstName, final String theFullName) {
        displayChainSpacer();

        console.disableInput(true); // disable input box while output is currently being displayed

        console.println();

        if (theFunnyMode) {
            console.print("Dungeon Keeper: ");
            console.typeAnimation("Wait, are you serious? Your name is just " + theFirstName + "?\n", TextSpeed.FAST).join();
            Delay.pause(1).join();

            console.print("                ");
            console.typeAnimation("That's far too ordinary -_-\n", TextSpeed.FAST).join();
            Delay.pause(2).join();

            console.print("                ");
            console.typeAnimation("I dub thee...", TextSpeed.MEDIUM).join();
            Delay.pause(1).join();

//            console.printAnimation("【 Sir " + theFullName + "! 】\n", TextSpeed.SLOW);
            console.typeAnimation(" ◄{ Sir " + theFullName + "! }► \n\n", TextSpeed.SLOW).join();
            Delay.pause(1).join();

        } else {
            Delay.pause(1).join();
            console.print("Dungeon Keeper: ");
            console.typeAnimation("Ah, " + theFirstName + ", a fine name indeed.\n", TextSpeed.MEDIUM).join();

            Delay.pause(1).join();
            console.print("                ");
            console.typeAnimation("However, ◄{ " + theFullName + " }► is befitting of one such as yourself.\n\n", TextSpeed.MEDIUM).join();
            Delay.pause(1).join();
        }

        console.print("Dungeon Keeper: ");
        console.typeAnimation("Now then, " + theFullName + ", I hope you're ready to begin your adventure.\n", TextSpeed.MEDIUM).join();
        Delay.pause(1).join();

        console.print("                ");
        console.typeAnimation("Don't think you're the first to explore this dungeon.\n", TextSpeed.MEDIUM).join();
        console.print("                ");
        console.typeAnimation("Many others have come before you... Not one has made it out alive.\n", TextSpeed.MEDIUM).join();
        Delay.pause(2).join();

        console.print("                ");
        console.typeAnimation("Do you think you have what it takes to overcome this challenge?\n", TextSpeed.MEDIUM).join();
        Delay.pause(1).join();

        console.print("                ");
        console.typeAnimation("Or will you become yet another ", TextSpeed.MEDIUM).join();
        console.typeAnimation("FALLEN CHAMPION?\n", TextSpeed.SLOW).join();
        Delay.pause(1).join();

        if (theFunnyMode) {
            audio.playSFX(audio.rimshot, 0.8);
            Delay.pause(6).join();

            console.print("\nDungeon Keeper: ");
            console.typeAnimation("Anyway, choose one of these I guess\n", TextSpeed.MEDIUM).join();

        } else {
            audio.playSFX(audio.dunDunDun, 0.8);
            Delay.pause(6).join();

            console.print("\nDungeon Keeper: ");
            console.typeAnimation("So " + theFullName + ", what kind of adventurer are you anyway?\n", TextSpeed.MEDIUM).join();
        }

        Delay.pause(1.5).join(); // buffer for next message

        // Re-enable input box after output is finished displaying
        console.disableInput(false);

        // Create a CompletableFuture to signal completion
        CompletableFuture<Void> completionIndicator = new CompletableFuture<>();

        // Complete the CompletableFuture
        completionIndicator.complete(null);
        return completionIndicator;
    }


    /**
     * Dungeon menu
     * @return user's choice from the dungeon menu
     */
    public CompletableFuture<Character> gameplayMenu() {
        console.println();
        console.println("╔══════════════════════════════════════════════════════════════════╗");
        console.println("╠ 'w' move North, 'd' move East, 's' move South, 'a' move West     ╣");
        console.println("╠ 'e' bag, '1' hero info, '2' HELP, '4' main menu, '5' save game   ╣");
        console.println("╚══════════════════════════════════════════════════════════════════╝");
        console.println();
        console.print("Make your selection: ");

        return futureCharInput();
    }

    /**
     * Displays a line spacer
     */
    public void displayNormalSpacer() {
        console.println("---------------------------------------------------------------------");
    }

    /**
     * Displays a chain spacer
     */
    public void displayChainSpacer() {
//        console.println("<=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=>");
        console.println("<=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=->");
    }

    /**
     * Displays a top bar spacer
     */
    public void displayUpperSpacer() {
//        console.println("╔═══════════════════════════════════════════════════════════════════╗");
        console.println("╔══════════════════════════════════════════════════════════════════════════════════════╗");
    }

    /**
     * Displays a bottom bar spacer
     */
    public void displayLowerSpacer() {
//        console.println("╚═══════════════════════════════════════════════════════════════════╝");
        console.println("╚══════════════════════════════════════════════════════════════════════════════════════╝");
    }


    /**
     * Battle menu for battling monsters
     * @param theHero the hero the user is playing as
     * @return user's battle menu choice
     */
    public CompletableFuture<Character> battleMenu(final Hero theHero) {

        String specialButton = " ['2' - Special] ";
        if (theHero.onCooldown() || theHero.hasDebuff(Debuff.SILENCE)) {
            specialButton = " ║'2' - Special║ ";
        }

        console.println("               ┏━━━━ ( Battle Menu ) ━━━━┓");
        console.println(" ┏━━━━━━━━━━━━━┛                         ┗━━━━━━━━━━━━━┓");
        console.println(" ┃         ['1' - Basic]    " + specialButton + "          ┃");
        console.println(" ┃ ['e' - Bag]  ['r' - Monster Info]  ['q' - Run Away] ┃");
        console.println(" ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
        console.println();
        console.print(" Choose an action: ");

//        console.disableInput(false); // re-enable input box after output is finished displaying
        return futureCharInput();
    }

    /**
     * Displays hero and monster info for a battle
     * @param theHero the hero
     * @param theMonster the monster
     */
    public void displayHeroAndMonsterInfo(final Hero theHero, final Monster theMonster) {
        // hero info
        console.println();
        console.println(" ║ Hero:");
        displayHealth(theHero);
        if (!theHero.getActiveDebuffs().isEmpty()) {
            displayDebuffs(theHero);
        } else {
            console.println();
        }

        // monster info
        console.println();
        console.println(" ║ Monster:");
        displayHealth(theMonster);
        if (!theMonster.getActiveDebuffs().isEmpty()) {
            displayDebuffs(theMonster);
        } else {
            console.println();
        }
        console.println();
        console.println();
    }

    /**
     * Displays the character health
     * @param theCharacter the character's health to display
     */
    public void displayHealth(final DungeonCharacter theCharacter) {

        console.print(" ║    HEALTH: " + theCharacter.getHealth() + "/" + theCharacter.getMaxHealth()); // ❤
    }

    /**
     * Displays the character's debuffs
     * @param theCharacter the character's debuffs to display
     */
    public void displayDebuffs(final DungeonCharacter theCharacter) {
        console.println();
        console.print(" ║    STATUS:"); // ✺
        for (Debuff debuff : theCharacter.getActiveDebuffs().keySet()) {
            console.print(" (" + debuff + ")");
        }
        console.println();
    }

    /**
     * Displays the character's health in the dungeon
     * @param theCharacter the character's health to display
     */
    public void displayHealthInDungeon(final DungeonCharacter theCharacter) {

        console.print("  HEALTH: " + theCharacter.getHealth() + "/" + theCharacter.getMaxHealth());
    }

    /**
     * Menu for choosing the difficulty
     * @return the difficulty choice
     */
    public CompletableFuture<Character> chooseDifficulty(final boolean theMediumUnlocked, final boolean theHardUnlocked,
                                 final boolean theGlitchUnlocked) {
        console.println("Choose your difficulty!\n"); //TODO: consider making "levels" or "dungeon floors" instead of difficulty

        if (theGlitchUnlocked) {
            console.println("           ╔═══════════════════════════════════════ʘ═---══▒═-╗     ");
            console.println("           ║ 1 for ▒▓▒ | 2 for ▒▒▓▒ | 3 for ▓▓█ | 4 for ???  |     ");
            console.println("           ╚══════════════════════════════════════█-══--══ʘ══╝     ");
            console.println("                  ^            ^             ^         ▲           ");
            console.println("               (L0CK[D)     (L0CK[D)      (L0CK[D)              ");

        } else {
            console.println("              ╔════════════════════════════════════════╗             ");
            console.println("              ║ 1 for easy | 2 for medium | 3 for hard ║             ");
            console.println("              ╚════════════════════════════════════════╝             ");

            if (theHardUnlocked) {
                console.println("                     ▲            ▲             ▲                   ");
                console.println();
            } else if (theMediumUnlocked) {
                console.println("                     ▲            ▲             ^                   ");
                console.println("                                             (Locked)               ");
            } else {
                console.println("                     ▲            ^             ^                   ");
                console.println("                               (Locked)      (Locked)               ");
            }
        }

        console.print("Make your selection: ");

//        console.disableInput(false); // re-enable input box after output is finished displaying
        return futureCharInput();
    }

    /**
     * Displays the difficulty selected
     * @param difficulty the difficulty selected
     */
    public void displayDifficultySelected(final Difficulty difficulty) {
        console.println();
        console.println("You have selected " + difficulty.toString() + " difficulty.");
    }

    public void displayHeroSelected(final String heroType) {
        console.println("\nYou have selected the " + heroType + ".");
    }

    /**
     * Menu for inventory choice
     * @param theBag hero's inventory bag
     * @param inBattle whether hero is in battle
     * @return the inventory choice
     */
    public CompletableFuture<Character> openBag(final Inventory theBag, final boolean inBattle) { // TODO: replace inBattle with a state variable
        console.println("Opening bag...");
        Delay.pause(1);

        if (inBattle) {
            console.println(theBag.getItemView());
        } else {
            console.println(theBag.getFullView());
        }

        console.print("Choose an item (1-4) (e - Back) --> ");

        CompletableFuture<Character> userInputFuture = futureCharInput();
        char userInput = userInputFuture.join(); // wait for user input

        int slotIndex = Character.getNumericValue(userInput)-1; // convert input to int (with -1 due to array index)

        if ((slotIndex < 0 || slotIndex > 3) && userInput != 'e') { // out of bounds
            audio.playSFX(audio.error, 1.0);
            displayWrongInput();
            return openBag(theBag, inBattle);

        } else if (slotIndex > theBag.getCurrentSize()-1 && slotIndex <= theBag.getMaxSize() && userInput != 'e') { // empty slot
            audio.playSFX(audio.error, 1.0);
            console.println("That slot is empty!");
            return openBag(theBag, inBattle);
        }
        console.println();

        return userInputFuture;
    }

    public void closeBag() {
        console.println("Closing bag...");
        audio.playSFX(audio.heroBagClose, 1.0);
        Delay.pause(0.5).join();
        displayChainSpacer();
    }


    /**
     * Brings you a menu for whether you want to quit the game or not
     * @return the menu selection
     */
    public CompletableFuture<Character> quitProcessMsg() {
        console.println("Are you sure you want to quit?");
        console.println("1 for yes, 2 for no");
        console.print("Make your selection: ");

//        console.disableInput(false); // re-enable input box after output is finished displaying
        return futureCharInput();
    }

    /**
     * Brings you a menu for whether you want to quit the game or not
     * @return the menu selection
     */
    public CompletableFuture<Character> goToMainMenu() {
        console.println("\nAre you sure you want to go to the main menu?");
        console.println("1 for yes, 2 for no");
        console.print("Make your selection: ");

//        console.disableInput(false); // re-enable input box after output is finished displaying
        return futureCharInput();
    }

    public CompletableFuture<Character> goToContinueMenu() {
        console.println("\nAre you sure you want to continue?");
        console.println("1 for yes, 2 for no");
        console.print("Make your selection: ");

        return futureCharInput();
    }

    /**
     * Displays the dungeon map
     * @param theDungeon the map
     */
    public void displayDungeonMap(Dungeon theDungeon) {
        console.println(theDungeon.toString());
        console.println();
    }

    /**
     * Displays the hero info
     * @param theHero you
     */
    public void displayHeroStats(final Hero theHero) {

        console.println(" ╔════════════════════════════════════════════════");
        console.println(" ║ 『" + theHero.getName() + "』");
        console.println(" ║ " + theHero.getType());
        console.println(" ║ ");
        console.println(" ║ -ABILITIES-");
        console.println(" ╠ Basic: ");
        console.println(" ║ " + theHero.getBasicName()[0]);
        console.println(" ║ " + theHero.getBasicName()[1]);
        console.println(" ║ ");
        console.println(" ╠ Special: ");
        console.println(" ║ " + theHero.getSpecialName()[0]);
        console.println(" ║ " + theHero.getSpecialName()[1]);
        console.println(" ║ ");
        console.println(" ╠ Passive: ");
        console.println(" ║ " + theHero.getPassiveName()[0]);
        console.println(" ║ " + theHero.getPassiveName()[1]);
        console.println(" ║ ");
        console.println(" ║ -STATS-");
        console.println(" ╠ Health: " + theHero.getHealth() + "/" + theHero.getMaxHealth());
        console.println(" ╠ Attack: " + (theHero.getMinDmg() + theHero.getMaxDmg())/2);
        console.println(" ╠ Speed: " + theHero.getSpeed());
        console.println(" ╠ Level: " + theHero.getLevel());
        console.println(" ╠ Experience: " + theHero.getXP() + "/" + theHero.getXPToLevel());
        console.println(" ╚════════════════════════════════════════════════");
    }

    /**
     * Displays a debugging version of hero stats
     * @param theHero you
     */
    public void debugHeroStats(final Hero theHero) {

        console.println(" ╔════════════════════════════════════════════════");
        console.println(" ║ 『" + theHero.getName() + "』");
        console.println(" ║ " + theHero.getType());
        console.println(" ║ ");
        console.println(" ║ STATS");
        console.println(" ╠ Health: " + theHero.getHealth() + "/" + theHero.getMaxHealth());
        console.println(" ╠ Speed: " + theHero.getSpeed());
        console.println(" ╠ MinDmg: " + theHero.getMinDmg() + "   MaxDmg: " + theHero.getMaxDmg());
        console.println(" ╠ Defense: " + theHero.getLevel());
        console.println(" ╠ Basic Accuracy: " + theHero.getBasicAccuracy());
        console.println(" ╠ Special Accuracy: " + theHero.getSpecialAccuracy());
        console.println(" ╠ Level: " + theHero.getLevel());
        console.println(" ╠ Experience: " + theHero.getXP() + "/" + theHero.getXPToLevel());
        console.println(" ╚════════════════════════════════════════════════");
    }

    /**
     * Displays the monster info
     * @param theMonster the monster
     */
    public void displayMonsterStats(final Monster theMonster) {
        console.println(" ╔════════════════════════════════════════════════");
        console.println(" ║ " + theMonster.getName());
        console.println(" ║ ");
        console.println(" ║ DESCRIPTION: ");
        console.println(" ║ " + theMonster.getDescription()[0]);
        console.println(" ║ " + theMonster.getDescription()[1]);
        console.println(" ║ ");
        console.println(" ║ ABILITIES");
        console.println(" ╠ Basic: ");
        console.println(" ║ " + theMonster.getBasicName()[0]);
        console.println(" ║ " + theMonster.getBasicName()[1]);
        console.println(" ║ ");
        console.println(" ╠ Special: ");
        console.println(" ║ " + theMonster.getSpecialName()[0]);
        console.println(" ║ " + theMonster.getSpecialName()[1]);
        console.println(" ║ ");
        console.println(" ╠ Passive: ");
        console.println(" ║ " + theMonster.getPassiveName()[0]);
        console.println(" ║ " + theMonster.getPassiveName()[1]);
        console.println(" ║ ");
        console.println(" ║ STATS:");
        console.println(" ╠ Health: " + theMonster.getHealth() + "/" + theMonster.getMaxHealth());
        console.println(" ╠ Attack: " + (theMonster.getMinDmg() + theMonster.getMaxDmg())/2);
        console.println(" ╠ Speed: " + theMonster.getSpeed());
        console.println(" ╚════════════════════════════════════════════════");

    }

    /**
     * Displays the potion info
     * @param thePotion the potion info
     */
    public void collectPotionMsg(final Potion thePotion) {
        console.println(" -You collected a " + thePotion.type() + "!");
    }

    /**
     * Displays Abstraction Pillar Message
     */
    public void displayAbstractionPillarMsg() {
        console.println(" -You collected a PILLAR!");
    }
    /**
     * Displays Encapsulation Pillar Message
     */
    public void displayEncapsulationPillarMsg() {
        console.println(" -You collected a PILLAR!");
    }
    /**
     * Displays Inheritance Pillar Message
     */
    public void displayInheritancePillarMsg() {
        console.println(" -You collected a PILLAR!");
    }
    /**
     * Displays Polymorphism Pillar Message
     */
    public void displayPolymorphismPillarMsg() {
        console.println(" -You collected a PILLAR!");
    }


    public void monsterHeal(final int theHealAmt) {
        console.println(" -The enemy heals themselves for " + theHealAmt + " Health Points!");
        Delay.pause(1).join();
    }

    public void characterSelectAbility(final DungeonCharacter theAttacker, final DungeonCharacter theDefender,
                                       final Ability theAbility) {

        if (theAbility == Ability.BASIC) {
            console.println(" " + theAttacker.getBasicSelectMsg(theDefender) + "...");

        } else if (theAbility == Ability.SPECIAL){
            console.println(" " + theAttacker.getSpecialSelectMsg(theDefender) + "...");
        }
        Delay.pause(1.5).join();
    }

    public void monsterAttackResult(final Monster theMonster, final Hero theHero, final int theDamageDealt) {

        console.println(" " + theMonster.getAttackResult()); // attackResult contains msg for hits, misses, basics, and specials
        if (theDamageDealt != 0) {
            console.println("   -" + theHero.getName() + " took " + theDamageDealt + " damage!");
        }
        Delay.pause(1).join();
    }

    public void heroAttackResult(final Hero theHero, final int theDamageDealt) {

        console.println(" " + theHero.getAttackResult());
        if (theDamageDealt != 0) { // if (theMonster.doesDamageOnSpecial() || theAttackHit)
            console.println("   -" + "The enemy took " + theDamageDealt + " damage!");
        }
        Delay.pause(1).join();
    }
    public void playerCritMsg() {
        console.println("   -It was a critical hit!");
    }

    public void inflictedDebuffMsg(final Debuff theDebuff, final int theDuration) {
        if (theDuration == 1) {
            console.println("   -Inflicted " + theDebuff + " for " + theDuration + " turn");
        } else {
            console.println("   -Inflicted " + theDebuff + " for " + theDuration + " turns");
        }
    }



    public void displayCooldown(final int theCooldown) {
        if (theCooldown == 1) {
            console.println(" -You can't use that ability for " + theCooldown + " more turn!");
        } else {
            console.println(" -You can't use that ability for " + theCooldown + " more turns!");
        }
    }

    /**
     * The game's victory message
     */
    public void displayVictoryMsg(final int theHeroSteps, final int theMonstersDefeated, final int theLevel,
                                  final Difficulty theDifficulty, boolean theFunnyMode, final boolean theDebugMode) {

        displayChainSpacer();
        console.println();
        console.println(" -You escaped the dungeon!");
        console.println();
        console.println("" +
                "██╗   ██╗ ██████╗ ██╗   ██╗    ██╗    ██╗██╗███╗   ██╗██╗\n" +
                "╚██╗ ██╔╝██╔═══██╗██║   ██║    ██║    ██║██║████╗  ██║██║\n" +
                " ╚████╔╝ ██║   ██║██║   ██║    ██║ █╗ ██║██║██╔██╗ ██║██║\n" +
                "  ╚██╔╝  ██║   ██║██║   ██║    ██║███╗██║██║██║╚██╗██║╚═╝\n" +
                "   ██║   ╚██████╔╝╚██████╔╝    ╚███╔███╔╝██║██║ ╚████║██╗\n" +
                "   ╚═╝    ╚═════╝  ╚═════╝      ╚══╝╚══╝ ╚═╝╚═╝  ╚═══╝╚═╝");
        console.println();

        if (!theDebugMode) {
            console.disableInput(true); // disable input box while output is currently being displayed

            Delay.pause(1);
            console.typeAnimation("  RESULTS:", TextSpeed.MEDIUM).join();
            console.typeAnimation("  You beat the game on " + theDifficulty + " difficulty!", TextSpeed.MEDIUM).join();
            console.typeAnimation("  You took " + theHeroSteps + " steps to escape the dungeon.", TextSpeed.MEDIUM).join();
            console.typeAnimation("  You reached level " + theLevel + ".", TextSpeed.MEDIUM).join();
            console.typeAnimation("  You defeated " + theMonstersDefeated + " monsters.", TextSpeed.MEDIUM).join();

            console.disableInput(false); // re-enable input box after output is finished displaying
        }


    }

    /**
     * The battle win message
     */
    public void displayBattleWinMsg(final Monster theMonster) {
        console.println();
        Delay.pause(0.5).join();
        console.println(" -" + theMonster.getDeathMsg());
        console.println();
        Delay.pause(2).join();
        console.println("                ╒≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡╕");
        console.println("                │ ENEMY DEFEATED │");
        console.println("                ╘≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡╛");
        console.println();
        Delay.pause(2).join();
        console.println(" RESULTS:");
        console.println(" -You gained " + theMonster.getXPWorth() + " experience points!");
        console.println();
    }

    /**
     * The death message
     */
    public void displayDeathMsg(final boolean theFunnyMode) {
        console.println(" -You have been defeated.");
        Delay.pause(2).join();
        if (theFunnyMode) {
            console.println("" +
                    " $$$$$$\\  $$$$$$\\ $$$$$$$$\\        $$$$$$\\  $$\\   $$\\ $$$$$$$\\         $$$$$$\\   $$$$$$\\  $$$$$$$\\  $$\\   $$\\ $$$$$$$\\  \n" +
                    "$$  __$$\\ \\_$$  _|\\__$$  __|      $$  __$$\\ $$ |  $$ |$$  __$$\\       $$  __$$\\ $$  __$$\\ $$  __$$\\ $$ |  $$ |$$  __$$\\ \n" +
                    "$$ /  \\__|  $$ |     $$ |         $$ /  \\__|$$ |  $$ |$$ |  $$ |      $$ /  \\__|$$ /  \\__|$$ |  $$ |$$ |  $$ |$$ |  $$ |\n" +
                    "$$ |$$$$\\   $$ |     $$ |         $$ |$$$$\\ $$ |  $$ |$$ |  $$ |      \\$$$$$$\\  $$ |      $$$$$$$  |$$ |  $$ |$$$$$$$\\ |\n" +
                    "$$ |\\_$$ |  $$ |     $$ |         $$ |\\_$$ |$$ |  $$ |$$ |  $$ |       \\____$$\\ $$ |      $$  __$$< $$ |  $$ |$$  __$$\\ \n" +
                    "$$ |  $$ |  $$ |     $$ |         $$ |  $$ |$$ |  $$ |$$ |  $$ |      $$\\   $$ |$$ |  $$\\ $$ |  $$ |$$ |  $$ |$$ |  $$ |\n" +
                    "\\$$$$$$  |$$$$$$\\    $$ |         \\$$$$$$  |\\$$$$$$  |$$$$$$$  |      \\$$$$$$  |\\$$$$$$  |$$ |  $$ |\\$$$$$$  |$$$$$$$  |\n" +
                    " \\______/ \\______|   \\__|          \\______/  \\______/ \\_______/        \\______/  \\______/ \\__|  \\__| \\______/ \\_______/ !");
        } else {
            console.println("\n" +
                    " $$$$$$\\                                           $$$$$$\\                                 \n" +
                    "$$  __$$\\                                         $$  __$$\\                                \n" +
                    "$$ /  \\__| $$$$$$\\  $$$$$$\\$$$$\\   $$$$$$\\        $$ /  $$ |$$\\    $$\\  $$$$$$\\   $$$$$$\\  \n" +
                    "$$ |$$$$\\  \\____$$\\ $$  _$$  _$$\\ $$  __$$\\       $$ |  $$ |\\$$\\  $$  |$$  __$$\\ $$  __$$\\ \n" +
                    "$$ |\\_$$ | $$$$$$$ |$$ / $$ / $$ |$$$$$$$$ |      $$ |  $$ | \\$$\\$$  / $$$$$$$$ |$$ |  \\__|\n" +
                    "$$ |  $$ |$$  __$$ |$$ | $$ | $$ |$$   ____|      $$ |  $$ |  \\$$$  /  $$   ____|$$ |      \n" +
                    "\\$$$$$$  |\\$$$$$$$ |$$ | $$ | $$ |\\$$$$$$$\\        $$$$$$  |   \\$  /   \\$$$$$$$\\ $$ |      \n" +
                    " \\______/  \\_______|\\__| \\__| \\__| \\_______|       \\______/     \\_/     \\_______|\\__|      \n" +
                    "                                                                                           \n" +
                    "                                                                                           \n" +
                    "                                                                                           \n");
        }
    }

    /**
     * The cheat mode message
     */
    public void displayCheatMode() {
        console.println("***********************************");
        console.println(" You have entered cheat mode ಠ_ಠ ");
        console.println("***********************************");
    }

    /**
     * The funny mode message
     */
    public void displayFunnyMode() {
        console.println("****************************************");
        console.println(" You have entered funny mode ( ͡° ͜ʖ ͡°) ");
        console.println("****************************************");
    }

    /**
     * The debug mode message
     */
    public void displayDebugMode() {
        console.println("************************************");
        console.println(" You have entered debug mode ❐‿❑ ");
        console.println("************************************");
    }

    public void displayHeroDirection(final Hero theHero) {
        console.print("    Facing: " + theHero.getDirection());
    }

    /**
     * Display the steps left with the vision potion
     * @param theSteps the steps the hero has taken while the vision potion is active
     */
    public void displayStepsWithVisionPotion(final int theSteps) {
        console.print("    Steps left with vision potion: " + (4-theSteps));
    }


    /**
     * The instant kill message
     */
    public void displayInstaKill() {
        console.println("Woooow, you must be so powerful... cheater");
    }

    /**
     * Monster encounter message
     * @param theMonster the monster you're encountering
     */
    public void displayMonsterEncounterMsg(final Monster theMonster) {
        String monsterName = theMonster.getType().toString();
        String article = isVowel(monsterName.charAt(0)) ? " an " : " a ";
        console.println(" -You have encountered" + article + monsterName);
        Delay.pause(1).join();
        console.println();
        console.println("████████████████████████████████████████████████████████████████████");
        console.println("█                       --- BATTLE START ---                       █");
        console.println("████████████████████████████████████████████████████████████████████");
        console.println();
        Delay.pause(0.5).join();
    }

    /**
     * Locked Exit message
     */
    public void exitLocked() {
        console.println(" -The exit is locked! You need to collect all 4 pillars to open it!");
    }

    /**
     * Fell into pit message
     * @param theFallDamage the pit you fell in
     */
    public void displayPitMsg(final int theFallDamage) {
        console.println(" -You fell into a pit! You took " + theFallDamage + " damage!");
    }

    /**
     * The use potion message
     * @param thePotion the potion you used
     * @param theSlotIndex the slot of the potion
     */
    public void usePotionMsg(final Potion thePotion, int theSlotIndex) {
        console.println(" -You used a " + thePotion.type() + " from the slot #" + (theSlotIndex+1));
        console.println(thePotion.useMsg());
    }

    /**
     * The invalid input message
     */
    public void displayWrongInput() {
        console.println("Invalid input. Please try again.");
    }

    public void displayNotEnoughLetters() {
        console.println("Your name contain least 3 letters. Please try again.");
    }

    public void displayTooManyLetters() {
        console.println("Your name must contain no more than 10 letters. Please try again.");
    }

    public void displayNumberStartingName() {
        console.println("Your name cannot start with a number. Please try again.");
    }

    public CompletableFuture<Character> playIntroOrNot() {
        console.println("Would you like to play the intro? (please say yes)");
        console.println("1 for no, 2 for yes");
        console.print("Make your selection: ");

        console.setHintText("");

//        console.disableInput(false); // re-enable input box after output is finished displaying
        return futureCharInput();
    }

    public void displayWallMsg() {
        console.println("WHAT ARE YOU DOING IN A WALL?! GET OUT OF THERE YOU FOOL");
    }

    public void displayHitWallMsg() {
        console.println(" -You ran into a wall you oaf");
    }

    public void heroIntroduction(Hero hero) {
        console.disableInput(true); // disable input box while output is currently being displayed

        console.print("\nDungeon Keeper: ");
        console.typeAnimation("Ah, a " + hero.getType() + "!\n", TextSpeed.MEDIUM).join();
        console.print("                ");
        console.typeAnimation("I'm sure that skill will serve you well.\n", TextSpeed.MEDIUM).join();
        console.print("                ");
        console.typeAnimation("Good luck!\n", TextSpeed.MEDIUM).join();
//        console.print("                ");
//        console.printAnimation("*cranks gate open*\n", TextSpeed.MEDIUM).join();
        console.println();

        console.disableInput(false); // re-enable input box after output is finished displaying
    }

    public void displayInstructions() {
        console.println();
        console.println();
        console.println("" +
                "88 88b 88 .dP\"Y8 888888 88\"\"Yb 88   88  dP\"\"b8 888888 88  dP\"Yb  88b 88 .dP\"Y8 \n" +
                "88 88Yb88 `Ybo.\"   88   88__dP 88   88 dP   `\"   88   88 dP   Yb 88Yb88 `Ybo.\" \n" +
                "88 88 Y88 o.`Y8b   88   88\"Yb  Y8   8P Yb        88   88 Yb   dP 88 Y88 o.`Y8b \n" +
                "88 88  Y8 8bodP'   88   88  Yb `YbodP'  YboodP   88   88  YbodP  88  Y8 8bodP' ");
        console.println();
        console.println("▂ ▃ ▄ ▅ ▆ ▇ █  What Am I Looking at?  █ ▇ ▆ ▅ ▄ ▃ ▂");
        console.println();
        console.println(" The giant square you see is a top-down view of the dungeon,");
        console.println(" which is a 2D grid of rooms/walls.");
        console.println(" The empty spaces are rooms, and the '*' symbols are walls.");
        console.println(" The '□' symbol is you, the hero.");
        console.println(" The contents of the rooms will remain hidden until you enter them.");
        console.println();
        console.println(" Each room may contain one or more of the following:");
        console.println("" +
                " ║ '▮' = exit\n" +
                " ║ 'M' = monster\n" +
                " ║ 'X' = pit\n" +
                " ║ 'p' = potion\n" +
                " ║ 'I' = pillar\n" +
                " ║ '&' = multiple items in the same room");
        console.println();
        console.println(" You may find a vision potion on your journey.");
        console.println(" Using this potion will reveal the contents of EVERY room");

        pressAnyKeyNextPage();
        audio.playSFX(audio.swishOn, 1.0);

        console.println();
        console.println();
        console.println("" +
                "88 88b 88 .dP\"Y8 888888 88\"\"Yb 88   88  dP\"\"b8 888888 88  dP\"Yb  88b 88 .dP\"Y8 \n" +
                "88 88Yb88 `Ybo.\"   88   88__dP 88   88 dP   `\"   88   88 dP   Yb 88Yb88 `Ybo.\" \n" +
                "88 88 Y88 o.`Y8b   88   88\"Yb  Y8   8P Yb        88   88 Yb   dP 88 Y88 o.`Y8b \n" +
                "88 88  Y8 8bodP'   88   88  Yb `YbodP'  YboodP   88   88  YbodP  88  Y8 8bodP' ");
        console.println();
        console.println("▂ ▃ ▄ ▅ ▆ ▇ █  How Does the Dungeon Work?  █ ▇ ▆ ▅ ▄ ▃ ▂");
        console.println();
        console.println("Beware! Monsters will spawn in a random location every 4 steps you take.");
//         talk about what different potions do

        pressAnyKeyNextPage();
        audio.playSFX(audio.swishOn, 1.0);

        console.println();
        console.println();
        console.println("" +
                "88 88b 88 .dP\"Y8 888888 88\"\"Yb 88   88  dP\"\"b8 888888 88  dP\"Yb  88b 88 .dP\"Y8 \n" +
                "88 88Yb88 `Ybo.\"   88   88__dP 88   88 dP   `\"   88   88 dP   Yb 88Yb88 `Ybo.\" \n" +
                "88 88 Y88 o.`Y8b   88   88\"Yb  Y8   8P Yb        88   88 Yb   dP 88 Y88 o.`Y8b \n" +
                "88 88  Y8 8bodP'   88   88  Yb `YbodP'  YboodP   88   88  YbodP  88  Y8 8bodP' ");
        console.println();
        console.println("▂ ▃ ▄ ▅ ▆ ▇ █  How do I battle?  █ ▇ ▆ ▅ ▄ ▃ ▂");
        console.println();
        console.println(" You will eventually encounter monsters in the dungeon.");
        console.println(" After encountering one, a battle will commence.");
        console.println(" Whoever has a higher speed will attack first.");
        console.println(" You will take turns attacking the monster until one of you dies.");
        console.println();
        console.println(" On your turn, you have 3 main choices:");
        console.println("  ║ 1. Use your basic attack");
        console.println("  ║ 2. Use your special attack");
        console.println("  ║ 3. Use a potion from your inventory");
        console.println();
        console.println(" Strategy is key!");
        console.println(" Use all 3 options to gain the upper hand and win the battle.");
        console.println();
        console.println(" There are negative effects characters can inflict on each other.");
        console.println(" These effects will last for a certain number of turns.");
        console.println(" The effects are:");
        console.println("  ║ Poisoned: Lose 10 health every turn");
        console.println("  ║ Blinded: Decrease your accuracy");
        console.println("  ║ Stuck: Skip your next turn");
        console.println("  ║ Weakened: Your next hit will deal half the damage");
        console.println("  ║ Vulnerable: Next hit on you will deal 2x the damage");
        console.println("  ║ Silenced: Can't use special ability");
        console.println(" You will see these under the \"S̲T̲A̲T̲U̲S̲\" section during a battle.");

        pressAnyKeyNextPage();
        audio.playSFX(audio.swishOn, 1.0);

        console.println();
        console.println();
        console.println("" +
                "88 88b 88 .dP\"Y8 888888 88\"\"Yb 88   88  dP\"\"b8 888888 88  dP\"Yb  88b 88 .dP\"Y8 \n" +
                "88 88Yb88 `Ybo.\"   88   88__dP 88   88 dP   `\"   88   88 dP   Yb 88Yb88 `Ybo.\" \n" +
                "88 88 Y88 o.`Y8b   88   88\"Yb  Y8   8P Yb        88   88 Yb   dP 88 Y88 o.`Y8b \n" +
                "88 88  Y8 8bodP'   88   88  Yb `YbodP'  YboodP   88   88  YbodP  88  Y8 8bodP' ");
        console.println();
        console.println("▂ ▃ ▄ ▅ ▆ ▇ █  How Do I Win?  █ ▇ ▆ ▅ ▄ ▃ ▂");
        console.println();
        console.println(" You win by escaping through the exit door.");
        console.println(" However, the exit will remain locked until you possess");
        console.println(" all 4 pillars in your inventory.");
    }

    public void displayCantUseItemDuringBattle(Potion thePotion) {
        console.println(" -You can't use a " + thePotion.type() + " during a battle!");
    }

    public void displayCantUseItemOutsideBattle(Potion thePotion) {
        console.println(" -You can't use a " + thePotion.type() + " outside of a battle!");
    }

    public void levelUpMsg(final int theLevel) {
        console.println(" -You leveled up!");
        console.println("  You are now level " + theLevel);
        console.println("  Gained +5 damage, +10 max health");
        console.println("  Your health is fully restored!");
        if (theLevel == 5) {
            console.println("  You have reached the max level");
        }
    }

    public void displayInventoryFullMsg() {
        console.println(" -You found a potion, but your inventory is full.");
    }

    public void displayBattleEnd() {
        console.println();
        console.println("████████████████████████████████████████████████████████████████████");
        console.println("█                        --- BATTLE END ---                        █");
        console.println("████████████████████████████████████████████████████████████████████");
    }

    private boolean isVowel(char ch) {
        ch = Character.toLowerCase(ch);
        return ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u';
    }

    public void exitIsOpenMsg() {
        console.println(" -You hear a door creak open in the distance...");
    }

    public void displayArrowSpacer() {
        console.println("<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>");
    }

    public void printAudioError(URISyntaxException theError) {
        console.println("Error: Audio source could not be parsed." + theError);
    }

    public void heroFasterThanMonsterMsg(final Monster theMonster) {
        console.println(" -You have a higher speed than " + theMonster.getName() + ", so you get to attack first!");
        console.println();
    }

    public void monsterFasterThanHeroMsg(final Monster theMonster) {
        console.println(" -The " + theMonster.getName() + " has a higher speed than you, so it attacks first!");
        console.println();
    }

    public void pressAnyKeyGoBack() {
        console.println();
        displayChainSpacer();
        console.println("Press any key, then ENTER to return...");

//        console.disableInput(false); // re-enable input box after output is finished displaying

        CompletableFuture<Character> futureChar = futureCharInput();

        // Ensure that all the asynchronous tasks associated with the CompletableFuture chain have finished before the program proceeds further
        futureChar.join();

        futureChar.thenApplyAsync(userInput -> {
            audio.playSFX(audio.menu1, 1.0);
            displayChainSpacer(); //!!! BIG PROBLEM: this displays after a delay, causing other stuff to display before this
            console.println();
            return userInput;
        });
    }

    public void pressAnyKeyContinue() {
        console.println();
        displayChainSpacer();
        console.println("Press any key, then ENTER to continue...");

//        console.disableInput(false); // re-enable input box after output is finished displaying

        CompletableFuture<Character> futureChar = futureCharInput();

        // Ensure that all the asynchronous tasks associated with the CompletableFuture chain have finished before the program proceeds further
        futureChar.join();

        futureChar.thenApplyAsync(userInput -> {
            audio.playSFX(audio.menu1, 1.0);
            displayChainSpacer();
            console.println();
            return userInput;
        });
    }

    public void pressAnyKeyNextPage() {
        console.println();
        displayChainSpacer();
        console.println("Press any key, then ENTER for the next page...");

//        console.disableInput(false); // re-enable input box after output is finished displaying

        CompletableFuture<Character> futureChar = futureCharInput();

        // Ensure that all the asynchronous tasks associated with the CompletableFuture chain have finished before the program proceeds further
        futureChar.join();

        futureChar.thenApplyAsync(userInput -> {
            audio.playSFX(audio.menu1, 1.0);
            displayChainSpacer();
            console.println();
            return userInput;
        });
    }

    public void displayUnlockedMedium() {
        console.println();
        console.println("╔════════════════════ ◈ INFO ◈ ════════════════════╗");
        console.println("║        You've unlocked MEDIUM difficulty!         ║");
        console.println("║   This mode contains pits and tougher monsters    ║");
        console.println("╚═══════════════════════════════════════════════════╝");
        console.println();
    }

    public void displayUnlockedHard() {
        console.println();
        console.println("╔════════════════════ ◈ INFO ◈ ════════════════════╗");
        console.println("║         You've unlocked HARD difficulty!          ║");
        console.println("║   This mode has something waiting at the end...   ║");
        console.println("╚═══════════════════════════════════════════════════╝");
        console.println();
    }

    public void displayUnlockedJuggernautAndThief() {
        console.println();
        console.println("╔════════════════════ ◈ INFO ◈ ════════════════════╗");
        console.println("║ You've unlocked the JUGGERNAUT and THIEF heroes!  ║");
        console.println("║ They will now appear in the hero selection menu.  ║");
        console.println("╚═══════════════════════════════════════════════════╝");
        console.println();
    }
    public void displayUnlockedDoctorAndNinja() {
        console.println();
        console.println("╔════════════════════ ◈ INFO ◈ ════════════════════╗");
        console.println("║   You've unlocked the DOCTOR and NINJA heroes!    ║");
        console.println("║ They will now appear in the hero selection menu.  ║");
        console.println("╚═══════════════════════════════════════════════════╝");
        console.println();
    }
    public void displayUnlockedScientist() {
        console.println();
        console.println("╔════════════════════ ◈ INFO ◈ ════════════════════╗");
        console.println("║         You've unlocked the SCIENTIST hero        ║");
        console.println("║  He will now appear in the hero selection menu.   ║");
        console.println("╚═══════════════════════════════════════════════════╝");
        console.println();
    }
    public void displayUnlockedMage() {
        console.println();
        console.println("╔════════════════════ ◈ INFO ◈ ════════════════════╗");
        console.println("║           You've unlocked the MAGE hero!          ║");
        console.println("║  He will now appear in the hero selection menu.   ║");
        console.println("╚═══════════════════════════════════════════════════╝");
        console.println();
    }

    public void displayUnlockedBeastmaster() {
        console.println();
        console.println("╔════════════════════ ◈ INFO ◈ ════════════════════╗");
        console.println("║        You've unlocked the BEASTMASTER hero       ║");
        console.println("║  He will now appear in the hero selection menu.   ║");
        console.println("╚═══════════════════════════════════════════════════╝");
        console.println();
    }

    public void displayUnlockedAll() {
        console.println();
        console.println("╔════════════════════ ◈ INFO ◈ ════════════════════╗");
        console.println("║           You've unlocked ALL the heroes          ║");
        console.println("║                 Congratulations!!!                ║");
        console.println("╚═══════════════════════════════════════════════════╝");
        console.println();
    }

    public void displayHintStillMoreHeroes() {
        console.println();
        console.println("╔════════════════════ ? HINT ? ═════════════════════╗");
        console.println("║       There are still more heroes to find.        ║");
        console.println("║    Keep exploring the dungeon to unlock them...   ║");
        console.println("╚═══════════════════════════════════════════════════╝");
        console.println();
    }

    public void displayHintUnlocking() {
        console.println();
        console.println("╔════════════════════ ? HINT ? ═════════════════════╗"); // either the message for the dragon after beating medium
        console.println("║    You hear a bone-chilling roar. Something is    ║"); // or a funny message for the super gremlin after beating hard
        console.println("║    still lurking in the depths of the dungeon...  ║");
        console.println("╚═══════════════════════════════════════════════════╝");
        console.println();
    }

    public void displayGlitchHintParchment() {
        console.println();
        console.println("   ╔═--══ʘ═══---═▒═══ʘ══ ? H!NT ? ═════ʘ════----═══▒═══╗");
        console.println("   ║ {}    USE PAR<HMENT INSCR!PTI0N IN GAM3 C0DE []   |");
        console.println(" ║   () ..UN..L0CK..... .....M0DE....()......[]....  ║");
        console.println(" ╚═ʘ════--═▒═══ʘ════-----════▒══════ʘ═══--═══ʘ═════--╝");
        console.println();
    }

    public void beatEasyDungeonKeeperMsg() {
        // make dungeon keeper congratulate player
        console.println("Dungeon Keeper: Congratulations! You've beaten the easy dungeon!");
    }

    public void findParchmentDungeonKeeperMsg(final List<Parchment> theParchments) { // only 1 parchment scrap per dungeon
        console.disableInput(true); // disable input box while output is currently being displayed

        if (theParchments.size() == 1) {
            console.print("Dungeon Keeper: ");
            console.typeAnimation("What's that? You found a scrap of parchment?! Let me see it...", TextSpeed.FAST).join();
            console.print("                ");
            console.typeAnimation("Ah... it's nothing but a bunch of scribbles. Pay it no mind.", TextSpeed.MEDIUM).join();
            console.print("                ");
            console.typeAnimation("It's probably dangerous if anything.", TextSpeed.SLOW).join();

        } else if (theParchments.size() == 2) {
            console.print("Dungeon Keeper: ");
            console.typeAnimation("Another parchment scrap?", TextSpeed.MEDIUM).join();
            console.print("                ");
            console.typeAnimation("I've already told you, it's dangerous...", TextSpeed.MEDIUM).join();
            console.print("                ");
            console.typeAnimation("Leave it alone and stop collecting these!", TextSpeed.SLOW).join();
        }

        console.disableInput(false); // re-enable input box after output is finished displaying
    }

    public void beatMediumDungeonKeeperMsg() {
        // make dungeon keeper congratulate player
        console.println("");
    }

    public void beatHardDungeonKeeperMsg() {
        // make dungeon keeper congratulate player
        console.println("");
    }

    public void displayScrapOfParchment(final Parchment theParchment) {
        console.println("You found a scrap of parchment!");
        console.println("You see something scribbled on it from a previous adventurer: ");
        console.println(theParchment.toString());
    }




    public void displayCredits() { // CREDITS
        Delay.pause(2).join();
        audio.playMusic(audio.rickRollSong, false, 0.6);
        Delay.pause(1).join();
        console.println();
        console.println();
        console.println("" +
                "    ,o888888o.    8 888888888o.   8 8888888888   8 888888888o.       8 8888 8888888 8888888888    d888888o.   \n" +
                "   8888     `88.  8 8888    `88.  8 8888         8 8888    `^888.    8 8888       8 8888        .`8888:' `88. \n" +
                ",8 8888       `8. 8 8888     `88  8 8888         8 8888        `88.  8 8888       8 8888        8.`8888.   Y8 \n" +
                "88 8888           8 8888     ,88  8 8888         8 8888         `88  8 8888       8 8888        `8.`8888.     \n" +
                "88 8888           8 8888.   ,88'  8 888888888888 8 8888          88  8 8888       8 8888         `8.`8888.    \n" +
                "88 8888           8 888888888P'   8 8888         8 8888          88  8 8888       8 8888          `8.`8888.   \n" +
                "88 8888           8 8888`8b       8 8888         8 8888         ,88  8 8888       8 8888           `8.`8888.  \n" +
                "`8 8888       .8' 8 8888 `8b.     8 8888         8 8888        ,88'  8 8888       8 8888       8b   `8.`8888. \n" +
                "   8888     ,88'  8 8888   `8b.   8 8888         8 8888    ,o88P'    8 8888       8 8888       `8b.  ;8.`8888 \n" +
                "    `8888888P'    8 8888     `88. 8 888888888888 8 888888888P'       8 8888       8 8888        `Y8888P ,88P' \n" +
                "▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄  ");


        Delay.pause(1).join();
        console.println();
        Delay.pause(1).join();
        console.println();
        Delay.pause(1).join();
        console.println();
        Delay.pause(1).join();
        console.println();
        Delay.pause(1).join();
        console.println("" +
                "  _____                                               _             \n" +
                " |  __ \\                                             (_)            \n" +
                " | |__) | __ ___   __ _ _ __ __ _ _ __ ___  _ __ ___  _ _ __   __ _ \n" +
                " |  ___/ '__/ _ \\ / _` | '__/ _` | '_ ` _ \\| '_ ` _ \\| | '_ \\ / _` |\n" +
                " | |   | | | (_) | (_| | | | (_| | | | | | | | | | | | | | | | (_| |\n" +
                " |_|   |_|  \\___/ \\__, |_|  \\__,_|_| |_| |_|_| |_| |_|_|_| |_|\\__, |\n" +
                "                   __/ |                                       __/ |\n" +
                "                  |___/                                       |___/ ");

        Delay.pause(1).join();
        console.println();
        Delay.pause(1).join();
        console.println("  Nathan Hinthorne");
        Delay.pause(1).join();
        console.println();
        Delay.pause(1).join();
        console.println("  (with a little help from Brendan Smith and Austin Roaf)");
        Delay.pause(1).join();
        console.println();
        Delay.pause(1).join();
        console.println();
        Delay.pause(1).join();
        console.println();
        Delay.pause(1).join();
        console.println();

        console.println("" +
                "  __  __           _      \n" +
                " |  \\/  |         (_)     \n" +
                " | \\  / |_   _ ___ _  ___ \n" +
                " | |\\/| | | | / __| |/ __|\n" +
                " | |  | | |_| \\__ \\ | (__ \n" +
                " |_|  |_|\\__,_|___/_|\\___|");

        Delay.pause(1).join();
        console.println();
        Delay.pause(1).join();
        console.println(" Starting Off Theme by: Nathan Hinthorne");
        Delay.pause(1).join();
        console.println();
        Delay.pause(1).join();
        console.println(" Dungeon Theme by: Nathan Hinthorne");
        Delay.pause(1).join();
        console.println();
        Delay.pause(1).join();
        console.println(" Battle Theme by: Nathan Hinthorne");
        Delay.pause(1).join();
        console.println();
        Delay.pause(1).join();
        console.println(" Victory Theme by: Jon Presstone");
        Delay.pause(1).join();
        console.println();
        Delay.pause(1).join();
        console.println(" Credits Theme by: 8 Bit Universe");
        Delay.pause(1).join();
        console.println();
        Delay.pause(1).join();
        console.println();
        Delay.pause(1).join();
        console.println();
        Delay.pause(1).join();
        console.println("" +
                "   _____                 _       _   _______ _                 _        \n" +
                "  / ____|               (_)     | | |__   __| |               | |       \n" +
                " | (___  _ __   ___  ___ _  __ _| |    | |  | |__   __ _ _ __ | | _____ \n" +
                "  \\___ \\| '_ \\ / _ \\/ __| |/ _` | |    | |  | '_ \\ / _` | '_ \\| |/ / __|\n" +
                "  ____) | |_) |  __/ (__| | (_| | |    | |  | | | | (_| | | | |   <\\__ \\\n" +
                " |_____/| .__/ \\___|\\___|_|\\__,_|_|    |_|  |_| |_|\\__,_|_| |_|_|\\_\\___/\n" +
                "        | |                                                             \n" +
                "        |_|                                                             ");

        Delay.pause(1).join();
        console.println();
//        Delay.pause(1).join();
//        console.println("  My friends and family, for all their amazing ideas for characters and abilities.");
//        Delay.pause(1).join();
//        console.println();
//        Delay.pause(1).join();
//        console.println("  My professor, Tom Capaul, for giving me the assignment which started this whole game off.");
        Delay.pause(1).join();
        console.println("  Ethan, for his wonderful cooking skills");
        Delay.pause(1).join();
        console.println("  Garret, for his insightful ideas on battle mechanics and, of course, his ownership of the lead pipe");
        Delay.pause(1).join();
        console.println("  Aaron, for giving a supportive comment on every update I made to the game.");
        Delay.pause(1).join();

        // make the rest of the text run off the screen with a bunch of blank lines
        for (int i = 0; i < 30; i++) {
            Delay.pause(1).join();
            console.println();
        }

        Delay.pause(2).join();
        displayRecommendListening();
        Delay.pause(2).join();
        pressAnyKeyContinue();
        audio.stopAll();
    }

    public CompletableFuture<Character> displayPlayAgainMenu() {
        console.println("Would you like to play again? (1 for yes, 2 for no)");
        console.print("Make your selection: ");

//        console.disableInput(false); // re-enable input box after output is finished displaying
        return futureCharInput();
    }

    public void displayCyaNerd(final boolean theFunnyMode) {
        console.disableInput(true); // disable input box while output is currently being displayed

        console.println();
        if (theFunnyMode) {
            console.print("Nathan: ");
            console.typeAnimation("Nah, you're trapped here forever now.", TextSpeed.MEDIUM).join();
            Delay.pause(4);
            console.print("                ");
            console.typeAnimation("Don't you dare hit that X button.", TextSpeed.MEDIUM).join();
            console.print("                ");
            console.typeAnimation(". ", TextSpeed.VERY_SLOW).join();
            console.typeAnimation(". ", TextSpeed.VERY_SLOW).join();
            console.typeAnimation(". ", TextSpeed.VERY_SLOW).join();
            Delay.pause(1);
            console.typeAnimation("FINE!", TextSpeed.MEDIUM).join();
            console.print("                ");
            console.typeAnimation("I'll let you leave.", TextSpeed.MEDIUM).join();
            console.println();
            Delay.pause(2);

            console.println("" +
                    " ▄▄▄▄▄▄▄▄▄▄▄  ▄         ▄  ▄▄▄▄▄▄▄▄▄▄▄            ▄▄        ▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄  \n" +
                    "▐░░░░░░░░░░░▌▐░▌       ▐░▌▐░░░░░░░░░░░▌          ▐░░▌      ▐░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░▌ \n" +
                    "▐░█▀▀▀▀▀▀▀▀▀ ▐░▌       ▐░▌▐░█▀▀▀▀▀▀▀█░▌          ▐░▌░▌     ▐░▌▐░█▀▀▀▀▀▀▀▀▀ ▐░█▀▀▀▀▀▀▀█░▌▐░█▀▀▀▀▀▀▀█░▌\n" +
                    "▐░▌          ▐░▌       ▐░▌▐░▌       ▐░▌          ▐░▌▐░▌    ▐░▌▐░▌          ▐░▌       ▐░▌▐░▌       ▐░▌\n" +
                    "▐░▌          ▐░█▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄█░▌          ▐░▌ ▐░▌   ▐░▌▐░█▄▄▄▄▄▄▄▄▄ ▐░█▄▄▄▄▄▄▄█░▌▐░▌       ▐░▌\n" +
                    "▐░▌          ▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌          ▐░▌  ▐░▌  ▐░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░▌       ▐░▌\n" +
                    "▐░▌           ▀▀▀▀█░█▀▀▀▀ ▐░█▀▀▀▀▀▀▀█░▌          ▐░▌   ▐░▌ ▐░▌▐░█▀▀▀▀▀▀▀▀▀ ▐░█▀▀▀▀█░█▀▀ ▐░▌       ▐░▌\n" +
                    "▐░▌               ▐░▌     ▐░▌       ▐░▌          ▐░▌    ▐░▌▐░▌▐░▌          ▐░▌     ▐░▌  ▐░▌       ▐░▌\n" +
                    "▐░█▄▄▄▄▄▄▄▄▄      ▐░▌     ▐░▌       ▐░▌          ▐░▌     ▐░▐░▌▐░█▄▄▄▄▄▄▄▄▄ ▐░▌      ▐░▌ ▐░█▄▄▄▄▄▄▄█░▌\n" +
                    "▐░░░░░░░░░░░▌     ▐░▌     ▐░▌       ▐░▌          ▐░▌      ▐░░▌▐░░░░░░░░░░░▌▐░▌       ▐░▌▐░░░░░░░░░░▌ \n" +
                    " ▀▀▀▀▀▀▀▀▀▀▀       ▀       ▀         ▀            ▀        ▀▀  ▀▀▀▀▀▀▀▀▀▀▀  ▀         ▀  ▀▀▀▀▀▀▀▀▀▀  ");
            console.println();
        } else {
            console.println("" +
                    " ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄   ▄▄▄▄▄▄▄▄▄▄   ▄         ▄  ▄▄▄▄▄▄▄▄▄▄▄   ▄ \n" +
                    "▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░▌ ▐░░░░░░░░░░▌ ▐░▌       ▐░▌▐░░░░░░░░░░░▌ ▐░▌\n" +
                    "▐░█▀▀▀▀▀▀▀▀▀ ▐░█▀▀▀▀▀▀▀█░▌▐░█▀▀▀▀▀▀▀█░▌▐░█▀▀▀▀▀▀▀█░▌▐░█▀▀▀▀▀▀▀█░▌▐░▌       ▐░▌▐░█▀▀▀▀▀▀▀▀▀  ▐░▌\n" +
                    "▐░▌          ▐░▌       ▐░▌▐░▌       ▐░▌▐░▌       ▐░▌▐░▌       ▐░▌▐░▌       ▐░▌▐░▌           ▐░▌\n" +
                    "▐░▌ ▄▄▄▄▄▄▄▄ ▐░▌       ▐░▌▐░▌       ▐░▌▐░▌       ▐░▌▐░█▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄▄▄  ▐░▌\n" +
                    "▐░▌▐░░░░░░░░▌▐░▌       ▐░▌▐░▌       ▐░▌▐░▌       ▐░▌▐░░░░░░░░░░▌ ▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌ ▐░▌\n" +
                    "▐░▌ ▀▀▀▀▀▀█░▌▐░▌       ▐░▌▐░▌       ▐░▌▐░▌       ▐░▌▐░█▀▀▀▀▀▀▀█░▌ ▀▀▀▀█░█▀▀▀▀ ▐░█▀▀▀▀▀▀▀▀▀  ▐░▌\n" +
                    "▐░▌       ▐░▌▐░▌       ▐░▌▐░▌       ▐░▌▐░▌       ▐░▌▐░▌       ▐░▌     ▐░▌     ▐░▌            ▀ \n" +
                    "▐░█▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄█░▌     ▐░▌     ▐░█▄▄▄▄▄▄▄▄▄   ▄ \n" +
                    "▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░▌ ▐░░░░░░░░░░▌      ▐░▌     ▐░░░░░░░░░░░▌ ▐░▌\n" +
                    " ▀▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀▀   ▀▀▀▀▀▀▀▀▀▀        ▀       ▀▀▀▀▀▀▀▀▀▀▀   ▀ ");
        }

        console.disableInput(false); // re-enable input box after output is finished displaying
    }

    public void displayRecommendListening() {
        console.println("Personally, I'd recommend listening to the rest of the song. It's pretty good.");
        console.println("But when you're ready.");
    }

    public CompletableFuture<String> cheatMenu() {
        console.println();
        console.println();
        console.println();
        console.println("╔██♥☺▓▒▓▒♦▒██▒(ʘ ͟ʖ ʘ)══▓▒█♠♣▒█♥▒▓█▒♣▓█══♣▒▓▒☻█▒═▒♥═╗");
        console.println("▒                                                  ▓");
        console.println("█          ╔═╗╦ ╦╔═╗╔═╗╔╦╗  ╔╦╗╔═╗╔╗╔╦ ╦           ▒");
        console.println("║          ║  ╠═╣║╣ ╠═╣ ║   ║║║║╣ ║║║║ ║           ♦");
        console.println("☺          ╚═╝╩ ╩╚═╝╩ ╩ ╩   ╩ ╩╚═╝╝╚╝╚═╝           █");
        console.println("▓                                                  ║");
        console.println("╚═▓♣▒¯\\_(ツ)_/¯══▓▓█♥▓☺▒▒═══▒▒▒▓▓♣▓(͡° ͜ʖ ͡°)█════▓▒▓▓╝");

        console.println();
        console.print("Enter a cheat code: ");

//        console.disableInput(false); // re-enable input box after output is finished displaying
        return futureStringInput();
    }


    public void mazeAbilityText(final Hero theHero) {
//        console.println(theHero.getPassiveMsgs().remove(0));
    }

    public CompletableFuture<Character> quitOrContinueMenu() {
        console.println("Would you like to quit or continue? (1 for continue, 2 for quit)");
        console.print("Make your selection: ");

//        console.disableInput(false); // re-enable input box after output is finished displaying
        return futureCharInput();
    }

    public CompletableFuture<Character> mainMenu() {
        for (int i = 0; i < 25; i++) {
            console.println();
        }
        console.println("" +
                "8b    d8    db    88 88b 88     8b    d8 888888 88b 88 88   88 \n" +
                "88b  d88   dPYb   88 88Yb88     88b  d88 88__   88Yb88 88   88 \n" +
                "88YbdP88  dP__Yb  88 88 Y88     88YbdP88 88\"\"   88 Y88 Y8   8P \n" +
                "88 YY 88 dP\"\"\"\"Yb 88 88  Y8     88 YY 88 888888 88  Y8 `YbodP' \n\n");
//        console.println("╔═════════════════════╗");
//        console.println("║ 1. Continue         ║    ╔═════════════════════╗");
//        console.println("║ 2. Change Hero      ║    ║ 6. Save             ║");
//        console.println("║ 3. Change Name      ║    ║ 7. Quit             ║");
//        console.println("║ 4. Cheat Code Menu  ║    ║                     ║");
//        console.println("║                     ║    ╚═════════════════════╝");
//        console.println("╚═════════════════════╝");
        console.println("╔═════════════════════╗                           ");
        console.println("║ 1. Continue         ║    ╔═════════════════════╗");
        console.println("║ 2. Change Hero      ║    ║ 6. Save             ║");
        console.println("║ 3. Change Name      ║    ║ 7. Quit             ║");
        console.println("╚═════════════════════╝    ╚═════════════════════╝");

        console.print("Make your selection: ");

//        console.disableInput(false); // re-enable input box after output is finished displaying
        return futureCharInput();
    }

    public void codeSuccessMsg() {
        console.disableInput(true); // disable input box while output is currently being displayed

        console.println();
        console.typeAnimation(" -CODE ACCEPTED", TextSpeed.MEDIUM).join();
        console.println();

        console.disableInput(false); // re-enable input box after output is finished displaying
    }


    public void codeFailMsg() {
        console.disableInput(true); // disable input box while output is currently being displayed

        console.println();
        console.typeAnimation(" -CODE DENIED", TextSpeed.MEDIUM).join();
        console.println();

        console.disableInput(false); // re-enable input box after output is finished displaying
    }

    public void displayHeroChanged(final Hero theHero) {
        console.println();
        console.println(" -You are now the " + theHero.getType() + "!");
        console.println();
    }

    public void sentToMainMenuMsg() {
        console.println();
        displayChainSpacer();
        console.println(" You will now be sent to the main menu, where you can select");
        console.println(" a different hero and continue your adventure...");
        displayChainSpacer();
    }

    public void displayHeroNameChanged(final Hero theHero) {
        console.println();
        console.println(" -You are now ◄{ Sir " + theHero.getName() + "! }►");
        console.println();
    }

    public void displayHeroName(final String theHeroName) {
        console.println("◄{ Sir " + theHeroName + "! }►");
//                console.println("◢◤ " + heroFullName + " ◢◤");
        displayChainSpacer();
        console.println();
    }

    public void displayDifficultyLocked() {
        console.println();
        console.println(" -This difficulty is locked!");
        console.println();
    }

    public void displayPlayerTurn() {
        console.println("\n--------------------------- PLAYER'S TURN ---------------------------");
    }

    public void displayEnemyTurn() {
        console.println("\n--------------------------- ENEMY'S TURN ----------------------------");
    }

    public void bossAttack1() { // with new changes to GUI, attacks can finally depend on timing.
                                // to utilize, use thenApplyAsync() instead of join()  i.e. futureUserInput.thenApplyAsync()
                                // not perfect. still allows user to press buttons at different timings. make specific timespan they can press buttons

        // need to control missile process somehow
        // should it be controlled via a method in a boss class?
        Missile missile = new Missile();
        int distFromGround = missile.getDistToGround();
        for (int i = 0; i < distFromGround; i++) {
            missileMove(missile);
            Delay.pause(0.5f).join();
        }

    }
    private void drawGround() {
        console.println("    ┕━━━━━ 1 ━━━━━ 2 ━━━━━ 3 ━━━━━ 4 ━━━━━ 5 ━━━━━ 6 ━━━━━ 7 ━━━━━ 8 ━━━━━ 9 ━━━━━┙");
    }
    private void missileMove(final Missile theMissile) {
        console.clearMove();
        console.println(theMissile.drawMissile());
        drawGround();
        theMissile.closerToGround(); // this line is logical, should it be in the view?
    }

    public void bossAttack2() {
        // giant laser that fires down from the sky

        List<String> laser = new ArrayList<>();
        laser.add("    *   ");
        laser.add("   ***  ");
        laser.add("  ***** ");
        laser.add("*********");
        laser.add("*********");
        laser.add("*********");
        laser.add("*********");
        laser.add("*********");
        laser.add("*********");
        laser.add("*********");
        laser.add("*********");
        laser.add("*********");
        laser.add("*********");
        laser.add("*********");
        laser.add("*********");
        laser.add("*********");
        laser.add(" ******* ");

        console.lineAnimation(laser, 0.02f).join();

        // like above, but the logic is complex for winning or losing, so it should be contained as a class in the model
    }

    public void bossAttack3() { // for this attack, like the missile, print the screen every time to appear as if it's moving
        // horizontal flappy bird where you need to press the right key to dodge
        console.println("    ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓       ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓");
        console.println("    ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓       ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓");
        console.println("    ━━━━━━ 1 ━━━━━ 2 ━━━━━ 3 ━━━━━ 4 ━━━━━ 5 ━━━━━ 6 ━━━━━ 7 ━━━━━ 8 ━━━━━ 9 ━━━━━━ ");

        console.println("    ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓       ▓▓▓▓▓▓▓▓▓▓▓▓▓");
        console.println("    ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓       ▓▓▓▓▓▓▓▓▓▓▓▓▓");
        console.println("    ━━━━━━ 1 ━━━━━ 2 ━━━━━ 3 ━━━━━ 4 ━━━━━ 5 ━━━━━ 6 ━━━━━ 7 ━━━━━ 8 ━━━━━ 9 ━━━━━━ ");
    }

    public void bossAttack4() {
        // add stuff related to GUI like buttons that pop up, and you need to press them in time

        /*
                                   ________________
                              ____/ (  (    )   )  \___
                             /( (  (  )   _    ))  )   )\
                           ((     (   )(    )  )   (   )  )
                         ((/  ( _(   )   (   _) ) (  () )  )
                        ( (  ( (_)   ((    (   )  .((_ ) .  )_
                       ( (  )    (      (  )    )   ) . ) (   )
                      (  (   (  (   ) (  _  ( _) ).  ) . ) ) ( )
                      ( (  (   ) (  )   (  ))     ) _)(   )  )  )
                     ( (  ( \ ) (    (_  ( ) ( )  )   ) )  )) ( )
                      (  (   (  (   (_ ( ) ( _    )  ) (  )  )   )
                     ( (  ( (  (  )     (_  )  ) )  _)   ) _( ( )
                      ((  (   )(    (     _    )   _) _(_ (  (_ )
                       (_((__(_(__(( ( ( |  ) ) ) )_))__))_)___)
                       ((__)        \\||lll|l||///          \_))
                                (   /(/ (  )  ) )\   )
                              (    ( ( ( | | ) ) )\   )
                               (   /(| / ( )) ) ) )) )
                             (     ( ((((_(|)_)))))     )
                              (      ||\(|(|)|/||     )
                            (        |(||(||)||||        )
                              (     //|/l|||)|\\ \     )
                            (/ / //  /|//||||\\  \ \  \ _)
    -------------------------------------------------------------------------------

         */
    }

    public void passiveAbilityActivated(final Queue<String> theMsgs) {
        while (!theMsgs.isEmpty()) {
            console.println(theMsgs.remove());
        }
    }

    public void displayStuckifyMsg(final DungeonCharacter theCharacter) {
        console.println(" " + theCharacter.getName() + " is stuck and cannot move this turn!");
    }

    public void displayBlindedMsg(final DungeonCharacter theCharacter) {
        console.println(" " + theCharacter.getName() + " is blinded and has a high chance of missing on this move!");
    }

    public void displayPoisonedMsg(final DungeonCharacter theCharacter) {
        console.println(" " + theCharacter.getName() + " is poisoned and will take 10 damage when the turn ends!");
    }

    public void displaySilencedMsg(final DungeonCharacter theCharacter) {
        console.println(" " + theCharacter.getName() + " is silenced and cannot use their special this turn!");
    }

    public void displayVulnerableMsg(final DungeonCharacter theCharacter) {
        console.println(" " + theCharacter.getName() + " is vulnerable and will take 2x the damage on the next hit!");
    }

    public void displayWeakenedMsg(final DungeonCharacter theCharacter) {
        console.println(" " + theCharacter.getName() + " is weakened and will deal half the damage this turn!");
    }

    public void displaySilenced() {
        console.println(" You are silenced and cannot use your special this turn!");
    }

    public void displayRunAway(final Hero theHero) {
        console.println(" " + theHero.getName() + " attempted to run away...");
        Delay.pause(2).join();
    }


    public void displayRunAwaySuccess() {
        console.println(" You successfully ran away!");
    }

    public void displayRunAwayFail() {
        console.println(" You failed to run away!");
    }

    public void gameSaved() {
        console.println();
        console.println("[ [ [ [ [ [ [ [ [ [       ---Game saved!---       ] ] ] ] ] ] ] ] ] ]\n");
        console.println();
    }

    public void gameLoaded() {
        console.println();
        console.println("[ [ [ [ [ [ [ [ [ [       ---Game loaded!---        ] ] ] ] ] ] ] ] ] ]\n");
        console.println();
    }

    public void displayHeroHealthCool(final Hero theHero) {
        int health = theHero.getHealth();
        final int healthChunkCount = 8;
        final int healthChunkSize = Math.ceilDiv(theHero.getMaxHealth(), healthChunkCount);

        console.println();
        console.print("    ");
        for (int i = 0; i < healthChunkCount; i++) {
            if (health > healthChunkSize) {
                console.print("▣");
                health -= healthChunkSize;
            } else {
                console.print("▢");
            }
        }
        console.println();
    }

    public void println() {
        console.println();
    }

    public void noSavedGames() {
        console.println("\nCouldn't find any saved games! Try starting a new game instead.");
    }

    public void gameNotSaved() {
        console.println("\nCouldn't save game!");
    }

}


