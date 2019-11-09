package Gameplay.View.Game;


import Gameplay.Model.Game;
import Gameplay.Util.Point;

public interface GameView {


    void start();

    void printMap(boolean[][] map, Point heroCoordinate);

    void update(Game game);

    void gameFinished();

    void showMessage(String message);


    void getEvilCollisionInput();

    boolean replaceArtifact(String message);

    void switchView();
}

