package view;

import controller.DungeonGame;
import controller.MonsterBattleTestDriver;
import model.Dungeon;
import model.HeroTypes;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window_ChooseHero {

    JFrame mainFrame = new JFrame("Fallen Champions V0.1");
    private Window_Dungeon myGameWindow;

    HeroTypes chosenType = null;

    ImageIcon myBackground;
    ImageIcon myHero1;
    ImageIcon myHero2;
    ImageIcon myHero3;
    ImageIcon myHero4;
    ImageIcon myHero5;

    JLabel myBackgroundField;
    JLabel myHeroChoice1 = new JLabel("Class:");
    JLabel myHeroChoice2 = new JLabel("None");
    JButton myHeroLabel1;
    JButton myHeroLabel2;
    JButton myHeroLabel3;
    JButton myHeroLabel4;
    JButton myHeroLabel5;

    JButton myGoButton = new JButton("Go!");

    public Window_ChooseHero(Dungeon theDungeon) {

        myBackground = loadImage("assets\\images\\game_background.png");
        myHero1 = loadImage("assets\\images\\game_sprite_hero_warrior.png");
        myHeroLabel1 = new JButton((myHero1));
        myHero2 = loadImage("assets\\images\\game_sprite_hero_enforcer.png");
        myHeroLabel2 = new JButton((myHero2));
        myHero3 = loadImage("assets\\images\\game_sprite_hero_support.png");
        myHeroLabel3 = new JButton((myHero3));
        myHero4 = loadImage("assets\\images\\game_sprite_hero_robot.png");
        myHeroLabel4 = new JButton((myHero4));
        myHero5 = loadImage("assets\\images\\game_sprite_hero_scientist.png");
        myHeroLabel5 = new JButton((myHero5));

        myBackgroundField = new JLabel(myBackground);
        myBackgroundField.setBounds(0,0,800,450);
        myHeroLabel1.setBounds(80,30,80,80);
        myHeroLabel2.setBounds(160,30,80,80);
        myHeroLabel3.setBounds(240,30,80,80);
        myHeroLabel4.setBounds(120,110,80,80);
        myHeroLabel5.setBounds(200,110,80,80);

        myHeroChoice1.setBounds(160,180,80,50);
        myHeroChoice2.setBounds(210,180,80,50);
        myGoButton.setBounds(170,220,60,50);
        myGoButton.setEnabled(false);

        mainFrame.add(myHeroLabel1);
        mainFrame.add(myHeroLabel2);
        mainFrame.add(myHeroLabel3);
        mainFrame.add(myHeroLabel4);
        mainFrame.add(myHeroLabel5);
        mainFrame.add(myHeroChoice1);
        mainFrame.add(myHeroChoice2);
        mainFrame.add(myGoButton);
        mainFrame.add(myBackgroundField);

        addListeners(theDungeon);

        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.setSize(400,350);
        mainFrame.setLayout(null);
        mainFrame.setVisible(true);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);
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

    private void addListeners(Dungeon theDungeon) {
        myHeroLabel1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Audio.play(Audio.menuOne);
                myHeroChoice2.setText("Warrior");
                chosenType = HeroTypes.WARRIOR;
                myGoButton.setEnabled(true);
            }
        });
        myHeroLabel2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Audio.play(Audio.menuOne);
                myHeroChoice2.setText("Enforcer");
                chosenType = HeroTypes.ENFORCER;
                myGoButton.setEnabled(true);
            }
        });
        myHeroLabel3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Audio.play(Audio.menuOne);
                myHeroChoice2.setText("Support");
                chosenType = HeroTypes.SUPPORT;
                myGoButton.setEnabled(true);
            }
        });
        myHeroLabel4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Audio.play(Audio.menuOne);
                myHeroChoice2.setText("Robot");
                chosenType = HeroTypes.ROBOT;
                myGoButton.setEnabled(true);
            }
        });
        myHeroLabel5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Audio.play(Audio.menuOne);
                myHeroChoice2.setText("Scientist");
                chosenType = HeroTypes.SCIENTIST;
                myGoButton.setEnabled(true);
            }
        });
        myGoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Audio.play(Audio.menuTwo);
                MonsterBattleTestDriver.setHero(chosenType);
                Window_Dungeon myGameWindow = new Window_Dungeon(theDungeon, chosenType);
                myGameWindow.setHeroSprite(chosenType);

                mainFrame.dispose();
            }
        });
    }

    public HeroTypes getChosenType() {
        return chosenType;
    }

}
