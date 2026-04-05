package com.ecommerce.product_detail_service.repository;

import com.ecommerce.product_detail_service.model.ProductDetail;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailRepository extends MongoRepository<ProductDetail, String> {

    ProductDetail findByProductId(String productId);

}
