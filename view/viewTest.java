package view;
import Direction;

public class viewTest {
    public static void main(String[] theArgs) {

    }

    public void movePlayer(Direction dir) {

        // send a keypress (W, A, S, D) to the controller. The controller will give it to Dungeon's playerMove() method
        // code isn't complete yet
        myScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode() == KeyCode.W) {
                    playerMove(Direction.NORTH); // send this direction
                } else if (keyEvent.getCode() == KeyCode.A) {
                    playerMove(Direction.EAST);
                } else if (keyEvent.getCode() == KeyCode.S) {
                    playerMove(Direction.SOUTH);
                } else if (keyEvent.getCode() == KeyCode.D) {
                    playerMove(Direction.WEST);
                } else {
                    System.out.println("Wrong Key, please select W,A,S,D");
                }
            }
        });
    }
}
