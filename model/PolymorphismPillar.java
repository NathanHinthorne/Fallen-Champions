package model;

public class PolymorphismPillar extends Pillar {

    private static PolymorphismPillar instance = new PolymorphismPillar();

    private PolymorphismPillar() {
        super();
    }

    public static synchronized PolymorphismPillar getInstance() {
        return instance;
    }

    public String toString() {
        return "P";
    }
}
