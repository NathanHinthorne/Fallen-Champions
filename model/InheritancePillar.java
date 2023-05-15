package model;

public class InheritancePillar extends Pillar {

    private static InheritancePillar instance = new InheritancePillar();

    private InheritancePillar() {
        super();
    }

    public static synchronized InheritancePillar getInstance() {
        return instance;
    }

    public String toString() {
        return "I";
    }
}
