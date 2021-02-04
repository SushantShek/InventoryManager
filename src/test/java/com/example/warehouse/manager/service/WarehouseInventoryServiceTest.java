package com.example.warehouse.manager.service;

import com.example.warehouse.manager.domain.Inventory;
import com.example.warehouse.manager.domain.InventoryStock;
import com.example.warehouse.manager.repository.WarehouseInventoryRepository;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@RunWith(SpringRunner.class)
@SpringBootTest
class WarehouseInventoryServiceTest {

    private final String INVENTORY_STOCK = "{\n" +
            "  \"inventory\": [\n" +
            "    {\n" +
            "      \"art_id\": \"1\",\n" +
            "      \"name\": \"table top\",\n" +
            "      \"stock\": \"1\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";
    @MockBean
    WarehouseInventoryRepository repository;
    @Autowired
    WarehouseInventoryService service;
    private ObjectMapper mapper = new ObjectMapper();
    private Inventory inventory;
    private InventoryStock stock;

    @Before
    public void setUp() {
        inventory = new Inventory(1L, "service", 10L);
        stock = new InventoryStock(Arrays.asList(inventory));
    }

    @Test
    void loadInventoryFile_multipart() throws IOException {
        Mockito.when(repository.save(any()))
                .thenReturn("Inventory Saved");

        MultipartFile multipartFile = new MockMultipartFile("sourceFile.tmp", INVENTORY_STOCK.getBytes());
        String response = service.loadInventoryFile(multipartFile);
        assertEquals("Inventory Saved", response);
    }

    @Test
    void getAllInventory() throws JsonProcessingException {
        InventoryStock inventoryStock = mapper.readValue(INVENTORY_STOCK, InventoryStock.class);
        Mockito.when(repository.findAll())
                .thenReturn(inventoryStock.getInventory());
        String inventory = service.getAllInventory();
        assertNotNull(inventory);
        assertEquals("[{\"art_id\":1,\"name\":\"table top\",\"stock\":1}]",inventory);
    }

    @Test
    void getInventoryById() throws JsonProcessingException {
        InventoryStock inventoryStock = mapper.readValue(INVENTORY_STOCK, InventoryStock.class);
        Mockito.when(repository.findById(anyLong()))
                .thenReturn(inventoryStock.getInventory());
        String invn = service.getInventoryById(1L);
        assertEquals("[{\"art_id\":1,\"name\":\"table top\",\"stock\":1}]",invn);
    }
}