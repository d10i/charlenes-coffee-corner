package tech.dario.swissre.charlenescoffeecorner.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SizeTest {
    @Test
    void testDisplay() {
        String name = "name";
        var size = new Size(name, 0.5f);
        assertEquals(name, size.display());
    }
}
