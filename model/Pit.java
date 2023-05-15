package model;

public class Pit {

    private boolean myIsVisible;


    public Pit(final boolean theIsVisible) { // for testing purposes, make all pits visible
        myIsVisible = theIsVisible;
    }

    public Pit() {
        myIsVisible = false;
    }

    public void fall() {
        // TODO make hero lose health

        myIsVisible = true;
    }

    public String toString() {
        if (myIsVisible) {
            return "P";
        } else {
            return Room.EMPTY;
        }
    }
}
