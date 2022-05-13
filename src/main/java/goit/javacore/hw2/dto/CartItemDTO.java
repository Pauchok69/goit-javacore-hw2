package goit.javacore.hw2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItemDTO {
    ProductDTO product;
    int quantity;
}
