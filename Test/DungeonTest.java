import model.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class DungeonTest {


    // How should I test this?

    @Test
    public void testSmallDungeonBuilder() {
        Dungeon.SmallDungeonBuilder theSmallDungeon = new Dungeon.SmallDungeonBuilder();
        assertEquals(theSmallDungeon, theSmallDungeon);
    }

    @Test
    public void testMediumDungeonBuilder() {
        Dungeon.MediumDungeonBuilder theMediumDungeon = new Dungeon.MediumDungeonBuilder();
        assertEquals(theMediumDungeon, theMediumDungeon);
    }

    @Test
    public void testLargeDungeonBuilder() {
        Dungeon.LargeDungeonBuilder theLargeDungeon = new Dungeon.LargeDungeonBuilder();
        assertEquals(theLargeDungeon, theLargeDungeon);
    }

}