package Gameplay.Model;

import Gameplay.Model.Artifact.Armor;
import Gameplay.Model.Artifact.Artifact;
import Gameplay.Model.Artifact.Helm;
import Gameplay.Model.Artifact.Weapon;
import Gameplay.Model.Charactor.Evil;
import Gameplay.Model.Charactor.Hero;
import Gameplay.Model.Charactor.Character;
import Gameplay.Util.Point;

import java.util.concurrent.ThreadLocalRandom;

public class Game {
    private static Game instance = null;

    private Hero hero;
    private Point heroCoordinate;
    private int mapSize;
    private boolean[][] map;

    private Game() {
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public void initGame(Hero hero) {
        this.hero = hero;
        generateMap();
        generateEvils();
        putHero();
    }

    private void generateMap() {
        int level = hero.getLevel();
        mapSize = (level - 1) * 5 + 10 - (level % 2);
        map = new boolean[mapSize][mapSize];
    }

    private void generateEvils() {
        int random;
        int level = hero.getLevel();

        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                random = ThreadLocalRandom.current().nextInt(0, 101);
                if ((level + 1) * 10 >= random)
                    map[i][j] = true;
            }
        }
    }

    public Evil generateEvil() {
        int attack = ThreadLocalRandom.current().nextInt(hero.getAttack() - 20, hero.getAttack() + 2 + hero.getLevel());
        int defense = ThreadLocalRandom.current().nextInt(hero.getDefense() - 20, hero.getDefense() + 2 + hero.getLevel());
        int hitPoints = ThreadLocalRandom.current().nextInt(hero.getHitPoints() - 50, hero.getHitPoints() + 20 + hero.getLevel());

        attack = attack < 0 ? -attack : attack;
        defense = defense < 0 ? -defense : defense;
        hitPoints = hitPoints < 0 ? -hitPoints : hitPoints;
        Artifact artifact = generateArtifact();

        return new Evil("Evil", attack, defense, hitPoints, artifact);
    }

    private Artifact generateArtifact() {
        int random = ThreadLocalRandom.current().nextInt(0, 10);

        Artifact artifact = null;
        if (random == 0)
            artifact = new Weapon("Sword", ThreadLocalRandom.current().nextInt(1, 5 * (hero.getLevel() + 1)));
        else if (random == 1)
            artifact = new Helm("Pot", ThreadLocalRandom.current().nextInt(1, 3 * (hero.getLevel() + 1)));
        else if (random == 2)
            artifact = new Armor("Shield", ThreadLocalRandom.current().nextInt(1, 4 * (hero.getLevel() + 1)));
        return artifact;
    }

    public int fightResult(Character evil) {
        int xp = evil.getAttack() + evil.getDefense() + evil.getHitPoints();
        int random = ThreadLocalRandom.current().nextInt(0, 101);

        if (random < 3)
            return xp;
        else if (random > 98)
            return -1;

        return hero.fight(evil) ? xp : -1;
    }

    private void putHero() {
        heroCoordinate = new Point(mapSize / 2, mapSize / 2);
        map[heroCoordinate.getY()][heroCoordinate.getX()] = false;
    }

    public int getMapSize() {
        return mapSize;
    }

    public void setMapSize(int mapSize) {
        this.mapSize = mapSize;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Point getHeroCoordinate() {
        return heroCoordinate;
    }

    public void setHeroCoordinate(Point heroCoordinate) {
        this.heroCoordinate = heroCoordinate;
    }

    public boolean[][] getMap() {
        return map;
    }

    public void setMap(boolean[][] map) {
        this.map = map;
    }
}
