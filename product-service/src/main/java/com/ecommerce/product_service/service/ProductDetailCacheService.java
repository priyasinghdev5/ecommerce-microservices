package com.ecommerce.product_service.service;

import com.ecommerce.product_service.model.dto.ProductDetailDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ProductDetailCacheService {
    private static final Logger LOG = LoggerFactory.getLogger(ProductDetailCacheService.class);


    private final WebClient webClient;

    public ProductDetailCacheService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://PRODUCT-DETAIL-SERVICE").build();
    }

    @Cacheable(value = "productDetails", key = "#id")
    public ProductDetailDTO getProductDetail(String id) {

        LOG.info("Calling PRODUCT-DETAIL-SERVICE (Not from cache)");

        return webClient.get()
                .uri("/product-detail/" + id)
                .retrieve()
                .bodyToMono(ProductDetailDTO.class)
                .block();
    }
}