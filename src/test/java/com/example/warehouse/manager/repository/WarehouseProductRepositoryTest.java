package com.example.warehouse.manager.repository;

import com.example.warehouse.manager.configuration.TestRedisConfiguration;
import com.example.warehouse.manager.domain.Article;
import com.example.warehouse.manager.domain.ProductCatalogue;
import com.example.warehouse.manager.domain.Products;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestRedisConfiguration.class)
class WarehouseProductRepositoryTest {

    @Autowired
    WarehouseProductRepository productRepository;

    @Test
    void save_product_catalogue() {
        List<Article> lst = new ArrayList<>();
        List<Products> prod = new ArrayList<>();
        Article art = new Article(1L, 2L, 3L);
        lst.add(art);
        prod.add(new Products("test", lst));
        ProductCatalogue catalogue = new ProductCatalogue(prod);

        String result = productRepository.save(catalogue);
        assertNotNull(result);
        assertEquals(result, "Products uploaded");
    }

    @Test
    void findAll_product_as_list() {
        save_product_catalogue();
        List<Products> lst = productRepository.findAll();
        assertTrue( lst.size() >0);
        assertEquals("test", lst.get(0).getName());

    }

    @Test
    void updateProduct() {
        save_product_catalogue();
        List<Article> lst = new ArrayList<>();
        lst.add(new Article(1L, 2L, 3L));
        Products product = new Products("test", lst);
        String response = productRepository.updateProduct(product);
        assertNotNull(response);
        assertEquals(response, "Product Updated Successfully");
    }
}