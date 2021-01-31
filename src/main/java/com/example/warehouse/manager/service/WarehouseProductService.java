package com.example.warehouse.manager.service;

import com.example.warehouse.manager.domain.Article;
import com.example.warehouse.manager.domain.Catalogue;
import com.example.warehouse.manager.domain.Inventory;
import com.example.warehouse.manager.domain.Products;
import com.example.warehouse.manager.repository.WarehouseCatalogueRepository;
import com.example.warehouse.manager.repository.WarehouseStockRepository;
import com.example.warehouse.manager.util.MultipartFileReader;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class WarehouseProductService {
    private static final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    WarehouseCatalogueRepository productRepository;
    @Autowired
    WarehouseStockRepository stockRepository;

    /**
     * readProductFile to populate inventory
     *
     * @param file from temp location
     * @return String JSON response
     */
    public String loadProductFile(MultipartFile file) throws IOException {
        // read json and write to db/cache
        String fileContentString = MultipartFileReader.saveFileAndGetContent(file);
        Catalogue catalogue = mapper.readValue(fileContentString, Catalogue.class);

        return productRepository.save(catalogue);
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
                Inventory inv = (Inventory) stockRepository.findById(art.getArtId());
                art.setQuantity(inv.getStock());
            }
        }
        return mapper.writeValueAsString(productList);
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
                Inventory inv = (Inventory) stockRepository.findById(art.getArtId());
                if (inv != null) {
                    inv.setStock(art.getQuantity());
                    response = stockRepository.update(inv);
                }
            }
        return response;
    }
}
