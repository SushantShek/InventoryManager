package com.example.warehouse.manager.controller;

import com.example.warehouse.manager.domain.Products;
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
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            String response = inventoryService.loadInventoryFile(file);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            log.error("Exception in loadInventory " + e);
            return new ResponseEntity<>("Some exception while saving the data", HttpStatus.BAD_REQUEST);
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
            return new ResponseEntity<>("Some exception while parsing JSON", HttpStatus.BAD_REQUEST);
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
            return new ResponseEntity<>("Some exception while parsing JSON", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "/inventory/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> removeInventoryForID(@PathVariable long id) {
        try {
            String response =inventoryService.removeInventory(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception in removeInventoryForID " + e);
            return new ResponseEntity<>("Some exception while parsing JSON", HttpStatus.BAD_REQUEST);
        }
    }

    //***** Product Article request methods and Mappings *****

    /**
     * Upload product data from file
     *
     * @param file Inventory.json
     * @return Update confirmation
     */
    @PostMapping(path = "/products")
    public ResponseEntity<String> loadProduct(@RequestPart("file") MultipartFile file) {
        if (null == file.getOriginalFilename()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            String response = productService.loadProductFile(file);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            log.error("Exception in loadProduct " + e);
            return new ResponseEntity<>("Some exception while saving the data", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * findAllProducts
     *
     * @return JSon for all products as String
     */
    @GetMapping(path = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> findAllProducts() {

        try {
            String response = productService.getAllProducts();
            if (response.isEmpty() || response.equals(Constant.EMPTY_JSON)) {
                return new ResponseEntity<>("Products list is Empty ", HttpStatus.OK);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            log.error("Exception in findAllProducts " + e);
            return new ResponseEntity<>("Some exception while parsing JSON", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * findAllProducts
     *
     * @return JSon for all products as String
     */
    @PutMapping(path = "/products", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> findAllProducts(@RequestBody Products products) {

        try {
            String response = productService.updateProducts(products);
            if (response.isEmpty() || response.equals(Constant.EMPTY_JSON)) {
                return new ResponseEntity<>("Products not present ", HttpStatus.OK);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            log.error("Exception in findAllProducts " + e);
            return new ResponseEntity<>("Some exception while parsing JSON", HttpStatus.BAD_REQUEST);
        }
    }


}
