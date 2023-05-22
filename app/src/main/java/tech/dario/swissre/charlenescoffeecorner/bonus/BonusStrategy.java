package tech.dario.swissre.charlenescoffeecorner.bonus;

import tech.dario.swissre.charlenescoffeecorner.model.Order;

/**
 * Interface for bonus strategies.
 */
public interface BonusStrategy {
    Order applyBonus(Order order);
}
