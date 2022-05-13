package goit.javacore.hw2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDTO {
    String name;
    Double price;
    Integer saleCount;
    Double salePrice;
}
