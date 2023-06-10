package controller;
import model.*;
import model.test.TestHero;
import model.test.TestMonster;
import view.TUI;


public class MonsterBattleDemoDriver {
    /**
     * Create new hero for testing
     */
    private static final Hero hero = new TestHero();
    /**
     * Create new monster for testing
     */
    private static final Monster monster = new TestMonster();
    /**
     * Create new text mode interface for testing
     */
    private static final TUI game = new TUI();

    public static void main(String[] theArgs) {



        System.out.println("Player info:");
        System.out.println("HP:      " + hero.getHitPoints());
        System.out.println("Max HP:  " + hero.getMaxHitPoints());
        System.out.println("Speed:   " + hero.getSpd());
        System.out.println("LHC:     " + hero.getLowHitChance());
        System.out.println("HHC:     " + hero.getHighHitChance());
        System.out.println("HCM:     " + hero.getHitChanceMedian());
        System.out.println("HC:      " + hero.getHitChance());
        System.out.println("Min DMG: " + hero.getMinDamage());
        System.out.println("Max DMG: " + hero.getMaxDamage());
        System.out.println("Sp CD:   " + hero.getSpecialCooldown());

        System.out.println();

        System.out.println("Monster info:");
        System.out.println("HP:      " + monster.getHitPoints());
        System.out.println("Max HP:  " + monster.getMaxHitPoints());
        System.out.println("Speed:   " + monster.getSpd());
        System.out.println("LHC:     " + monster.getLowHitChance());
        System.out.println("HHC:     " + monster.getHighHitChance());
        System.out.println("HCM:     " + monster.getHitChanceMedian());
        System.out.println("HC:      " + monster.getHitChance());
        System.out.println("Min DMG: " + monster.getMinDamage());
        System.out.println("Max DMG: " + monster.getMaxDamage());
        System.out.println("Sp CD:   " + monster.getSpecialCooldown());

        System.out.println();

        Potion potion1 = new HealthPotion();

        hero.getMyInventory().addToInventory(potion1);
        hero.getMyInventory().addToInventory(new HealthPotion());
        hero.getMyInventory().addToInventory(new VisionPotion());
        hero.getMyInventory().addPillar(Pillars.INHERITANCE);

        System.out.println(hero.getMyInventory().toString());
        System.out.println("Item # - " + hero.getMyInventory().getMyItemCount());
        System.out.println("size   - " + hero.getMyInventory().getMaxSize());

        MonsterBattle battle = new MonsterBattle(hero, monster, game, false);
        if (battle.newBattle()) {
            System.out.println("You Win!");
        } else {
            System.out.println("You Lose!");
        }
    }

}
