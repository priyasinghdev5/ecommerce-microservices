package com.ecommerce.product_detail_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "product_details")
public class ProductDetail {
    @Id
    String id;
    String productId;
    String size;
    String price;
    String category;
}
