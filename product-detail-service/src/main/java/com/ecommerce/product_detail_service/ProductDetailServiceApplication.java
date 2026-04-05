package com.ecommerce.product_detail_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ProductDetailServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductDetailServiceApplication.class, args);
	}

}
