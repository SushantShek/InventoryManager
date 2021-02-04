package com.example.warehouse.manager.service;

import com.example.warehouse.manager.domain.InventoryStock;
import com.example.warehouse.manager.exception.JsonParseOrProcessingException;
import com.example.warehouse.manager.repository.WarehouseInventoryRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class WarehouseInventoryService {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Gson GSON = new Gson();

    @Autowired
    WarehouseInventoryRepository stockRepository;


    /**
     * loadInventoryFile from http
     *
     * @param file located in temp folder
     * @return String of the content of file
     * @throws IOException file reading and writting
     */
    public String loadInventoryFile(MultipartFile file) throws IOException {
        // read json and write to db/cache
        Path tempFile = Files.createTempFile(null, null);
        // write a line
        Files.write(tempFile, file.getBytes());
        BufferedReader reader = Files.newBufferedReader(tempFile);

        try {
            InventoryStock inventoryStock = MAPPER.readValue(reader, InventoryStock.class);
            return stockRepository.save(inventoryStock);
        } catch (JsonParseException | JsonMappingException jsonException) {
            throw new JsonParseOrProcessingException("File could not be parsed or mapped to object");
        }finally {
            Files.delete(tempFile);
        }
    }

    /**
     * find all inventory item
     *
     * @return all inventory item as JSON String
     * @throws JsonProcessingException if value cannot be mapped to Object
     */
    public String getAllInventory() throws JsonProcessingException {
        return MAPPER.writeValueAsString(stockRepository.findAll());
    }

    /**
     * Get one inventory item by ID
     *
     * @param id of inventory
     * @return JSON String of inventory item
     * @throws JsonProcessingException if value cannot be mapped to Object
     */
    public String getInventoryById(long id) throws JsonProcessingException {
        return MAPPER.writeValueAsString(stockRepository.findById(id));
    }
}
