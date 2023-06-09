package model;

public abstract class Potion implements java.io.Serializable {


    public int getDetail(Hero thePlayer) {
        return 0;
    }

    public abstract String type();

    public void effect(final Hero thePlayer) {
    }
}
