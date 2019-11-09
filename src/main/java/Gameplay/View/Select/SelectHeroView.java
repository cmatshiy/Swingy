package Gameplay.View.Select;

public interface SelectHeroView {

    void start();

    void updateInfo(String info);

    void errorManagementMsg(String messages);

    void openGame();

    void openBuildHero();

}
