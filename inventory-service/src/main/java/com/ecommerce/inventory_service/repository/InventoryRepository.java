package com.ecommerce.inventory_service.repository;

import com.ecommerce.inventory_service.model.Stocks;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
public interface InventoryRepository extends MongoRepository<Stocks, String> {
    public Stocks findByProductId(String productId);
}
