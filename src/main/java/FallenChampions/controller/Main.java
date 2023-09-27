package FallenChampions.controller;

public class Main {
    public static void main(String[] args) {

        // need to call Game from this class because JavaFX doesn't like to be called from a class extending Application
        Game.main(args);
    }
}
