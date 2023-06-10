import model.Dungeon;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DungeonBuilderTest {

    static Dungeon smallDungeon;
    static Dungeon mediumDungeon;
    static Dungeon largeDungeon;

    @BeforeAll
    public static void setUp() {
        Dungeon.SmallDungeonBuilder smallDungeonBuilder = new Dungeon.SmallDungeonBuilder();
        smallDungeon = smallDungeonBuilder.buildDungeon();

        Dungeon.MediumDungeonBuilder mediumDungeonBuilder = new Dungeon.MediumDungeonBuilder();
        mediumDungeon = mediumDungeonBuilder.buildDungeon();

        Dungeon.LargeDungeonBuilder largeDungeonBuilder = new Dungeon.LargeDungeonBuilder();
        largeDungeon = largeDungeonBuilder.buildDungeon();
    }


    @Test
    public void testDifficulty() {
        assertEquals("Easy", smallDungeon.getDifficulty());
        assertEquals("Medium", mediumDungeon.getDifficulty());
        assertEquals("Hard", largeDungeon.getDifficulty());
    }

    @Test
    public void testMazeWidth() {
        assertEquals(7, smallDungeon.getMazeWidth());
        assertEquals(10, mediumDungeon.getMazeWidth());
        assertEquals(15, largeDungeon.getMazeWidth());
    }

    @Test
    public void testMazeHeight() {
        assertEquals(7, smallDungeon.getMazeHeight());
        assertEquals(10, mediumDungeon.getMazeHeight());
        assertEquals(15, largeDungeon.getMazeHeight());
    }


}
