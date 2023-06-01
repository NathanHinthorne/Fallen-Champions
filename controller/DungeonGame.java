package controller;

import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import model.*;
import org.w3c.dom.Text;
import view.*;

public class DungeonGame {

    private static HeroFactory HERO_FACTORY = new HeroFactory();
    private static MonsterFactory MONSTER_FACTORY = new MonsterFactory();
    private static Dungeon myDungeon;
    private static TextModeInterface myGame;
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

        // setup
        setupDungeon();
        updateMap();

        // game loop
//        while (!gameOver) {

            // get player input from view (can be movement input, inventory input, save, exit, etc.)
                // if movement input, check if valid
                    // if invalid, prompt player to try again
                    // if valid, move player
                        // if player moves into a room with a monster, start battle
                            // if player wins, continue game, earn rewards
                            // if player loses, gameOver = true
                // if inventory input, check if valid
                    // if invalid, prompt player to try again
                    // if valid, use item
                // if save, save game
                // if exit, exit game

//            updateMap();
//            updatePlayerView();

//        }


    }


    public static void setupDungeon() {
        // small dungeon = easy difficulty
        // medium dungeon = medium difficulty
        // large dungeon = hard difficulty

        DungeonBuilder theSmallDungeonBuilder = new Dungeon.SmallDungeonBuilder(); // declare the builder
        myDungeon = theSmallDungeonBuilder.buildDungeon(); // use it to build the dungeon
    }

    /**
     * Displays the dungeon map
     */
    public static void updateMap() { System.out.println(myDungeon.toString()); }

    /**
     * Displays what the player should be seeing in the GUI
     */
    public static void updatePlayerView() { System.out.println(myDungeon.getView()); }

}
