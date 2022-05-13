package goit.javacore.hw2.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import goit.javacore.hw2.dto.ProductDTO;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductsRepository {
    public static final String PRODUCTS_FILE_PATH = "./src/main/java/goit/javacore/hw2/storage/products.json";

    Map<String, ProductDTO> products;

    public ProductsRepository() {
        getProductsFromFile();
    }

    private void getProductsFromFile() {
        Gson gson = new Gson();
        Type type = new TypeToken<List<ProductDTO>>() {
        }.getType();

        try {
            FileReader reader = new FileReader(PRODUCTS_FILE_PATH);
            List<ProductDTO> productsList = gson.fromJson(new JsonReader(reader), type);

            products = productsList.stream().collect(Collectors.toMap(ProductDTO::getName, item -> item));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(String.format("File '%s' not found or broken", PRODUCTS_FILE_PATH));
        }
    }

    ProductDTO findOneByName(String name) {
        return products.get(name);
    }
}
