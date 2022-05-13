package goit.javacore.hw2.repository;

import goit.javacore.hw2.dto.ProductDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductsRepositoryTest {
    ProductsRepository productsRepository;

    @BeforeEach
    void setUp() {
        productsRepository = new ProductsRepository();
    }

    @Test
    void testFindOneByNameIsWorksCorrectlyForExistsProduct() {
        ProductDTO productA = productsRepository.findOneByCode('A');

        Assertions.assertInstanceOf(ProductDTO.class, productA);
        Assertions.assertEquals(
                new ProductDTO('A', 1.25, 3, 3.0),
                productA
        );
        Assertions.assertEquals('A', productA.getCode());
    }

    @Test
    void testFindOneByNameReturnsNullForNotExistProduct() {
        Assertions.assertNull(productsRepository.findOneByCode('W'));
    }
}