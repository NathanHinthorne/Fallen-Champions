package view;

import model.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * This class will display the dungeon that the player
 * has been placed in, controls to navigate it, and
 * other actions the player can take, such as saving,
 * opening their inventory, returning to menu, or
 * lowering the music volume.
 *
 * @author Brendan Smith
 * @version 1.0 - 5/30/23
 */
public class Window_Dungeon implements ActionListener {

    private int myHp = 100;
    int mySize;
    JLabel[][] myTiles;

    JFrame mainFrame = new JFrame("Fallen Champions V0.1");
    JSlider myVolume = new JSlider(-20, 0);
    JLabel myVolText = new JLabel("Music Volume");

    JButton myBagButton = new JButton("Bag");
    JButton mySaveButton = new JButton("Save");
    JButton myMenuButton = new JButton("Menu");
    JButton myUp;
    JButton myDown;
    JButton myLeft;
    JButton myRight;
//
//    JLabel myTile00;
//    JLabel myTile01;
//    JLabel myTile02;
//    JLabel myTile10;
//    JLabel myTile11;
//    JLabel myTile12;
//    JLabel myTile20;
//    JLabel myTile21;
//    JLabel myTile22;



    JLabel myDungeonBorder;
    JLabel myPlayerLabel;
    JLabel myPlayerInfo;
    JLabel myPlayerHealth1;
    JLabel myPlayerHealth2;

    ImageIcon myWall;
    ImageIcon myPath;
    ImageIcon myEntrance;
    ImageIcon myExit;
    ImageIcon myBorderImage;
    ImageIcon myUpArrow;
    ImageIcon myDownArrow;
    ImageIcon myLeftArrow;
    ImageIcon myRightArrow;
    ImageIcon myPlayerSprite;
    ImageIcon myHealthPotionSprite;
    ImageIcon myVisionPotionSprite;

    ImageIcon myBackground;
    JLabel myBackgroundField;

    /**
     * This will perform necessary operations
     * to build the game window and assign the
     * components their proper values.
     *
     * @param theDungeon What the player will navigate
     * @param thePlayer Used to know what character sprite
     *                  to use
     */
    public Window_Dungeon(Dungeon theDungeon, Hero thePlayer) {
        Audio.playMusic(Audio.ambientSong, true);
        Audio.play(Audio.beginGame);
        myPlayerInfo = new JLabel("Class: " + thePlayer.getType().toString());
        setHeroSprite(thePlayer.getType());
        setupFrame();
        addListeners();
        ArrowActions();
        initializeTiles(theDungeon);
    }


    /**
     * This will set up every component that is going
     * to be added to the window, and then adds them
     * in order.
     */
    private void setupFrame() {

        // Finalizing mainFrame settings
        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.setSize(1600,900);
        mainFrame.setLayout(null);
        mainFrame.setVisible(true);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);

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
        myPlayerLabel = new JLabel(myPlayerSprite);
        myPlayerHealth1 = new JLabel("HP:");
        myPlayerHealth2 = new JLabel(String.valueOf(myHp));

        // Image file prep
        myBackgroundField.setBounds(0,0,1600,900);

        myUp.setBounds((mainFrame.getWidth()-235),100,80,80);
        myUp.setFocusable(false);
        myDown.setBounds((mainFrame.getWidth()-235),260,80,80);
        myDown.setFocusable(false);
        myLeft.setBounds((mainFrame.getWidth()-315),180,80,80);
        myLeft.setFocusable(false);
        myRight.setBounds((mainFrame.getWidth()-155),180,80,80);
        myRight.setFocusable(false);

        myVolText.setBounds((mainFrame.getWidth()-350), 20, 120, 20);
        myVolume.setBounds((mainFrame.getWidth()-230), 20, 190, 20);
        myVolume.setValue(-10);
        myBagButton.setBounds((mainFrame.getWidth()-350),50,100,40);
        myBagButton.setFocusable(false);
        mySaveButton.setBounds((mainFrame.getWidth()-245),50,100,40);
        mySaveButton.setFocusable(false);
        myMenuButton.setBounds((mainFrame.getWidth()-140),50,100,40);
        myMenuButton.setFocusable(false);

        myPlayerLabel.setBounds(160,160,80,80);
        myPlayerInfo.setBounds(60, 12, 100, 80);
        myPlayerHealth1.setBounds(280, 12, 40, 80);
        myPlayerHealth2.setBounds(320, 12, 60, 80);

        // Order is top to bottom
        mainFrame.add(myVolume);
        mainFrame.add(myVolText);
        mainFrame.add(myUp);
        mainFrame.add(myDown);
        mainFrame.add(myLeft);
        mainFrame.add(myRight);
        mainFrame.add(myBagButton);
        mainFrame.add(mySaveButton);
        mainFrame.add(myMenuButton);
        mainFrame.add(myPlayerHealth1);
        mainFrame.add(myPlayerHealth2);
        mainFrame.add(myPlayerLabel);
        mainFrame.add(myPlayerInfo);
        mainFrame.add(myDungeonBorder);
        mainFrame.add(myBackgroundField);

//        fetchTiles();

//        myTile00.setBounds(65,65,90,90);
//        myTile01.setBounds(155,65,90,90);
//        myTile02.setBounds(245,65,90,90);
//        myTile10.setBounds(65,155,90,90);
//        myTile11.setBounds(155,155,90,90);
//        myTile12.setBounds(245,155,90,90);
//        myTile20.setBounds(65,245,90,90);
//        myTile21.setBounds(155,245,90,90);
//        myTile22.setBounds(245,245,90,90);

//
//        mainFrame.add(myTile00);
//        mainFrame.add(myTile01);
//        mainFrame.add(myTile02);
//        mainFrame.add(myTile10);
//        mainFrame.add(myTile11);
//        mainFrame.add(myTile12);
//        mainFrame.add(myTile20);
//        mainFrame.add(myTile21);
//        mainFrame.add(myTile22);



    }

