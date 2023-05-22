package tech.dario.swissre.charlenescoffeecorner.model;

/**
 * Represents a size.
 */
public record Size(String name, float price) implements Displayable {
    /**
     * Displays the size.
     *
     * @return the name of the size
     */
    @Override
    public String display() {
        return name;
    }
}
