package controller;
import model.*;
import model.test.TestHero;
import model.test.TestMonster;
import view.TextModeInterface;

public class MonsterBattleTestDriver {

    private static TextModeInterface myGame;

    public static void main(String[] theArgs) {
        Hero theHero = new TestHero();
        Monster theMonster = new TestMonster();
        myGame = new TextModeInterface();

        System.out.println("Player info:");
        System.out.println(theHero.toString() + " HP: " + theHero.getHitPoints());
        System.out.println("Monster info:");
        System.out.println(theMonster.toString() + " HP: " + theMonster.getHitPoints());

        MonsterBattle newBattle = new MonsterBattle(theHero, theMonster, myGame);

    }

}
