package Gameplay.View.Build;

import Gameplay.Controller.BuildHeroController;
import Gameplay.Main;
import Gameplay.View.Game.GameViewGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BuildHeroViewGUI extends JPanel implements BuildHeroView {

    private JLabel heroNameLabel = new JLabel("Hero Name:");
    private JTextField heroNameField = new JTextField(10);
    private JButton buildHeroButton = new JButton("Create Hero");
    private String[] heroClasses = {"Tiger", "SuperHero", "Scorpion", "Hunter", "Dragon",
            "Miracle", "Grinder"};
    private JLabel heroClass = new JLabel("Class:");
    private JComboBox<String> classesComboBox = new JComboBox<>(heroClasses);
    private JEditorPane infoPane = new JEditorPane();

    private BuildHeroController controller;

    @Override
    public void start() {
        controller = new BuildHeroController(this);

        buildGUI();
    }

    private void buildGUI(){
        Main.getFrame().setTitle("Create Hero");
        this.setLayout(new GridBagLayout());
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints grid = new GridBagConstraints();
        grid.gridwidth = GridBagConstraints.REMAINDER;
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.insets = new Insets(5, 5, 5, 5);

        JPanel createHeroPanel = new JPanel();
        createHeroPanel.add(heroNameLabel);
        createHeroPanel.add(heroNameField);
        createHeroPanel.setVisible(true);
        this.add(createHeroPanel, grid);

        JPanel classPannel = new JPanel();
        classPannel.add(heroClass);
        classesComboBox.setSelectedIndex(0);
        classPannel.add(classesComboBox);
        classPannel.setVisible(true);
        this.add(classPannel, grid);


        infoPane.setEditable(false);
        infoPane.setFont(new Font("fixed-pitch", Font.PLAIN, 12));
        infoPane.setText("      attack defence hp\n" +
                "Tiger      35      20      100\n" +
                "SuperHero  40      10      80\n" +
                "Scorpion   30      20      90\n" +
                "Hunter     25      20      120\n" +
                "Dragon     40      35      90\n" +
                "Miracle    50      50      101\n" +
                "Grinder    25      15      80\n");
        infoPane.setPreferredSize(new Dimension(200, 120));
        infoPane.setMinimumSize(new Dimension(200, 120));
        this.add(infoPane, grid);

        this.add(buildHeroButton, grid);
        this.setVisible(true);

        Main.getFrame().setContentPane(this);
        Main.getFrame().revalidate();
        Main.showFrame();

        buildHeroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.onBuildButtonPressed(heroNameField.getText(), (String) classesComboBox.getSelectedItem());
            }
        });
    }

    @Override
    public void getUserInput() {

    }
    @Override
    public void errorManagementMsg(String message) {
        JOptionPane.showMessageDialog(Main.getFrame(), message);
    }

    @Override
    public void openGame() {
        this.setVisible(false);
        new GameViewGUI().start();
    }

}
