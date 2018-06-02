package com.tianyalan.orders.clients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Component;

import com.tianyalan.orders.model.Product;

@Component
public class ProductClient {
	
    @Autowired
    OAuth2RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(ProductClient.class);

    public Product getProduct(String productCode){

    	logger.debug("Get product: {}", productCode);

        ResponseEntity<Product> restExchange =
                restTemplate.exchange(
                        "http://localhost:5555/api/product/v1/products/{productCode}", 
                        HttpMethod.GET,
                        null, Product.class, productCode);
         
        Product product = restExchange.getBody();

        return product;
    }
}