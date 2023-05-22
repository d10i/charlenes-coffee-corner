package tech.dario.swissre.charlenescoffeecorner;

import tech.dario.swissre.charlenescoffeecorner.bonus.BonusStrategy;
import tech.dario.swissre.charlenescoffeecorner.bonus.FifthBeverageBonusStrategy;
import tech.dario.swissre.charlenescoffeecorner.bonus.FreeExtraBonusStrategy;
import tech.dario.swissre.charlenescoffeecorner.model.Extra;
import tech.dario.swissre.charlenescoffeecorner.model.Product;
import tech.dario.swissre.charlenescoffeecorner.model.Size;
import tech.dario.swissre.charlenescoffeecorner.receipt.ReceiptGeneratorImpl;

import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        final var receiptGenerator = new ReceiptGeneratorImpl();
        final List<BonusStrategy> bonusStrategies = List.of(
                new FifthBeverageBonusStrategy(),
                new FreeExtraBonusStrategy()
        );
        final List<Product> offering = List.of(
                new Product("Coffee",
                        true,
                        List.of(
                                new Size("Small", 2.50f),
                                new Size("Medium", 3.00f),
                                new Size("Large", 3.50f)
                        ),
                        List.of(
                                new Extra("Extra milk", 0.30f),
                                new Extra("Foamed milk", 0.50f),
                                new Extra("Special roast coffee", 0.90f)
                        )
                ),
                new Product("Bacon Roll",
                        false,
                        List.of(
                                new Size("Standard", 4.50f)
                        ),
                        Collections.emptyList()
                ),
                new Product("Freshly squeezed orange juice",
                        true,
                        List.of(
                                new Size("0.25l", 3.95f)
                        ),
                        Collections.emptyList()
                )
        );
        final var orderSystem = new OrderSystem(receiptGenerator, bonusStrategies, offering);

        orderSystem.newOrder(System.in, System.out);
    }
}
