import model.HealthPotion;
import model.Hero;
import model.Hero_Warrior;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class HealthPotionTest {


    @Test
    public void testHealingAmount() {
        Hero hero = new Hero_Warrior();
        HealthPotion potion = new HealthPotion();
        assertTrue(potion.getHealingAmount() >= 150 &&
                potion.getHealingAmount() <= 250);
    }

    @Test
    public void testGetDetail() {
        Hero hero = new Hero_Warrior();
        HealthPotion potion = new HealthPotion();
        hero.setHitPoints(hero.getHitPoints() - 10);
        potion.getDetail(hero);
        assertEquals(10, potion.getHealingAmount());
    }

}