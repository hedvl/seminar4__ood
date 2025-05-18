package se.kth.iv1350.pos.model;

/**
 * Applies a percentage-based discount.
 */
public class PercentageDiscount implements DiscountStrategy {
    private final double percentage; // e.g. 0.10 = 10%

    public PercentageDiscount(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public double calculateDiscount(double totalCost) {
        return totalCost * percentage;
    }
}
