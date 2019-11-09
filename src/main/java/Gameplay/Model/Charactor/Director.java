package Gameplay.Model.Charactor;


public class Director {

    public static HeroBuilder buildNew(String name) {
        HeroBuilder builder = new HeroBuilder();
        builder.setName(name);
        builder.setLevel(0);
        builder.setExperience(0);
        return builder;
    }

    public static Hero buildTiger(String name) {
        HeroBuilder builder = buildNew(name);
        builder.setAttack(35);
        builder.setDefense(20);
        builder.setHitPoint(100);
        builder.setHeroClass("Tiger");
        return builder.getHero();
    }

    public static Hero buildScorpion(String name) {
        HeroBuilder builder = buildNew(name);
        builder.setAttack(40);
        builder.setDefense(10);
        builder.setHitPoint(80);
        builder.setHeroClass("Scorpion");
        return builder.getHero();
    }

    public static Hero buildHunter(String name) {
        HeroBuilder builder = buildNew(name);
        builder.setAttack(30);
        builder.setDefense(20);
        builder.setHitPoint(90);
        builder.setHeroClass("Scorpion");
        return builder.getHero();
    }

    public static Hero buildDragon(String name) {
        HeroBuilder builder = buildNew(name);
        builder.setAttack(25);
        builder.setExperience(20);
        builder.setHitPoint(120);
        builder.setHeroClass("Dragon");
        return builder.getHero();
    }

    public static Hero buildSuperHero(String name) {
        HeroBuilder builder = buildNew(name);
        builder.setAttack(40);
        builder.setDefense(35);
        builder.setHitPoint(90);
        builder.setHeroClass("SuperHero");
        return builder.getHero();
    }

    public static Hero buildMiracle(String name) {
        HeroBuilder builder = buildNew(name);
        builder.setAttack(50);
        builder.setDefense(50);
        builder.setHitPoint(101);
        builder.setHeroClass("Miracle");
        return builder.getHero();
    }
}
