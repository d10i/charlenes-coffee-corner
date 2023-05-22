package tech.dario.swissre.charlenescoffeecorner.bonus;

import org.junit.jupiter.api.Test;
import tech.dario.swissre.charlenescoffeecorner.model.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static tech.dario.swissre.charlenescoffeecorner.TestObjects.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FreeExtraBonusStrategyTest {
    private final FreeExtraBonusStrategy freeExtraBonusStrategy = new FreeExtraBonusStrategy();

    @Test
    void testApplyBonusNoItems() {
        List<ProductChoice> choices = Collections.emptyList();
        var order = new Order(choices);
        var orderWithBonuses = freeExtraBonusStrategy.applyBonus(order);
        assertEquals(new Order(choices), orderWithBonuses);
    }

    @Test
    void testApplyBonusNoSnacks() {
        List<ProductChoice> choices = List.of(
                new ProductChoice(1, PRODUCT_BEVERAGE, SIZE_STANDARD, Collections.emptySet(), Optional.empty())
        );
        var order = new Order(choices);
        var orderWithBonuses = freeExtraBonusStrategy.applyBonus(order);
        assertEquals(new Order(choices), orderWithBonuses);
    }

    @Test
    void testApplyBonusOneSnackAndOneBeverageWithoutExtras() {
        List<ProductChoice> choices = List.of(
                new ProductChoice(1, PRODUCT_BEVERAGE, SIZE_STANDARD, Collections.emptySet(), Optional.empty()),
                new ProductChoice(2, PRODUCT_SNACK, SIZE_STANDARD, Collections.emptySet(), Optional.empty())
        );
        var order = new Order(choices);
        var orderWithBonuses = freeExtraBonusStrategy.applyBonus(order);
        assertEquals(new Order(choices), orderWithBonuses);
    }

    @Test
    void testApplyBonusOneSnackAndOneBeverageWithOneExtra() {
        List<ProductChoice> choices = List.of(
                new ProductChoice(1, PRODUCT_BEVERAGE, SIZE_STANDARD, Set.of(CHEAP_EXTRA), Optional.empty()),
                new ProductChoice(2, PRODUCT_SNACK, SIZE_STANDARD, Collections.emptySet(), Optional.empty())
        );
        var order = new Order(choices);
        var orderWithBonuses = freeExtraBonusStrategy.applyBonus(order);
        assertEquals(new Order(List.of(
                new ProductChoice(1, PRODUCT_BEVERAGE, SIZE_STANDARD, Set.of(CHEAP_EXTRA), Optional.of(new Bonus("Free extra with a beverage and a snack", CHEAP_EXTRA.price()))),
                new ProductChoice(2, PRODUCT_SNACK, SIZE_STANDARD, Collections.emptySet(), Optional.empty())
        )), orderWithBonuses);
    }

    @Test
    void testApplyBonusOneSnackAndOneBeverageWithOneExtraAndOneBonusAlreadyApplied() {
        List<ProductChoice> choices = List.of(
                new ProductChoice(1, PRODUCT_BEVERAGE, SIZE_STANDARD, Set.of(CHEAP_EXTRA), Optional.of(BONUS)),
                new ProductChoice(2, PRODUCT_SNACK, SIZE_STANDARD, Collections.emptySet(), Optional.empty())
        );
        var order = new Order(choices);
        var orderWithBonuses = freeExtraBonusStrategy.applyBonus(order);
        assertEquals(new Order(List.of(
                new ProductChoice(1, PRODUCT_BEVERAGE, SIZE_STANDARD, Set.of(CHEAP_EXTRA), Optional.of(BONUS)),
                new ProductChoice(2, PRODUCT_SNACK, SIZE_STANDARD, Collections.emptySet(), Optional.empty())
        )), orderWithBonuses);
    }

    @Test
    void testApplyBonusOneSnackAndOneBeverageWithTwoExtras() {
        List<ProductChoice> choices = List.of(
                new ProductChoice(1, PRODUCT_BEVERAGE, SIZE_STANDARD, Set.of(CHEAP_EXTRA, EXPENSIVE_EXTRA), Optional.empty()),
                new ProductChoice(2, PRODUCT_SNACK, SIZE_STANDARD, Collections.emptySet(), Optional.empty())
        );
        var order = new Order(choices);
        var orderWithBonuses = freeExtraBonusStrategy.applyBonus(order);
        assertEquals(new Order(List.of(
                new ProductChoice(1, PRODUCT_BEVERAGE, SIZE_STANDARD, Set.of(CHEAP_EXTRA, EXPENSIVE_EXTRA), Optional.of(new Bonus("Free extra with a beverage and a snack", EXPENSIVE_EXTRA.price()))),
                new ProductChoice(2, PRODUCT_SNACK, SIZE_STANDARD, Collections.emptySet(), Optional.empty())
        )), orderWithBonuses);
    }

    @Test
    void testApplyBonusOneSnackAndOneBeverageWithTwoExtrasAndOneBonusAlreadyApplied() {
        List<ProductChoice> choices = List.of(
                new ProductChoice(1, PRODUCT_BEVERAGE, SIZE_STANDARD, Set.of(CHEAP_EXTRA, EXPENSIVE_EXTRA), Optional.of(BONUS)),
                new ProductChoice(2, PRODUCT_SNACK, SIZE_STANDARD, Collections.emptySet(), Optional.empty())
        );
        var order = new Order(choices);
        var orderWithBonuses = freeExtraBonusStrategy.applyBonus(order);
        assertEquals(new Order(List.of(
                new ProductChoice(1, PRODUCT_BEVERAGE, SIZE_STANDARD, Set.of(CHEAP_EXTRA, EXPENSIVE_EXTRA), Optional.of(BONUS)),
                new ProductChoice(2, PRODUCT_SNACK, SIZE_STANDARD, Collections.emptySet(), Optional.empty())
        )), orderWithBonuses);
    }

    @Test
    void testApplyBonusOneSnackAndTwoBeverageWithExtras() {
        List<ProductChoice> choices = List.of(
                new ProductChoice(1, PRODUCT_BEVERAGE, SIZE_STANDARD, Set.of(CHEAP_EXTRA), Optional.empty()),
                new ProductChoice(2, PRODUCT_BEVERAGE, SIZE_STANDARD, Set.of(EXPENSIVE_EXTRA), Optional.empty()),
                new ProductChoice(3, PRODUCT_SNACK, SIZE_STANDARD, Collections.emptySet(), Optional.empty())
        );
        var order = new Order(choices);
        var orderWithBonuses = freeExtraBonusStrategy.applyBonus(order);
        assertEquals(new Order(List.of(
                new ProductChoice(1, PRODUCT_BEVERAGE, SIZE_STANDARD, Set.of(CHEAP_EXTRA), Optional.empty()),
                new ProductChoice(2, PRODUCT_BEVERAGE, SIZE_STANDARD, Set.of(EXPENSIVE_EXTRA), Optional.of(new Bonus("Free extra with a beverage and a snack", EXPENSIVE_EXTRA.price()))),
                new ProductChoice(3, PRODUCT_SNACK, SIZE_STANDARD, Collections.emptySet(), Optional.empty())
        )), orderWithBonuses);
    }

    @Test
    void testApplyBonusOneSnackAndTwoBeverageWithExtrasAndOneBonusAlreadyApplied() {
        List<ProductChoice> choices = List.of(
                new ProductChoice(1, PRODUCT_BEVERAGE, SIZE_STANDARD, Set.of(CHEAP_EXTRA), Optional.empty()),
                new ProductChoice(2, PRODUCT_BEVERAGE, SIZE_STANDARD, Set.of(EXPENSIVE_EXTRA), Optional.of(BONUS)),
                new ProductChoice(3, PRODUCT_SNACK, SIZE_STANDARD, Collections.emptySet(), Optional.empty())
        );
        var order = new Order(choices);
        var orderWithBonuses = freeExtraBonusStrategy.applyBonus(order);
        assertEquals(new Order(List.of(
                new ProductChoice(1, PRODUCT_BEVERAGE, SIZE_STANDARD, Set.of(CHEAP_EXTRA), Optional.of(new Bonus("Free extra with a beverage and a snack", CHEAP_EXTRA.price()))),
                new ProductChoice(2, PRODUCT_BEVERAGE, SIZE_STANDARD, Set.of(EXPENSIVE_EXTRA), Optional.of(BONUS)),
                new ProductChoice(3, PRODUCT_SNACK, SIZE_STANDARD, Collections.emptySet(), Optional.empty())
        )), orderWithBonuses);
    }

    @Test
    void testApplyBonusTwoSnacksAndTwoBeverageWithExtras() {
        List<ProductChoice> choices = List.of(
                new ProductChoice(1, PRODUCT_BEVERAGE, SIZE_STANDARD, Set.of(CHEAP_EXTRA), Optional.empty()),
                new ProductChoice(2, PRODUCT_SNACK, SIZE_STANDARD, Collections.emptySet(), Optional.empty()),
                new ProductChoice(3, PRODUCT_BEVERAGE, SIZE_STANDARD, Set.of(CHEAP_EXTRA, EXPENSIVE_EXTRA), Optional.empty()),
                new ProductChoice(4, PRODUCT_SNACK, SIZE_STANDARD, Collections.emptySet(), Optional.empty())
        );
        var order = new Order(choices);
        var orderWithBonuses = freeExtraBonusStrategy.applyBonus(order);
        assertEquals(new Order(List.of(
                new ProductChoice(1, PRODUCT_BEVERAGE, SIZE_STANDARD, Set.of(CHEAP_EXTRA), Optional.of(new Bonus("Free extra with a beverage and a snack", CHEAP_EXTRA.price()))),
                new ProductChoice(2, PRODUCT_SNACK, SIZE_STANDARD, Collections.emptySet(), Optional.empty()),
                new ProductChoice(3, PRODUCT_BEVERAGE, SIZE_STANDARD, Set.of(CHEAP_EXTRA, EXPENSIVE_EXTRA), Optional.of(new Bonus("Free extra with a beverage and a snack", EXPENSIVE_EXTRA.price()))),
                new ProductChoice(4, PRODUCT_SNACK, SIZE_STANDARD, Collections.emptySet(), Optional.empty())
        )), orderWithBonuses);
    }
}
