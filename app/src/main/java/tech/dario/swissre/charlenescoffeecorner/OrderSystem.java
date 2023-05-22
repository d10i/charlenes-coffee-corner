package tech.dario.swissre.charlenescoffeecorner;

import tech.dario.swissre.charlenescoffeecorner.bonus.BonusStrategy;
import tech.dario.swissre.charlenescoffeecorner.model.*;

import tech.dario.swissre.charlenescoffeecorner.receipt.ReceiptGenerator;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;

/**
 * The order system is the main entry point to the order system.
 */
public class OrderSystem {
    private static final String THANK_YOU = "Thank you.";

    private final ReceiptGenerator receiptGenerator;
    private final List<BonusStrategy> bonusStrategies;
    private final List<Product> offering;

    /**
     * Creates a new order system.
     * @param receiptGenerator receipt generator to use
     * @param bonusStrategies list of bonus strategies to apply to the order
     * @param offering list of products offered by the order system
     */
    public OrderSystem(ReceiptGenerator receiptGenerator,
                       List<BonusStrategy> bonusStrategies,
                       List<Product> offering) {
        this.receiptGenerator = receiptGenerator;
        this.bonusStrategies = bonusStrategies;
        this.offering = offering;
    }

    /**
     * Allows placing a new order in the order system. It takes order from the input and provides guidance throughout
     * the order process. It guides the customer through the order process, allowing them to choose products, sizes and
     * extras. Customers can continue adding items to their order until they decide to finish. Once the order is
     * completed, the method applies any applicable bonuses to the order. Finally, it generates a clear and concise
     * receipt for the order and displays it to the customer.
     * @param in input stream to read from
     * @param out output stream to write to
     */
    public void newOrder(InputStream in, PrintStream out) {
        var scanner = new Scanner(in);
        Order order = new Order(Collections.emptyList());
        boolean firstOrder = true;
        int currentItemNumber = 1;

        while (true) {
            var optionalProductChoice = chooseProduct(scanner, out, offering, firstOrder);
            firstOrder = false;

            if (optionalProductChoice.isEmpty()) {
                break;
            }

            Product product = optionalProductChoice.get();
            var size = chooseSize(scanner, out, product);
            var extras = chooseExtras(scanner, out, product);

            order = order.withChoice(new ProductChoice(currentItemNumber++, product, size, extras, Optional.empty()));
        }

        out.println();

        order = applyBonuses(order);

        out.println(receiptGenerator.generateReceipt(order));
    }

    private Optional<Product> chooseProduct(Scanner scanner,
                                            PrintStream out,
                                            List<Product> offering,
                                            boolean firstOrder) {
        if (firstOrder) {
            out.println("Hello, what would you like? (or 'done' to finish order)");
        } else {
            out.println("Would you like to order anything else? (or 'done' to finish order)");
        }

        return chooseGeneric(scanner, out, true, offering);
    }

    private Size chooseSize(Scanner scanner, PrintStream out, Product product) {
        if (product.sizes().size() == 1) {
            return product.sizes().get(0);
        }

        out.println("What size?");

        return chooseGeneric(scanner, out, false, product.sizes()).get();
    }

    private Set<Extra> chooseExtras(Scanner scanner, PrintStream out, Product product) {
        if (product.extras().isEmpty()) {
            return Collections.emptySet();
        }

        var firstExtra = true;
        Set<Extra> extras = new HashSet<>(product.extras().size());

        while (true) {
            if (firstExtra) {
                out.println("Any extras? (or 'done' if not)");
            } else {
                out.println("Any more extras? (or 'done' if not)");
            }

            firstExtra = false;

            var optionalExtra = chooseGeneric(scanner, out, true, product.extras());
            if (optionalExtra.isEmpty()) {
                return extras;
            }

            extras.add(optionalExtra.get());
        }
    }

    private <T extends Displayable> Optional<T> chooseGeneric(Scanner scanner,
                                          PrintStream out,
                                          boolean optional,
                                          List<T> items) {
        while (true) {
            for (int i = 0; i < items.size(); i++) {
                out.printf("%d. %s\n", i + 1, items.get(i).display());
            }

            out.print("\n> ");

            String choice = scanner.nextLine();

            out.println();

            if (optional && choice.equalsIgnoreCase("done")) {
                out.println(THANK_YOU);
                return Optional.empty();
            }

            try {
                int choiceNumber = Integer.parseInt(choice);
                if (choiceNumber >= 1 && choiceNumber <= items.size()) {
                    out.println(THANK_YOU);
                    return Optional.of(items.get(choiceNumber - 1));
                } else {
                    out.printf("Please choose a number between 1 and %d.\n", items.size());
                }
            } catch (NumberFormatException e) {
                out.println("Sorry, I didn't catch that, could you please repeat?");
            }
        }
    }

    private Order applyBonuses(Order order) {
        for (BonusStrategy strategy : this.bonusStrategies) {
            order = strategy.applyBonus(order);
        }

        return order;
    }
}
