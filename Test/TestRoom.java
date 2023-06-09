import model.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

public class TestRoom {

    Dungeon.SmallDungeonBuilder theSmallDugeon = new Dungeon.SmallDungeonBuilder();

    Room theRoom;

    Monster monster;

    List<Monster> monsters = new LinkedList<>();

    @Test
    public void testMonsterPlacement() {
        theSmallDugeon.buildDungeon();
        theRoom = new Room(5,5);
        theRoom.placeMonster(monsters);
        assertTrue(theRoom.hasMonster());
    }
}
