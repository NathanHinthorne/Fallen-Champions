package model;

public class HeroFactory {

    Hero myHero;

    public Hero buildHero(String theValue) {

        if (theValue.equalsIgnoreCase("Enforcer")) {
            myHero = new Hero_Enforcer();
        } else if (theValue.equalsIgnoreCase("Robot")) {
            myHero = new Hero_Robot();
        } else if (theValue.equalsIgnoreCase("Scientist")) {
            myHero = new Hero_Scientist();
        } else if (theValue.equalsIgnoreCase("Warrior")) {
            myHero = new Hero_Warrior();
        } else if (theValue.equalsIgnoreCase("Support")) {
            myHero = new Hero_Support();
        } else {
            /*
             * Temporary default value of the hero if
             * theValue doesn't equal.
             */
            myHero = new Hero_Warrior();
        }

        return myHero;

    }

}
