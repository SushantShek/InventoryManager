package com.example.warehouse.manager.repository;

import com.example.warehouse.manager.domain.ProductCatalogue;
import com.example.warehouse.manager.domain.Products;
import com.example.warehouse.manager.util.Constant;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WarehouseProductRepository {
    private HashOperations hashOperations;

    private RedisTemplate redisTemplate;

    public WarehouseProductRepository(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = this.redisTemplate.opsForHash();
    }

    public String save(ProductCatalogue productCatalogue) {
        for (Products p : productCatalogue.getProducts()) {
            hashOperations.putIfAbsent(Constant.PRODUCT, p.getName(), p);
        }
        return "Products uploaded";
    }

    public List<Products> findAll() {
        return hashOperations.values(Constant.PRODUCT);
    }

    public String updateProduct(Products product) {
        hashOperations.put(Constant.PRODUCT, product.getName(), product);
        return "Product Updated Successfully";
    }
}
