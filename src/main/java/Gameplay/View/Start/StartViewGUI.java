package Gameplay.View.Start;


import Gameplay.Controller.StartController;
import Gameplay.Main;
import Gameplay.View.Build.BuildHeroViewGUI;
import Gameplay.View.Select.SelectHeroViewGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartViewGUI extends JPanel implements StartView {


    private JButton BuildHeroButton = new JButton("Build Hero");
    private JButton selectHeroButton = new JButton("Select Hero");
    private JButton switchViewButton = new JButton("Switch to Console");

    private StartController controller;

    @Override
    public void start() {
        controller = new StartController(this);

        buildGUI();
    }
    private void buildGUI() {

        Main.getFrame().setTitle("LET THE GAME BEGIN");
        this.setLayout(new GridBagLayout());
        this.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        this.setBackground(Color.DARK_GRAY);


        GridBagConstraints grid = new GridBagConstraints();
        grid.gridwidth = GridBagConstraints.REMAINDER;
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.insets = new Insets(5, 5, 5, 5);

        this.add(BuildHeroButton, grid);
        this.add(selectHeroButton, grid);
        this.add(switchViewButton, grid);

        this.setVisible(true);
        Main.getFrame().setContentPane(this);
        Main.getFrame().revalidate();
        Main.showFrame();

        BuildHeroButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.onBuildHeroButtonPressed();
            }
        });
        selectHeroButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.onSelectHeroButtonPressed();
            }
        });
        switchViewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.onSwitchedButtonPressed();
            }
        });

    }

    @Override
    public void openBuildHero() {
        this.setVisible(false);         //close the current frame
        new BuildHeroViewGUI().start(); //open the new frame of gui


    }

    @Override
    public void switchView(){
        Main.hideFrame();
        new StartViewPanel().start();

    }

    @Override
    public void openSelectHero() {
        this.setVisible(false);                       //it's must close the current frame
        new SelectHeroViewGUI().start();             //this.SelectHeroViewGUI().start();//its must open a frame called "SelectHeroViewGui" where I would able to select a Hero

    }
}
