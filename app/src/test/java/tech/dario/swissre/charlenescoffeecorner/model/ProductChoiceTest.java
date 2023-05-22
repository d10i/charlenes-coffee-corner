package tech.dario.swissre.charlenescoffeecorner.model;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static tech.dario.swissre.charlenescoffeecorner.TestObjects.*;

import static org.junit.jupiter.api.Assertions.*;

class ProductChoiceTest {
    @Test
    void testWithBonus() {
        var originalChoice = new ProductChoice(1, PRODUCT_BEVERAGE, SIZE_STANDARD, Set.of(EXPENSIVE_EXTRA), Optional.empty());
        var newChoice = originalChoice.withBonus(BONUS);
        var expectedChoice = new ProductChoice(1, PRODUCT_BEVERAGE, SIZE_STANDARD, Set.of(EXPENSIVE_EXTRA), Optional.of(BONUS));
        assertEquals(expectedChoice, newChoice, "Bonus should be added to the choice");
        assertTrue(originalChoice.bonus().isEmpty(), "Original choice shouldn't have a bonus");
    }

    @Test
    void testPriceWithoutBonusWithNoExtras() {
        var choice = new ProductChoice(1, PRODUCT_BEVERAGE, SIZE_STANDARD, Collections.emptySet(), Optional.empty());
        assertEquals(SIZE_STANDARD.price(), choice.priceWithoutBonus());
    }

    @Test
    void testPriceWithoutBonusWithOneExtra() {
        var choice = new ProductChoice(1, PRODUCT_BEVERAGE, SIZE_STANDARD, Set.of(EXPENSIVE_EXTRA), Optional.empty());
        assertEquals(SIZE_STANDARD.price() + EXPENSIVE_EXTRA.price(), choice.priceWithoutBonus());
    }

    @Test
    void testPriceWithoutBonusWithTwoExtras() {
        var choice = new ProductChoice(1, PRODUCT_BEVERAGE, SIZE_STANDARD, Set.of(CHEAP_EXTRA, EXPENSIVE_EXTRA), Optional.empty());
        assertEquals(SIZE_STANDARD.price() + CHEAP_EXTRA.price() + EXPENSIVE_EXTRA.price(), choice.priceWithoutBonus());
    }

    @Test
    void testMostExpensiveExtraWithNoExtras() {
        var choice = new ProductChoice(1, PRODUCT_BEVERAGE, SIZE_STANDARD, Collections.emptySet(), Optional.empty());
        assertEquals(0.0f, choice.mostExpensiveExtra());
    }

    @Test
    void testMostExpensiveExtraWithOneExtra() {
        var choice = new ProductChoice(1, PRODUCT_BEVERAGE, SIZE_STANDARD, Set.of(EXPENSIVE_EXTRA), Optional.empty());
        assertEquals(EXPENSIVE_EXTRA.price(), choice.mostExpensiveExtra());
    }

    @Test
    void testMostExpensiveExtraWithTwoExtras() {
        var choice = new ProductChoice(1, PRODUCT_BEVERAGE, SIZE_STANDARD, Set.of(CHEAP_EXTRA, EXPENSIVE_EXTRA), Optional.empty());
        assertEquals(EXPENSIVE_EXTRA.price(), choice.mostExpensiveExtra());
    }
}
