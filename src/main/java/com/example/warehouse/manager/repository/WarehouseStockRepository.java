package com.example.warehouse.manager.repository;

import com.example.warehouse.manager.domain.Inventory;
import com.example.warehouse.manager.domain.Stock;
import com.example.warehouse.manager.util.Constant;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WarehouseStockRepository {

    private HashOperations hashOperations;

    private RedisTemplate redisTemplate;

    public WarehouseStockRepository(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = this.redisTemplate.opsForHash();
    }

    public String save(Stock stock) {
        for (Inventory i : stock.getInventory()) {
            hashOperations.putIfAbsent(Constant.STOCK, i.getArtId(), i);
        }
        return "Inventory uploaded successfully";
    }

    public List findAll() {
        return hashOperations.values(Constant.STOCK);
    }

    public Object findById(Long id) {
        return hashOperations.get(Constant.STOCK, id.longValue());
    }

    public String update(Inventory inventory) {
        hashOperations.put(Constant.STOCK, inventory.getArtId(), inventory);
        return "Stocks Updated";
    }

    //Not In Use
    public String delete(Long id) {
        hashOperations.delete(Constant.STOCK, id);
        return "Stocks DELETED";
    }

}