
import java.util.*;
import javafx.*;
import javafx.scene.control.Alert;

public interface Character {

    final static Random MY_RANDOM = new Random();

    int hitPoints = 0;

    int atkSpd = 0;

    float hitChance = 0;

    int minDmg = 0;

    int maxDmg = 0;

    int specialCooldown = 0;

    /* Will be worked on later */
    public default int basicAtk() {
        return minDmg;
    }

    /* Will be worked on later */
    public default int specialAtk() {
        return maxDmg;
    }
    public default int getHitPoints() {
        return hitPoints;
    }

    /* Will be worked on later */
    public default void setHitPoints(int hp) {
        if(hp < 0) {
            // Code from https://stackoverflow.com/questions/39149242/how-can-i-do-an-error-messages-in-javafx
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("GAME ERROR!!!");
            errorAlert.setContentText("Hit Points cannot be below zero!");
            throw new IllegalArgumentException();
        }
        hp = hitPoints;
    }

}
