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
    void testFindOneByNameIsWorksCorrectly() {
        Assertions.assertInstanceOf(ProductDTO.class, productsRepository.findOneByName("A"));
        Assertions.assertEquals(
                new ProductDTO("A", 1.25, 3, 3.0),
                productsRepository.findOneByName("A")
        );
    }
}