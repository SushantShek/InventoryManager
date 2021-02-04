package com.example.warehouse.manager.controller;

import com.example.warehouse.manager.domain.Products;
import com.example.warehouse.manager.exception.FileProcessingException;
import com.example.warehouse.manager.exception.JsonParseOrProcessingException;
import com.example.warehouse.manager.service.WarehouseInventoryService;
import com.example.warehouse.manager.service.WarehouseProductService;
import com.example.warehouse.manager.util.Constant;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api")
public class WarehouseController {

    @Autowired
    WarehouseInventoryService inventoryService;
    @Autowired
    WarehouseProductService productService;

    /**
     * Upload inventory data from file
     *
     * @param file Inventory.json
     * @return Update confirmation
     */
    @PostMapping(path = "/inventory")
    public ResponseEntity<String> loadInventory(@RequestPart("file") MultipartFile file) {
        if (null == file.getOriginalFilename()) {
            return new ResponseEntity<>("File not found", HttpStatus.BAD_REQUEST);
        }
        if (!file.getOriginalFilename().endsWith(".json")) {
            return new ResponseEntity<>("Invalid File Format. Expected JSON", HttpStatus.BAD_REQUEST);
        }
        try {
            String response = inventoryService.loadInventoryFile(file);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            log.error("Exception in loadInventory " + e);
            throw new FileProcessingException("Some exception while saving the data");
        }
    }

    /**
     * Get all inventory items from cache
     *
     * @return Inventory stock object as String
     */
    @GetMapping(path = "/inventory", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> findAllInventory() {

        try {
            String response = inventoryService.getAllInventory();
            if (response.isEmpty() || response.equals(Constant.EMPTY_JSON)) {
                return new ResponseEntity<>("Inventory is Empty ", HttpStatus.OK);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            log.error("Exception in findAllInventory " + e);
            throw new JsonParseOrProcessingException("Some exception while parsing JSON");
        }
    }

    /**
     * Get specific inventory item from artID
     *
     * @param id Art_ID from the inventory
     * @return Inventory object as String
     */
    @GetMapping(path = "/inventory/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getInventoryForID(@PathVariable long id) {
        try {
            String response = inventoryService.getInventoryById(id);
            if (response.isEmpty() || response.equals(Constant.EMPTY_JSON)) {
                return new ResponseEntity<>("No values available for Id " + id, HttpStatus.OK);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            log.error("Exception in getInventoryForID " + e);
            throw new JsonParseOrProcessingException("Some exception while parsing JSON");
        }
    }
    //***** Product Article request methods and Mappings *****

    /**
     * Upload product data from file
     *
     * @param file Inventory.json
     * @return Update confirmation
     */
    @PostMapping(path = "/product")
    public ResponseEntity<String> loadProduct(@RequestPart("file") MultipartFile file) {
        if (null == file.getOriginalFilename()) {
            return new ResponseEntity<>("File not found", HttpStatus.BAD_REQUEST);
        }
        if (!file.getOriginalFilename().endsWith(".json")) {
            throw new FileProcessingException("Invalid File Format. Expected JSON");
        }
        try {
            String response = productService.loadProductFile(file);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            log.error("Exception in loadProduct " + e);
            throw new FileProcessingException("Some exception while saving the data");
        }
    }

    /**
     * findAllProducts
     *
     * @return JSon for all products as String
     */
    @GetMapping(path = "/product", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> findAllProducts() {

        try {
            String response = productService.getAllProducts();
            if (response.isEmpty() || response.equals(Constant.EMPTY_JSON)) {
                return new ResponseEntity<>("Products list is Empty ", HttpStatus.OK);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            log.error("Exception in findAllProducts " + e);
            throw new JsonParseOrProcessingException("Some exception while parsing JSON");
        }
    }

    /**
     * findAllProducts
     *
     * @return Json for all products as String
     */
    @PutMapping(path = "/product", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateProducts(@RequestBody Products products) {

        try {
            String response = productService.updateProducts(products);
            if (response.isEmpty() || response.equals(Constant.EMPTY_JSON)) {
                return new ResponseEntity<>("Products not present ", HttpStatus.OK);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            log.error("Exception in findAllProducts " + e);
            throw new JsonParseOrProcessingException("Some exception while parsing JSON");
        }
    }
}
