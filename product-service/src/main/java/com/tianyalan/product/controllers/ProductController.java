package com.tianyalan.product.controllers;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tianyalan.product.model.Product;
import com.tianyalan.product.services.ProductService;

@RestController
@RequestMapping(value="v1/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    
	@Autowired
	ProductService productService;
	
    @Autowired
    private HttpServletRequest request;
    	
	@RequestMapping(value = "/{productCode}", method = RequestMethod.GET)
    public Product getProduct(@PathVariable String productCode) {		

		logger.info("Get product by code: {} from port: {}", productCode, request.getServerPort());
		
		Product product = productService.getProductByCode(productCode);
    	return product;
    }
}
