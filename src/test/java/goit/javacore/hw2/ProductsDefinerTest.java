package goit.javacore.hw2;

import goit.javacore.hw2.dto.ProductDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class ProductsDefinerTest {
    @Test
    void testIfDefineProductListFromCartIsCorrect() {
        ProductsDefiner productsDefiner = new ProductsDefiner();
        List<ProductDTO> expectedProductsList = List.of(
                new ProductDTO("A", 1.25, 3, 3.00)
        );

        Assertions.assertEquals(expectedProductsList, productsDefiner.defineProductsListFrom("A"));
    }
}