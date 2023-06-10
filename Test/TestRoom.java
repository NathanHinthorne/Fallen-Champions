import model.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

public class TestRoom {

    Room room;

    List<Monster> monsterList = new LinkedList<>();

    @Test
    public void testMonsterPlacement() {
        room = new Room(0,0);

        MonsterFactory monsterFactory = new MonsterFactory();
        monsterList.add(monsterFactory.buildMonster(MonsterTypes.SKELETON));

        room.placeMonster(monsterList);
        assertTrue(room.hasMonster());
    }
}
