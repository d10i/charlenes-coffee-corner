package tech.dario.swissre.charlenescoffeecorner.bonus;

import org.junit.jupiter.api.Test;
import tech.dario.swissre.charlenescoffeecorner.model.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static tech.dario.swissre.charlenescoffeecorner.TestObjects.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FifthBeverageBonusStrategyTest {
    private final FifthBeverageBonusStrategy fifthBeverageBonusStrategy = new FifthBeverageBonusStrategy();

    @Test
    void testApplyBonusNoItems() {
        List<ProductChoice> choices = Collections.emptyList();
        var order = new Order(choices);
        var orderWithBonuses = fifthBeverageBonusStrategy.applyBonus(order);
        assertEquals(new Order(choices), orderWithBonuses);
    }

    @Test
    void testApplyBonusNoBeverages() {
        List<ProductChoice> choices = List.of(
                new ProductChoice(1, PRODUCT_SNACK, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(2, PRODUCT_SNACK, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(3, PRODUCT_SNACK, SIZE_LARGE, Collections.emptySet(), Optional.empty()),
                new ProductChoice(4, PRODUCT_SNACK, SIZE_MEDIUM, Collections.emptySet(), Optional.empty()),
                new ProductChoice(5, PRODUCT_SNACK, SIZE_MEDIUM, Collections.emptySet(), Optional.empty())
        );
        var order = new Order(choices);
        var orderWithBonuses = fifthBeverageBonusStrategy.applyBonus(order);
        assertEquals(new Order(choices), orderWithBonuses);
    }

    @Test
    void testApplyBonusFourBeverages() {
        List<ProductChoice> choices = List.of(
                new ProductChoice(1, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(2, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(3, PRODUCT_BEVERAGE, SIZE_LARGE, Collections.emptySet(), Optional.empty()),
                new ProductChoice(4, PRODUCT_BEVERAGE, SIZE_MEDIUM, Collections.emptySet(), Optional.empty()),
                new ProductChoice(5, PRODUCT_SNACK, SIZE_MEDIUM, Collections.emptySet(), Optional.empty())
        );
        var order = new Order(choices);
        var orderWithBonuses = fifthBeverageBonusStrategy.applyBonus(order);
        assertEquals(new Order(choices), orderWithBonuses);
    }

    @Test
    void testApplyBonusFourBeveragesAndOneSnack() {
        List<ProductChoice> choices = List.of(
                new ProductChoice(1, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(2, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(3, PRODUCT_BEVERAGE, SIZE_LARGE, Collections.emptySet(), Optional.empty()),
                new ProductChoice(4, PRODUCT_SNACK, SIZE_LARGE, Collections.emptySet(), Optional.empty()),
                new ProductChoice(5, PRODUCT_BEVERAGE, SIZE_MEDIUM, Collections.emptySet(), Optional.empty())
        );
        var order = new Order(choices);
        var orderWithBonuses = fifthBeverageBonusStrategy.applyBonus(order);
        assertEquals(new Order(choices), orderWithBonuses);
    }

    @Test
    void testApplyBonusFiveBeverages() {
        List<ProductChoice> choices = List.of(
                new ProductChoice(1, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(2, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(3, PRODUCT_BEVERAGE, SIZE_LARGE, Collections.emptySet(), Optional.empty()),
                new ProductChoice(4, PRODUCT_BEVERAGE, SIZE_MEDIUM, Collections.emptySet(), Optional.empty()),
                new ProductChoice(5, PRODUCT_BEVERAGE, SIZE_MEDIUM, Collections.emptySet(), Optional.empty())
        );
        var order = new Order(choices);
        var orderWithBonuses = fifthBeverageBonusStrategy.applyBonus(order);
        assertEquals(new Order(List.of(
                new ProductChoice(1, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(2, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(3, PRODUCT_BEVERAGE, SIZE_LARGE, Collections.emptySet(), Optional.empty()),
                new ProductChoice(4, PRODUCT_BEVERAGE, SIZE_MEDIUM, Collections.emptySet(), Optional.empty()),
                new ProductChoice(5, PRODUCT_BEVERAGE, SIZE_MEDIUM, Collections.emptySet(), Optional.of(new Bonus("Every 5th beverage is for free", SIZE_MEDIUM.price())))
        )), orderWithBonuses);
    }

    @Test
    void testApplyBonusFiveBeveragesAndOneBonusAlreadyApplied() {
        List<ProductChoice> choices = List.of(
                new ProductChoice(1, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(2, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(3, PRODUCT_BEVERAGE, SIZE_LARGE, Collections.emptySet(), Optional.of(BONUS)),
                new ProductChoice(4, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(5, PRODUCT_BEVERAGE, SIZE_MEDIUM, Collections.emptySet(), Optional.empty())
        );
        var order = new Order(choices);
        var orderWithBonuses = fifthBeverageBonusStrategy.applyBonus(order);
        assertEquals(new Order(List.of(
                new ProductChoice(1, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(2, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(3, PRODUCT_BEVERAGE, SIZE_LARGE, Collections.emptySet(), Optional.of(BONUS)),
                new ProductChoice(4, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(5, PRODUCT_BEVERAGE, SIZE_MEDIUM, Collections.emptySet(), Optional.of(new Bonus("Every 5th beverage is for free", SIZE_MEDIUM.price())))
        )), orderWithBonuses);
    }

    @Test
    void testApplyBonusNineBeverages() {
        List<ProductChoice> choices = List.of(
                new ProductChoice(1, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(2, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(3, PRODUCT_BEVERAGE, SIZE_LARGE, Collections.emptySet(), Optional.empty()),
                new ProductChoice(4, PRODUCT_BEVERAGE, SIZE_MEDIUM, Collections.emptySet(), Optional.empty()),
                new ProductChoice(5, PRODUCT_BEVERAGE, SIZE_MEDIUM, Collections.emptySet(), Optional.empty()),
                new ProductChoice(6, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(7, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(8, PRODUCT_BEVERAGE, SIZE_MEDIUM, Collections.emptySet(), Optional.empty()),
                new ProductChoice(9, PRODUCT_BEVERAGE, SIZE_MEDIUM, Collections.emptySet(), Optional.empty())
        );
        var order = new Order(choices);
        var orderWithBonuses = fifthBeverageBonusStrategy.applyBonus(order);
        assertEquals(new Order(List.of(
                new ProductChoice(1, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(2, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(3, PRODUCT_BEVERAGE, SIZE_LARGE, Collections.emptySet(), Optional.empty()),
                new ProductChoice(4, PRODUCT_BEVERAGE, SIZE_MEDIUM, Collections.emptySet(), Optional.empty()),
                new ProductChoice(5, PRODUCT_BEVERAGE, SIZE_MEDIUM, Collections.emptySet(), Optional.of(new Bonus("Every 5th beverage is for free", SIZE_MEDIUM.price()))),
                new ProductChoice(6, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(7, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(8, PRODUCT_BEVERAGE, SIZE_MEDIUM, Collections.emptySet(), Optional.empty()),
                new ProductChoice(9, PRODUCT_BEVERAGE, SIZE_MEDIUM, Collections.emptySet(), Optional.empty())
        )), orderWithBonuses);
    }

    @Test
    void testApplyBonusNineBeveragesAndOneSnack() {
        List<ProductChoice> choices = List.of(
                new ProductChoice(1, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(2, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(3, PRODUCT_BEVERAGE, SIZE_LARGE, Collections.emptySet(), Optional.empty()),
                new ProductChoice(4, PRODUCT_BEVERAGE, SIZE_MEDIUM, Collections.emptySet(), Optional.empty()),
                new ProductChoice(5, PRODUCT_SNACK, SIZE_LARGE, Collections.emptySet(), Optional.empty()),
                new ProductChoice(6, PRODUCT_BEVERAGE, SIZE_MEDIUM, Collections.emptySet(), Optional.empty()),
                new ProductChoice(7, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(8, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(9, PRODUCT_BEVERAGE, SIZE_MEDIUM, Collections.emptySet(), Optional.empty()),
                new ProductChoice(10, PRODUCT_BEVERAGE, SIZE_MEDIUM, Collections.emptySet(), Optional.empty())
        );
        var order = new Order(choices);
        var orderWithBonuses = fifthBeverageBonusStrategy.applyBonus(order);
        assertEquals(new Order(List.of(
                new ProductChoice(1, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(2, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(3, PRODUCT_BEVERAGE, SIZE_LARGE, Collections.emptySet(), Optional.empty()),
                new ProductChoice(4, PRODUCT_BEVERAGE, SIZE_MEDIUM, Collections.emptySet(), Optional.empty()),
                new ProductChoice(5, PRODUCT_SNACK, SIZE_LARGE, Collections.emptySet(), Optional.empty()),
                new ProductChoice(6, PRODUCT_BEVERAGE, SIZE_MEDIUM, Collections.emptySet(), Optional.of(new Bonus("Every 5th beverage is for free", SIZE_MEDIUM.price()))),
                new ProductChoice(7, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(8, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(9, PRODUCT_BEVERAGE, SIZE_MEDIUM, Collections.emptySet(), Optional.empty()),
                new ProductChoice(10, PRODUCT_BEVERAGE, SIZE_MEDIUM, Collections.emptySet(), Optional.empty())
        )), orderWithBonuses);
    }

    @Test
    void testApplyBonusTenBeverages() {
        List<ProductChoice> choices = List.of(
                new ProductChoice(1, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(2, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(3, PRODUCT_BEVERAGE, SIZE_LARGE, Collections.emptySet(), Optional.empty()),
                new ProductChoice(4, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(5, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(6, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(7, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(8, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(9, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(10, PRODUCT_BEVERAGE, SIZE_MEDIUM, Collections.emptySet(), Optional.empty())
        );
        var order = new Order(choices);
        var orderWithBonuses = fifthBeverageBonusStrategy.applyBonus(order);
        assertEquals(new Order(List.of(
                new ProductChoice(1, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(2, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(3, PRODUCT_BEVERAGE, SIZE_LARGE, Collections.emptySet(), Optional.empty()),
                new ProductChoice(4, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(5, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.of(new Bonus("Every 5th beverage is for free", SIZE_SMALL.price()))),
                new ProductChoice(6, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(7, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(8, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(9, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(10, PRODUCT_BEVERAGE, SIZE_MEDIUM, Collections.emptySet(), Optional.of(new Bonus("Every 5th beverage is for free", SIZE_MEDIUM.price())))
        )), orderWithBonuses);
    }

    @Test
    void testApplyBonusTenBeveragesAndOneBonusAlreadyApplied() {
        List<ProductChoice> choices = List.of(
                new ProductChoice(1, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(2, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(3, PRODUCT_BEVERAGE, SIZE_LARGE, Collections.emptySet(), Optional.empty()),
                new ProductChoice(4, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(5, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.of(BONUS)),
                new ProductChoice(6, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(7, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(8, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(9, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(10, PRODUCT_BEVERAGE, SIZE_MEDIUM, Collections.emptySet(), Optional.empty())
        );
        var order = new Order(choices);
        var orderWithBonuses = fifthBeverageBonusStrategy.applyBonus(order);
        assertEquals(new Order(List.of(
                new ProductChoice(1, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(2, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(3, PRODUCT_BEVERAGE, SIZE_LARGE, Collections.emptySet(), Optional.empty()),
                new ProductChoice(4, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(5, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.of(BONUS)),
                new ProductChoice(6, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.of(new Bonus("Every 5th beverage is for free", SIZE_SMALL.price()))),
                new ProductChoice(7, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(8, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(9, PRODUCT_BEVERAGE, SIZE_SMALL, Collections.emptySet(), Optional.empty()),
                new ProductChoice(10, PRODUCT_BEVERAGE, SIZE_MEDIUM, Collections.emptySet(), Optional.of(new Bonus("Every 5th beverage is for free", SIZE_MEDIUM.price())))
        )), orderWithBonuses);
    }
}
