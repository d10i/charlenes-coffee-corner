package tech.dario.swissre.charlenescoffeecorner.bonus;

import tech.dario.swissre.charlenescoffeecorner.model.Bonus;
import tech.dario.swissre.charlenescoffeecorner.model.Order;
import tech.dario.swissre.charlenescoffeecorner.model.ProductChoice;

import java.util.ArrayList;

/**
 * Bonus strategy that allows applying the bonus to an order so that every 5th beverage is for free.
 */
public class FifthBeverageBonusStrategy implements BonusStrategy {
    private static final String BONUS_NAME = "Every 5th beverage is for free";

    /**
     * Applies the bonus to an order so that every 5th beverage is for free.
     *
     * @param order the order to apply the bonus to
     * @return the order with the bonus applied
     */
    @Override
    public Order applyBonus(Order order) {
        var numBeverages = 0;
        var numBonusesApplied = 0;
        var newChoices = new ArrayList<ProductChoice>();

        for (ProductChoice choice : order.choices()) {
            if (choice.product().beverage()) {
                numBeverages++;
            }

            if (!bonusAppliesOnChoice(choice, numBeverages, numBonusesApplied)) {
                newChoices.add(choice);
                continue;
            }

            Bonus bonus = new Bonus(BONUS_NAME, choice.priceWithoutBonus());
            newChoices.add(choice.withBonus(bonus));
            numBonusesApplied++;
        }

        return new Order(newChoices);
    }

    private static boolean bonusAppliesOnChoice(ProductChoice choice, int numBeverages, int numBonusesApplied) {
        return choice.bonus().isEmpty() &&
                choice.product().beverage() &&
                numBonusesApplied < numBeverages / 5;
    }
}
