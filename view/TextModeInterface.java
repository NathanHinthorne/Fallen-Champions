package view;
import model.*;
import controller.DungeonGame;

public class TextModeInterface {

    public void menu() {

    }

    public boolean is_ongoing(int ongoing) {
        if(ongoing == 1) {
            return true;
        } else if(ongoing == 0) {
            return false;
        }

        return false;
    }



}
