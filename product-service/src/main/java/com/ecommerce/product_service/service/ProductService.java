package com.ecommerce.product_service.service;

import com.ecommerce.product_service.model.Product;
import com.ecommerce.product_service.model.dto.ProductDetailDTO;
import com.ecommerce.product_service.model.dto.ProductResponse;
import com.ecommerce.product_service.model.dto.StocksDTO;
import com.ecommerce.product_service.repository.ProductRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class ProductService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductService.class);

    private final WebClient webClient;

    public ProductService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://INVENTORY-SERVICE").build();
    }

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ProductDetailCacheService productDetailCacheService;

    public Product addProduct(Product product) {
        return repository.save(product);
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public void deleteProduct(String id) {
        repository.deleteById(id);
    }

    @Retry(name = "inventoryService")
    @CircuitBreaker(name = "inventoryService", fallbackMethod = "inventoryFallback")
    @TimeLimiter(name = "inventoryService")
    private Integer getInventory(String id) {
        return webClient.get()
                .uri("/inventory/" + id)
                .retrieve()
                .bodyToMono(StocksDTO.class)
                .map(StocksDTO::getQuantity)
                .block();
    }

    public Integer inventoryFallback(String id, Throwable ex) {
        LOG.error("Inventory service failed for product {}. Error: {}", id, ex.getMessage());
        return 0;
    }

    public ProductResponse getProductWithDetails(String id) {
        Product product = repository.findById(id).orElseThrow();
        ProductDetailDTO detail = productDetailCacheService.getProductDetail(id);
        Integer quantity = getInventory(id);
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setProductDetailDTO(detail);
        response.setQuantity(quantity);
        return response;
    }
}
