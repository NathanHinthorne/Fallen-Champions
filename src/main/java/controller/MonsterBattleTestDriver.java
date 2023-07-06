package controller;
import model.*;
import view.Audio;
import view.TUI;

import java.net.URISyntaxException;

/**
 * Driver for testing monster battles
 *
 * @author Brendan
 * @version 1.0 - 5/15/23
 */
public class MonsterBattleTestDriver {
    /**
     * Create new text mode interface for testing
     */
    private static final TUI game = new TUI();
    /**
     * Create new dungeon for testing
     */
    private static Dungeon dungeon;
    /**
     * Create new hero for testing
     */
    private static final Hero hero = HeroFactory.buildHero(HeroTypes.SWORDSMAN);
    /**
     * Create new monster for testing
     */
    private static final Monster monster = MonsterFactory.buildMonster(MonsterTypes.GREMLIN);

    // Unfortunately, we were not able to finish the GUI in time, but it's left here for reference and testing
//    public static void main(String[] theArgs) {
//        setupDungeon(2);
//        DungeonGame newGame = new DungeonGame();
//
//        Window_MainMenu mainMenu = new Window_MainMenu();
//    }


    public static void main(String[] theArgs) throws URISyntaxException {
        System.out.println("Player info:");
        System.out.println("HP:      " + hero.getHealth());
        System.out.println("Max HP:  " + hero.getMaxHealth());
        System.out.println("Speed:   " + hero.getSpeed());
        System.out.println("HC:      " + hero.getBasicChance());
        System.out.println("Min DMG: " + hero.getMinDmg());
        System.out.println("Max DMG: " + hero.getMaxDmg());
        System.out.println("Sp CD:   " + hero.getCooldown());

        System.out.println();

        System.out.println("Monster info:");
        System.out.println("HP:      " + monster.getHealth());
        System.out.println("Max HP:  " + monster.getMaxHealth());
        System.out.println("Speed:   " + monster.getSpeed());
        System.out.println("HC:      " + monster.getBasicChance());
        System.out.println("Min DMG: " + monster.getMinDmg());
        System.out.println("Max DMG: " + monster.getMaxDmg());
        System.out.println("Sp CD:   " + monster.getCooldown());

        System.out.println();

        Potion potion1 = new HealthPotion();

        hero.getInventory().addToInventory(potion1);
        hero.getInventory().addToInventory(new HealthPotion());
        hero.getInventory().addToInventory(new VisionPotion());
        hero.getInventory().addPillar(Pillars.INHERITANCE);

        System.out.println(hero.getInventory().toString());
        System.out.println("Item # - " + hero.getInventory().getMyItemCount());
        System.out.println("size   - " + hero.getInventory().getMaxSize());

        MonsterBattle battle = new MonsterBattle(hero, monster, game, false, Audio.getInstance());
        if (battle.newBattle()) {
            System.out.println("You Win!");
        } else {
            System.out.println("You Lose!");
        }

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
    }

}
