package tech.dario.swissre.charlenescoffeecorner.model;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static tech.dario.swissre.charlenescoffeecorner.TestObjects.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderTest {
    @Test
    void testWithChoice() {
        var originalChoices = List.of(
                new ProductChoice(1, PRODUCT_BEVERAGE, SIZE_STANDARD, Set.of(EXPENSIVE_EXTRA), Optional.empty()),
                new ProductChoice(2, PRODUCT_SNACK, SIZE_STANDARD, Collections.emptySet(), Optional.empty())
        );
        var newChoice = new ProductChoice(3, PRODUCT_BEVERAGE, SIZE_STANDARD, Set.of(CHEAP_EXTRA), Optional.empty());
        var order = new Order(originalChoices);
        var newOrder = order.withChoice(newChoice);
        var expectedChoices = List.of(
                new ProductChoice(1, PRODUCT_BEVERAGE, SIZE_STANDARD, Set.of(EXPENSIVE_EXTRA), Optional.empty()),
                new ProductChoice(2, PRODUCT_SNACK, SIZE_STANDARD, Collections.emptySet(), Optional.empty()),
                new ProductChoice(3, PRODUCT_BEVERAGE, SIZE_STANDARD, Set.of(CHEAP_EXTRA), Optional.empty())
        );

        assertEquals(expectedChoices, newOrder.choices(), "New choice should be added to the order");
        assertEquals(originalChoices, order.choices(), "Original order should not be modified");
    }
}
