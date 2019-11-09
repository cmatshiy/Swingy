package Gameplay.Controller;
import Gameplay.Exception.HeroValidationException;
import Gameplay.Model.Charactor.Hero;
import Gameplay.Model.Game;
import Gameplay.Util.DataBase;
import Gameplay.View.Select.SelectHeroView;

import java.util.ArrayList;


public class SelectHeroController {

    private SelectHeroView view;
    private Game game;

    public SelectHeroController(SelectHeroView view) {
        this.view = view;
        game = Game.getInstance();
    }

    public void onListElementSelected(int idx) {
        Hero hero = DataBase.selectHeroById(idx + 1);
        view.updateInfo(hero.toString());
    }

    public String[] getListData() {
        ArrayList<String> list = DataBase.selectAll();
        String[] listArr = new String[list.size()];
        listArr = list.toArray(listArr);
        return listArr;
    }

    public void onSelectButtonPressed(int idx) {
        Hero hero;
        try {
            hero = DataBase.selectHeroById(idx + 1);
            hero.validateHero();
        } catch (HeroValidationException e) {
            view.errorManagementMsg(e.getMessage());
            return;
        }

        game.initGame(hero);
        view.openGame();
    }

    public void onBuildButtonPressed() {
        view.openBuildHero();
    }
}
