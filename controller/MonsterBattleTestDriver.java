package controller;
import model.*;
import view.TextModeInterface;

public class MonsterBattleTestDriver {

    private static TextModeInterface myGame = new TextModeInterface();

    public static void main(String[] theArgs) {
        Hero theHero = new Hero_Warrior();
        Monster theMonster = new Monster_Skeleton();

        System.out.println("Player info:");
        System.out.println("HP:      " + theHero.getHitPoints());
        System.out.println("Max HP:  " + theHero.getMaxHitPoints());
        System.out.println("Speed:   " + theHero.getSpd());
        System.out.println("LHC:     " + theHero.getLowHitChance());
        System.out.println("HHC:     " + theHero.getHighHitChance());
        System.out.println("HCM:     " + theHero.getHitChanceMedian());
        System.out.println("HC:      " + theHero.getHitChance());
        System.out.println("Min DMG: " + theHero.getMinDamage());
        System.out.println("Max DMG: " + theHero.getMaxDamage());
        System.out.println("Sp CD:   " + theHero.getSpecialCooldown());

        System.out.println();

        System.out.println("Monster info:");
        System.out.println("HP:      " + theMonster.getHitPoints());
        System.out.println("Max HP:  " + theMonster.getMaxHitPoints());
        System.out.println("Speed:   " + theMonster.getSpd());
        System.out.println("LHC:     " + theMonster.getLowHitChance());
        System.out.println("HHC:     " + theMonster.getHighHitChance());
        System.out.println("HCM:     " + theMonster.getHitChanceMedian());
        System.out.println("HC:      " + theMonster.getHitChance());
        System.out.println("Min DMG: " + theMonster.getMinDamage());
        System.out.println("Max DMG: " + theMonster.getMaxDamage());
        System.out.println("Sp CD:   " + theMonster.getSpecialCooldown());

        System.out.println();

        MonsterBattle newBattle = new MonsterBattle(theHero, theMonster, myGame);

    }

}
