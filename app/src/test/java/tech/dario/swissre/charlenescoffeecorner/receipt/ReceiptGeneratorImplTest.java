package tech.dario.swissre.charlenescoffeecorner.receipt;

import org.junit.jupiter.api.Test;
import tech.dario.swissre.charlenescoffeecorner.model.Order;
import tech.dario.swissre.charlenescoffeecorner.model.ProductChoice;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static tech.dario.swissre.charlenescoffeecorner.TestObjects.*;

class ReceiptGeneratorImplTest {
    private final ReceiptGeneratorImpl receiptGenerator = new ReceiptGeneratorImpl();

    @Test
    void testGenerateReceiptEmptyOrder() {
        var order = new Order(Collections.emptyList());
        assertEquals("", receiptGenerator.generateReceipt(order));
    }

    @Test
    void testGenerateReceiptOneItemNoBonuses() {
        var order = new Order(List.of(
                new ProductChoice(1, PRODUCT_SNACK, SIZE_STANDARD, Collections.emptySet(), Optional.empty())
        ));
        assertEquals("""
Charlene's Coffee Corner
RECEIPT
----------------------------
Standard snack      5.00 CHF
----------------------------
TOTAL               5.00 CHF
""", receiptGenerator.generateReceipt(order));
    }

    @Test
    void testGenerateReceiptTwoItemsNoBonuses() {
        var order = new Order(List.of(
                new ProductChoice(1, PRODUCT_SNACK, SIZE_STANDARD, Collections.emptySet(), Optional.empty()),
                new ProductChoice(2, PRODUCT_BEVERAGE, SIZE_SMALL, Set.of(EXPENSIVE_EXTRA), Optional.empty())
        ));
        assertEquals("""
Charlene's Coffee Corner
RECEIPT
----------------------------------------------
Standard snack                        5.00 CHF
Small drink with expensive extra      4.50 CHF
----------------------------------------------
TOTAL                                 9.50 CHF
""", receiptGenerator.generateReceipt(order));
    }

    @Test
    void testGenerateReceiptTwoItemsOneBonus() {
        var order = new Order(List.of(
                new ProductChoice(1, PRODUCT_SNACK, SIZE_STANDARD, Collections.emptySet(), Optional.empty()),
                new ProductChoice(2, PRODUCT_BEVERAGE, SIZE_SMALL, Set.of(EXPENSIVE_EXTRA), Optional.of(BONUS))
        ));
        assertEquals("""
Charlene's Coffee Corner
RECEIPT
----------------------------------------------
Standard snack                        5.00 CHF
Small drink with expensive extra      4.50 CHF
Bonus: Bonus                         -0.50 CHF
----------------------------------------------
TOTAL                                 9.00 CHF
""", receiptGenerator.generateReceipt(order));
    }
}
