package Gameplay.View.Game;

import Gameplay.Controller.GameController;
import Gameplay.Main;
import Gameplay.Model.Game;
import Gameplay.Util.Point;

import javax.swing.*;
import javax.swing.JComboBox;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * A JComboBox, which lets the user choose one of several choice
 */
public class GameViewGUI extends JPanel implements GameView {

    private String[] directions = {"North", "East", "South", "West"};
    private JComboBox<String> directionComboBox = new JComboBox<>(directions);
    private JButton moveButton = new JButton("Move");
    private JButton switchButton = new JButton("Switch to console");

    private JEditorPane informationPane = new JEditorPane();
    private JEditorPane mapPane = new JEditorPane();

    private GameController controller;

    @Override
    public void start() {
        controller = new GameController(this);

        buildGI();
        controller.onStart();
    }
    private void buildGI(){
        Main.getFrame().setTitle("Game");
        this.setLayout(new GridBagLayout());
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints grid = new GridBagConstraints();
        grid.gridwidth = GridBagConstraints.REMAINDER;
        grid.fill = GridBagConstraints.HORIZONTAL;

        informationPane.setEditable(false);
        informationPane.setText("Click Hero to get information");
        informationPane.setPreferredSize(new Dimension(220, 190));
        informationPane.setMinimumSize(new Dimension(200, 200));
        this.add(informationPane, grid);
        grid.insets = new Insets(5, 5, 5, 5);

        mapPane.setEditable(false);
        mapPane.setText("Map");
        JScrollPane mapScroll = new JScrollPane(mapPane);
        mapScroll.setPreferredSize(new Dimension(300, 300));
        mapScroll.setMinimumSize(new Dimension(200, 200));

        directionComboBox.setSelectedIndex(0);
        this.add(directionComboBox, grid);
        this.add(moveButton, grid);
        this.add(switchButton, grid);

        this.setVisible(true);
        Main.getFrame().setContentPane(this);
        Main.getFrame().revalidate();
        Main.showFrame();

        moveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.onMove((String) directionComboBox.getSelectedItem());
            }
        });
        switchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.onSwitchButtonPressed();
            }
        });
    }

    public void printMap(boolean[][] map, Point heroCoordinate) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("Map %dx%dx\n", map.length, map.length));
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[i].length; i++) {
                if(heroCoordinate.getX() == j && heroCoordinate.getY() == i)
                    stringBuilder.append("! ");
                else if (map[i][j])
                    stringBuilder.append("* ");
                else
                    stringBuilder.append(". ");
            }
            stringBuilder.append("\n");
        }
        mapPane.setText(stringBuilder.toString());
    }


    @Override
    public void update(Game game) {
        informationPane.setText(game.getHero().toString() +
                "Position: " + "(" + game.getHeroCoordinate().getX() +
                "," + game.getHeroCoordinate().getY() + ")");

        printMap(game.getMap(), game.getHeroCoordinate());
    }

    @Override
    public void gameFinished() {
        Main.hideFrame();
        Main.getFrame().dispose();
        Main.closeConnections();
    }

    @Override
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(Main.getFrame(), message);
    }

    @Override
    public void getEvilCollisionInput() {
        Object options[] = {"Fight", "Run"};

        int result = JOptionPane.showOptionDialog(Main.getFrame(),
                "You take position occupied by Evil",
                "Fight or run?", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (result == JOptionPane.YES_OPTION)
            controller.onFight();
        else
            controller.onRun();
    }

    @Override
    public boolean replaceArtifact(String replaceMessage) {
        Object options[] = {"Replace", "Leave"};

        int result = JOptionPane.showOptionDialog(Main.getFrame(),
                "You can replace if you like " + replaceMessage + "?",
                "Replace or leave?", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        return result == JOptionPane.YES_OPTION;
    }

    @Override
    public void switchView() {
        Main.hideFrame();
        new GameViewPanel().start();
    }

}