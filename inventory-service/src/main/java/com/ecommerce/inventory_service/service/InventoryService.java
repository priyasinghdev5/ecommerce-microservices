package com.ecommerce.inventory_service.service;

import com.ecommerce.inventory_service.model.Stocks;
import com.ecommerce.inventory_service.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


@Service
public class InventoryService {

    @Autowired
    InventoryRepository inventoryRepository;

    public Stocks addStock(Stocks stocks) {
        return inventoryRepository.save(stocks);
    }

    public Stocks getStock(@PathVariable String productId) {
        return inventoryRepository.findByProductId(productId);
    }


}
