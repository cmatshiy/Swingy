package Gameplay.Controller;

import Gameplay.Exception.HeroValidationException;
import Gameplay.Model.Charactor.Hero;
import Gameplay.Model.Charactor.HeroFactory;
import Gameplay.Model.Game;
import Gameplay.Util.DataBase;
import Gameplay.View.Build.BuildHeroView;

public class BuildHeroController {

    private BuildHeroView view;
    private Game game;

    public BuildHeroController(BuildHeroView view) {
        this.view = view;
        game = Game.getInstance();
    }

    public void onBuildButtonPressed(String name, String heroClass) {
        Hero hero;
        try {
            hero = HeroFactory.newHero(name, heroClass);
            hero.validateHero();
        } catch (IllegalArgumentException | HeroValidationException e) {
            view.errorManagementMsg(e.getMessage());
            view.getUserInput();
            return;
        }

        int id = DataBase.insert(hero.getName(), hero.getHeroClass(), hero.getLevel(), hero.getExperience(), hero.getAttack(), hero.getDefense(), hero.getHitPoints());
        hero.setId(id);
        game.initGame(hero);
        view.openGame();
    }
}
