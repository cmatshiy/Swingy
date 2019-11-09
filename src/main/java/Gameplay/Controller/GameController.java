package Gameplay.Controller;

import Gameplay.Model.Artifact.Armor;
import Gameplay.Model.Artifact.Artifact;
import Gameplay.Model.Artifact.Helm;
import Gameplay.Model.Artifact.Weapon;
import Gameplay.Model.Charactor.Evil;
import Gameplay.Model.Charactor.Hero;
import Gameplay.Model.Game;
import Gameplay.Util.DataBase;
import Gameplay.Util.Point;
import Gameplay.View.Game.GameView;


public class GameController {

    private GameView view;
    private Game game;
    private Point previousPosition;

    public GameController(GameView view) {
        this.view = view;
        game = Game.getInstance();
        previousPosition = new Point(0, 0);
    }

    public void onStart() {
        view.update(game);
    }

    public void onPrintMap() {
        view.printMap(game.getMap(), game.getHeroCoordinate());
        view.update(game);
    }

    public void onMove(String direction) {
        int x = game.getHeroCoordinate().getX();
        int y = game.getHeroCoordinate().getY();
        previousPosition.setX(x);
        previousPosition.setY(y);

        switch (direction.toUpperCase()) {
            case "NORTH":
                y--;
                break;
            case "EAST":
                x++;
                break;
            case "SOUTH":
                y++;
                break;
            case "WEST":
                x--;
                break;
        }

        if (x < 0 || y < 0 || x >= game.getMapSize() || y >= game.getMapSize()) {
            winGame();
            return;
        }

        game.getHeroCoordinate().setX(x);
        game.getHeroCoordinate().setY(y);

        if (game.getMap()[y][x]) {
            EvilCollision();
        }

        if (game.getHero().getHitPoints() > 0)
            view.update(game);
    }

    private void winGame() {
        view.showMessage("You win! " + game.getMapSize() * 100 + "xp!");
        addExperience(game.getMapSize() * 100);
        updateDataBase();
        view.gameFinished();
    }

    private void updateDataBase() {
        Hero hero = game.getHero();
        DataBase.updateHero(hero);
    }

    private void EvilCollision() {
        view.getEvilCollisionInput();
    }


    private void setArtifact(Artifact artifact) {
        if (artifact != null) {
            if (artifact instanceof Weapon) {
                if (game.getHero().getWeapon() == null || view.replaceArtifact("your weapon: " + game.getHero().getWeapon() + ", found: " + artifact)) {
                    game.getHero().equipWeapon((Weapon) artifact);
                    view.showMessage("You got new weapon: " + artifact);
                }
            } else if (artifact instanceof Helm) {
                if (game.getHero().getHelm() == null || view.replaceArtifact("your helmet: " + game.getHero().getHelm() + ", found: " + artifact)) {
                    game.getHero().equipHelm((Helm) artifact);
                    view.showMessage("You got new helm: " + artifact);
                }
            } else if (artifact instanceof Armor) {
                if (game.getHero().getArmor() == null || view.replaceArtifact("your armor: " + game.getHero().getArmor() + ", found: " + artifact)) {
                    game.getHero().equipArmor((Armor) artifact);
                    view.showMessage("You got new armor: " + artifact);
                }
            }
        }
    }


    public void onFight() {
        Evil evil = game.generateEvil();
        int xp = game.fightResult(evil);

        if (xp >= 0) {
            view.showMessage("You win, and got it " + xp + "xp.");
            addExperience(xp);
            game.getMap()[game.getHeroCoordinate().getY()][game.getHeroCoordinate().getX()] = false;
            setArtifact(evil.getArtifact());
        } else {
            view.showMessage("Game over :(");
            view.gameFinished();
        }
    }

    private void addExperience(int addXP) {
        int level = game.getHero().getLevel();
        game.getHero().addExperience(addXP);
        if (level != game.getHero().getLevel())
            view.showMessage("Level GO UP!\nHP, attack & defense were increased!");
    }

    public void onSwitchButtonPressed() {
        view.switchView();
    }

    public void onRun() {

    }
}
