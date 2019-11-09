package Gameplay.Model.Charactor;

public abstract class HeroFactory {

    public static Hero newHero(String name, String heroClass) {
        switch (heroClass.toUpperCase()) {
            case "TIGER":
                return Director.buildTiger(name);
            case "SUPERHERO":
                return Director.buildSuperHero(name);
            case "SCORPION":
                return Director.buildScorpion(name);
            case "HUNTER":
                return Director.buildHunter(name);
            case "DRAGON":
                return Director.buildDragon(name);
            case "MIRACLE":
                return Director.buildMiracle(name);
            default:
                throw new IllegalArgumentException("Invalid hero class: " + heroClass);
        }
    }
}
