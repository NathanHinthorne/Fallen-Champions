package model;

public abstract class PotionDefensive implements Potion {

    /**
     * The potion effect
     * @param thePlayer the potion effect for the Hero to consume
     */
    public abstract void effect(final Hero thePlayer);


}
