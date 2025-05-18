package pos.test.se.kth.iv1350.pos.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.kth.iv1350.pos.controller.Controller;
import se.kth.iv1350.pos.model.ItemDTO;
import se.kth.iv1350.pos.model.SaleDTO;
import se.kth.iv1350.pos.integration.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Controller class.
 */
public class ControllerTest {
    private Controller controller;

    @BeforeEach
    public void setUp() {
        controller = new Controller();
        controller.startSale();
    }

    @Test
    public void testEnterValidItemIDReturnsCorrectItemDTO() throws Exception {
        ItemDTO item = controller.enterItemID("abc123");
        assertEquals("abc123", item.getItemID());
    }

    @Test
    public void testEnterInvalidItemIDThrowsException() {
        assertThrows(ItemNotFoundException.class, () -> {
            controller.enterItemID("invalidID");
        });
    }

    @Test
    public void testGetSaleDetailsReturnsCorrectType() {
        SaleDTO saleInfo = controller.getSaleDetails();
        assertNotNull(saleInfo);
    }
}
