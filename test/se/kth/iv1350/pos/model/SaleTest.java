package se.kth.iv1350.pos.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Sale class.
 */
public class SaleTest {
    private Sale sale;

    @BeforeEach
    public void setUp() {
        sale = new Sale();
    }

    @Test
    public void testAddItemUpdatesTotalCost() {
        Item item = new Item("abc123", "Test Item", "Test Description", 50.0, 0.1, 1);
        sale.addItem(item);
        double expected = 50.0 + (50.0 * 0.1); // price + VAT
        assertEquals(expected, sale.getTotalCost(), 0.001);
    }

    @Test
    public void testSetDiscountStrategyAffectsTotalCostAfterDiscount() {
        Item item = new Item("abc123", "Test Item", "Test Description", 100.0, 0.0, 1);
        sale.addItem(item);
        sale.setDiscountStrategy(new FixedDiscount(10));
        double expected = 90.0;
        assertEquals(expected, sale.getTotalCostAfterDiscount(), 0.001);
    }
}
