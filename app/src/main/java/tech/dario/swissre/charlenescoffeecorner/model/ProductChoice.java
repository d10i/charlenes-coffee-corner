package tech.dario.swissre.charlenescoffeecorner.model;

import java.util.Optional;
import java.util.Set;

/**
 * Represents a product choice.
 */
public record ProductChoice (int itemNumber, Product product, Size size, Set<Extra> extras, Optional<Bonus> bonus) { }
