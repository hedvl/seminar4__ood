package se.kth.iv1350.pos.model;

/**
 * No discount is applied.
 */
public class NoDiscount implements DiscountStrategy {
    @Override
    public double calculateDiscount(double totalCost) {
        return 0.0;
    }
}
