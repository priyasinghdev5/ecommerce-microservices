package com.ecommerce.inventory_service.controller;

import com.ecommerce.inventory_service.model.Stocks;
import com.ecommerce.inventory_service.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    InventoryService inventoryService;


    @PostMapping("/add")
    public Stocks addStock(@RequestBody Stocks stocks) {
        return inventoryService.addStock(stocks);
    }

    @GetMapping("/{productId}")
    public Stocks getStock(@PathVariable String productId) {
        return inventoryService.getStock(productId);
    }


}
