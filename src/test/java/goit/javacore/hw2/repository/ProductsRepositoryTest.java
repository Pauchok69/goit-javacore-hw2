package goit.javacore.hw2.repository;

import goit.javacore.hw2.dto.ProductDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductsRepositoryTest {
    ProductsRepository productsRepository;

    @BeforeEach
    void setUp() {
        productsRepository = new ProductsRepository();
    }

    @Test
    void testFindOneByNameIsWorksCorrectlyForExistsProduct() {
        ProductDTO productA = productsRepository.findOneByName("A");

        Assertions.assertInstanceOf(ProductDTO.class, productA);
        Assertions.assertEquals(
                new ProductDTO("A", 1.25, 3, 3.0),
                productA
        );
        Assertions.assertEquals("A", productA.getName());
    }

    @Test
    void testFindOneByNameReturnsNullForNotExistProduct() {
        Assertions.assertNull(productsRepository.findOneByName("W"));
    }
}