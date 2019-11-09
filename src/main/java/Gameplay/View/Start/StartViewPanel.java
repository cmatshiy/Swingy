package Gameplay.View.Start;

import Gameplay.Controller.StartController;
import Gameplay.Main;
import Gameplay.View.Build.BuildHeroViewPanel;
import Gameplay.View.Select.SelectHeroViewPanel;

import java.util.Scanner;

public class StartViewPanel implements StartView {

    private StartController controller;

    @Override
    public void start() {
        controller = new StartController(this);
        System.out.println("Let the game begin on the console");

        Scanner scanner = Main.getScanner();
        System.out.println();
        System.out.println("BUILD - to build/create the hero");
        System.out.println("SELECT - choose created or selected hero");
        System.out.println("SWITCH - switch to GUI view");
        System.out.println("Commands (BUILD, SELECT, SWITCH):");
        while (scanner.hasNext()) {
            String input = scanner.nextLine();

            if ("build".equalsIgnoreCase(input)) {
                controller.onBuildHeroButtonPressed();
                break;
            } else if ("select".equalsIgnoreCase(input)) {
                controller.onSelectHeroButtonPressed();
                break;
            } else if ("switch".equalsIgnoreCase(input)) {
                controller.onSwitchedButtonPressed();
                break;
            } else {
                System.out.println("Unknown command");
            }
        }
    }

    @Override
    public void openBuildHero() {
        new BuildHeroViewPanel().start();
    }

    @Override
    public void switchView() {
        new StartViewGUI().start();
    }

    @Override
    public void openSelectHero() {
        new SelectHeroViewPanel().start();
    }
}
