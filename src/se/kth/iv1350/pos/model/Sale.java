package se.kth.iv1350.pos.model;

import java.util.HashMap;
import java.util.Map;

/**
 * This class handles a sale and holds information about the items being sold and the total cost.
 */
public class Sale {
    private final Map<String, Item> soldItems = new HashMap<>();
    private double runningTotal = 0;
    private double totalVAT = 0;
    private DiscountStrategy discountStrategy = new NoDiscount(); // Default strategy

    public void addItem(Item item) {
        String itemID = item.getItemID();
        if (soldItems.containsKey(itemID)) {
            Item existing = soldItems.get(itemID);
            int newQuantity = existing.getQuantity() + item.getQuantity();
            Item updatedItem = new Item(
                    itemID,
                    existing.getName(),
                    existing.getItemDescription(),
                    existing.getPrice(),
                    existing.getVat(),
                    newQuantity
            );
            soldItems.put(itemID, updatedItem);
        } else {
            soldItems.put(itemID, item);
        }

        runningTotal += item.getPrice() * item.getQuantity();
        totalVAT += item.getPrice() * item.getQuantity() * item.getVat();
    }

    public double getTotalCost() {
        return runningTotal + totalVAT;
    }

    public double getTotalVat() {
        return totalVAT;
    }

    public Map<String, Item> getSoldItems() {
        return soldItems;
    }

    public SaleDTO getSaleDTO() {
        return new SaleDTO(
                getTotalCost(),
                getTotalVat(),
                convertToItemDTOMap(soldItems),
                getTimeOfSale()
        );
    }

    public double getTotalCostAfterDiscount() {
        double discount = discountStrategy.calculateDiscount(getTotalCost());
        return getTotalCost() - discount;
    }

    public void setDiscountStrategy(DiscountStrategy strategy) {
        this.discountStrategy = strategy;
    }

    private Map<String, ItemDTO> convertToItemDTOMap(Map<String, Item> items) {
        Map<String, ItemDTO> dtoMap = new HashMap<>();
        for (Map.Entry<String, Item> entry : items.entrySet()) {
            Item item = entry.getValue();
            dtoMap.put(entry.getKey(), item.itemToItemDTO());
        }
        return dtoMap;
    }

    private String getCurrentTime() {
        return java.time.LocalTime.now().withNano(0).toString();
    }

    public String getTimeOfSale() {
        return getCurrentTime();
    }

    public boolean hasValidItems() {
        return !soldItems.isEmpty();
    }
}
