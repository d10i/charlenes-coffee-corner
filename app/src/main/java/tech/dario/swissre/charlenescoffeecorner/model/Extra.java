package tech.dario.swissre.charlenescoffeecorner.model;

/**
 * Represents an extra.
 */
public record Extra(String name, float price) implements Displayable {
    /**
     * Displays the extra.
     *
     * @return the name of the extra
     */
    @Override
    public String display() {
        return name;
    }
}
