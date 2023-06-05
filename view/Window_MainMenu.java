package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window_MainMenu implements ActionListener {

    JFrame mainFrame = new JFrame("Fallen Champions V0.1");
    JButton myStartButton = new JButton("New Game");
    JButton myLoadButton = new JButton("Continue");
    JButton myExitButton = new JButton("Exit Game");

    ImageIcon myLogo;
    JLabel myImageField;

    public Window_MainMenu() {

        try {
            myLogo = new ImageIcon(getClass().getResource("assets\\images\\game_logo.png"));
            myImageField = new JLabel(myLogo);
        } catch (Exception e) {
            System.out.println("Image Not Found!");
        }

        myImageField.setBounds(129,40,542,214);

        myStartButton.setBounds(340,250,120,40);
        myStartButton.setFocusable(false);
        myStartButton.addActionListener(this);

        myLoadButton.setBounds(340,300,120,40);
        myLoadButton.setFocusable(false);
        myLoadButton.addActionListener(this);
        myLoadButton.setEnabled(false);

        myExitButton.setBounds(340,350,120,40);
        myExitButton.setFocusable(false);
        myExitButton.addActionListener(this);

        mainFrame.add(myImageField);
        mainFrame.add(myStartButton);
        mainFrame.add(myLoadButton);
        mainFrame.add(myExitButton);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800,450);
        mainFrame.setLayout(null);
        mainFrame.setVisible(true);
        mainFrame.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
