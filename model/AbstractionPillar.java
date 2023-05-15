package model;

public class AbstractionPillar extends Pillar {

    private static AbstractionPillar instance = new AbstractionPillar();

    private AbstractionPillar() {
        super();
    }

    public static synchronized AbstractionPillar getInstance() {
        return instance;
    }

    public String toString() {
        return "A";
    }
}
