package com.ecommerce.order_service.model;

import com.ecommerce.order_service.model.dtos.CartItemDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private String id;
    private String userId;
    private List<CartItemDTO> items;
    private Double totalAmount;
    private String status;
}