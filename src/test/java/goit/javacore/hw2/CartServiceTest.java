package goit.javacore.hw2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class CartServiceTest {
    @Test
    void testShouldCalculateCartCorrectly() {
        CartService cartService = new CartService();

        Map<String, Double> tests = new HashMap<>();
        tests.put("A", 1.25);
        tests.put("AA", 2.50);
        tests.put(" AAB", 6.75);
        tests.put(" AABZ_33", 6.75);
        tests.put(" AAA", 3.00);
        tests.put(" ABCDABA", 13.25);
        tests.put("", 0.00);

        for (Map.Entry<String, Double> entry : tests.entrySet()) {
            Assertions.assertEquals(
                    entry.getValue(),
                    cartService.calculateTotalPrice(entry.getKey()),
                    entry::getKey
            );
        }
    }
}