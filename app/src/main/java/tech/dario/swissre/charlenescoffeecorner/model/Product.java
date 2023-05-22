package tech.dario.swissre.charlenescoffeecorner.model;

import java.util.List;

/**
 * Represents a product.
 */
public record Product(String name, boolean beverage, List<Size> sizes, List<Extra> extras) implements Displayable {
    /**
     * Displays the product.
     *
     * @return the name of the product
     */
    @Override
    public String display() {
        return name;
    }
}
