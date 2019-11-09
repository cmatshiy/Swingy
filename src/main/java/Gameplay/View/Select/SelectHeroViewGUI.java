package Gameplay.View.Select;

import Gameplay.Controller.SelectHeroController;
import Gameplay.Main;
import Gameplay.View.Build.BuildHeroViewGUI;
import Gameplay.View.Game.GameViewGUI;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectHeroViewGUI extends JPanel implements SelectHeroView {

    private JEditorPane informationPane = new JEditorPane();
    private JButton selectButton = new JButton("Select");
    private JButton createButton = new JButton("Create");

    private SelectHeroController controller;
    private int lastSelectedIdx;

    @Override
    public void start() {
        controller = new SelectHeroController(this);

        buildUI();
    }

    private void buildUI() {
        Main.getFrame().setTitle("Select Hero");
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.darkGray);
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints grid = new GridBagConstraints();
        grid.gridwidth = GridBagConstraints.REMAINDER;
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.insets = new Insets(5, 5, 5, 5);

        String[] data = controller.getListData();
        final JList list = new JList(data);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        JScrollPane listScroll = new JScrollPane(list);
        listScroll.setPreferredSize(new Dimension(200, 200));
        listScroll.setMinimumSize(new Dimension(150, 150));
        this.add(listScroll);

        informationPane.setEditable(false);
        informationPane.setText("Select hero to see information");
        if (data.length == 0)
            informationPane.setText("No saved heroes");
        JScrollPane infoScroll = new JScrollPane(informationPane);
        infoScroll.setPreferredSize(new Dimension(200, 200));
        infoScroll.setMinimumSize(new Dimension(150, 150));
        this.add(infoScroll, grid);

        this.add(selectButton, grid);
        this.add(createButton, grid);
        selectButton.setEnabled(false);

        this.setVisible(true);

        Main.getFrame().setContentPane(this);
        Main.getFrame().revalidate();
        Main.showFrame();

        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    if (list.getSelectedIndex() != -1) {
                        controller.onListElementSelected(list.getSelectedIndex());
                        selectButton.setEnabled(true);
                        lastSelectedIdx = list.getSelectedIndex();
                    } else
                        selectButton.setEnabled(false);
                }
            }
        });
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.onSelectButtonPressed(lastSelectedIdx);
            }
        });
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.onBuildButtonPressed();
            }
        });
    }

    @Override
    public void updateInfo(String info) {
        informationPane.setText(info);
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

    @Override
    public void openBuildHero() {
        this.setVisible(false);
        new BuildHeroViewGUI().start();
    }
}