//    private void fetchTiles() {
//        myTile00 = new JLabel(myWall);
//        myTile01 = new JLabel(myExit);
//        myTile02 = new JLabel(myWall);
//        myTile10 = new JLabel(myPath);
//        myTile11 = new JLabel(myPath);
//        myTile12 = new JLabel(myPath);
//        myTile20 = new JLabel(myWall);
//        myTile21 = new JLabel(myEntrance);
//        myTile22 = new JLabel(myWall);
//    }

    /**
     * This will add the listeners to the controls
     * that are on screen
     */
    private void addListeners() {

        myBagButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Audio.play(Audio.menuTwo);
            }
        });

        mySaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Audio.play(Audio.menuTwo);
            }
        });

        myMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Audio.play(Audio.menuTwo);
                int val = JOptionPane.showConfirmDialog(null,
                        "Are you sure you return to the main menu?", "Return To Menu",
                        JOptionPane.YES_NO_OPTION);


                if (val == JOptionPane.YES_OPTION) {
                    Audio.stopAll();
                    Window_MainMenu menu = new Window_MainMenu();
                    mainFrame.dispose();
                }
            }
        });

        myVolume.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (myVolume.getValue() == myVolume.getMinimum()) {
                    Audio.setVolume(-80);
                } else {
                    Audio.setVolume(myVolume.getValue());
                }
            }
        });

    }

    /**
     * Method to make loading images more concise.
     * @param theFileName Path to the image
     * @return The new ImageIcon
     */
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

    /**
     * Method to  assign the proper character sprite
     * for the player
     * @param theType The type of hero
     */
    public void setHeroSprite(HeroTypes theType) {

        if (theType == HeroTypes.ENFORCER) {
            myPlayerSprite = loadImage("assets\\images\\game_sprite_hero_enforcer.png");
        } else if (theType == HeroTypes.ROBOT) {
            myPlayerSprite = loadImage("assets\\images\\game_sprite_hero_robot.png");
        } else if (theType == HeroTypes.SCIENTIST) {
            myPlayerSprite = loadImage("assets\\images\\game_sprite_hero_scientist.png");
        } else if (theType == HeroTypes.WARRIOR) {
            myPlayerSprite = loadImage("assets\\images\\game_sprite_hero_warrior.png");
        } else if (theType == HeroTypes.SUPPORT) {
            myPlayerSprite = loadImage("assets\\images\\game_sprite_hero_support.png");
        } else { // Failsafe default value
            myPlayerSprite = loadImage("assets\\images\\game_sprite_hero_default.png");
        }
    }

    /**
     * Adds action listeners to the arrows and
     * controls the character as needed.
     */
    private void ArrowActions() {
        myUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playStep();
            }
        });
        myLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playStep();
            }
        });
        myDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playStep();
            }
        });
        myRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playStep();
            }
        });
    }

    private void setRoomOrWall(JLabel theTile) {

    }

    /**
     * chooses a random step sound effect to play
     * when the arrow is pressed.
     */
    private void playStep() {
        Random rand = new Random();
        int choice = rand.nextInt(4);
        if (choice == 0) {
            Audio.play(Audio.step1);
        } else if (choice == 1) {
            Audio.play(Audio.step2);
        } else if (choice == 2) {
            Audio.play(Audio.step3);
        } else if (choice == 3) {
            Audio.play(Audio.step4);
        }
    }

    /**
     * updates the HP on the player.
     * @param theIn HP value
     */
    public void setHpGameMenu(int theIn) {
        myHp = theIn;
    }

    /**
     * Adds tiles of dungeon to view.
     * @param theRooms - the dungeon input.
     */
    public void initializeTiles(final Dungeon theRooms) {

        int x = 65;
        int y = 65;

        System.out.println(theRooms.toString());

        mySize = theRooms.getMaze().length;
        myTiles = new JLabel[mySize][mySize];

        for (int i = 0; i < mySize; i ++) {
            for (int j = 0; j < mySize; j ++) {
                if (theRooms.getMaze()[i][j].hasWall()) {
                    myTiles[i][j] = new JLabel(myWall);
                } else if (theRooms.getMaze()[i][j].isEmpty()) {
                    myTiles[i][j] = new JLabel(myPath);
                } else { // temporarily fills the rest with paths, ignoring items.
                    myTiles[i][j] = new JLabel(myPath);
                }
                myTiles[i][j].setBounds(x,y,90,90);
                mainFrame.add(myTiles[i][j]);
                x += 90;
                y += 90;
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }


}
