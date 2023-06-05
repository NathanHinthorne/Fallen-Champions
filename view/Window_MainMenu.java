package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window_MainMenu implements ActionListener {

    JFrame mainFrame = new JFrame();
    JButton myStartButton = new JButton("New Game");
    JButton myLoadButton = new JButton("Continue");
    JButton myExitButton = new JButton("Exit Game");
    ImageIcon myLogo = new ImageIcon();

    public Window_MainMenu() {

        myStartButton.setBounds(340,350,120,40);
        myStartButton.setFocusable(false);
        myStartButton.addActionListener(this);

        myLoadButton.setBounds(340,400,120,40);
        myLoadButton.setFocusable(false);
        myLoadButton.addActionListener(this);
        myLoadButton.setEnabled(false);

        myExitButton.setBounds(340,450,120,40);
        myExitButton.setFocusable(false);
        myExitButton.addActionListener(this);

        mainFrame.add(myStartButton);
        mainFrame.add(myLoadButton);
        mainFrame.add(myExitButton);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800,650);
        mainFrame.setLayout(null);
        mainFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
