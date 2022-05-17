package goit.javacore.hw2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class CartItemsServiceTest {
    private CartItemsService cartItemsService;

    @BeforeEach
    void beforeEach() {
        cartItemsService = new CartItemsService();
    }

    @Test
    void testShouldContainCorrectSizeOfCartItemsMap() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("AAABBB", 2);
        expected.put("AA", 1);
        expected.put("AACCBB", 3);
        expected.put("   AACCBB__U", 3);

        for (Map.Entry<String, Integer> entry : expected.entrySet()) {
            Assertions.assertEquals(
                    entry.getValue(),
                    cartItemsService.defineCartItems(entry.getKey()).size(),
                    entry::getKey
            );
        }
    }
}