import model.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DungeonTest {

    Dungeon theDungeon = new Dungeon();

    @Test
    public void testDifficulty() {
        theDungeon.setDifficulty("Hard");
        assertEquals("Hard", theDungeon.getDifficulty());
    }

    @Test
    public void testMazeWidth() {
       theDungeon.setMazeWidth(5);
       assertEquals(5, theDungeon.getMazeWidth());
    }

    @Test
    public void testMazeHeight() {
        theDungeon.setMazeHeight(5);
        assertEquals(5, theDungeon.getMazeHeight());
    }

    @Test
    public void testHeroX() {
        theDungeon.setHeroX(3);
        assertEquals(3, theDungeon.getHeroX());
    }


    @Test
    public void testHeroY() {
        theDungeon.setHeroY(3);
        assertEquals(3, theDungeon.getMyHeroY());
    }


}