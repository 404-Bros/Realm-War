package main.java.models.units;

import main.java.models.Position;

public class Spearman extends Unit {
    private static int hitPoints=50;
    private static int attackPower=10;
    private static int paymentCost=4;
    private static int rationCost=2;

    public Spearman(int kingdomId, Position position) {
        super(50, 2, 10, 2, 4, 2, 2, kingdomId, position);
    }

    @Override
    public boolean canMerge(Unit other) {
        return other instanceof Spearman && this.getKingdomId() == other.getKingdomId();
    }

    @Override
    public Unit merge(Unit other) {
        if (this.getHitPoints() != 50 || other.getHitPoints() !=50) {
            throw new IllegalStateException("Cannot merge these units");
        }
        if (!canMerge(other)) {
            throw new IllegalArgumentException("Cannot merge these units");
        }
        return new Swordman(this.getKingdomId(), this.getPosition());
    }
    public static int getStaticHitPoints() {
        return hitPoints;
    }


    public static int getStaticAttackPower() {
        return attackPower;
    }


    public static int getStaticPaymentCost() {
        return paymentCost;
    }


    public static int getStaticRationCost() {
        return rationCost;
    }
}