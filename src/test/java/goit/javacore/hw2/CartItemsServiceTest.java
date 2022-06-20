package goit.javacore.hw2;

import goit.javacore.hw2.dto.CartItemDTO;
import goit.javacore.hw2.dto.ProductDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartItemsServiceTest {
    //ProductsDefiner is internal dependency in the class. You have to mock all calls using Mockito
    //here is simple example https://pastebin.com/1zSjHTC2
    //Done

    @Mock
    private ProductsDefiner productsDefiner;
    @Mock
    private ProductDTO productDTO;
    @InjectMocks
    private CartItemsService cartItemsService;

    @BeforeEach
    void beforeEach() throws NoSuchFieldException, IllegalAccessException {
        Field field = CartItemsService.class.getDeclaredField("productsDefiner");
        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(cartItemsService, productsDefiner);
    }

    @Test
    void testShouldContainCorrectSizeAndQuantityForAAABBBB() {
        String cart = "AAABBBB";

        when(productsDefiner.defineProductsListFrom(cart))
                .thenReturn(Arrays.asList(productDTO, productDTO, productDTO, productDTO, productDTO, productDTO, productDTO));
        when(productDTO.getCode()).thenReturn('A', 'A', 'A', 'B', 'B', 'B', 'B');
        Map<Character, CartItemDTO> cartItems = cartItemsService.defineCartItems(cart);

        verify(productDTO, times(7)).getCode();
        Assertions.assertEquals(2, cartItems.size());
        Assertions.assertEquals(3, cartItems.get('A').getQuantity());
        Assertions.assertEquals(4, cartItems.get('B').getQuantity());
    }

    @Test
    void testShouldContainCorrectSizeAndQuantityForAA() {
        String cart = "AA";

        when(productsDefiner.defineProductsListFrom(cart))
                .thenReturn(Arrays.asList(productDTO, productDTO));
        when(productDTO.getCode()).thenReturn('A', 'A');
        Map<Character, CartItemDTO> cartItems = cartItemsService.defineCartItems(cart);

        verify(productDTO, times(2)).getCode();
        Assertions.assertEquals(1, cartItems.size());
        Assertions.assertEquals(2, cartItems.get('A').getQuantity());
    }

    @Test
    void testShouldContainCorrectSizeAndQuantityForACCCBB() {
        String cart = "ACCCBB";

        when(productsDefiner.defineProductsListFrom(cart))
                .thenReturn(Arrays.asList(productDTO, productDTO, productDTO, productDTO, productDTO, productDTO));
        when(productDTO.getCode()).thenReturn('A', 'C', 'C', 'C', 'B', 'B');
        Map<Character, CartItemDTO> cartItems = cartItemsService.defineCartItems(cart);

        verify(productDTO, times(6)).getCode();
        Assertions.assertEquals(3, cartItems.size());
        Assertions.assertEquals(1, cartItems.get('A').getQuantity());
        Assertions.assertEquals(2, cartItems.get('B').getQuantity());
        Assertions.assertEquals(3, cartItems.get('C').getQuantity());
    }

    @Test
    void testShouldContainCorrectSizeAndQuantityForCartWithUninitialisedProducts() {
        String cart = "   AACCBB__U";

        when(productsDefiner.defineProductsListFrom(cart))
                .thenReturn(Arrays.asList(productDTO, productDTO, productDTO, productDTO, productDTO, productDTO));
        when(productDTO.getCode()).thenReturn('A', 'A', 'C', 'C', 'B', 'B');
        Map<Character, CartItemDTO> cartItems = cartItemsService.defineCartItems(cart);

        verify(productDTO, times(6)).getCode();
        Assertions.assertEquals(3, cartItems.size());
        Assertions.assertEquals(2, cartItems.get('A').getQuantity());
        Assertions.assertEquals(2, cartItems.get('B').getQuantity());
        Assertions.assertEquals(2, cartItems.get('C').getQuantity());
    }
}
