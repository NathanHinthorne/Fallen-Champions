
import java.util.*;
import javafx.*;

public interface Character {

    final static Random MY_RANDOM = new Random();

    int hitPoints = 0;

    int atkSpd = 0;

    float theLowChance = 0.0f;

    float theHighChance = 100.0f;

    float hitChance = generateHitChance(theLowChance, theHighChance);

    int minDmg = 0;

    int maxDmg = 0;

    int specialCooldown = 0;

    static float generateHitChance(float theLowChance, float theHighChance) {
        return theLowChance + MY_RANDOM.nextFloat(theHighChance - theLowChance + 1);
    }

    /* Will be worked on later */
    public default int basicAtk(int theMinDamage) {
        if(hitPoints < 0)
        {
            throw new IllegalArgumentException("Hit Points cannot be less than zero");
        }

        theMinDamage = minDmg;

        getHitChance();

        if(getHitChance() > 75) {
            // Attack successful
            setHitPoints(getHitPoints() - theMinDamage);
        } else {
            // Attack failed
        }

        return minDmg;
    }

    /* Will be worked on later */
    public default int specialAtk() {
        if(hitPoints < 0)
        {
            throw new IllegalArgumentException("Hit Points cannot be less than zero");
        }

        return maxDmg;
    }
    public default int getHitPoints() {
        return hitPoints;
    }

    public default float getHitChance() {
        return hitChance;
    }

    public default void setHitChance(float theChance) {

        theChance = hitChance;
    }

    /* Will be worked on later */
    public default void setHitPoints(int hp) {
        if(hp < 0) { // Will need to be looked at later
            throw new IllegalArgumentException("Hit Points cannot be less than zero");
        }
        hp = hitPoints;
    }

}
