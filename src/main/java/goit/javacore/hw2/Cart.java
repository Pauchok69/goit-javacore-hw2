package goit.javacore.hw2;

import goit.javacore.hw2.dto.CartItemDTO;
import goit.javacore.hw2.dto.ProductDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {

    public Double calculateTotalPrice(String cart) {
        ProductsDefiner productsDefiner = new ProductsDefiner();

        List<ProductDTO> productsList = productsDefiner.defineProductsListFrom(cart);

        Map<Character, CartItemDTO> cartItems = new HashMap<>();

        for (ProductDTO productDTO : productsList) {
            if (cartItems.containsKey(productDTO.getCode())) {
                CartItemDTO cartItem = cartItems.get(productDTO.getCode());
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                cartItems.put(productDTO.getCode(), cartItem);
                continue;
            }
            CartItemDTO cartItem = new CartItemDTO(productDTO, 1);
            cartItems.put(productDTO.getCode(), cartItem);
        }

        Double result = 0.0;

        for (Map.Entry<Character, CartItemDTO> entry : cartItems.entrySet()) {
            CartItemDTO cartItem = entry.getValue();

            result += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }

        return result;
    }
}
