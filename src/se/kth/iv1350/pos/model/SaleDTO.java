package se.kth.iv1350.pos.model;

import java.util.Collections;
import java.util.Map;

/**
 * The DTO that represents a completed sale.
 */
public class SaleDTO {
    private final double totalCost;
    private final double totalVat;
    private final Map<String, ItemDTO> soldItems;
    private final String timeOfSale;

    /**
     * Creates a SaleDTO for the sale.
     *
     * @param totalCost The total cost of the sale.
     * @param totalVat The total VAT of the sale.
     * @param soldItems The sold items in the sale.
     * @param timeOfSale The time the sale was made.
     */
    public SaleDTO(double totalCost, double totalVat, Map<String, ItemDTO> soldItems, String timeOfSale) {
        this.totalCost = totalCost;
        this.totalVat = totalVat;
        this.soldItems = Collections.unmodifiableMap(soldItems);
        this.timeOfSale = timeOfSale;
    }

    /**
     * Gets the total cost of the sale.
     *
     * @return The total cost.
     */
    public double getTotalCost() {
        return totalCost;
    }

    /**
     * Gets the total VAT of the sale.
     *
     * @return The total VAT.
     */
    public double getTotalVat() {
        return totalVat;
    }

    /**
     * Gets the sold items.
     *
     * @return An unmodifiable map of item ID to ItemDTO.
     */
    public Map<String, ItemDTO> getSoldItems() {
        return soldItems;
    }

    /**
     * Gets the time the sale was made.
     *
     * @return Time as a string in HH:mm:ss format.
     */
    public String getTimeOfSale() {
        return timeOfSale;
    }
}
