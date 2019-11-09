package Gameplay.Model.Charactor;

import Gameplay.Model.Artifact.Armor;
import Gameplay.Model.Artifact.Helm;
import Gameplay.Model.Artifact.Weapon;

public class HeroBuilder {

    private int id;
    private String name;
    private int attack;
    private int defense;
    private int hitPoints;
    private String heroClass;
    private int level;
    private int experience;
    private Weapon weapon;
    private Armor armor;
    private Helm helm;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setHitPoint(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public void setHeroClass(String heroClass) {
        this.heroClass = heroClass;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
    }

    public void setHelm(Helm helm) {
        this.helm = helm;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public Hero getHero() {
        return new Hero(name, attack, defense, hitPoints, id, heroClass, level, experience, weapon, armor, helm);
    }
}
