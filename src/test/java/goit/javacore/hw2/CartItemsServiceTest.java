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
    void testShouldContainCorrectSizeAndQuantityForAAABBBB() {
        String cart = "AAABBBB";
        ProductDTO productA = getProductDTOA();
        ProductDTO productB = getProductDTOB();

        when(productsDefiner.defineProductsListFrom(cart))
                .thenReturn(Arrays.asList(productA, productA, productA, productB, productB, productB, productB));
        Map<Character, CartItemDTO> cartItems = cartItemsService.defineCartItems(cart);

        Assertions.assertEquals(2, cartItems.size());
        Assertions.assertEquals(3, cartItems.get('A').getQuantity());
        Assertions.assertEquals(4, cartItems.get('B').getQuantity());
    }

    @Test
    void testShouldContainCorrectSizeAndQuantityForAA() {
        String cart = "AA";
        ProductDTO productA = getProductDTOA();

        when(productsDefiner.defineProductsListFrom(cart))
                .thenReturn(Arrays.asList(productA, productA));
        Map<Character, CartItemDTO> cartItems = cartItemsService.defineCartItems(cart);

        Assertions.assertEquals(1, cartItems.size());
        Assertions.assertEquals(2, cartItems.get('A').getQuantity());
    }

    @Test
    void testShouldContainCorrectSizeAndQuantityForACCCBB() {
        String cart = "ACCCBB";
        ProductDTO productA = getProductDTOA();
        ProductDTO productB = getProductDTOB();
        ProductDTO productC = getProductDTOC();


        when(productsDefiner.defineProductsListFrom(cart))
                .thenReturn(Arrays.asList(productA, productC, productC, productC, productB, productB));
        Map<Character, CartItemDTO> cartItems = cartItemsService.defineCartItems(cart);

        Assertions.assertEquals(3, cartItems.size());
        Assertions.assertEquals(1, cartItems.get('A').getQuantity());
        Assertions.assertEquals(2, cartItems.get('B').getQuantity());
        Assertions.assertEquals(3, cartItems.get('C').getQuantity());
    }

    @Test
    void testShouldContainCorrectSizeAndQuantityForCartWithUninitialisedProducts() {
        String cart = "   AACCBB__U";
        ProductDTO productA = getProductDTOA();
        ProductDTO productB = getProductDTOB();
        ProductDTO productC = getProductDTOC();

        when(productsDefiner.defineProductsListFrom(cart))
                .thenReturn(Arrays.asList(productA, productA, productC, productC, productB, productB));
        Map<Character, CartItemDTO> cartItems = cartItemsService.defineCartItems(cart);

        Assertions.assertEquals(3, cartItems.size());
        Assertions.assertEquals(2, cartItems.get('A').getQuantity());
        Assertions.assertEquals(2, cartItems.get('B').getQuantity());
        Assertions.assertEquals(2, cartItems.get('C').getQuantity());
    }
}
