package goit.javacore.hw2;

import goit.javacore.hw2.dto.CartItemDTO;
import goit.javacore.hw2.dto.ProductDTO;

import java.util.Map;

public class CartService {
    private final CartItemsService cartItemsService = new CartItemsService();

    public Double calculateTotalPrice(String cartRow) {
        return doCalculate(getCartItems(cartRow));
    }

    private Map<Character, CartItemDTO> getCartItems(String cartRow) {
        return cartItemsService.defineCartItems(cartRow);
    }

    private double doCalculate(Map<Character, CartItemDTO> cartItems) {
        double result = 0.0;

        for (Map.Entry<Character, CartItemDTO> entry : cartItems.entrySet()) {
            CartItemDTO cartItem = entry.getValue();
            ProductDTO product = cartItem.getProduct();
            int quantity = cartItem.getQuantity();
            Integer saleCount = product.getSaleCount();

            if (product.hasSalePrice() && saleCount <= quantity) {
                int remainder = quantity % saleCount;
                int saleGroups = quantity / saleCount;

                result += saleGroups * product.getSalePrice() + remainder * product.getPrice();
            } else {
                result += product.getPrice() * quantity;
            }
        }

        return result;
    }
}
