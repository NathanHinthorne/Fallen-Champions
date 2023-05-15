package model;

public class AbstractionPillar extends Pillar {

    private static AbstractionPillar instance = new AbstractionPillar();

    private AbstractionPillar() {
        super();
    }

    public static synchronized AbstractionPillar getInstance() { // do we need an if statement checking if instance is null?
        return instance;
    }

    public String toString() {
        return "A";
    }
}
