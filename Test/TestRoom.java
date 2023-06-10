import model.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class TestRoom {

    Room room;

    List<Monster> monsterList = new LinkedList<>();

    Set<Pillars> thePillars = new HashSet<>();

    @Test
    public void testMonsterPlacement() {
        room = new Room(0,0);

        MonsterFactory monsterFactory = new MonsterFactory();
        monsterList.add(monsterFactory.buildMonster(MonsterTypes.SKELETON));

        room.placeMonster(monsterList);
        assertTrue(room.hasMonster());
    }

    @Test
    public void testPitPlacement() {
        room = new Room(0,0);

        room.placePit();
        assertTrue(room.hasPit());
    }

    @Test
    public void testHeroPlacement() {
        room = new Room(0,0);

        room.placeHero();
        assertTrue(room.hasHero());
    }

    @Test
    public void testPotionPlacement() {
        room = new Room(0,0);

        room.placePotion();
        assertTrue(room.hasPotion());
    }

    @Test
    public void testExitPlacement() {
        room = new Room(0,0);

        room.placeExit();
        assertTrue(room.hasExit());
    }

    @Test
    public void testWallPlacement() {
        room = new Room(0,0);

        room.placeWall();
        assertTrue(room.hasWall());
    }

    @Test
    public void testPillarPlacement() {
        room = new Room(0,0);



        room.placePillar(thePillars);
        assertTrue(room.hasPillar());
    }
}
