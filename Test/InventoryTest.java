import model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InventoryTest {

    @Test
    public void testGetSize() {
        Inventory bag = new Inventory();
        assertEquals(4, bag.getSize());
    }
}
