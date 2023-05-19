package controller;
import model.*;

public class DungeonGame {

    private static HeroFactory HERO_FACTORY = new HeroFactory();
    private static MonsterFactory MONSTER_FACTORY = new MonsterFactory();

    public Hero myHero;
    public Monster myMonster;

    private DungeonGame() {

        /* These Hero and Monster factory uses are temporary,
         * to see if they work properly.
         */
        myHero = HERO_FACTORY.buildHero("");
        myMonster = MONSTER_FACTORY.buildMonster("");
    }

    public static void main(String[] theArgs) {
        System.out.println("GAME CREATED");
    }

}
