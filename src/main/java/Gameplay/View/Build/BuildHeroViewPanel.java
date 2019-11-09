package Gameplay.View.Build;

import Gameplay.Controller.BuildHeroController;
import Gameplay.Main;
import Gameplay.View.Game.GameViewPanel;

import java.util.Scanner;

/**
 * in this file the user would able to key in something using a keyboard
 */
public class BuildHeroViewPanel implements BuildHeroView{

    private BuildHeroController controller;

    @Override
    public void start() {
        controller = new BuildHeroController(this);

        getUserInput();
    }

    @Override
    public void getUserInput() {
        Scanner scanner = Main.getScanner();

        System.out.println("To create hero enter his name and class.");
        System.out.println("Enter name:");
        String name = scanner.nextLine();
        System.out.println("Classes: attack  defense   hp\n" +
                "Tiger      35      20      100\n" +
                "SuperHero  40      10      80\n" +
                "Scorpion   30      20      90\n" +
                "Hunter     25      20      120\n" +
                "Dragon     40      35      90\n" +
                "Miracle    50      50      101\n" +
                "Grinder    25      15      80\n" +
                "Enter class name: ");
        String heroClass = scanner.nextLine();

        System.out.println("BUILD - to create hero with previously entered Name and Class");
        System.out.println("Command (BUILD):");
        while (scanner.hasNext()) {
            String input = scanner.nextLine();

            if ("build".equalsIgnoreCase(input)) {
                controller.onBuildButtonPressed(name, heroClass);
                break;
            } else {
                System.out.println("Unknown command");
            }
        }
    }

    @Override
    public void errorManagementMsg(String message) {
        System.out.println("Error: " + message);
    }

    @Override
    public void openGame() {
        new GameViewPanel().start();
    }
}

