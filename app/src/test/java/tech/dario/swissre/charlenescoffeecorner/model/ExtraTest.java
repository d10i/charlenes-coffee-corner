package tech.dario.swissre.charlenescoffeecorner.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExtraTest {
    @Test
    void testDisplay() {
        String name = "name";
        var extra = new Extra(name, 0.5f);
        assertEquals(name, extra.display());
    }
}
