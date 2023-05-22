package tech.dario.swissre.charlenescoffeecorner.receipt;

import tech.dario.swissre.charlenescoffeecorner.model.Bonus;
import tech.dario.swissre.charlenescoffeecorner.model.Order;
import tech.dario.swissre.charlenescoffeecorner.model.ProductChoice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Allows generating a receipt for an order.
 */
public class ReceiptGeneratorImpl implements ReceiptGenerator {
    private static final String CURRENCY = "CHF";
    private static final String TOTAL = "TOTAL";
    private static final int NUMBER_WIDTH = 10;
    private static final String NUMBER_FORMAT = String.format("%%%d.2f %s", NUMBER_WIDTH, CURRENCY);

    /**
     * Generates a receipt for an order.
     *
     * @param order the order to generate the receipt for
     * @return the receipt
     */
    @Override
    public String generateReceipt(Order order) {
        var sb = new StringBuilder();

        var items = getReceiptItems(order);

        if (items.isEmpty()) {
            return "";
        }

        var longestItemName = calculateLongestItem(items);

        appendHeader(sb, longestItemName);
        appendItems(sb, items, longestItemName);
        appendFooter(sb, items, longestItemName);

        return sb.toString();
    }

    private List<ReceiptItem> getReceiptItems(Order order) {
        var items = new ArrayList<ReceiptItem>();

        for (ProductChoice choice : order.choices()) {
            items.add(new ReceiptItem(getReceiptItemName(choice), choice.priceWithoutBonus()));

            var optionalBonus = choice.bonus();
            if (optionalBonus.isPresent()) {
                var bonus = optionalBonus.get();
                items.add(new ReceiptItem(getReceiptItemBonusName(bonus), -bonus.amount()));
            }
        }

        return items;
    }

    private String getReceiptItemName(ProductChoice choice) {
        StringBuilder str = new StringBuilder();

        str.append(choice.size().name());
        str.append(" ");
        str.append(choice.product().name().toLowerCase());

        if (!choice.extras().isEmpty()) {
            var extras = choice.extras().stream()
                    .map(s -> s.name().toLowerCase()).collect(Collectors.joining(", "));
            str.append(" with ");
            str.append(extras);
        }

        return str.toString();
    }

    private String getReceiptItemBonusName(Bonus bonus) {
        return "Bonus: " + bonus.name();
    }

    private int calculateLongestItem(List<ReceiptItem> items) {
        var longestItemName = 0;

        for (ReceiptItem item : items) {
            if (item.name().length() > longestItemName) {
                longestItemName = item.name().length();
            }
        }

        return longestItemName;
    }

    private static void appendHeader(StringBuilder sb, int longestItemName) {
        sb.append("Charlene's Coffee Corner\n");
        sb.append("RECEIPT\n");
        sb.append("-".repeat(longestItemName + NUMBER_WIDTH + 1 + CURRENCY.length()));
        sb.append("\n");
    }

    private void appendItems(StringBuilder sb, List<ReceiptItem> items, int longestItemName) {
        for (ReceiptItem item : items) {
            sb.append(item.name());
            sb.append(" ".repeat(longestItemName - item.name().length()));
            sb.append(String.format(NUMBER_FORMAT, item.amount()));
            sb.append("\n");
        }
    }

    private void appendFooter(StringBuilder sb, List<ReceiptItem> items, int longestItemName) {
        var totalCost = calculateTotalCost(items);

        sb.append("-".repeat(longestItemName + NUMBER_WIDTH + 1 + CURRENCY.length()));
        sb.append("\n");

        sb.append(TOTAL);
        sb.append(" ".repeat(longestItemName - TOTAL.length()));
        sb.append(String.format(NUMBER_FORMAT, totalCost));
        sb.append("\n");
    }

    private float calculateTotalCost(List<ReceiptItem> items) {
        float sum = 0.0f;

        for (ReceiptItem item : items) {
            sum += item.amount();
        }

        return sum;
    }
}
