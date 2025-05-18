package se.kth.iv1350.pos.model;

/**
 * Strategy interface for applying different discount types.
 */
public interface DiscountStrategy {
    /**
     * Calculates discount amount based on total cost.
     *
     * @param totalCost The total cost before discount.
     * @return The amount to subtract as discount.
     */
    double calculateDiscount(double totalCost);
}
