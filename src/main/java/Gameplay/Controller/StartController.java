package Gameplay.Controller;

import Gameplay.View.Start.StartView;

public class StartController {

    private StartView view;


    public StartController(StartView view) {
        this.view = view;
    }

    public void onBuildHeroButtonPressed() {
        view.openBuildHero();
    }

    public void onSwitchedButtonPressed() {
        view.switchView();
    }

    public void onSelectHeroButtonPressed() {
        view.openSelectHero();
    }
}
