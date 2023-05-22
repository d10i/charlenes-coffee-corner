package tech.dario.swissre.charlenescoffeecorner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainIntegrationTest {
    private final PrintStream originalSystemOut = System.out;
    private final InputStream originalSystemIn = System.in;

    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public void setUp() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalSystemOut);
        System.setIn(originalSystemIn);
    }

    @Test
    void testMainEmptyOrder() {
        String input = """
                done
                """;
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        Main.main(null);

        String expectedOutput = """
                Hello, what would you like? (or 'done' to finish order)
                1. Coffee
                2. Bacon Roll
                3. Freshly squeezed orange juice

                >\s
                Thank you.""";
        assertEquals(expectedOutput, outputStream.toString().trim());
    }

    @Test
    void testMainNumberTooHigh() {
        String input = """
                4
                done
                """;
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        Main.main(null);

        String expectedOutput = """
                Hello, what would you like? (or 'done' to finish order)
                1. Coffee
                2. Bacon Roll
                3. Freshly squeezed orange juice

                >\s
                Please choose a number between 1 and 3.
                1. Coffee
                2. Bacon Roll
                3. Freshly squeezed orange juice

                >\s
                Thank you.""";
        assertEquals(expectedOutput, outputStream.toString().trim());
    }

    @Test
    void testMainInvalidNumber() {
        String input = """
                notanumber
                done
                """;
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        Main.main(null);

        String expectedOutput = """
                Hello, what would you like? (or 'done' to finish order)
                1. Coffee
                2. Bacon Roll
                3. Freshly squeezed orange juice

                >\s
                Sorry, I didn't catch that, could you please repeat?
                1. Coffee
                2. Bacon Roll
                3. Freshly squeezed orange juice

                >\s
                Thank you.""";
        assertEquals(expectedOutput, outputStream.toString().trim());
    }

    @Test
    void testMainOneSnack() {
        String input = """
                2
                done
                """;
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        Main.main(null);

        String expectedOutput = """
                Hello, what would you like? (or 'done' to finish order)
                1. Coffee
                2. Bacon Roll
                3. Freshly squeezed orange juice

                >\s
                Thank you.
                Would you like to order anything else? (or 'done' to finish order)
                1. Coffee
                2. Bacon Roll
                3. Freshly squeezed orange juice

                >\s
                Thank you.

                Charlene's Coffee Corner
                RECEIPT
                ---------------------------------
                Standard bacon roll      4.50 CHF
                ---------------------------------
                TOTAL                    4.50 CHF""";
        assertEquals(expectedOutput, outputStream.toString().trim());
    }

    @Test
    void testMainOneSnackAndOneDrinkWithNoExtras() {
        String input = """
                2
                1
                1
                done
                done
                """;
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        Main.main(null);

        String expectedOutput = """
                Hello, what would you like? (or 'done' to finish order)
                1. Coffee
                2. Bacon Roll
                3. Freshly squeezed orange juice

                >\s
                Thank you.
                Would you like to order anything else? (or 'done' to finish order)
                1. Coffee
                2. Bacon Roll
                3. Freshly squeezed orange juice

                >\s
                Thank you.
                What size?
                1. Small
                2. Medium
                3. Large

                >\s
                Thank you.
                Any extras? (or 'done' if not)
                1. Extra milk
                2. Foamed milk
                3. Special roast coffee

                >\s
                Thank you.
                Would you like to order anything else? (or 'done' to finish order)
                1. Coffee
                2. Bacon Roll
                3. Freshly squeezed orange juice

                >\s
                Thank you.

                Charlene's Coffee Corner
                RECEIPT
                ---------------------------------
                Standard bacon roll      4.50 CHF
                Small coffee             2.50 CHF
                ---------------------------------
                TOTAL                    7.00 CHF""";
        assertEquals(expectedOutput, outputStream.toString().trim());
    }

    @Test
    void testMainOneSnackAndOneDrinkWithOneExtra() {
        String input = """
                2
                1
                1
                2
                done
                done
                """;
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        Main.main(null);

        String expectedOutput = """
                Hello, what would you like? (or 'done' to finish order)
                1. Coffee
                2. Bacon Roll
                3. Freshly squeezed orange juice

                >\s
                Thank you.
                Would you like to order anything else? (or 'done' to finish order)
                1. Coffee
                2. Bacon Roll
                3. Freshly squeezed orange juice

                >\s
                Thank you.
                What size?
                1. Small
                2. Medium
                3. Large

                >\s
                Thank you.
                Any extras? (or 'done' if not)
                1. Extra milk
                2. Foamed milk
                3. Special roast coffee

                >\s
                Thank you.
                Any more extras? (or 'done' if not)
                1. Extra milk
                2. Foamed milk
                3. Special roast coffee

                >\s
                Thank you.
                Would you like to order anything else? (or 'done' to finish order)
                1. Coffee
                2. Bacon Roll
                3. Freshly squeezed orange juice

                >\s
                Thank you.

                Charlene's Coffee Corner
                RECEIPT
                -----------------------------------------------------------
                Standard bacon roll                                4.50 CHF
                Small coffee with foamed milk                      3.00 CHF
                Bonus: Free extra with a beverage and a snack     -0.50 CHF
                -----------------------------------------------------------
                TOTAL                                              7.00 CHF""";
        assertEquals(expectedOutput, outputStream.toString().trim());
    }

    @Test
    void testMainOneSnackAndOneDrinkWithTwoExtras() {
        String input = """
                2
                1
                1
                2
                1
                done
                done
                """;
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        Main.main(null);

        String expectedOutput = """
                Hello, what would you like? (or 'done' to finish order)
                1. Coffee
                2. Bacon Roll
                3. Freshly squeezed orange juice

                >\s
                Thank you.
                Would you like to order anything else? (or 'done' to finish order)
                1. Coffee
                2. Bacon Roll
                3. Freshly squeezed orange juice

                >\s
                Thank you.
                What size?
                1. Small
                2. Medium
                3. Large

                >\s
                Thank you.
                Any extras? (or 'done' if not)
                1. Extra milk
                2. Foamed milk
                3. Special roast coffee

                >\s
                Thank you.
                Any more extras? (or 'done' if not)
                1. Extra milk
                2. Foamed milk
                3. Special roast coffee

                >\s
                Thank you.
                Any more extras? (or 'done' if not)
                1. Extra milk
                2. Foamed milk
                3. Special roast coffee

                >\s
                Thank you.
                Would you like to order anything else? (or 'done' to finish order)
                1. Coffee
                2. Bacon Roll
                3. Freshly squeezed orange juice

                >\s
                Thank you.

                Charlene's Coffee Corner
                RECEIPT
                -----------------------------------------------------------
                Standard bacon roll                                4.50 CHF
                Small coffee with extra milk, foamed milk          3.30 CHF
                Bonus: Free extra with a beverage and a snack     -0.50 CHF
                -----------------------------------------------------------
                TOTAL                                              7.30 CHF""";
        assertEquals(expectedOutput, outputStream.toString().trim());
    }

    @Test
    void testMainTenBeveragesAndOneSnack() {
        String input = """
                1
                1
                1
                done
                1
                2
                1
                done
                1
                3
                1
                done
                1
                1
                2
                done
                1
                1
                1
                2
                3
                done
                1
                1
                done
                1
                2
                1
                2
                done
                1
                1
                3
                done
                1
                1
                1
                3
                done
                1
                3
                1
                2
                3
                done
                2
                done
                """;
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        Main.main(null);

        String expectedOutput = """
                Hello, what would you like? (or 'done' to finish order)
                1. Coffee
                2. Bacon Roll
                3. Freshly squeezed orange juice

                >\s
                Thank you.
                What size?
                1. Small
                2. Medium
                3. Large

                >\s
                Thank you.
                Any extras? (or 'done' if not)
                1. Extra milk
                2. Foamed milk
                3. Special roast coffee

                >\s
                Thank you.
                Any more extras? (or 'done' if not)
                1. Extra milk
                2. Foamed milk
                3. Special roast coffee

                >\s
                Thank you.
                Would you like to order anything else? (or 'done' to finish order)
                1. Coffee
                2. Bacon Roll
                3. Freshly squeezed orange juice

                >\s
                Thank you.
                What size?
                1. Small
                2. Medium
                3. Large

                >\s
                Thank you.
                Any extras? (or 'done' if not)
                1. Extra milk
                2. Foamed milk
                3. Special roast coffee

                >\s
                Thank you.
                Any more extras? (or 'done' if not)
                1. Extra milk
                2. Foamed milk
                3. Special roast coffee

                >\s
                Thank you.
                Would you like to order anything else? (or 'done' to finish order)
                1. Coffee
                2. Bacon Roll
                3. Freshly squeezed orange juice

                >\s
                Thank you.
                What size?
                1. Small
                2. Medium
                3. Large

                >\s
                Thank you.
                Any extras? (or 'done' if not)
                1. Extra milk
                2. Foamed milk
                3. Special roast coffee

                >\s
                Thank you.
                Any more extras? (or 'done' if not)
                1. Extra milk
                2. Foamed milk
                3. Special roast coffee

                >\s
                Thank you.
                Would you like to order anything else? (or 'done' to finish order)
                1. Coffee
                2. Bacon Roll
                3. Freshly squeezed orange juice

                >\s
                Thank you.
                What size?
                1. Small
                2. Medium
                3. Large

                >\s
                Thank you.
                Any extras? (or 'done' if not)
                1. Extra milk
                2. Foamed milk
                3. Special roast coffee

                >\s
                Thank you.
                Any more extras? (or 'done' if not)
                1. Extra milk
                2. Foamed milk
                3. Special roast coffee

                >\s
                Thank you.
                Would you like to order anything else? (or 'done' to finish order)
                1. Coffee
                2. Bacon Roll
                3. Freshly squeezed orange juice

                >\s
                Thank you.
                What size?
                1. Small
                2. Medium
                3. Large

                >\s
                Thank you.
                Any extras? (or 'done' if not)
                1. Extra milk
                2. Foamed milk
                3. Special roast coffee

                >\s
                Thank you.
                Any more extras? (or 'done' if not)
                1. Extra milk
                2. Foamed milk
                3. Special roast coffee

                >\s
                Thank you.
                Any more extras? (or 'done' if not)
                1. Extra milk
                2. Foamed milk
                3. Special roast coffee

                >\s
                Thank you.
                Any more extras? (or 'done' if not)
                1. Extra milk
                2. Foamed milk
                3. Special roast coffee

                >\s
                Thank you.
                Would you like to order anything else? (or 'done' to finish order)
                1. Coffee
                2. Bacon Roll
                3. Freshly squeezed orange juice

                >\s
                Thank you.
                What size?
                1. Small
                2. Medium
                3. Large

                >\s
                Thank you.
                Any extras? (or 'done' if not)
                1. Extra milk
                2. Foamed milk
                3. Special roast coffee

                >\s
                Thank you.
                Would you like to order anything else? (or 'done' to finish order)
                1. Coffee
                2. Bacon Roll
                3. Freshly squeezed orange juice

                >\s
                Thank you.
                What size?
                1. Small
                2. Medium
                3. Large

                >\s
                Thank you.
                Any extras? (or 'done' if not)
                1. Extra milk
                2. Foamed milk
                3. Special roast coffee

                >\s
                Thank you.
                Any more extras? (or 'done' if not)
                1. Extra milk
                2. Foamed milk
                3. Special roast coffee

                >\s
                Thank you.
                Any more extras? (or 'done' if not)
                1. Extra milk
                2. Foamed milk
                3. Special roast coffee

                >\s
                Thank you.
                Would you like to order anything else? (or 'done' to finish order)
                1. Coffee
                2. Bacon Roll
                3. Freshly squeezed orange juice

                >\s
                Thank you.
                What size?
                1. Small
                2. Medium
                3. Large

                >\s
                Thank you.
                Any extras? (or 'done' if not)
                1. Extra milk
                2. Foamed milk
                3. Special roast coffee

                >\s
                Thank you.
                Any more extras? (or 'done' if not)
                1. Extra milk
                2. Foamed milk
                3. Special roast coffee

                >\s
                Thank you.
                Would you like to order anything else? (or 'done' to finish order)
                1. Coffee
                2. Bacon Roll
                3. Freshly squeezed orange juice

                >\s
                Thank you.
                What size?
                1. Small
                2. Medium
                3. Large

                >\s
                Thank you.
                Any extras? (or 'done' if not)
                1. Extra milk
                2. Foamed milk
                3. Special roast coffee

                >\s
                Thank you.
                Any more extras? (or 'done' if not)
                1. Extra milk
                2. Foamed milk
                3. Special roast coffee

                >\s
                Thank you.
                Any more extras? (or 'done' if not)
                1. Extra milk
                2. Foamed milk
                3. Special roast coffee

                >\s
                Thank you.
                Would you like to order anything else? (or 'done' to finish order)
                1. Coffee
                2. Bacon Roll
                3. Freshly squeezed orange juice

                >\s
                Thank you.
                What size?
                1. Small
                2. Medium
                3. Large

                >\s
                Thank you.
                Any extras? (or 'done' if not)
                1. Extra milk
                2. Foamed milk
                3. Special roast coffee

                >\s
                Thank you.
                Any more extras? (or 'done' if not)
                1. Extra milk
                2. Foamed milk
                3. Special roast coffee

                >\s
                Thank you.
                Any more extras? (or 'done' if not)
                1. Extra milk
                2. Foamed milk
                3. Special roast coffee

                >\s
                Thank you.
                Any more extras? (or 'done' if not)
                1. Extra milk
                2. Foamed milk
                3. Special roast coffee

                >\s
                Thank you.
                Would you like to order anything else? (or 'done' to finish order)
                1. Coffee
                2. Bacon Roll
                3. Freshly squeezed orange juice

                >\s
                Thank you.
                Would you like to order anything else? (or 'done' to finish order)
                1. Coffee
                2. Bacon Roll
                3. Freshly squeezed orange juice

                >\s
                Thank you.

                Charlene's Coffee Corner
                RECEIPT
                -----------------------------------------------------------------------------
                Small coffee with extra milk                                         2.80 CHF
                Medium coffee with extra milk                                        3.30 CHF
                Large coffee with extra milk                                         3.80 CHF
                Small coffee with foamed milk                                        3.00 CHF
                Small coffee with extra milk, foamed milk, special roast coffee      4.20 CHF
                Bonus: Every 5th beverage is for free                               -4.20 CHF
                Small coffee                                                         2.50 CHF
                Medium coffee with extra milk, foamed milk                           3.80 CHF
                Small coffee with special roast coffee                               3.40 CHF
                Small coffee with extra milk, special roast coffee                   3.70 CHF
                Bonus: Free extra with a beverage and a snack                       -0.90 CHF
                Large coffee with extra milk, foamed milk, special roast coffee      5.20 CHF
                Bonus: Every 5th beverage is for free                               -5.20 CHF
                Standard bacon roll                                                  4.50 CHF
                -----------------------------------------------------------------------------
                TOTAL                                                               29.90 CHF""";
        assertEquals(expectedOutput, outputStream.toString().trim());
    }
}
