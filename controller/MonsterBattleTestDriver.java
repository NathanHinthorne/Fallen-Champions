package controller;
import model.*;
import model.test.TestHero;
import model.test.TestMonster;
import view.TUI;
import view.Window_MainMenu;

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
    private static final Hero hero = new TestHero();
    /**
     * Create new monster for testing
     */
    private static final Monster monster = new TestMonster();

    // Unfortunately, we were not able to finish the GUI in time, but it's left here for reference and testing
    public static void main(String[] theArgs) {
        setupDungeon(2);
        DungeonGame newGame = new DungeonGame();

        Window_MainMenu mainMenu = new Window_MainMenu(dungeon);
    }


//    public static void main(String[] theArgs) {
//        System.out.println("Player info:");
//        System.out.println("HP:      " + theHero.getHitPoints());
//        System.out.println("Max HP:  " + theHero.getMaxHitPoints());
//        System.out.println("Speed:   " + theHero.getSpd());
//        System.out.println("LHC:     " + theHero.getLowHitChance());
//        System.out.println("HHC:     " + theHero.getHighHitChance());
//        System.out.println("HCM:     " + theHero.getHitChanceMedian());
//        System.out.println("HC:      " + theHero.getHitChance());
//        System.out.println("Min DMG: " + theHero.getMinDamage());
//        System.out.println("Max DMG: " + theHero.getMaxDamage());
//        System.out.println("Sp CD:   " + theHero.getSpecialCooldown());
//
//        System.out.println();
//
//        System.out.println("Monster info:");
//        System.out.println("HP:      " + theMonster.getHitPoints());
//        System.out.println("Max HP:  " + theMonster.getMaxHitPoints());
//        System.out.println("Speed:   " + theMonster.getSpd());
//        System.out.println("LHC:     " + theMonster.getLowHitChance());
//        System.out.println("HHC:     " + theMonster.getHighHitChance());
//        System.out.println("HCM:     " + theMonster.getHitChanceMedian());
//        System.out.println("HC:      " + theMonster.getHitChance());
//        System.out.println("Min DMG: " + theMonster.getMinDamage());
//        System.out.println("Max DMG: " + theMonster.getMaxDamage());
//        System.out.println("Sp CD:   " + theMonster.getSpecialCooldown());
//
//        System.out.println();
//
//        Potion potion1 = new HealthPotion();
//
//        theHero.getMyInventory().addToInventory(potion1);
//        theHero.getMyInventory().addToInventory(new HealthPotion());
//        theHero.getMyInventory().addToInventory(new VisionPotion());
//        theHero.getMyInventory().addPillar(Pillars.INHERITANCE);
//
//        System.out.println(theHero.getMyInventory().toString());
//        System.out.println("Item # - " + theHero.getMyInventory().getMyItemCount());
//        System.out.println("size   - " + theHero.getMyInventory().getMaxSize());
//
//        MonsterBattle battle = new MonsterBattle(theHero, theMonster, myGame);
//        if (battle.newBattle(theHero, theMonster)) {
//            System.out.println("You Win!");
//        } else {
//            System.out.println("You Lose!");
//        }
//
//    }

    /**
     * Sets the hero type for testing
     * @param theType the type of hero to set
     */
    public static void setHero(HeroTypes theType) {
        hero.setType(theType);
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
