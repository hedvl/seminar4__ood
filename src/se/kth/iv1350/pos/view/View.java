package se.kth.iv1350.pos.view;

import se.kth.iv1350.pos.controller.Controller;
import se.kth.iv1350.pos.integration.Inventory;
import se.kth.iv1350.pos.integration.InventoryDatabaseException;
import se.kth.iv1350.pos.integration.ItemNotFoundException;
import se.kth.iv1350.pos.model.*;

public class View {
    private final Controller contr;

    public View(Controller contr) {
        this.contr = contr;
    }

    public void runFakeExecution() {
        runSaleWithStrategy(new NoDiscount(), "🟢 NO DISCOUNT");
        runSaleWithStrategy(new FixedDiscount(15), "🟡 FIXED DISCOUNT (15 SEK)");
        runSaleWithStrategy(new PercentageDiscount(0.10), "🔵 PERCENTAGE DISCOUNT (10%)");
    }

    private void runSaleWithStrategy(DiscountStrategy strategy, String label) {
        System.out.println("\n========== " + label + " ==========");

        // Reset inventory to ensure items exist
        Inventory.getInstance().resetInventory();

        contr.startSale();
        contr.setDiscountStrategy(strategy);

        addItem("abc123");
        addItem("def456");
        addItem("invalidID");


        
        if(contr.hasValidItems()) {
            try {
            contr.enterAmountPaid(100);
            } catch (Exception e) {
            System.out.println("Payment failed: " + e.getMessage());
            }

            showChange();
        }
        else {
            System.out.println("Sale aborted: No valid items bought.");
        }


    }

    private void addItem(String itemID) {
        try {
            ItemDTO item = contr.enterItemID(itemID);
            System.out.println("Added item: " + item.getName() +
                    " | Price: " + item.getPrice() + " SEK");
        } catch (ItemNotFoundException e) {
            System.out.println("Error: Item ID '" + itemID + "' was not found.");
        } catch (InventoryDatabaseException e) {
            System.out.println("Error: Inventory database failure occurred.");
        }
    }

    private void showChange() {
        System.out.println("Change to customer: " +
                String.format("%.2f", contr.getChangeAmount()) + " SEK");
    }

    public static void main(String[] args) {
        Controller controller = new Controller();
        View view = new View(controller);
        view.runFakeExecution();
    }
}
