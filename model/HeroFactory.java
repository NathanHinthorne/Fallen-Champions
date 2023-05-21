package model;

public class HeroFactory {

    private Hero myHero;

    public Hero buildHero(HeroTypes theValue) {

        if (theValue == HeroTypes.ENFORCER) {
            myHero = new Hero_Enforcer();
        } else if (theValue == HeroTypes.ROBOT) {
            myHero = new Hero_Robot();
        } else if (theValue == HeroTypes.SCIENTIST) {
            myHero = new Hero_Scientist();
        } else if (theValue == HeroTypes.WARRIOR) {
            myHero = new Hero_Scientist();
        } else if (theValue == HeroTypes.SUPPORT) {
            myHero = new Hero_Support();
        }

        return myHero;

    }

}
