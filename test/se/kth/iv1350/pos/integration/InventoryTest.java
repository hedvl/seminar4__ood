package se.kth.iv1350.pos.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import se.kth.iv1350.pos.model.Item;
import se.kth.iv1350.pos.model.Sale;

import java.util.Map;

/**
 * Unit tests for the Inventory class.
 */
public class InventoryTest {
    private Inventory inventory;
    private Sale sale;

    @BeforeEach
    public void setUp() {
        inventory = Inventory.getInstance();
        sale = new Sale();
        try {
            Item item = inventory.fetchItem("abc123");
            sale.addItem(item);
        } catch (Exception e) {
            fail("Setup failed: " + e.getMessage());
        }
    }

    @Test
    public void testFetchItemWithValidID() {
        try {
            Item item = inventory.fetchItem("abc123");
            assertNotNull(item);
        } catch (Exception e) {
            fail("Should not throw exception for valid item.");
        }
    }

    @Test
    public void testFetchItemWithInvalidIDThrowsException() {
        assertThrows(ItemNotFoundException.class, () -> {
            inventory.fetchItem("invalidID");
        });
    }

    @Test
    public void testFetchItemTriggersInventoryDatabaseException() {
        assertThrows(InventoryDatabaseException.class, () -> {
            inventory.fetchItem("failDB");
        });
    }

    @Test
    public void testUpdateInventoryRemovesSoldItem() {
        inventory.updateInventory(sale);
        Map<String, Item> updated = inventory.getItems();
        assertFalse(updated.containsKey("abc123"));
    }
}
