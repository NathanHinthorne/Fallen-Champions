package view;
import model.*;
import controller.DungeonGame;

public class TextModeInterface {

    public static void menu() {

    }

    public boolean is_ongoing(int ongoing) {
        if(ongoing == 0) {
            return false;
        } else if(has_won(1) || has_lost(1)) {
            return false;
        }

        return true;
    }

    public boolean has_won(int win) {
        if(win == 1) {
            return true;
        }
        return false;
    }

    public boolean has_lost(int lose) {
        if(lose == 1) {
            return true;
        }
        return false;
    }

}
