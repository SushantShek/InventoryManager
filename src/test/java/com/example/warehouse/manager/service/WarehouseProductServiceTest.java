package com.example.warehouse.manager.service;

import com.example.warehouse.manager.domain.Article;
import com.example.warehouse.manager.domain.Inventory;
import com.example.warehouse.manager.domain.ProductCatalogue;
import com.example.warehouse.manager.domain.Products;
import com.example.warehouse.manager.repository.WarehouseInventoryRepository;
import com.example.warehouse.manager.repository.WarehouseProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@RunWith(SpringRunner.class)
@SpringBootTest
class WarehouseProductServiceTest {
    private final String PRODUCT_CATALOUGE = "{\n" +
            "  \"products\": [\n" +
            "    {\n" +
            "      \"name\": \"Dining Chair\",\n" +
            "      \"contain_articles\": [\n" +
            "        {\n" +
            "          \"art_id\": \"1\",\n" +
            "          \"amount_of\": \"4\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"art_id\": \"2\",\n" +
            "          \"amount_of\": \"8\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"art_id\": \"3\",\n" +
            "          \"amount_of\": \"1\"\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  ]\n" +
            "}\n";
    private final String INVENTORY_STOCK =
            "    {\n" +
            "      \"art_id\": \"1\",\n" +
            "      \"name\": \"table top\",\n" +
            "      \"stock\": \"1\"\n" +
            "    }\n" ;

    @MockBean
    WarehouseProductRepository repository;
    @MockBean
    WarehouseInventoryRepository inventoryRepository;
    @MockBean
    WarehouseInventoryService inventoryService;

    @Autowired
    WarehouseProductService service;
    private ObjectMapper mapper = new ObjectMapper();
    private Products products;
    private Article article;
    private ProductCatalogue catalogue;

    @Before
    public void setUp() {

        catalogue = new ProductCatalogue(Arrays.asList(products));
    }

    @Test
    void loadProductFile() throws IOException {
        Mockito.when(repository.save(any()))
                .thenReturn("Product Saved");

        MultipartFile multipartFile = new MockMultipartFile("sourceFile.tmp", PRODUCT_CATALOUGE.getBytes());
        String response = service.loadProductFile(multipartFile);
        assertEquals("Product Saved", response);
    }

    @Test
    void getAllProducts() throws JsonProcessingException {
        ProductCatalogue productCatalogue = mapper.readValue(PRODUCT_CATALOUGE, ProductCatalogue.class);
        Mockito.when(repository.findAll())
                .thenReturn(productCatalogue.getProducts());
        String products = service.getAllProducts();
        assertNotNull(products);
        assertSame(true,products.contains("Dining Chair"));
    }

    @Test
    void updateProducts() throws JsonProcessingException {
        Inventory inventory = mapper.readValue(INVENTORY_STOCK,Inventory.class);
        article = new Article(2L, 5L, 10L);
        products = new Products("test", Arrays.asList(article));
        Mockito.when(repository.updateProduct(any()))
                .thenReturn("products.getName()");
        String response = service.updateProducts(products);
        assertNotNull(response);
    }
}