package Gameplay.Model.Charactor;

import Gameplay.Model.Artifact.Artifact;

public class Evil extends Character {

    private Artifact artifact;

    public Evil(String name, int attack, int defense, int hitPoints, Artifact artifact) {
        super(name, attack, defense, hitPoints);
        this.artifact = artifact;
    }

    public Artifact getArtifact() {
        return artifact;
    }
}
