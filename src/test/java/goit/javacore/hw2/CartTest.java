package goit.javacore.hw2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CartTest {
    @Test
    void testIfCalculationOfTotalPriceForCartIsCorrectly() {
        Cart cartCalculator = new Cart();

        Assertions.assertEquals(
                1.25,
                cartCalculator.calculateTotalPrice("A")
        );
    }
}