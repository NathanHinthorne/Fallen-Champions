import model.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class DungeonTest {


    // How should I test this?

    Dungeon theDungeon = new Dungeon();

    @Test
    public void testDifficulty() {
        theDungeon.setDifficulty("HARD");
        assertEquals("HARD", theDungeon.getDifficulty());
    }

    @Test
    public void testMazeWidth() {
       theDungeon.setMazeWidth(5);
       assertEquals(5, theDungeon.getMyMazeWidth());
    }

    @Test
    public void testMazeHeight() {
        theDungeon.setMazeHeight(5);
        assertEquals(5, theDungeon.getMyMazeHeight());
    }

    @Test
    public void testHeroX() {
        theDungeon.setHeroX(3);
        assertEquals(3, theDungeon.getMyHeroX());
    }


    @Test
    public void testHeroY() {
        theDungeon.setHeroY(3);
        assertEquals(3, theDungeon.getMyHeroY());
    }


}