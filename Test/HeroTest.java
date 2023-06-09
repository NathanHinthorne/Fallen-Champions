import model.*;
import controller.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import model.test.TestHero;
import model.test.TestMonster;
import org.junit.jupiter.api.Test;

public class HeroTest {

    private static Hero Hero;



    @Test
    public void testHeroHealth() {
        Hero = new TestHero();
        assertEquals(100, Hero.getHitPoints());
    }

    @Test
    public void testHeroMinDamage() {
        Hero = new TestHero();
        assertEquals(20, Hero.getMinDamage());
    }

    @Test
    public void testHeroMaxDamage() {
        Hero = new TestHero();
        assertEquals(70, Hero.getMaxDamage());
    }


    @Test
    public void testAtkSpd() {
        Hero = new TestHero();
        assertEquals(8, Hero.getSpd());
    }

    @Test
    public void testLowHitChance() {
        Hero = new TestHero();
        assertEquals(50, Hero.getLowHitChance());
    }

    @Test
    public void testHighHitChance() {
        Hero = new TestHero();
        assertEquals(80, Hero.getHighHitChance());
    }

    @Test
    public void testCooldown() {
        Hero = new TestHero();
        assertEquals(0, Hero.getSpecialCooldown());
    }

}
