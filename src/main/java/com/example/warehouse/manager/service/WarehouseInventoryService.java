package com.example.warehouse.manager.service;

import com.example.warehouse.manager.domain.Inventory;
import com.example.warehouse.manager.domain.InventoryStock;
import com.example.warehouse.manager.repository.WarehouseInventoryRepository;
import com.example.warehouse.manager.util.MultipartFileReader;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class WarehouseInventoryService {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    WarehouseInventoryRepository stockRepository;

    /**
     * loadInventoryFile from http
     *
     * @param uri file located in temp folder
     * @return String of the content of file
     * @throws IOException file reading and writting
     */
    public String loadInventoryFile(MultipartFile uri) throws IOException {
        // read json and write to db/cache
        String fileContentString = MultipartFileReader.saveFileAndGetContent(uri);

        InventoryStock inventoryStock = mapper.readValue(fileContentString, InventoryStock.class);

        return stockRepository.save(inventoryStock);
    }

    /**
     * find all inventory item
     *
     * @return all inventory item as JSON String
     * @throws JsonProcessingException if value cannot be mapped to Object
     */
    public String getAllInventory() throws JsonProcessingException {
        return mapper.writeValueAsString(stockRepository.findAll());
    }

    /**
     * Get one inventory item by ID
     *
     * @param id of inventory
     * @return JSON String of inventory item
     * @throws JsonProcessingException if value cannot be mapped to Object
     */
    public String getInventoryById(long id) throws JsonProcessingException {
        return mapper.writeValueAsString(stockRepository.findById(id));
    }

    public String removeInventory(long id) {
        InventoryStock inventoryStock = (InventoryStock) stockRepository.findAll().get(0);
        List<Inventory> filterByArtID = inventoryStock.getInventory()
                .stream()
                .filter(c -> c.getArtId() != id)
                .collect(Collectors.toList());
        inventoryStock.setInventory(filterByArtID);
        return stockRepository.save(inventoryStock);
    }
}
