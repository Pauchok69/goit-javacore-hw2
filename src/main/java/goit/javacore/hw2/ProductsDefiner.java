package goit.javacore.hw2;

import java.util.ArrayList;
import java.util.List;

import goit.javacore.hw2.dto.ProductDTO;
import goit.javacore.hw2.repository.ProductsRepository;

public class ProductsDefiner {
    private final ProductsRepository productsRepository = new ProductsRepository();

    public List<ProductDTO> defineProductsListFrom(String cart) {
        List<ProductDTO> productsList = new ArrayList<>();
        char[] chars = cart.toCharArray();

        for (char aChar : chars) {
            ProductDTO product = productsRepository.findOneByCode(aChar);

            if (product == null) {
                continue;
            }
            productsList.add(product);
        }

        return productsList;
    }
}
