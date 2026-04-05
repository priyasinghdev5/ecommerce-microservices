package com.ecommerce.product_service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StocksDTO {
    private String id;
    private String productId;
    private Integer quantity;
}
