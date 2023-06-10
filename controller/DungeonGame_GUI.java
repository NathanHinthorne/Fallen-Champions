package controller;
import model.*;
import view.*;

import java.awt.*;

/**
 * Test driver for the GUI version of the game.
 *
 * @author Brendan Smith
 * @author Nathan Hinthorne
 * @version 1.0 - 6/9/23
 */
public class DungeonGame_GUI {

    /**
     * The game's cheat mode. If true, the player will be given extra items and abilities.
     */
    private final static boolean CHEAT_MODE = true;

    /**
     * The game's debug mode. If true, the player will be able to skip past enemies and see the whole map.
     */
    private final static boolean DEBUG_MODE = false;

    /**
     * the dungeon to be used in the game
     */
    private static Dungeon dungeon; // from model

    /**
     * the hero to be used in the game
     */
    private static Hero hero; // move to main and make non static?

    /**
     * whether the game is over
     */
    private static boolean gameOver = false; // set to true when player dies or exits dungeon

    /**
     * Used to start the game by the GUI
     */
    private static boolean gameBegun = false; // called by GUI to start the game

    /**
     * whether the exit is open
     */
    private static boolean exitIsOpen = false; // set to true when player collects all pillars

    private static Window_MainMenu menu;
    private static Window_ChooseHero choose;
    private static Window_Dungeon game;

    public static void main(String[] theArgs) {
        menu = new Window_MainMenu();
    }

    /**
     * sets up the dungeon for testing
     * @param theDifficulty the difficulty of the dungeon
     */
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

        System.out.println("Difficulty: " + theDifficulty);
        System.out.println(dungeon.toString());
    }

    /**
     * Sets the hero type for testing
     * @param theType the type of hero to set
     */
    public static void setHero(HeroTypes theType) {
        if (theType == HeroTypes.WARRIOR) {
            hero = new Hero_Warrior();
        } else if (theType == HeroTypes.ENFORCER) {
            hero = new Hero_Enforcer();
        } else if (theType == HeroTypes.SCIENTIST) {
            hero = new Hero_Scientist();
        } else if (theType == HeroTypes.ROBOT) {
            hero = new Hero_Robot();
        } else if (theType == HeroTypes.SUPPORT) {
            hero = new Hero_Support();
        }
        System.out.println("Hero: " + hero.getType().toString());
    }

    public static void startGame() {
        game = new Window_Dungeon(dungeon, hero);
    }

}
