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

    private ProductDTO getProductDTOA() {
        return new ProductDTO('A', 1.25, 3, 3.00);
    }

    private ProductDTO getProductDTOB() {
        return new ProductDTO('B', 4.25, null, null);
    }

    private ProductDTO getProductDTOC() {
        return new ProductDTO('C', 1.00, 6, 5.00);
    }

    private ProductDTO getProductDTOD() {
        return new ProductDTO('D', 0.75, null, null);
    }

    @Test
    void testDefineProductListFromCartWithExistProducts() {
        when(productsRepositoryMock.findOneByCode('A')).thenReturn(getProductDTOA());
        when(productsRepositoryMock.findOneByCode('B')).thenReturn(getProductDTOB());
        when(productsRepositoryMock.findOneByCode('C')).thenReturn(getProductDTOC());
        when(productsRepositoryMock.findOneByCode('D')).thenReturn(getProductDTOD());

        Assertions.assertEquals(7, productsDefiner.defineProductsListFrom("AAABACD").size());
        verify(productsRepositoryMock, times(7)).findOneByCode(anyChar());
    }

    @Test
    void testDefineProductListFromCartWithNotProductsCharactersInCart() {
        when(productsRepositoryMock.findOneByCode('A')).thenReturn(getProductDTOA());

        Assertions.assertEquals(1, productsDefiner.defineProductsListFrom("   __ 324 A W").size());
        verify(productsRepositoryMock, times(13)).findOneByCode(anyChar());
    }
}