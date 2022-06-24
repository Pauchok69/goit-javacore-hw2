package goit.javacore.hw2;

import goit.javacore.hw2.dto.ProductDTO;
import goit.javacore.hw2.repository.ProductsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.mockito.ArgumentMatchers.anyChar;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductsDefinerTest {
    //here you have to mock all calls to ProductsRepository - Done
    @Mock
    private ProductsRepository productsRepositoryMock;
    @Mock
    private ProductDTO productDTOMock;
    @InjectMocks
    private ProductsDefiner productsDefiner;

    @BeforeEach
    void beforeEach() throws NoSuchFieldException, IllegalAccessException {
        Field field = ProductsDefiner.class.getDeclaredField("productsRepository");
        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(productsDefiner, productsRepositoryMock);

        when(productsRepositoryMock.findOneByCode(anyChar())).thenReturn(null);
    }

    @Test
    void testDefineProductListFromCartWithExistProducts() {
        when(productsRepositoryMock.findOneByCode('A')).thenReturn(productDTOMock);
        when(productsRepositoryMock.findOneByCode('B')).thenReturn(productDTOMock);
        when(productsRepositoryMock.findOneByCode('C')).thenReturn(productDTOMock);
        when(productsRepositoryMock.findOneByCode('D')).thenReturn(productDTOMock);

        Assertions.assertEquals(7, productsDefiner.defineProductsListFrom("AAABACD").size());
        verify(productsRepositoryMock, times(7)).findOneByCode(anyChar());
    }

    @Test
    void testDefineProductListFromCartWithNotProductsCharactersInCart() {
        when(productsRepositoryMock.findOneByCode('A')).thenReturn(productDTOMock);

        Assertions.assertEquals(1, productsDefiner.defineProductsListFrom("   __ 324 A W").size());
        verify(productsRepositoryMock, times(13)).findOneByCode(anyChar());
    }
}