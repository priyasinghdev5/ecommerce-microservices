package com.ecommerce.product_detail_service.controller;

import com.ecommerce.product_detail_service.model.ProductDetail;
import com.ecommerce.product_detail_service.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product-detail")
public class ProductDetailController {

    @Autowired
    ProductDetailService productDetailService;


    @GetMapping("/{productId}")
    public ProductDetail getProductDetails(@PathVariable String productId) {
        return productDetailService.getProductDetails(productId);
    }

    @PostMapping
    public ProductDetail saveProductDetails(@RequestBody ProductDetail productDetail) {
        return productDetailService.saveProductDetails(productDetail);
    }

    @DeleteMapping("/{productId}")
    public void deleteProductDetail(@PathVariable String productId) {
        productDetailService.deleteProductDetail(productId);
    }
}
