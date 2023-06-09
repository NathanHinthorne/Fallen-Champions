package model.unittest;

import model.HealthPotion;
import model.Hero;
import model.Hero_Warrior;
import org.junit.Test;
import static org.junit.Assert.*;

class HealthPotionTest {

    Hero hero = new Hero_Warrior();
    HealthPotion potion = new HealthPotion();

    @Test
    public void testHealingAmount() {
        assertTrue(potion.getHealingAmount() >= 150 &&
                potion.getHealingAmount() <= 250);
    }

    @Test
    public void testGetDetail() {
        hero.setHitPoints(hero.getHitPoints() - 10);
        potion.getDetail(hero);
        assertEquals(10, potion.getHealingAmount());
    }

}