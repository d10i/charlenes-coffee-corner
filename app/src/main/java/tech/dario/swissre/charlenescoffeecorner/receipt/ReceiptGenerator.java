package tech.dario.swissre.charlenescoffeecorner.receipt;

import tech.dario.swissre.charlenescoffeecorner.model.Order;

/**
 * Interface for receipt generators.
 */
public interface ReceiptGenerator {
    String generateReceipt(Order order);
}
