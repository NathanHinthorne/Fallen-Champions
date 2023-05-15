package model;

public class EncapsulationPillar extends Pillar {

    private static EncapsulationPillar instance = new EncapsulationPillar();

    private EncapsulationPillar() {
        super();
    }

    public static synchronized EncapsulationPillar getInstance() {
        return instance;
    }

    public String toString() {
        return "E";
    }
}
