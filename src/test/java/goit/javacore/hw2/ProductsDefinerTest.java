package goit.javacore.hw2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ProductsDefinerTest {
    @Test
    void testDefineProductListFromCartWithExistProducts() {
        ProductsDefiner productsDefiner = new ProductsDefiner();
        Assertions.assertEquals(7, productsDefiner.defineProductsListFrom("AAABACD").size());
    }

    @Test
    void testDefineProductListFromCartWithNotProductsCharactersInCart() {
        ProductsDefiner productsDefiner = new ProductsDefiner();
        Assertions.assertEquals(1, productsDefiner.defineProductsListFrom("   __ 324 A W").size());
    }
}