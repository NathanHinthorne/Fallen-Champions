package view;

import controller.DungeonGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window_Dungeon implements ActionListener {

    private DungeonGame myGame;

    JFrame mainFrame = new JFrame("Fallen Champions V0.1");

    JButton myBagButton = new JButton("Bag");
    JButton mySaveButton = new JButton("Save");
    JButton myMenuButton = new JButton("Menu");

    JButton myUp;
    JButton myDown;
    JButton myLeft;
    JButton myRight;

    JLabel myTile00;
    JLabel myTile01;
    JLabel myTile02;
    JLabel myTile10;
    JLabel myTile11;
    JLabel myTile12;
    JLabel myTile20;
    JLabel myTile21;
    JLabel myTile22;
    JLabel myDungeonBorder;

    ImageIcon myWall;
    ImageIcon myPath;
    ImageIcon myEntrance;
    ImageIcon myExit;
    ImageIcon myBorderImage;
    ImageIcon myUpArrow;
    ImageIcon myDownArrow;
    ImageIcon myLeftArrow;
    ImageIcon myRightArrow;

    ImageIcon myBackground;
    JLabel myBackgroundField;

    public Window_Dungeon(DungeonGame theGame) {
        myGame = theGame;
        setupFrame();
        addListeners();
    }

    private void setupFrame() {

        myWall = loadImage("assets\\images\\game_dungeon_wall.png");
        myPath = loadImage("assets\\images\\game_dungeon_path.png");
        myEntrance = loadImage("assets\\images\\game_dungeon_entrance.png");
        myExit = loadImage("assets\\images\\game_dungeon_exit.png");
        myBorderImage = loadImage("assets\\images\\game_dungeon_border.png");
        myBackground = loadImage("assets\\images\\game_background.png");
        myUpArrow = loadImage("assets\\images\\game_button_up.png");
        myDownArrow = loadImage("assets\\images\\game_button_down.png");
        myRightArrow = loadImage("assets\\images\\game_button_right.png");
        myLeftArrow = loadImage("assets\\images\\game_button_left.png");

        myUp    = new JButton(myUpArrow);
        myDown  = new JButton(myDownArrow);
        myLeft  = new JButton(myLeftArrow);
        myRight = new JButton(myRightArrow);

        myBackgroundField = new JLabel(myBackground);
        myDungeonBorder = new JLabel(myBorderImage);
        myDungeonBorder.setBounds(50,50,300,300);

        // Image file prep
        myBackgroundField.setBounds(0,0,800,450);

        myUp.setBounds(565,100,80,80);
        myUp.setFocusable(false);
        myDown.setBounds(565,260,80,80);
        myDown.setFocusable(false);
        myLeft.setBounds(485,180,80,80);
        myLeft.setFocusable(false);
        myRight.setBounds(645,180,80,80);
        myRight.setFocusable(false);

        myBagButton.setBounds(450,50,100,40);
        myBagButton.setFocusable(false);
        mySaveButton.setBounds(555,50,100,40);
        mySaveButton.setFocusable(false);
        myMenuButton.setBounds(660,50,100,40);
        myMenuButton.setFocusable(false);

        // Order is top to bottom
        mainFrame.add(myUp);
        mainFrame.add(myDown);
        mainFrame.add(myLeft);
        mainFrame.add(myRight);
        mainFrame.add(myBagButton);
        mainFrame.add(mySaveButton);
        mainFrame.add(myMenuButton);

        fetchTiles();

        myTile00.setBounds(65,65,90,90);
        myTile01.setBounds(155,65,90,90);
        myTile02.setBounds(245,65,90,90);
        myTile10.setBounds(65,155,90,90);
        myTile11.setBounds(155,155,90,90);
        myTile12.setBounds(245,155,90,90);
        myTile20.setBounds(65,245,90,90);
        myTile21.setBounds(155,245,90,90);
        myTile22.setBounds(245,245,90,90);

        mainFrame.add(myTile00);
        mainFrame.add(myTile01);
        mainFrame.add(myTile02);
        mainFrame.add(myTile10);
        mainFrame.add(myTile11);
        mainFrame.add(myTile12);
        mainFrame.add(myTile20);
        mainFrame.add(myTile21);
        mainFrame.add(myTile22);

        mainFrame.add(myDungeonBorder);


        mainFrame.add(myBackgroundField);

        // Finalizing mainFrame settings
        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.setSize(800,450);
        mainFrame.setLayout(null);
        mainFrame.setVisible(true);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);

    }

    private void fetchTiles() {
        myTile00 = new JLabel(myWall);
        myTile01 = new JLabel(myExit);
        myTile02 = new JLabel(myWall);
        myTile10 = new JLabel(myPath);
        myTile11 = new JLabel(myPath);
        myTile12 = new JLabel(myPath);
        myTile20 = new JLabel(myWall);
        myTile21 = new JLabel(myEntrance);
        myTile22 = new JLabel(myWall);
    }

    private void addListeners() {

        myMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Audio.play(Audio.testSound);
                int val = JOptionPane.showConfirmDialog(null,
                        "Are you sure you return to the main menu?", "Return To Menu",
                        JOptionPane.YES_NO_OPTION);


                if (val == JOptionPane.YES_OPTION) {
                    Window_MainMenu menu = new Window_MainMenu(myGame);
                    mainFrame.dispose();
                }
            }
        });

    }

    private ImageIcon loadImage(String theFileName) {
        ImageIcon ret;
        try {
            ret = new ImageIcon(getClass().getResource(theFileName));
            return ret;
        } catch (Exception e) {
            System.out.println(theFileName + " Not Found!");
        }
        return null;
    }

    private void ArrowActions() {
        myUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    private void setRoomOrWall(JLabel theTile) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }


}
