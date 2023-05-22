package tech.dario.swissre.charlenescoffeecorner.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an order.
 */
public record Order(List<ProductChoice> choices) {
    /**
     * Adds a choice to a copy of the order without modifying the original order.
     *
     * @param productChoice the choice to add
     * @return the new order
     */
    public Order withChoice(ProductChoice productChoice) {
        var clone = new ArrayList<>(choices);

        clone.add(productChoice);

        return new Order(clone);
    }
}
