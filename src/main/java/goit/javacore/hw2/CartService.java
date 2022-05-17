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

            if (product.hasSalePrice() && product.getSaleCount() >= quantity) {
                int remainder = quantity % product.getSaleCount();
                int saleGroups = quantity / product.getSaleCount();

                result += saleGroups * product.getSalePrice() + remainder * product.getPrice();
            } else {
                result += cartItem.getProduct().getPrice() * quantity;
            }
        }

        return result;
    }
}
