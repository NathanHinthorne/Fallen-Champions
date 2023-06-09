package model.unittest;

import model.HeroFactory;
import model.Hero;
import model.HeroTypes;
import org.junit.Test;
import static org.junit.Assert.*;

class HeroFactoryTest {

    private static HeroFactory factory = new HeroFactory();

    @Test
    public void testBuildHeroEnforcer() {
        HeroTypes e = HeroTypes.ENFORCER;
        Hero hero = factory.buildHero(e);
        assertEquals(hero.getType(), e);
    }

    @Test
    public void testBuildHeroWarrior() {
        HeroTypes e = HeroTypes.WARRIOR;
        Hero hero = factory.buildHero(e);
        assertEquals(hero.getType(), e);
    }

    @Test
    public void testBuildHeroSupport() {
        HeroTypes e = HeroTypes.SUPPORT;
        Hero hero = factory.buildHero(e);
        assertEquals(hero.getType(), e);
    }

    @Test
    public void testBuildHeroScientist() {
        HeroTypes e = HeroTypes.SCIENTIST;
        Hero hero = factory.buildHero(e);
        assertEquals(hero.getType(), e);
    }

    @Test
    public void testBuildHeroRobot() {
        HeroTypes e = HeroTypes.ROBOT;
        Hero hero = factory.buildHero(e);
        assertEquals(hero.getType(), e);
    }
}