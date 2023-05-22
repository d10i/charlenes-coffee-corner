package tech.dario.swissre.charlenescoffeecorner.bonus;

import tech.dario.swissre.charlenescoffeecorner.model.Bonus;
import tech.dario.swissre.charlenescoffeecorner.model.Order;
import tech.dario.swissre.charlenescoffeecorner.model.ProductChoice;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Bonus strategy that allows applying the bonus to an order so that an extra is free with each ordered snack.
 */
public class FreeExtraBonusStrategy implements BonusStrategy {
    private static final String BONUS_NAME = "Free extra with a beverage and a snack";

    /**
     * Applies the bonus to an order so that an extra is free with each ordered snack.
     *
     * @param order the order to apply the bonus to
     * @return the order with the bonus applied
     */
    @Override
    public Order applyBonus(Order order) {
        var numBonusesToApply = (int)order.choices().stream()
                .filter(c -> !c.product().beverage())
                .count();

        if (numBonusesToApply == 0) {
            return order;
        }

        var choicesByMostExpensiveExtra = new ArrayList<>(order.choices());
        var numBonusesApplied = 0;
        var newChoices = new ArrayList<ProductChoice>();

        choicesByMostExpensiveExtra.sort(Comparator.comparing(ProductChoice::mostExpensiveExtra));

        for (var i = choicesByMostExpensiveExtra.size() - 1; i >= 0; i--) {
            if (!bonusAppliesOnChoice(choicesByMostExpensiveExtra.get(i), numBonusesToApply, numBonusesApplied)) {
                newChoices.add(choicesByMostExpensiveExtra.get(i));
                continue;
            }

            Bonus bonus = new Bonus(BONUS_NAME, choicesByMostExpensiveExtra.get(i).mostExpensiveExtra());
            newChoices.add(choicesByMostExpensiveExtra.get(i).withBonus(bonus));
            numBonusesApplied++;
        }

        newChoices.sort(Comparator.comparing(ProductChoice::itemNumber));

        return new Order(newChoices);
    }

    private static boolean bonusAppliesOnChoice(ProductChoice choice, int numBonusesToApply, int numBonusesApplied) {
        return choice.bonus().isEmpty() &&
                choice.mostExpensiveExtra() > 0.0f &&
                numBonusesApplied < numBonusesToApply;
    }
}
