package view;

import controller.DungeonGame;
import model.Dungeon;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This will display the Main Menu to the user, so that they can
 * choose to start a new game, load a previous one, or exit.
 *
 * @author Brendan Smith
 * @version 1.0 - 5/31/23
 */
public class Window_MainMenu implements ActionListener {

    Window_ChooseHero myChooseHeroWindow;
    JFrame mainFrame = new JFrame("Fallen Champions V0.1");
    JButton myStartButton = new JButton("New Game");
    JButton myLoadButton = new JButton("Continue");
    JButton myExitButton = new JButton("Quit Game");

    ImageIcon myLogo;
    JLabel myImageField;

    ImageIcon myBackground;
    JLabel myBackgroundField;
    JOptionPane myExitConfirm = new JOptionPane("Are you sure?");

    /**
     * Constructor for the Main Menu.
     * @param theDungeon the input dungeon.
     */
    public Window_MainMenu(Dungeon theDungeon) {

        setupFrame();
        myStartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Audio.play(Audio.menuTwo);
                myChooseHeroWindow = new Window_ChooseHero(theDungeon);
                mainFrame.dispose();
            }
        });

        myLoadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Audio.play(Audio.testSound);
            }
        });

        myExitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Audio.play(Audio.menuTwo);
                int val = JOptionPane.showConfirmDialog(myExitConfirm,
                        "Are you sure you want to quit?", "Quit Game",
                        JOptionPane.YES_NO_OPTION);


                if (val == JOptionPane.YES_OPTION) {
                    System.exit(616);
                }
            }
        });

    }

    /**
     * Sets up the necessary components and adds them to
     * the frame.
     */
    private void setupFrame() {
        try {
            myLogo = new ImageIcon(getClass().getResource("assets\\images\\game_logo.png"));
            myImageField = new JLabel(myLogo);
        } catch (Exception e) {
            System.out.println("game_logo.png Not Found!");
        }
        try {
            myBackground = new ImageIcon(getClass().getResource("assets\\images\\game_background.png"));
            myBackgroundField = new JLabel(myBackground);
        } catch (Exception e) {
            System.out.println("game_background.png Not Found!");
        }

        // Image file prep
        myImageField.setBounds(129,40,542,214);
        myBackgroundField.setBounds(0,0,800,450);

        myStartButton.setBounds(340,250,120,40);
        myStartButton.setFocusable(false);

        myLoadButton.setBounds(340,300,120,40);
        myLoadButton.setFocusable(false);
        myLoadButton.setEnabled(false);

        myExitButton.setBounds(340,350,120,40);
        myExitButton.setFocusable(false);
        myExitButton.addActionListener(this);

        // Order it top to bottom when being added
        mainFrame.add(myImageField);
        mainFrame.add(myStartButton);
        mainFrame.add(myLoadButton);
        mainFrame.add(myExitButton);
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

    /**
     * Returns the next window.
     * @return Choose Hero Window.
     */
    public Window_ChooseHero getMyChooseHeroWindow() {
        return myChooseHeroWindow;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
