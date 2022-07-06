package goit.javacore.hw2;

import goit.javacore.hw2.dto.CartItemDTO;
import goit.javacore.hw2.dto.ProductDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.stubbing.OngoingStubbingImpl;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {
    //here ypu also have to mock calls to CartItemsService

    @Mock
    private CartItemsService cartItemsServiceMock;
    @InjectMocks
    private CartService cartService;

    @BeforeEach
    void beforeEach() throws NoSuchFieldException, IllegalAccessException {
        Field field = CartService.class.getDeclaredField("cartItemsService");
        field.setAccessible(true);

        Field modifiers = Field.class.getDeclaredField("modifiers");
        modifiers.setAccessible(true);
        modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(cartService, cartItemsServiceMock);
    }

    private ProductDTO getProductDTOA() {
        return new ProductDTO('A', 1.25, 3, 3.00);
    }

    private ProductDTO getProductDTOB() {
        return new ProductDTO('B', 4.25, null, null);
    }

    private ProductDTO getProductDTOC() {
        return new ProductDTO('C', 1.00, 6, 5.00);
    }

    @Test
    void testShouldCalculateCartCorrectlyWithSingleProduct() {
        String cart = "A";
        double expectedTotalAmount = 1.25;

        CartItemDTO cartItemA = new CartItemDTO(getProductDTOA(), 1);


        when(cartItemsServiceMock.defineCartItems(cart))
                .thenReturn(Map.of('A', cartItemA));

        Assertions.assertEquals(expectedTotalAmount, cartService.calculateTotalPrice(cart));
    }

    @Test
    void testShouldCalculateCartCorrectlyWithSingleProductWIthSalesPriceAndMoreThanOneQuantity() {
        String cart = "AAAAA";
        double expectedTotalAmount = 5.50;

        CartItemDTO cartItemA = new CartItemDTO(getProductDTOA(), 5);

        when(cartItemsServiceMock.defineCartItems(cart))
                .thenReturn(Map.of('A', cartItemA));

        Assertions.assertEquals(expectedTotalAmount, cartService.calculateTotalPrice(cart));
    }

    @Test
    void testShouldCalculateCartCorrectlyWithSingleProductWithoutSalesPriceAndMoreThanOneQuantity() {
        String cart = "BBB";
        double expectedTotalAmount = 12.75;

        CartItemDTO cartItemB = new CartItemDTO(getProductDTOB(), 3);


        when(cartItemsServiceMock.defineCartItems(cart))
                .thenReturn(Map.of('B', cartItemB));

        Assertions.assertEquals(expectedTotalAmount, cartService.calculateTotalPrice(cart));
    }

    @Test
    void testShouldCalculateCartCorrectlyWithDifferentProductWithMoreThanOneQuantity() {
        String cart = "AABBBCCCCCCCC";
        double expectedTotalAmount = 22.25;
        CartItemDTO cartItemA = new CartItemDTO(getProductDTOA(), 2);
        CartItemDTO cartItemB = new CartItemDTO(getProductDTOB(), 3);
        CartItemDTO cartItemC = new CartItemDTO(getProductDTOC(), 8);

        when(cartItemsServiceMock.defineCartItems(cart))
                .thenReturn(Map.of('A', cartItemA, 'B', cartItemB, 'C', cartItemC));

        Assertions.assertEquals(expectedTotalAmount, cartService.calculateTotalPrice(cart));
    }

    @Test
    void testShouldCalculateCartCorrectlyWithEmptyCart() {
        String cart = "";
        double expectedTotalAmount = 0.00;

        when(cartItemsServiceMock.defineCartItems(cart))
                .thenReturn(Collections.emptyMap());

        Assertions.assertEquals(expectedTotalAmount, cartService.calculateTotalPrice(cart));

        verify(cartItemsServiceMock, times(1)).defineCartItems(cart);
    }
}