package goit.javacore.hw2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDTO {
    Character code;
    Double price;
    Integer saleCount;
    Double salePrice;

    public boolean hasSalePrice() {
        return saleCount != null && salePrice != null;
    }
}
