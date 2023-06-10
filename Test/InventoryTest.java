import model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the Inventory.java class
 *
 * @author Brendan Smith
 * @version 6/9/23
 */
public class InventoryTest {

    Inventory bag = new Inventory();

    @Test
    public void testGetSize() {
        assertEquals(4, bag.getSize());
    }

    @Test
    public void testGetItemCount() {
        bag = new Inventory();
        bag.addToInventory(new HealthPotion());
        assertEquals(1, bag.getMyItemCount());
        bag.addToInventory(new HealthPotion());
        assertEquals(2, bag.getMyItemCount());
        bag.addToInventory(new HealthPotion());
        assertEquals(3, bag.getMyItemCount());
        bag.addToInventory(new HealthPotion());
        assertEquals(4, bag.getMyItemCount());
    }

    @Test
    public void testGetPillarCount() {
        bag = new Inventory();
        bag.addPillar(Pillars.INHERITANCE);
        assertEquals(1, bag.getMyPillarCount());
        bag.addPillar(Pillars.ENCAPSULATION);
        assertEquals(2, bag.getMyPillarCount());
        bag.addPillar(Pillars.ABSTRACTION);
        assertEquals(3, bag.getMyPillarCount());
        bag.addPillar(Pillars.POLYMORPHISM);
        assertEquals(4, bag.getMyPillarCount());
    }

    @Test
    public void testAddToInventory() {
        bag = new Inventory();
        bag.addToInventory(new HealthPotion());
        assertTrue(bag.getSize() == 1);
    }

    @Test
    public void testGetArray() {
        bag = new Inventory();
        assertEquals(new Inventory().toString(), bag.toString());
    }

    @Test
    public void testGetMaxSize() {
        assertEquals(4, bag.getMaxSize());
    }

    @Test
    public void testHasAllPillars() {
        bag = new Inventory();
        bag.addPillar(Pillars.INHERITANCE);
        bag.addPillar(Pillars.ENCAPSULATION);
        bag.addPillar(Pillars.ABSTRACTION);
        bag.addPillar(Pillars.POLYMORPHISM);
        assertTrue(bag.hasAllPillars());
    }

    @Test
    public void testConsumeItem() {
        Hero hero = new Hero_Warrior();
        HealthPotion potion1 = new HealthPotion();
        hero.getMyInventory().addToInventory(potion1);
        assertEquals(potion1, hero.getMyInventory().consumeItem(hero, 0));
    }

}
