import model.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import model.test.TestMonster;
import org.junit.jupiter.api.Test;

public class MonsterTest {

    private static Monster monster;

    @Test
    public void testHeroHealth() {
        monster = new TestMonster();
        assertEquals(100, monster.getHitPoints());
    }

    @Test
    public void testHeroMinDamage() {
        monster = new TestMonster();
        assertEquals(20, monster.getMinDamage());
    }

    @Test
    public void testHeroMaxDamage() {
        monster = new TestMonster();
        assertEquals(70, monster.getMaxDamage());
    }


    @Test
    public void testAtkSpd() {
        monster = new TestMonster();
        assertEquals(8, monster.getSpd());
    }

    @Test
    public void testLowHitChance() {
        monster = new TestMonster();
        assertEquals(50, monster.getLowHitChance());
    }

    @Test
    public void testHighHitChance() {
        monster = new TestMonster();
        assertEquals(80, monster.getHighHitChance());
    }

    @Test
    public void testCooldown() {
        monster = new TestMonster();
        assertEquals(10, monster.getSpecialCooldown());
    }

    @Test
    public void testMinHeal() {
        monster = new TestMonster();
        assertEquals(10, monster.getMinHeal());
    }

    @Test
    public void testMaxHeal() {
        monster = new TestMonster();
        assertEquals(45, monster.getMaxHeal());
    }

    @Test
    public void testHealChance() {
        monster = new TestMonster();
        assertEquals(45, monster.getHealChance());
    }

}
