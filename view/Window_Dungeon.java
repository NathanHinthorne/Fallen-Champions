package view;

import controller.DungeonGame;
import model.Dungeon;
import model.HeroTypes;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Window_Dungeon implements ActionListener {

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
    JLabel myPlayerLabel;

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

    public Window_Dungeon(Dungeon theDungeon, HeroTypes theType) {
        setHeroSprite(theType);
        setupFrame();
        addListeners(theDungeon);
        ArrowActions();
    }

    private void setupFrame() {
        Audio.playMusic(Audio.ambientSong, true);
        Audio.play(Audio.beginGame);

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

        myVolText.setBounds(450, 20, 120, 20);
        myVolume.setBounds(570, 20, 190, 20);
        myVolume.setValue(-10);
        myBagButton.setBounds(450,50,100,40);
        myBagButton.setFocusable(false);
        mySaveButton.setBounds(555,50,100,40);
        mySaveButton.setFocusable(false);
        myMenuButton.setBounds(660,50,100,40);
        myMenuButton.setFocusable(false);

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
        myPlayerLabel.setBounds(160,160,80,80);

        mainFrame.add(myPlayerLabel);
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

    private void addListeners(Dungeon theDungeon) {

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
                    Window_MainMenu menu = new Window_MainMenu(theDungeon);
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

    @Override
    public void actionPerformed(ActionEvent e) {

    }


}