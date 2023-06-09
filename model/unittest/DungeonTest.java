package model.unittest;

import model.Dungeon;
import model.DungeonBuilder;
import org.junit.Test;
import static org.junit.Assert.*;

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