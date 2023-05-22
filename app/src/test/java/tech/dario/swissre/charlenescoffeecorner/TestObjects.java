package tech.dario.swissre.charlenescoffeecorner;

import tech.dario.swissre.charlenescoffeecorner.model.Extra;
import tech.dario.swissre.charlenescoffeecorner.model.Product;
import tech.dario.swissre.charlenescoffeecorner.model.Size;

import java.util.Collections;
import java.util.List;

public class TestObjects {
    public static final Size SIZE_SMALL = new Size("Small", 3.0f);
    public static final Size SIZE_MEDIUM = new Size("Medium", 3.5f);
    public static final Size SIZE_LARGE = new Size("Large", 4.0f);
    public static final Size SIZE_STANDARD = new Size("Standard", 5.0f);

    public static final Extra CHEAP_EXTRA = new Extra("Cheap extra", 0.5f);
    public static final Extra EXPENSIVE_EXTRA = new Extra("Expensive extra", 1.5f);

    public static final Product PRODUCT_BEVERAGE = new Product("Drink", true, List.of(SIZE_SMALL, SIZE_MEDIUM, SIZE_LARGE), List.of(CHEAP_EXTRA, EXPENSIVE_EXTRA));
    public static final Product PRODUCT_SNACK = new Product("Snack", false, List.of(SIZE_STANDARD), Collections.emptyList());
}
