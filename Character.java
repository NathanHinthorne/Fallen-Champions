
import java.util.*;

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
        return hitPoints;
    }

    /* Will be worked on later */
    public default int specialAtk() {
        return atkSpd;
    }
    public default int getHitPoints() {
        return hitPoints;
    }

    /* Will be worked on later */
    public default void setHitPoints(int hp) {

    }

}
