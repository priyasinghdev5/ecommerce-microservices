package com.ecommerce.product_detail_service.service;

import com.ecommerce.product_detail_service.model.ProductDetail;
import com.ecommerce.product_detail_service.repository.ProductDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class ProductDetailService {

    @Autowired
    ProductDetailRepository productDetailRepository;

    public ProductDetail getProductDetails(String productId) {
        return productDetailRepository.findByProductId(productId);
    }

    public ProductDetail saveProductDetails(ProductDetail productDetail){
        return productDetailRepository.save(productDetail);
    }

    public void deleteProductDetail(String productId){
        productDetailRepository.deleteById(productId);
    }
}
