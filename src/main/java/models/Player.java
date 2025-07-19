package models;

public class Player {
    private String name;
    private Kingdom kingdom;


    public Player(String name, Kingdom kingdom) {
        this.name = name;
        this.kingdom = kingdom;
    }

    public String getName() {
        return name;
    }

    public Kingdom getKingdom() {
        return kingdom;
    }

    public void setKingdom(Kingdom kingdom) {
        this.kingdom = kingdom;
    }
}
