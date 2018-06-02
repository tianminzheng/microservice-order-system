package com.tianyalan.product.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Service;

import com.tianyalan.product.model.Product;
import com.tianyalan.product.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	
	@Autowired
    private Tracer tracer;
	
	public Product getProductByCode(String productCode) {
		
		/* 使用Mock数据
		Product product = new Product();
		product.setDescription("New Book For Microservie By Tianyalan");
		product.setId(1L);
		product.setPrice(100F);
		product.setProductCode("001");
		product.setProductName("Microservie Practices");
		
		return product;
		*/
		
		Span newSpan = tracer.createSpan("getProductByCode");
		
		try {			
			return productRepository.findByProductCode(productCode);   
        }
        finally{
          newSpan.tag("peer.service", "database");
          newSpan.logEvent(org.springframework.cloud.sleuth.Span.CLIENT_RECV);
          tracer.close(newSpan);
        }
	}
}
