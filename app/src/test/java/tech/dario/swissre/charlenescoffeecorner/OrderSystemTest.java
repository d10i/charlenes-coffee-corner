package tech.dario.swissre.charlenescoffeecorner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.dario.swissre.charlenescoffeecorner.bonus.BonusStrategy;
import tech.dario.swissre.charlenescoffeecorner.model.Order;
import tech.dario.swissre.charlenescoffeecorner.model.ProductChoice;
import tech.dario.swissre.charlenescoffeecorner.receipt.ReceiptGenerator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static tech.dario.swissre.charlenescoffeecorner.TestObjects.*;

class OrderSystemTest {
    private ByteArrayOutputStream out;
    private ReceiptGeneratorStub receiptGeneratorStub;
    private BonusStrategyStub bonusStrategyStub1;
    private BonusStrategyStub bonusStrategyStub2;
    private OrderSystem orderSystem;

    @BeforeEach
    void init() {
        out = new ByteArrayOutputStream();
        receiptGeneratorStub = new ReceiptGeneratorStub();
        bonusStrategyStub1 = new BonusStrategyStub();
        bonusStrategyStub2 = new BonusStrategyStub();

        List<BonusStrategy> bonusStrategies = List.of(bonusStrategyStub1, bonusStrategyStub2);
        var offering = List.of(PRODUCT_BEVERAGE, PRODUCT_SNACK);

        orderSystem = new OrderSystem(receiptGeneratorStub, bonusStrategies, offering);
    }

    @Test
    void testNewOrderEmptyOrder() {
        var in = new ByteArrayInputStream("""
                done
                """.getBytes());
        orderSystem.newOrder(in, new PrintStream(out));
        assertEquals("""
                Hello, what would you like? (or 'done' to finish order)
                1. Drink
                2. Snack

                >\s
                Thank you.

                I AM THE RECEIPT""", out.toString().trim());
        var expectedOrder = new Order(Collections.emptyList());
        assertEquals(List.of(expectedOrder), bonusStrategyStub1.calledWith, "Calls bonus strategy 1 with the correct order");
        assertEquals(List.of(expectedOrder), bonusStrategyStub2.calledWith, "Calls bonus strategy 2 with the correct order");
        assertEquals(List.of(expectedOrder), receiptGeneratorStub.calledWith, "Generates receipt with the correct order");
    }

    @Test
    void testNewOrderNumberTooHigh() {
        var in = new ByteArrayInputStream("""
                3
                done
                """.getBytes());
        orderSystem.newOrder(in, new PrintStream(out));
        assertEquals("""
                Hello, what would you like? (or 'done' to finish order)
                1. Drink
                2. Snack

                >\s
                Please choose a number between 1 and 2.
                1. Drink
                2. Snack

                >\s
                Thank you.

                I AM THE RECEIPT""", out.toString().trim());
        var expectedOrder = new Order(Collections.emptyList());
        assertEquals(List.of(expectedOrder), bonusStrategyStub1.calledWith, "Calls bonus strategy 1 with the correct order");
        assertEquals(List.of(expectedOrder), bonusStrategyStub2.calledWith, "Calls bonus strategy 2 with the correct order");
        assertEquals(List.of(expectedOrder), receiptGeneratorStub.calledWith, "Generates receipt with the correct order");
    }

    @Test
    void testNewOrderInvalidNumber() {
        var in = new ByteArrayInputStream("""
                notanumber
                done
                """.getBytes());
        orderSystem.newOrder(in, new PrintStream(out));
        assertEquals("""
                Hello, what would you like? (or 'done' to finish order)
                1. Drink
                2. Snack

                >\s
                Sorry, I didn't catch that, could you please repeat?
                1. Drink
                2. Snack

                >\s
                Thank you.

                I AM THE RECEIPT""", out.toString().trim());
        var expectedOrder = new Order(Collections.emptyList());
        assertEquals(List.of(expectedOrder), bonusStrategyStub1.calledWith, "Calls bonus strategy 1 with the correct order");
        assertEquals(List.of(expectedOrder), bonusStrategyStub2.calledWith, "Calls bonus strategy 2 with the correct order");
        assertEquals(List.of(expectedOrder), receiptGeneratorStub.calledWith, "Generates receipt with the correct order");
    }

    @Test
    void testNewOrderOneSnack() {
        var in = new ByteArrayInputStream("""
                2
                done
                """.getBytes());
        orderSystem.newOrder(in, new PrintStream(out));
        assertEquals("""
                Hello, what would you like? (or 'done' to finish order)
                1. Drink
                2. Snack

                >\s
                Thank you.
                Would you like to order anything else? (or 'done' to finish order)
                1. Drink
                2. Snack

                >\s
                Thank you.

                I AM THE RECEIPT""", out.toString().trim());
        var expectedOrder = new Order(List.of(
                new ProductChoice(1, PRODUCT_SNACK, SIZE_STANDARD, Collections.emptySet(), Optional.empty())
        ));
        assertEquals(List.of(expectedOrder), bonusStrategyStub1.calledWith, "Calls bonus strategy 1 with the correct order");
        assertEquals(List.of(expectedOrder), bonusStrategyStub2.calledWith, "Calls bonus strategy 2 with the correct order");
        assertEquals(List.of(expectedOrder), receiptGeneratorStub.calledWith, "Generates receipt with the correct order");
    }

