package se.kth.iv1350.pos.model;

/**
 * Applies a fixed amount discount.
 */
public class FixedDiscount implements DiscountStrategy {
    private final double amount;

    public FixedDiscount(double amount) {
        this.amount = amount;
    }

    @Override
    public double calculateDiscount(double totalCost) {
        return (totalCost >= amount) ? amount : totalCost;
    }
}
