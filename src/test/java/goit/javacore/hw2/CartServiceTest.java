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
    @Mock
    private CartItemDTO cartItemDTOMock;
    @Mock
    ProductDTO productDTOMock;
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

    @Test
    void testShouldCalculateCartCorrectlyWithSingleProduct() {
        String cart = "A";
        double expectedTotalAmount = 1.25;

        when(cartItemsServiceMock.defineCartItems(cart))
                .thenReturn(Map.of('A', cartItemDTOMock));

        when(cartItemDTOMock.getProduct()).thenReturn(productDTOMock);
        when(cartItemDTOMock.getQuantity()).thenReturn(1);

        when(productDTOMock.getPrice()).thenReturn(1.25);
        when(productDTOMock.hasSalePrice()).thenReturn(true);
        when(productDTOMock.getSaleCount()).thenReturn(3);

        Assertions.assertEquals(expectedTotalAmount, cartService.calculateTotalPrice(cart));

        verify(cartItemsServiceMock, times(1)).defineCartItems(cart);

        verify(cartItemDTOMock, times(1)).getProduct();
        verify(cartItemDTOMock, times(1)).getQuantity();

        verify(productDTOMock, times(1)).getSaleCount();
        verify(productDTOMock, times(1)).hasSalePrice();
        verify(productDTOMock, times(1)).getPrice();
        verify(productDTOMock, never()).getSalePrice();
    }

    @Test
    void testShouldCalculateCartCorrectlyWithSingleProductWIthSalesPriceAndMoreThanOneQuantity() {
        String cart = "AAAAA";
        double expectedTotalAmount = 5.50;

        when(cartItemsServiceMock.defineCartItems(cart))
                .thenReturn(Map.of('A', cartItemDTOMock));

        when(cartItemDTOMock.getProduct()).thenReturn(productDTOMock);
        when(cartItemDTOMock.getQuantity()).thenReturn(5);

        when(productDTOMock.getPrice()).thenReturn(1.25);
        when(productDTOMock.hasSalePrice()).thenReturn(true);
        when(productDTOMock.getSaleCount()).thenReturn(3);
        when(productDTOMock.getSalePrice()).thenReturn(3.00);

        Assertions.assertEquals(expectedTotalAmount, cartService.calculateTotalPrice(cart));

        verify(cartItemsServiceMock, times(1)).defineCartItems(cart);

        verify(cartItemDTOMock, times(1)).getProduct();
        verify(cartItemDTOMock, times(1)).getQuantity();

        verify(productDTOMock, times(1)).getSaleCount();
        verify(productDTOMock, times(1)).hasSalePrice();
        verify(productDTOMock, times(1)).getPrice();
    }

    @Test
    void testShouldCalculateCartCorrectlyWithSingleProductWithoutSalesPriceAndMoreThanOneQuantity() {
        String cart = "BBB";
        double expectedTotalAmount = 12.75;

        when(cartItemsServiceMock.defineCartItems(cart))
                .thenReturn(Map.of('B', cartItemDTOMock));

        when(cartItemDTOMock.getProduct()).thenReturn(productDTOMock);
        when(cartItemDTOMock.getQuantity()).thenReturn(3);

        when(productDTOMock.getPrice()).thenReturn(4.25);
        when(productDTOMock.hasSalePrice()).thenReturn(false);
        when(productDTOMock.getSaleCount()).thenReturn(null);

        Assertions.assertEquals(expectedTotalAmount, cartService.calculateTotalPrice(cart));

        verify(cartItemsServiceMock, times(1)).defineCartItems(cart);

        verify(cartItemDTOMock, times(1)).getProduct();
        verify(cartItemDTOMock, times(1)).getQuantity();

        verify(productDTOMock, times(1)).getSaleCount();
        verify(productDTOMock, times(1)).hasSalePrice();
        verify(productDTOMock, times(1)).getPrice();
        verify(productDTOMock, never()).getSalePrice();
    }

    @Test
    void testShouldCalculateCartCorrectlyWithDifferentProductWithMoreThanOneQuantity() {
        String cart = "AABBBCCCCCCCC";
        double expectedTotalAmount = 22.25;
        CartItemDTO cartItemDTOMockA = mock(CartItemDTO.class);
        CartItemDTO cartItemDTOMockB = mock(CartItemDTO.class);
        CartItemDTO cartItemDTOMockC = mock(CartItemDTO.class);

        ProductDTO productDTOMockA = mock(ProductDTO.class);
        ProductDTO productDTOMockB = mock(ProductDTO.class);
        ProductDTO productDTOMockC = mock(ProductDTO.class);

        when(cartItemsServiceMock.defineCartItems(cart))
                .thenReturn(Map.of('A', cartItemDTOMockA, 'B', cartItemDTOMockB, 'C', cartItemDTOMockC));

        when(cartItemDTOMockA.getProduct()).thenReturn(productDTOMockA);
        when(cartItemDTOMockB.getProduct()).thenReturn(productDTOMockB);
        when(cartItemDTOMockC.getProduct()).thenReturn(productDTOMockC);

        when(cartItemDTOMockA.getQuantity()).thenReturn(2);
        when(cartItemDTOMockB.getQuantity()).thenReturn(3);
        when(cartItemDTOMockC.getQuantity()).thenReturn(8);

        when(productDTOMockA.getPrice()).thenReturn(1.25);
        when(productDTOMockB.getPrice()).thenReturn(4.25);
        when(productDTOMockC.getPrice()).thenReturn(1.00);

        when(productDTOMockA.hasSalePrice()).thenReturn(true);
        when(productDTOMockB.hasSalePrice()).thenReturn(false);
        when(productDTOMockC.hasSalePrice()).thenReturn(true);

        when(productDTOMockA.getSaleCount()).thenReturn(3);
        when(productDTOMockB.getSaleCount()).thenReturn(null);
        when(productDTOMockC.getSaleCount()).thenReturn(6);

        when(productDTOMockC.getSalePrice()).thenReturn(5.00);

        Assertions.assertEquals(expectedTotalAmount, cartService.calculateTotalPrice(cart));

        verify(productDTOMockC, times(1)).getSalePrice();
    }

    @Test
    void testShouldCalculateCartCorrectlyWithEmptyCart() {
        String cart = "";
        double expectedTotalAmount = 0.00;

        when(cartItemsServiceMock.defineCartItems(cart))
                .thenReturn(Collections.emptyMap());

        Assertions.assertEquals(expectedTotalAmount, cartService.calculateTotalPrice(cart));

        verify(cartItemsServiceMock, times(1)).defineCartItems(cart);

        verify(cartItemDTOMock, never()).getProduct();
        verify(cartItemDTOMock, never()).getQuantity();

        verify(productDTOMock, never()).getSaleCount();
        verify(productDTOMock, never()).hasSalePrice();
        verify(productDTOMock, never()).getPrice();
        verify(productDTOMock, never()).getSalePrice();
    }
}