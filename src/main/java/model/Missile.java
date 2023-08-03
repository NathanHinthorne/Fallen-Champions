package model;

import java.util.Random;

public class Missile {
    public static final Random RANDOM = new Random();
    private static final int SPEED = 2;
    private static final int MAX_DIST_TO_GROUND = 10;
    private int myDistToGround;
    private int myDistToWall;
    private String myWallWhitespace;
    private int myX;

    public Missile() {
        myDistToGround = MAX_DIST_TO_GROUND;
        myX = generateX();
        myDistToWall = (myX + 5) / 4; //TODO change this. the distFromWall should somehow be based off the x
        myWallWhitespace = calculateWhitespace();
    }

    private int generateX() {
        return RANDOM.nextInt(9) + 1;
    }

    private String calculateWhitespace() {
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < myDistToWall; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }

    public void closerToGround() {
        myDistToGround -= SPEED;
    }

    public boolean hasHitGround() {
        return myDistToGround <= 0;
    }

    public int getX() {
        return myX;
    }

    public int getDistFromWall() {
        return myDistToWall;
    }

    public int getDistToGround() {
        return myDistToGround;
    }

    // ascii art from https://www.asciiart.eu/buildings-and-places/castles
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(1000);

        if (!hasHitGround()) {
            sb.append(myWallWhitespace).append("           |\\**/|    \n");
            sb.append(myWallWhitespace).append("           \\ == /    \n");
            sb.append(myWallWhitespace).append("            |  |      \n");
            sb.append(myWallWhitespace).append("            |  |      \n");
            sb.append(myWallWhitespace).append("            \\  /     \n");
            sb.append(myWallWhitespace).append("             \\/      \n"); // might need a whitespace for missile to align with explosion
            for (int i = 0; i < myDistToGround; i++) {
                sb.append("\n");
            }
        } else {
            sb.append(myWallWhitespace).append("        _.-^^---....,,--           \n");
            sb.append(myWallWhitespace).append("    _--                  --_       \n");
            sb.append(myWallWhitespace).append("   <                        >)     \n");
            sb.append(myWallWhitespace).append("   |                         |     \n");
            sb.append(myWallWhitespace).append("    \\._                   _./      \n");
            sb.append(myWallWhitespace).append("       ```--. . , ; .--'''         \n");
            sb.append(myWallWhitespace).append("             | |   |               \n");
            sb.append(myWallWhitespace).append("          .-=||  | |=-.            \n");
            sb.append(myWallWhitespace).append("          `-=#$%&%$#=-'            \n");
            sb.append(myWallWhitespace).append("             | ;  :|               \n");
            sb.append(myWallWhitespace).append("    _____.,-#%&$@%#&#~,._____      \n");
        }
        return sb.toString();
    }
}
