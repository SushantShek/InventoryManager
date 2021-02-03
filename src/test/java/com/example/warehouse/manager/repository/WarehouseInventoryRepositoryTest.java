package com.example.warehouse.manager.repository;

import com.example.warehouse.manager.configuration.TestRedisConfiguration;
import com.example.warehouse.manager.domain.Inventory;
import com.example.warehouse.manager.domain.InventoryStock;
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
class WarehouseInventoryRepositoryTest {

    @Autowired
    private WarehouseInventoryRepository inventoryRepository;

    @Test
    void save() {
        Inventory inventory = new Inventory(1L, "test", 7L);
        List<Inventory> lst = new ArrayList<>();
        lst.add(inventory);
        InventoryStock stock = new InventoryStock(lst);
        String result = inventoryRepository.save(stock);
        assertEquals("Inventory uploaded successfully", result );
    }

    @Test
    void findAll() {
        save();
        List<Inventory> lst = inventoryRepository.findAll();
        assertTrue( lst.size() >0);

    }

    @Test
    void findById() {
        save();
        Inventory lst = (Inventory) inventoryRepository.findById(1L);
        assertEquals(1, lst.getArtId());

    }

    @Test
    void update() {
        Inventory inventory = new Inventory(1L, "test2", 11L);
        String response = inventoryRepository.update(inventory);
        assertNotNull(response);
        Inventory lst = (Inventory) inventoryRepository.findById(1L);
        assertEquals(1L, lst.getArtId());
    }
}