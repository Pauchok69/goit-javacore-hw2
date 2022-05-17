package goit.javacore.hw2;

import goit.javacore.hw2.dto.CartItemDTO;
import goit.javacore.hw2.dto.ProductDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartItemsService {
    private final ProductsDefiner productsDefiner = new ProductsDefiner();

    public Map<Character, CartItemDTO> defineCartItems(String cartRow) {
        Map<Character, CartItemDTO> cartItemsMap = new HashMap<>();

        for (ProductDTO productDTO : defineProductsList(cartRow)) {
            if (!cartItemsMap.containsKey(productDTO.getCode())) {
                CartItemDTO cartItem = new CartItemDTO(productDTO, 1);
                cartItemsMap.put(productDTO.getCode(), cartItem);

                continue;
            }

            CartItemDTO cartItem = cartItemsMap.get(productDTO.getCode());
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartItemsMap.put(productDTO.getCode(), cartItem);
        }

        return cartItemsMap;
    }

    private List<ProductDTO> defineProductsList(String cartRow) {
        return productsDefiner.defineProductsListFrom(cartRow);
    }
}
