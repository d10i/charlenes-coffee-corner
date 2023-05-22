package tech.dario.swissre.charlenescoffeecorner.model;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductTest {
    @Test
    void testDisplay() {
        String name = "name";
        var product = new Product(name, true, Collections.emptyList(), Collections.emptyList());
        assertEquals(name, product.display());
    }
}
