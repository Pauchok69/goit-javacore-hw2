package goit.javacore.hw2;

import goit.javacore.hw2.dto.ProductDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class ProductsDefinerTest {
    @Test
    void testIfDefineProductListFromCartIsCorrect() {
        ProductsDefiner productsDefiner = new ProductsDefiner();
        Assertions.assertEquals(7, productsDefiner.defineProductsListFrom("AAABACDWW").size());
    }
}