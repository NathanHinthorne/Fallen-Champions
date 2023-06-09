import model.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class DungeonTest {

    Dungeon.MediumDungeonBuilder theMediumDungeon = new Dungeon.MediumDungeonBuilder();
    Dungeon.LargeDungeonBuilder theLargeDungeon = new Dungeon.LargeDungeonBuilder();

    // How should I test this?

    @Test
    public void testSmallDungeonBuilder() {
        Dungeon.SmallDungeonBuilder theSmallDungeon = new Dungeon.SmallDungeonBuilder();
        assertEquals(theSmallDungeon, theSmallDungeon);
    }

}