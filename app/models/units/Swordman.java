package models.units;

public class Swordman extends Unit {
    public Swordman(int kingdomId) {
        super(80, 2, 15, 1, 8, 4, 3, kingdomId);
    }

    @Override
    public boolean canMerge(Unit other) {}

    @Override
    public Unit merge(Unit other) {}
}