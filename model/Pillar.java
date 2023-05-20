package model;


public abstract class Pillar implements Collectable {

    //TODO use singleton design pattern to ensure only one pillar of each type exists
    //TODO if it doesn't make sense to do this, move that singleton to each child pillar class

    public Pillar() {
        //TODO initialize the pillar
    }

    @Override
    public void collect() {
        //TODO cause the pillar to be collected:
        // 1. remove it from the world
        // 2. add it to the player's inventory
    }
}
