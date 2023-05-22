package tech.dario.swissre.charlenescoffeecorner.model;

import java.util.Optional;
import java.util.Set;

/**
 * Represents a product choice.
 */
public record ProductChoice (int itemNumber, Product product, Size size, Set<Extra> extras, Optional<Bonus> bonus) {
    /**
     * Displays the product choice.
     *
     * @return the name of the product choice
     */
    public ProductChoice withBonus(Bonus bonus) {
        return new ProductChoice(itemNumber, product, size, extras, Optional.ofNullable(bonus));
    }

    /**
     * Calculates the price of the product choice without considering the bonus.
     *
     * @return the price of the product choice without the bonus
     */
    public float priceWithoutBonus() {
        return size.price() + extras.stream()
                .map(Extra::price)
                .reduce(0.0f, Float::sum);
    }

    /**
     * Calculates the most expensive extra and returns it. If not extras are present, 0 is returned.
     *
     * @return the most expensive extra
     */
    public float mostExpensiveExtra() {
        return (float) extras().stream()
                .mapToDouble(Extra::price)
                .max()
                .orElse(0.0f);
    }
}
