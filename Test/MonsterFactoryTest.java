import model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MonsterFactoryTest {
    private static MonsterFactory factory = new MonsterFactory();

    @Test
    public void testBuildMonsterEnforcer() {
        MonsterTypes e = MonsterTypes.SKELETON;
        Monster monster = factory.buildMonster(e);
        assertEquals(monster.getType(), e);
    }

    @Test
    public void testBuildMonsterWarrior() {
        MonsterTypes e = MonsterTypes.OGRE;
        Monster monster = factory.buildMonster(e);
        assertEquals(monster.getType(), e);
    }

    @Test
    public void testBuildMonsterSupport() {
        MonsterTypes e = MonsterTypes.BOSS;
        Monster monster = factory.buildMonster(e);
        assertEquals(monster.getType(), e);
    }

    @Test
    public void testBuildMonsterScientist() {
        MonsterTypes e = MonsterTypes.GREMLIN;
        Monster monster = factory.buildMonster(e);
        assertEquals(monster.getType(), e);
    }

    @Test
    public void testBuildMonsterRobot() {
        MonsterTypes e = MonsterTypes.WARLOCK;
        Monster monster = factory.buildMonster(e);
        assertEquals(monster.getType(), e);
    }
}