package com.ecommerce.product_service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailDTO implements Serializable {
    String productId;
    String size;
    String price;
    String category;
}
