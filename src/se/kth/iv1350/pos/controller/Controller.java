package se.kth.iv1350.pos.controller;

import se.kth.iv1350.pos.integration.*;
import se.kth.iv1350.pos.model.*;

/**
 * This is the application's controller.
 */
public class Controller {
    private Sale sale;
    private final Inventory inventory;
    private final Printer printer = Printer.getInstance();
    private final Register register = new Register();
    private final Accounting accounting = new Accounting();
    private Receipt receipt;
    private Payment payment;

    /**
     * Creates an instance of the controller.
     * Uses Singleton pattern to ensure single Inventory instance.
     */
    public Controller() {
        this.inventory = Inventory.getInstance();
    }

    /**
     * Starts a new sale.
     */
    public void startSale() {
        sale = new Sale();
    }

    /**
     * Finds the scanned item in the inventory and adds it to the sale.
     *
     * @param itemID The ID of the item.
     * @return DTO representing the item.
     * @throws ItemNotFoundException If item is not found.
     * @throws InventoryDatabaseException If inventory access fails.
     */
    public ItemDTO enterItemID(String itemID) throws ItemNotFoundException, InventoryDatabaseException {
        Item item = inventory.fetchItem(itemID);
        sale.addItem(item);
        return item.itemToItemDTO();
    }

    public void setDiscountStrategy(DiscountStrategy strategy) {
        sale.setDiscountStrategy(strategy);
    }

    public Payment enterAmountPaid(double amountPaid) {
        payment = new Payment(amountPaid, sale.getTotalCostAfterDiscount());
        register.updateRegister(amountPaid);
        inventory.updateInventory(sale);
        accounting.updateAccounting(payment);

        receipt = new Receipt(sale, payment);
        printer.printReceipt(receipt);

        return payment;
    }

    public SaleDTO getSaleDetails() {
        return sale.getSaleDTO();
    }

    public double getTotalCost() {
        return sale.getTotalCost();
    }

    public double getTotalVat() {
        return sale.getTotalVat();
    }

    public double getChangeAmount() {
        if (payment == null) {
            System.out.println("Error: Payment is null. Can't calculate change.");
            return 0.0;
        }
        return payment.getChangeAmount();
    }

    public boolean hasValidItems() {
        return sale.hasValidItems();
    }
}
