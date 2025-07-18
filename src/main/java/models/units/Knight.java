package models.units;

import models.Position;

public class Knight extends Unit {
    private static int hitPoints=120;
    private static int attackPower=25;
    private static int paymentCost=16;
    private static int rationCost=6;
    public Knight(int kingdomId, Position position) {
        super(120, 3, 25, 2, 16, 6, 4, kingdomId, position);
    }

    @Override
    public boolean canMerge(Unit other) {
        return false;
    }

    @Override
    public Unit merge(Unit other) {
        throw new UnsupportedOperationException("Knights cannot be merged further");
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