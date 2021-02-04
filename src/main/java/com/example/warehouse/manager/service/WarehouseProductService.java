package com.example.warehouse.manager.service;

import com.example.warehouse.manager.domain.Article;
import com.example.warehouse.manager.domain.ProductCatalogue;
import com.example.warehouse.manager.domain.Inventory;
import com.example.warehouse.manager.domain.Products;
import com.example.warehouse.manager.exception.JsonParseOrProcessingException;
import com.example.warehouse.manager.repository.WarehouseProductRepository;
import com.example.warehouse.manager.repository.WarehouseInventoryRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class WarehouseProductService {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    @Autowired
    WarehouseProductRepository productRepository;
    @Autowired
    WarehouseInventoryRepository inventoryRepository;

    /**
     * readProductFile to populate inventory
     *
     * @param file from temp location
     * @return String JSON response
     */
    public String loadProductFile(MultipartFile file) throws IOException {
        // read json and write to db/cache
        Path tempFile = Files.createTempFile(null, null);
        // write a line
        Files.write(tempFile, file.getBytes());
        BufferedReader reader = Files.newBufferedReader(tempFile);
        try {
            ProductCatalogue productCatalogue = MAPPER.readValue(file.getBytes(), ProductCatalogue.class);
            return productRepository.save(productCatalogue);
        }catch (JsonParseException|JsonMappingException jsonException){
            throw new JsonParseOrProcessingException("File could not be parsed or mapped to object");
        }finally {
            Files.delete(tempFile);
        }



    }

    /**
     * getAllProducts from db/cache
     *
     * @return Json String object of Products
     * @throws JsonProcessingException if mapping is not possible
     */
    public String getAllProducts() throws JsonProcessingException {
        List<Products> productList = productRepository.findAll();
        for (Products product : productList) {
            for (Article art : product.getArticle()) {
                Inventory inv = (Inventory) inventoryRepository.findById(art.getArtId());
                if(inv !=null)
                art.setQuantity(inv.getStock());
            }
        }
        return MAPPER.writeValueAsString(productList);
    }

    /**
     * updateProducts in case of sale or removal
     *
     * @param product Object as JSON
     * @return String response
     * @throws JsonProcessingException if mapping is not possible
     */
    public String updateProducts(Products product) throws JsonProcessingException {
        String response="No date to update";
            for (Article art : product.getArticle()) {
                Inventory inv = (Inventory) inventoryRepository.findById(art.getArtId());
                if (inv != null) {
                    inv.setStock(art.getQuantity());
                    response = inventoryRepository.update(inv);
                }
            }
        return response;
    }
}