    @Test
    void testNewOrderOneSnackAndOneDrinkWithNoExtras() {
        var in = new ByteArrayInputStream("""
                2
                1
                1
                done
                done
                """.getBytes());
        orderSystem.newOrder(in, new PrintStream(out));
        assertEquals("""
                Hello, what would you like? (or 'done' to finish order)
                1. Drink
                2. Snack

                >\s
                Thank you.
                Would you like to order anything else? (or 'done' to finish order)
                1. Drink
                2. Snack

                >\s
                Thank you.
                What size?
                1. Small
                2. Medium
                3. Large

                >\s
                Thank you.
                Any extras? (or 'done' if not)
                1. Cheap extra
                2. Expensive extra

                >\s
                Thank you.
                Would you like to order anything else? (or 'done' to finish order)
                1. Drink
                2. Snack

                >\s
                Thank you.

                I AM THE RECEIPT""", out.toString().trim());
        var expectedOrder = new Order(List.of(
                new ProductChoice(1, PRODUCT_SNACK, SIZE_STANDARD, Collections.emptySet(), Optional.empty()),
                new ProductChoice(2, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty())
        ));
        assertEquals(List.of(expectedOrder), bonusStrategyStub1.calledWith, "Calls bonus strategy 1 with the correct order");
        assertEquals(List.of(expectedOrder), bonusStrategyStub2.calledWith, "Calls bonus strategy 2 with the correct order");
        assertEquals(List.of(expectedOrder), receiptGeneratorStub.calledWith, "Generates receipt with the correct order");
    }

    @Test
    void testNewOrderOneSnackAndOneDrinkWithOneExtra() {
        var in = new ByteArrayInputStream("""
                2
                1
                1
                2
                done
                done
                """.getBytes());
        orderSystem.newOrder(in, new PrintStream(out));
        assertEquals("""
                Hello, what would you like? (or 'done' to finish order)
                1. Drink
                2. Snack

                >\s
                Thank you.
                Would you like to order anything else? (or 'done' to finish order)
                1. Drink
                2. Snack

                >\s
                Thank you.
                What size?
                1. Small
                2. Medium
                3. Large

                >\s
                Thank you.
                Any extras? (or 'done' if not)
                1. Cheap extra
                2. Expensive extra

                >\s
                Thank you.
                Any more extras? (or 'done' if not)
                1. Cheap extra
                2. Expensive extra

                >\s
                Thank you.
                Would you like to order anything else? (or 'done' to finish order)
                1. Drink
                2. Snack

                >\s
                Thank you.

                I AM THE RECEIPT""", out.toString().trim());
        var expectedOrder = new Order(List.of(
                new ProductChoice(1, PRODUCT_SNACK, SIZE_STANDARD, Collections.emptySet(), Optional.empty()),
                new ProductChoice(2, PRODUCT_BEVERAGE, SIZE_SMALL, Set.of(EXPENSIVE_EXTRA), Optional.empty())
        ));
        assertEquals(List.of(expectedOrder), bonusStrategyStub1.calledWith, "Calls bonus strategy 1 with the correct order");
        assertEquals(List.of(expectedOrder), bonusStrategyStub2.calledWith, "Calls bonus strategy 2 with the correct order");
        assertEquals(List.of(expectedOrder), receiptGeneratorStub.calledWith, "Generates receipt with the correct order");
    }

    @Test
    void testNewOrderOneSnackAndOneDrinkWithTwoExtras() {
        var in = new ByteArrayInputStream("""
                2
                1
                1
                2
                1
                done
                done
                """.getBytes());
        orderSystem.newOrder(in, new PrintStream(out));
        assertEquals("""
                Hello, what would you like? (or 'done' to finish order)
                1. Drink
                2. Snack

                >\s
                Thank you.
                Would you like to order anything else? (or 'done' to finish order)
                1. Drink
                2. Snack

                >\s
                Thank you.
                What size?
                1. Small
                2. Medium
                3. Large

                >\s
                Thank you.
                Any extras? (or 'done' if not)
                1. Cheap extra
                2. Expensive extra

                >\s
                Thank you.
                Any more extras? (or 'done' if not)
                1. Cheap extra
                2. Expensive extra

                >\s
                Thank you.
                Any more extras? (or 'done' if not)
                1. Cheap extra
                2. Expensive extra

                >\s
                Thank you.
                Would you like to order anything else? (or 'done' to finish order)
                1. Drink
                2. Snack

                >\s
                Thank you.

                I AM THE RECEIPT""", out.toString().trim());
        var expectedOrder = new Order(List.of(
                new ProductChoice(1, PRODUCT_SNACK, SIZE_STANDARD, Collections.emptySet(), Optional.empty()),
                new ProductChoice(2, PRODUCT_BEVERAGE, SIZE_SMALL, Set.of(CHEAP_EXTRA, EXPENSIVE_EXTRA), Optional.empty())
        ));
        assertEquals(List.of(expectedOrder), bonusStrategyStub1.calledWith, "Calls bonus strategy 1 with the correct order");
        assertEquals(List.of(expectedOrder), bonusStrategyStub2.calledWith, "Calls bonus strategy 2 with the correct order");
        assertEquals(List.of(expectedOrder), receiptGeneratorStub.calledWith, "Generates receipt with the correct order");
    }

    // Generally it would be better to use mocking frameworks like Mockito, but requirements say that only Java SE
    // libraries and JUnit can be used.
    private static class ReceiptGeneratorStub implements ReceiptGenerator {
        private List<Order> calledWith = new ArrayList<>();

        @Override
        public String generateReceipt(Order order) {
            calledWith.add(order);
            return "I AM THE RECEIPT";
        }
    }

    private static class BonusStrategyStub implements BonusStrategy {
        private List<Order> calledWith = new ArrayList<>();

        @Override
        public Order applyBonus(Order order) {
            calledWith.add(order);
            return order;
        }
    }
}
